package caja.alanger.cooler.utils

public fun capitalize(s: String?): String {
    if (s == null || s.length == 0) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        Character.toUpperCase(first) + s.substring(1)
    }
}

fun formatSize(size: Long): String {
    var size = size

    var suffix: String? = null

    if (size >= 1024) {
        suffix = "KB"
        size /= 1024
        if (size >= 1024) {
            suffix = "MB"
            size /= 1024
            if (size >= 1024) {
                suffix = "GB"
                size /= 1024
            }
        }
    }

    val resultBuffer = StringBuilder(java.lang.Long.toString(size))

    var commaOffset = resultBuffer.length - 3
    while (commaOffset > 0) {
        resultBuffer.insert(commaOffset, ',')
        commaOffset -= 3
    }

    if (suffix != null) resultBuffer.append(suffix)
    return resultBuffer.toString()
}