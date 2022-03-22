package tech.ordinaryroad.android.feature1.pkg.main

import android.content.Context
import android.content.Intent
import tech.ordinaryroad.android.feature1.pkg.R
import tech.ordinaryroad.android.feature1.pkg.databinding.Feature1ActivityBinding
import tech.ordinaryroad.android.lib.ui.activity.OrDataBindingActivity

class Feature1Activity : OrDataBindingActivity<Feature1ActivityBinding>() {

    override val layoutId = R.layout.feature1_activity

    companion object {
        @JvmStatic
        fun start(context: Context) {

            val starter = Intent(context, Feature1Activity::class.java)
            context.startActivity(starter)
        }
    }

}