package com.liuh.screenadaptation;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomDensity(this, getApplication());
        setContentView(R.layout.activity_main);

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//
//        TypedValue typedValue;

    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    /**
     * 假设设计图宽度是360dp，以宽维度来适配
     * <p>
     * dp和px的转换公式：px = dp * density.
     * <p>
     * 当我们以宽维度来进行适配时，我们可以自己计算出恰当的 density 和 densityDpi ，然后修改系统类 DisplayMetrics 的 density 和 densityDpi属性值，
     * 以保证在所有的设备上计算得出的px值都正好是屏幕的宽度。
     *
     * @param activity
     * @param application
     */
    private static void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayDetrics = activity.getResources().getDisplayMetrics();
        activityDisplayDetrics.density = activityDisplayDetrics.scaledDensity = targetDensity;
        activityDisplayDetrics.scaledDensity = targetScaledDensity;
        activityDisplayDetrics.densityDpi = targetDensityDpi;

    }


}











