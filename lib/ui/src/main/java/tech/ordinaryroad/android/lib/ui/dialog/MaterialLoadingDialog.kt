package tech.ordinaryroad.android.lib.ui.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import org.jetbrains.annotations.NotNull
import tech.ordinaryroad.android.lib.ui.R

/**
 * LoadingDialog
 *
 * @author qq1962247851
 * @date 2020/2/18 14:19
 */
class MaterialLoadingDialog(val context: Context) {

    private var inflate = View.inflate(context, R.layout.dialog_loading, null)
    private var textView: TextView = inflate.findViewById(R.id.text_view)
    var dialog: MaterialDialog = MaterialDialog(context)

    fun dismiss() {
        if (instance != null) {
            instance!!.dialog.dismiss()
            instance = null
        }
    }

    fun show() {
        dialog.show {
            cancelable(false)
            customView(view = inflate, horizontalPadding = true)
        }
    }

    companion object {
        private var instance: MaterialLoadingDialog? = null

        @JvmStatic
        fun with(context: Context): MaterialLoadingDialog {
            if (instance == null) {
                instance = MaterialLoadingDialog(context)
            }
            return instance!!
        }
    }

    fun setMessage(@NotNull message: String): MaterialLoadingDialog {
        textView.text = message
        return this
    }

    fun setMessage(@StringRes resId: Int): MaterialLoadingDialog {
        textView.setText(resId)
        return this
    }

}