package tech.ordinaryroad.android.lib.ui

import android.widget.CheckBox
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.bm.library.Info
import com.bm.library.PhotoView
import com.google.android.material.textfield.TextInputEditText


/**
 *
 *
 * @author mjz
 * @date 2022/3/22
 */
object Extensions {

    fun ImageView?.getInfo(): Info {
        return if (this == null) {
            generateDefaultInfo()
        } else {
            PhotoView.getImageViewInfo(this)
        }
    }

    fun generateDefaultInfo(): Info {
        return Info().apply {
            getmRect().offset(
                ScreenUtils.getScreenWidth() / 2f,
                ScreenUtils.getScreenHeight() / 2f
            )
        }
    }

    fun CheckBox.disableAll() {
        this.isFocusable = false
        this.isLongClickable = false
        this.isClickable = false
        this.isFocusableInTouchMode = false
        this.setTextIsSelectable(false)
        this.setSelectAllOnFocus(false)
        this.isCursorVisible = false
    }

    fun TextInputEditText.disableAll() {
        this.isFocusable = false
        this.isLongClickable = false
        this.isClickable = false
        this.isFocusableInTouchMode = false
        this.setTextIsSelectable(false)
        this.setSelectAllOnFocus(false)
        this.isCursorVisible = false
    }

}