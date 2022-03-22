package tech.ordinaryroad.ioe.android.feature0.pkg.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import tech.ordinaryroad.android.lib.common.CommonTitleActivity;
import tech.ordinaryroad.ioe.android.feature0.pkg.BusConifg;
import tech.ordinaryroad.ioe.android.feature0.pkg.R;
import tech.ordinaryroad.ioe.android.feature1.export.api.Feature1Api;
import tech.ordinaryroad.ioe.android.feature1.export.bean.Feature1Param;
import tech.ordinaryroad.ioe.android.feature1.export.bean.Feature1Result;


public class Feature0Activity extends CommonTitleActivity {

    @BusUtils.Bus(tag = BusConifg.FEATURE0_SHOW_TOAST)
    public void showToast(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public CharSequence bindTitle() {
        return getString(R.string.feature0_title);
    }

    @Override
    public boolean isSwipeBack() {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.feature0_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        findViewById(R.id.startFeature1Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feature1Result result = ApiUtils.getApi(Feature1Api.class)
                        .startFeature1Activity(Feature0Activity.this, new Feature1Param("Feature1Param"));
                Toast.makeText(Feature0Activity.this, result.getName(), Toast.LENGTH_LONG).show();
                LogUtils.dTag("Feature1Result", result.getName());
                ToastUtils.showLong(result.getName());
            }
        });
        BusUtils.register(this);
        findViewById(R.id.showBusToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusUtils.post(BusConifg.FEATURE0_SHOW_TOAST, "show toast.");
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}
