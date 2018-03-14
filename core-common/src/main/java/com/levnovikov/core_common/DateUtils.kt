package com.levnovikov.core_common

import java.util.*

/**
 * Author: lev.novikov
 * Date: 14/3/18.
 */

fun equalWithoutTime(date1: Date, date2: Date): Boolean {
    val cal1 = GregorianCalendar().apply { time = date1 }
    val cal2 = GregorianCalendar().apply { time = date2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}