package caja.alanger.cooler.utils

import android.os.Environment
import android.os.StatFs

fun getTotalInternalMemorySize(): String {
    val path = Environment.getDataDirectory()
    val stat = StatFs(path.path)
    val blockSize = stat.blockSizeLong
    val totalBlocks = stat.blockCountLong
    return formatSize(totalBlocks * blockSize)
}
