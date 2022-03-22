package tech.ordinaryroad.android.lib.util

import android.view.Menu
import android.view.View
import androidx.databinding.ViewStubProxy
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import java.lang.reflect.Method
import java.util.concurrent.TimeUnit


/**
 *
 *
 * @author mjz
 * @date 2022/3/22
 */
object Extensions {

   private const val DOUBLE_CLICK_TIMESTAMP = 250L

    fun View.setOnClickAndDoubleClickListeners(
        onClick: Runnable? = null,
        onDoubleClick: Runnable? = null
    ) {
        var task: ThreadUtils.SimpleTask<Long>? = null
        var firstClickTimestamp = 0L
        var secondClickTimestamp = 0L
        this.setOnClickListener {
            if (firstClickTimestamp == 0L) {
                firstClickTimestamp = System.currentTimeMillis()
            } else {
                secondClickTimestamp = System.currentTimeMillis()
            }
            if (task != null) {
                ThreadUtils.cancel(task)
                task = null
            }
            task = object : ThreadUtils.SimpleTask<Long>() {
                override fun doInBackground(): Long {
                    return secondClickTimestamp - firstClickTimestamp
                }

                override fun onSuccess(result: Long?) {
                    result == null && return
                    if (result!! in 1..DOUBLE_CLICK_TIMESTAMP) {
                        onDoubleClick?.run()
                    } else {
                        onClick?.run()
                    }

                    firstClickTimestamp = 0L
                    secondClickTimestamp = 0L
                }
            }
            ThreadUtils.executeByCpuWithDelay(
                task,
                DOUBLE_CLICK_TIMESTAMP,
                TimeUnit.MILLISECONDS
            )
        }
    }

    fun ViewStubProxy.setVisible(visible: Boolean) {
        if (this.isInflated) {
            this.root.setVisible(visible)
        } else {
            this.viewStub?.setVisible(visible)
        }
    }

    fun View.setVisible(visible: Boolean) {
        val newVisibility = if (visible) View.VISIBLE else View.GONE
        this.visibility = newVisibility
    }

    fun View.toggleVisible() {
        if (this.visibility == View.VISIBLE) {
            this.setVisible(false)
        } else {
            this.setVisible(true)
        }
    }

    fun Menu?.showIcons() {
        this == null && return
        val menu = this!!
        if (menu.javaClass.simpleName == "MenuBuilder") {
            try {
                val m: Method = menu.javaClass.getDeclaredMethod(
                    "setOptionalIconsVisible", java.lang.Boolean.TYPE
                )
                m.isAccessible = true
                m.invoke(menu, true)
            } catch (e: Exception) {
                LogUtils.eTag(
                    javaClass.simpleName,
                    "onMenuOpened...unable to set icons for overflow menu",
                    e
                )
            }
        }
    }

    fun <T> Iterable<T>.findIndexed(predicate: (T) -> Boolean): Pair<T?, Int> {
        forEachIndexed { index, element -> if (predicate(element)) return Pair(element, index) }
        return Pair(null, -1)
    }

    fun String?.limit(maxLength: Int = 100, showOverflowDots: Boolean = true): String {
        this == null && return ""
        return if (this!!.length <= maxLength) {
            this
        } else {
            this.substring(0, maxLength) + if (showOverflowDots) "..." else ""
        }
    }

}