package io.wumf.wumf.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import io.wumf.wumf.R;

/**
 * Created by max on 30.07.16.
 */
public class EmptyView extends RelativeLayout {

    private static final long TIMEOUT = 2500;

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.empty_view2, this);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(getContext()).load(R.drawable.loading).into(imageViewTarget);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EmptyView.this.setBackgroundColor(Color.WHITE);
                ImageView imageView = (ImageView) findViewById(R.id.image_view);
                imageView.setImageResource(R.drawable.duck);
                TextView textView = (TextView) findViewById(R.id.text_view);
                textView.setText(R.string.we_found_only_a_duck);
                textView.setTextColor(Color.BLACK);
            }
        }, TIMEOUT);
    }

}
