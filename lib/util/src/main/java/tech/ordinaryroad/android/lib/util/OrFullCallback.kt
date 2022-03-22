package tech.ordinaryroad.android.lib.util

import com.blankj.utilcode.util.PermissionUtils

interface OrFullCallback : PermissionUtils.FullCallback {
    override fun onGranted(granted: MutableList<String>)

    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
        // ignore
    }
}