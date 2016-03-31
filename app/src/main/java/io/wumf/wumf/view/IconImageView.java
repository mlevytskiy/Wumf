package io.wumf.wumf.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.File;

import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 30.03.16.
 */
public class IconImageView extends ImageView {

    public IconImageView(Context context) {
        super(context);
    }

    public IconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setApp(App app) {
        setImageURI(Uri.fromFile(new File(app.getIconPath())));
    }

}
