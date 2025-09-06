package com.example.ravengamingnews.utils

import android.content.Context
import android.text.format.DateFormat
import java.util.Date
import kotlin.time.Instant
import kotlin.time.toJavaInstant

/**
 * Convert Instant to formatted date string based on user's locale and preferences
 * e.g. "Jan 1, 2023 5:00 PM"
 * @param content Context to access locale and preferences
 * @return Formatted date string
 */
fun Instant.toFormattedString(content: Context): String {
    val javaDateTime = this.toJavaInstant()
    val date = Date.from(javaDateTime)
    // Get date format based on locale
    val dateFormat = DateFormat.getMediumDateFormat(content)

    // Get time format based on user's preference (24 hr or not etc.)
    val timeFormat = DateFormat.getTimeFormat(content)

    return "${dateFormat.format(date)} ${timeFormat.format(date)}"
}
