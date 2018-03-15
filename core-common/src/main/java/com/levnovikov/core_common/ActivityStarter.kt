package com.levnovikov.core_common

import android.app.Activity
import android.content.Intent
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */
class ActivityStarter @Inject constructor(
        private val activity: Activity
) {

    fun getIntent(clazz: Class<*>): Intent = Intent(activity, clazz)

    fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }
}