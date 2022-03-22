package tech.ordinaryroad.android.lib.ui

import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.FullCallback
import com.blankj.utilcode.util.Utils
import tech.ordinaryroad.android.lib.ui.dialog.MaterialAlertDialog
import tech.ordinaryroad.android.lib.util.OrFullCallback

/**
 * @author mjz
 * @date 2022/3/21
 */
class OrPermissionCallback(private val callback: OrFullCallback) : FullCallback {

    override fun onGranted(granted: MutableList<String>) {
        callback.onGranted(granted)
    }

    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
        MaterialAlertDialog(Utils.getApp(), messageRes = R.string.permission_not_all_granted)
            .customAndShow {
                val dialog = cancelable(false)
                negativeButton {
                    dialog.dismiss()
                }
                positiveButton {
                    PermissionUtils.launchAppDetailsSettings()
                    dialog.dismiss()
                }
            }
        callback.onDenied(deniedForever, denied)
    }

}