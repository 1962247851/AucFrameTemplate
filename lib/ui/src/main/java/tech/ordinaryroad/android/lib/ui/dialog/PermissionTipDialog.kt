package tech.ordinaryroad.android.lib.ui.dialog

import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.Utils
import tech.ordinaryroad.android.lib.ui.R


/**
 *
 *
 * @author mjz
 * @date 2022/3/22
 */
class PermissionTipDialog {
    companion object{
        fun show() {
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
        }
    }
}