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
 * @date 2022/2/7
 */
class MaterialDeleteAlertDialog(
    val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    @StringRes private val messageRes: Int? = R.string.delete_forever_message
) {

    fun show(confirmCallback: DialogCallback) {
        MaterialDialog(context).show {
            lifecycleOwner(lifecycleOwner)
            title(R.string.attention)
            icon(drawable = AppCompatResources.getDrawable(context, R.drawable.ic_warning)?.apply {
                setTint(context.getColor(R.color.WindowBackgroundColorReserve))
            })
            message(res = messageRes)

            positiveButton()
            negativeButton(click = confirmCallback)
        }
    }

}