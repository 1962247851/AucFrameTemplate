/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.android.lib.ui.dialog

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import tech.ordinaryroad.android.lib.ui.R


/**
 *
 *
 * @author mjz
 * @date 2022/2/12
 */
class MaterialAlertDialog(
    val context: Context,
    private val lifecycleOwner: LifecycleOwner? = null,
    @StringRes private val titleRes: Int = R.string.attention,
    @StringRes private val messageRes: Int? = null
) {

    fun show(confirmCallback: DialogCallback) {
        MaterialDialog(context).show {
            if (lifecycleOwner != null) {
                lifecycleOwner(lifecycleOwner)
            }
            title(titleRes)
            icon(drawable = AppCompatResources.getDrawable(context, R.drawable.ic_warning)?.apply {
                setTint(context.getColor(R.color.WindowBackgroundColorReserve))
            })
            if (messageRes != null) {
                message(res = messageRes)
            }

            negativeButton()
            positiveButton(click = confirmCallback)
        }
    }

    fun customAndShow(func: MaterialDialog.() -> Unit): MaterialDialog {
        return MaterialDialog(context).show {
            if (lifecycleOwner != null) {
                lifecycleOwner(lifecycleOwner)
            }
            title(titleRes)
            icon(drawable = AppCompatResources.getDrawable(context, R.drawable.ic_warning)?.apply {
                setTint(context.getColor(R.color.WindowBackgroundColorReserve))
            })
            if (messageRes != null) {
                message(res = messageRes)
            }
        }.apply {
            func()
            show()
        }
    }

}