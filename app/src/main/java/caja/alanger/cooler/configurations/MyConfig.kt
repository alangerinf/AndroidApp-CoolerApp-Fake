package jp.takke.cpustats

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

internal class MyConfig {

    private val TAG : String = "MyConfig"

    // 更新間隔
    var intervalMs = (C.PREF_DEFAULT_UPDATE_INTERVAL_SEC * 1000).toLong()

    // 通知
    var showUsageNotification = true
    var showFrequencyNotification = false

    // CPU使用率通知のアイコンモード
    var coreDistributionMode = C.CORE_DISTRIBUTION_MODE_2ICONS

    /**
     * 設定のロード
     */
    fun loadSettings(context: Context) {

        Log.i(TAG,"load settings")

        val pref = PreferenceManager.getDefaultSharedPreferences(context)

        // 更新間隔
        val updateIntervalSec = pref.getString(C.PREF_KEY_UPDATE_INTERVAL_SEC, "" + C.PREF_DEFAULT_UPDATE_INTERVAL_SEC)
        try {
            intervalMs = (java.lang.Double.parseDouble(updateIntervalSec.toString()) * 1000.0).toInt().toLong()
            Log.i(TAG," interval[" + intervalMs + "ms]")
        } catch (e: NumberFormatException) {
            Log.e(TAG,e.toString());
        }

        // CPU使用率通知
        showUsageNotification = pref.getBoolean(C.PREF_KEY_SHOW_USAGE_NOTIFICATION, true)

        // クロック周波数通知
        showFrequencyNotification = pref.getBoolean(C.PREF_KEY_SHOW_FREQUENCY_NOTIFICATION, false)

        // CPU使用率通知のアイコンモード
        try {
            val s = pref.getString(C.PREF_KEY_CORE_DISTRIBUTION_MODE, "" + C.CORE_DISTRIBUTION_MODE_1ICON_SORTED)
            coreDistributionMode = Integer.parseInt(s.toString())
        } catch (e: NumberFormatException) {
            Log.e(TAG,e.toString());
        }

    }


}
