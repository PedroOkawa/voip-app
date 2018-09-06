package com.okawa.voip.utils

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionUtils {

    fun checkPermissions(context: Context, vararg permissions: String): Boolean {
        permissions.forEach { permission ->
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}