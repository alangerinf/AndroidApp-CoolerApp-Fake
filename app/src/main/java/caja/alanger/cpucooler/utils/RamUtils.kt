package caja.alanger.cpucooler.utils

import java.io.IOException
import java.io.RandomAccessFile
import java.text.DecimalFormat
import java.util.regex.Pattern

fun getRamSize(): String {

    var reader: RandomAccessFile? = null
    var load: String? = null
    val twoDecimalForm = DecimalFormat("#.##")
    var totRam = 0.0
    var lastValue = ""
    try {
        reader = RandomAccessFile("/proc/meminfo", "r")
        load = reader!!.readLine()

        // Get the Number value from the string
        val p = Pattern.compile("(\\d+)")
        val m = p.matcher(load)
        var value = ""
        while (m.find()) {
            value = m.group(1)
            // System.out.println("Ram : " + value);
        }
        reader!!.close()

        totRam = java.lang.Double.parseDouble(value)
        // totRam = totRam / 1024;

        val mb = totRam / 1024.0
        val gb = totRam / 1048576.0
        val tb = totRam / 1073741824.0

        if (tb > 1) {
            lastValue = twoDecimalForm.format(tb)+" TB"
        } else if (gb > 1) {
            lastValue = twoDecimalForm.format(gb)+" GB"
        } else if (mb > 1) {
            lastValue = twoDecimalForm.format(mb)+" MB"
        } else {
            lastValue = twoDecimalForm.format(totRam)+" KB"
        }


    } catch (ex: IOException) {
        ex.printStackTrace()
    } finally {
        // Streams.close(reader);
    }

    return lastValue
}