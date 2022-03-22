package tech.ordinaryroad.android.lib.ui

import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.Utils
import es.dmoral.toasty.Toasty
import tech.ordinaryroad.android.lib.util.Extensions.limit
import tech.ordinaryroad.android.lib.util.R


/**
 *
 *
 * @author mjz
 * @date 2022/3/22
 */
object Utils {
    fun copyToClipboard(text: String) {
        ClipboardUtils.copyText(text)
        val application = Utils.getApp()
        Toasty.success(
            application,
            application.getString(R.string.copy_to_clipboard, text.limit(10))
        )
            .show()
    }
}