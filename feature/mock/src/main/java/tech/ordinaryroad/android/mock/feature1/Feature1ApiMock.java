package tech.ordinaryroad.android.mock.feature1;

import android.content.Context;


@ApiUtils.Api(isMock = true)
public class Feature1ApiMock extends Feature1Api {
    @Override
    public Feature1Result startFeature1Activity(Context context, Feature1Param param) {
        return new Feature1Result("Mock Result");
    }
}
