package com.sandesh.fintrack.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun isToday(millis: Long): Boolean {
    val today = LocalDate.now()
    val date = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return date == today
}

@RequiresApi(Build.VERSION_CODES.O)
fun isYesterday(millis: Long): Boolean {
    val yesterday = LocalDate.now().minusDays(1)
    val date = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return date == yesterday
}