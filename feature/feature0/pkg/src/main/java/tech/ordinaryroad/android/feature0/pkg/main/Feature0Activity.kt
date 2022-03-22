package tech.ordinaryroad.android.feature0.pkg.main

import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import es.dmoral.toasty.Toasty
import tech.ordinaryroad.android.feature0.pkg.BusConifg
import tech.ordinaryroad.android.feature0.pkg.R
import tech.ordinaryroad.android.feature0.pkg.databinding.Feature0ActivityBinding
import tech.ordinaryroad.android.feature1.export.api.Feature1Api
import tech.ordinaryroad.android.feature1.export.bean.Feature1Param
import tech.ordinaryroad.android.lib.ui.activity.OrDataBindingActivity

class Feature0Activity : OrDataBindingActivity<Feature0ActivityBinding>() {

    override val layoutId: Int = R.layout.feature0_activity

    @BusUtils.Bus(tag = BusConifg.FEATURE0_SHOW_TOAST)
    fun onShowToast(x: String) {
        Toasty.success(this, x).show()
    }

    override fun initView() {
        super.initView()

        binding.apply {
            startFeature1Btn.setOnClickListener {
                val result = ApiUtils.getApi(Feature1Api::class.java)!!
                    .startFeature1Activity(
                        this@Feature0Activity,
                        Feature1Param(
                            "Feature1Param"
                        )
                    )
                LogUtils.dTag("Feature1Result", result.name)
                Toasty.success(this@Feature0Activity, result.name).show()
            }
            showBusToast.setOnClickListener {
                BusUtils.post(BusConifg.FEATURE0_SHOW_TOAST, "show toast.")
            }
        }
    }
}