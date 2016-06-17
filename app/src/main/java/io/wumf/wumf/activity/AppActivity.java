package io.wumf.wumf.activity;

import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;

import io.realm.Realm;
import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.AnimationActivity;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.PackageSizeUtil;
import io.wumf.wumf.util.packageSizeUtilImpl.PkgSizeObserver;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 11.04.16.
 */
public class AppActivity extends AnimationActivity {

    public static final String APP_ID = "appPrimaryKey";
    private App app = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_app);
        String appPrimaryKey = getIntent().getStringExtra(APP_ID);
        app = Realm.getDefaultInstance().where(App.class).equalTo("launcherActivity", appPrimaryKey).findFirst();
        IconImageView iconImageView = (IconImageView) findViewById(R.id.icon);
        iconImageView.setApp(app);
        LabelTextView labelTextView = (LabelTextView) findViewById(R.id.label);
        labelTextView.setApp(app);
        showPackageSize();
    }

    private void showPackageSize() {
        try {
            PackageSizeUtil.queryPackageSize(getPackageManager(), app.getPackageName(), new PkgSizeObserver() {
                @Override
                public void onGetSize(long size) {
                    String sizeStr = getFormatSize(size);
                    ((TextView) AppActivity.this.findViewById(R.id.size)).setText(String.valueOf(sizeStr));
                }

                @Override
                public void onGetApplicationSize(long size) {

                }

                @Override
                public void onGetCacheSize(long size) {

                }

                @Override
                public void onGetDataSize(long size) {

                }

                @Override
                public void onGetSDCardApp(long size) {

                }

                @Override
                public void onGetSDCardData(long size) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
