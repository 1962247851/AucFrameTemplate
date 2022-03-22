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

package tech.ordinaryroad.android.lib.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.ThreadUtils
import tech.ordinaryroad.android.lib.ui.R
import tech.ordinaryroad.android.lib.util.Extensions.showIcons

/**
 * @author mjz
 * @date 2022/1/27
 */
abstract class OrDataBindingActivity<BD : ViewDataBinding> : AppCompatActivity() {

    companion object {
        /**
         * 状态栏透明，ToolBar设置paddingTop
         *
         * 需要设置 fitsSystemWindows="true"
         * ToolBar高度为wrap_content
         */
        fun <BD : ViewDataBinding> transparentStatusBarWithToolbar(activity: OrDataBindingActivity<BD>) {
            BarUtils.transparentStatusBar(activity)
            activity.toolbar?.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        }
    }

    protected abstract val layoutId: Int

    protected lateinit var subTitle: String
    protected lateinit var binding: BD
    protected var toolbar: Toolbar? = null
    protected var menu: Menu? = null

    override fun onConfigurationChanged(newConfig: Configuration) {
        // super.onConfigurationChanged(newConfig)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BusUtils.register(this)
        initData(savedInstanceState)
        initView()
    }

    @CallSuper
    open fun initData(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    @CallSuper
    open fun initView() {
        toolbar = binding.root.findViewById(R.id.toolbar)
        initToolbar()
        initStatusBar()
    }

    open fun initToolbar() {
        if (toolbar?.title == null) {
            toolbar?.title = ""
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    open fun initStatusBar() {
        transparentStatusBarWithToolbar(this)
    }

    override fun onDestroy() {
        BusUtils.unregister(this)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (preFinish()) {
            super.onBackPressed()
        }
    }

    /**
     * @return 是否完成所有操作，退出界面
     */
    open fun preFinish(): Boolean = true

    /**
     * @return 是否有右上角菜单
     */
    open fun getOptionsMenuId(menu: Menu?): Int = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (getOptionsMenuId(menu) > 0) {
            menuInflater.inflate(getOptionsMenuId(menu), menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    @BusUtils.Bus(tag = BusConifg.ACTIVITY_THEME_CHANGE, threadMode = BusUtils.ThreadMode.MAIN)
    fun onAppThemeChange(appThemeChange: AppThemeChange) {
        ActivityUtils.finishActivity(this)
        ActivityUtils.startActivity(this.javaClass)
    }

    fun isDarkTheme(): Boolean {
        // return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val flag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * 设置状态栏亮色模式
     */
    fun setLightModeStatusBar() {
        BarUtils.setStatusBarLightMode(this, true)
    }

    fun clearLightModeStatusBar() {
        BarUtils.setStatusBarLightMode(this, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu.showIcons()
        this.menu = menu
        return super.onPrepareOptionsMenu(menu)
    }

    override fun postponeEnterTransition() {
        super.postponeEnterTransition()
        ThreadUtils.runOnUiThreadDelayed({
            startPostponedEnterTransition()
        }, 1000)
    }

}