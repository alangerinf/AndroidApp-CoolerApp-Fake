package caja.alanger.cooler.utils

import android.util.Log
import caja.alanger.cooler.models.AllCoreFrequencyInfo
import caja.alanger.cooler.models.CpuInfoCollector
import caja.alanger.cooler.models.OneCpuInfo
import jp.takke.cpustats.C
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.ArrayList



fun takeCpuUsageSnapshot(): ArrayList<OneCpuInfo>? {
    val TAG : String = "takeCpuUsageSnapshot"
    
    // [0] が全体、[1]以降が個別CPU
    val result = ArrayList<OneCpuInfo>()

    try {
        BufferedReader(InputStreamReader(FileInputStream("/proc/stat")), C.READ_BUFFER_SIZE).use { it ->
            it.forEachLine { line ->

                if (!line.startsWith("cpu")) {
                    return@forEachLine
                }
//                  MyLog.i(" load:" + load);

                //     user     nice    system  idle    iowait  irq     softirq     steal
                //cpu  48200 4601 35693 979258 5095 1 855 0 0 0
                //cpu0 26847 1924 25608 212324 2212 1 782 0 0 0
                //cpu1 8371 1003 4180 254096 1026 0 50 0 0 0
                //cpu2 8450 983 3916 252872 1304 0 9 0 0 0
                //cpu3 4532 691 1989 259966 553 0 14 0 0 0

                val tokens = line.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val oci = OneCpuInfo()
                oci.idle = java.lang.Long.parseLong(tokens[4])
                oci.total = (java.lang.Long.parseLong(tokens[1])
                        + java.lang.Long.parseLong(tokens[2])
                        + java.lang.Long.parseLong(tokens[3])
                        + oci.idle
                        + java.lang.Long.parseLong(tokens[5])
                        + java.lang.Long.parseLong(tokens[6])
                        + java.lang.Long.parseLong(tokens[7]))
                result.add(oci)
            }
        }
        return result

    } catch (ex: Exception) {
        Log.e(TAG,ex.toString())
        return null
    }

}
fun calcCpuUsages(currentInfo: ArrayList<OneCpuInfo>?, lastInfo: ArrayList<OneCpuInfo>?): IntArray? {

   val TAG : String = "calcCpuUsages"

    if (currentInfo == null || lastInfo == null) {
        // NPE対策(基本的に発生しないはず。サービスが死んだときにはありうるかな？)
        return null
    }

    val nLast = lastInfo.size
    val nCurr = currentInfo.size
    if (nLast == 0 || nCurr == 0) {
        Log.d(TAG," no info: [$nLast][$nCurr]")
        return null
    }

    // 前回と今回で小さい方の個数で比較する
    // ※Galaxy S II 等で発生するコア数の変動状態でも少ない方のコア数で比較してなるべくCPU使用率を表示する
    val n = if (nLast < nCurr) nLast else nCurr  // min(nLast, nCurr)
    val cpuUsages = IntArray(n)
    for (i in 0 until n) {
        val last = lastInfo[i]
        val curr = currentInfo[i]

        val totalDiff = (curr.total - last.total).toInt()  // 「差」なのでintに収まるはず。
        if (totalDiff > 0) {
            val idleDiff = (curr.idle - last.idle).toInt()
//              final double cpuUsage = 1.0 - (double)idleDiff / totalDiff;
//              cpuUsages[i] = (int)(cpuUsage * 100.0);
            // 高速化のため整数演算とする(切り上げの値になるけどいいでしょう)
            cpuUsages[i] = 100 - idleDiff * 100 / totalDiff

//              MyLog.i(" idle[" + idleDiff + "], total[" + totalDiff + "], " +
//                      "/[" + (100-idleDiff*100/totalDiff) + "], " +
//                      "rate[" + cpuUsages[i] + "], " +
//                      "rate[" + ((1.0-(double)idleDiff/totalDiff) * 100.0) + "]"
//                      );
        } else {
            cpuUsages[i] = 0
        }

//          MyLog.d(" [" + (i == 0 ? "all" : i) + "] : [" + (int)(cpuUsage * 100.0) + "%]" +
//                  " idle[" + idleDiff + "], total[" + totalDiff + "]");
    }

    return cpuUsages
}

fun calcCpuUsagesByCoreFrequencies(fi: AllCoreFrequencyInfo): IntArray {

    val coreCount = fi.freqs.size

    // [0] は全体、[1]～[coreCount] は各コアの CPU 使用率
    val cpuUsages = IntArray(coreCount + 1)

    // 各コアの CPU 使用率を算出する
//        MyLog.i("---");
    for (i in 0 until coreCount) {
        cpuUsages[i + 1] = getClockPercent(fi.freqs[i], fi.minFreqs[i], fi.maxFreqs[i])
//            MyLog.i("calc core[" + i + "] = " + cpuUsages[i+1] + "% (max=" + fi.maxFreqs[i] + ")");
    }

    // 全体の CPU 使用率を算出する
    // TODO big.LITTLE で停止するコアの考慮はしていない
    var freqSum = 0
    var minFreqSum = 0
    var maxFreqSum = 0
    for (i in 0 until coreCount) {
        freqSum += fi.freqs[i]
        minFreqSum += fi.minFreqs[i]
        maxFreqSum += fi.maxFreqs[i]
    }
    cpuUsages[0] = getClockPercent(freqSum, minFreqSum, maxFreqSum)

    return cpuUsages
}

/**
 * クロック周波数の current/min/max から [0, 100] % を算出する
 */
fun getClockPercent(currentFreq: Int, minFreq: Int, maxFreq: Int): Int {
    if (maxFreq - minFreq <= 0) {
        return 0
    }
    return if (maxFreq >= 0) (currentFreq - minFreq) * 100 / (maxFreq - minFreq) else 0
}

fun getActiveCoreIndex(freqs: IntArray): Int {

    var targetCore = 0
    for (i in 1 until freqs.size) {
        if (freqs[i] > freqs[targetCore]) {
            targetCore = i
        }
    }
    return targetCore
}


fun execTask() : IntArray?{

    //-------------------------------------------------
    // CPU クロック周波数の取得
    //-------------------------------------------------
    val fi = AllCoreFrequencyInfo(CpuInfoCollector.calcCpuCoreCount())
    CpuInfoCollector.takeAllCoreFreqs(fi)

    val activeCoreIndex = getActiveCoreIndex(fi.freqs)
    val currentCpuClock = fi.freqs[activeCoreIndex]

    // CPU クロック周波数のmin/max
    val minFreq = fi.minFreqs[activeCoreIndex]
    val maxFreq = fi.maxFreqs[activeCoreIndex]



    //-------------------------------------------------
    // CPU 使用率の取得
    //-------------------------------------------------
    // CPU 使用率の snapshot 取得
    var cpuUsages: IntArray? = null
    if (!mUseFreqForCpuUsage) {
        val currentCpuUsageSnapshot = CpuInfoCollector.takeCpuUsageSnapshot()
        if (currentCpuUsageSnapshot != null) {
            // CPU 使用率の算出
            cpuUsages = calcCpuUsages(currentCpuUsageSnapshot, mLastCpuUsageSnapshot)
            // 今回の snapshot を保存
            mLastCpuUsageSnapshot = currentCpuUsageSnapshot
        } else {
            // 取得できないので fallback する
            mUseFreqForCpuUsage = true
        }
    }
    if (cpuUsages == null) {
        // fallbackモード(Android 8.0以降では /proc/stat にアクセスできないのでコアの周波数からCPU使用率を算出する)
        cpuUsages = calcCpuUsagesByCoreFrequencies(fi)
    }


    //-------------------------------------------------
    // 通知判定
    //-------------------------------------------------
    // 前回と同じ CPU 使用率なら通知しない
    // ※通知アイコンだけなら丸めた値で比較すればいいんだけど通知テキストにCPU使用率%が出るので完全一致で比較する
   // val updated = isUpdated(currentCpuClock, cpuUsages)
    mLastCpuUsages = cpuUsages
    mLastCpuClock = currentCpuClock

    //-------------------------------------------------
    // 通知
    //-------------------------------------------------
    //if (!updated) {

    //} else {
        // ステータスバーの通知
       // mNotificationPresenter.updateNotifications(cpuUsages, currentCpuClock, minFreq, maxFreq, mRequestForeground)
        return cpuUsages;
       // distributeToCallbacks(cpuUsages, fi)
    //}

    mLastExecTask = System.currentTimeMillis()
}

var mLastExecTask : Long =0

// 前回のCPU使用率
private var mLastCpuUsages: IntArray? = null

// CPU使用率を各コアの周波数から算出するモード(Android 8.0以降用)
private var mUseFreqForCpuUsage = false

// 前回の CPU クロック周波数
private var mLastCpuClock = -1


// 前回の収集データ
private var mLastCpuUsageSnapshot: ArrayList<OneCpuInfo>? = null