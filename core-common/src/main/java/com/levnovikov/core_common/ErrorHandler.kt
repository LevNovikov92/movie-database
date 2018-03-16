package com.levnovikov.core_common

import android.util.Log

/**
 * Author: lev.novikov
 * Date: 16/3/18.
 */

val defaultError = fun (t: Throwable) {
    Log.d(">>>>ERROR", "Report error: ${t.message}") //Report error in fabric.io or similar system
}