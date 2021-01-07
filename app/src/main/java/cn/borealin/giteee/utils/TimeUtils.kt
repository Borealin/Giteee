/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午6:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午6:38
 */

package cn.borealin.giteee.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {
    fun fromTimeToRemaining(timeString: String): String {
        val date = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val diff = Duration.between(date, getCurrentDate())
        return when {
            diff.toDays() >= 1 -> {
                "${diff.toDays()} day${if (diff.toDays() > 1) "s" else ""} ago"
            }
            diff.toHours() >= 1 -> {
                "${diff.toHours()} hour${if (diff.toHours() > 1) "s" else ""} ago"
            }
            else -> {
                "${diff.toMinutes()} hour${if (diff.toMinutes() > 1) "s" else ""} ago"
            }
        }
    }

    private fun getCurrentDate(): LocalDateTime {
        return LocalDateTime.now()
    }
}