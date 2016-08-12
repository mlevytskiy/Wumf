package io.wumf.wumf.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import io.wumf.wumf.R;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.OnAppItemClickEvent;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 02.05.16.
 */
public class AppViewHolder extends AnyRealmViewHolder<App> {

    private IconImageView icon;
    private LabelTextView label;
    private ImageButton uninstall;
    private View bottomBar;
    private View systemApp;

    public AppViewHolder(ViewGroup parent) {
        super(parent, R.layout.viewholder_app);

        bottomBar = itemView.findViewById(R.id.bottom_bar);
        systemApp = itemView.findViewById(R.id.system_app);
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
//        uninstall = (ImageButton) itemView.findViewById(R.id.uninstall);
//        uninstall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String packageName = (String) v.getTag();
//                BusProvider.getInstance().post(new OnAppClickUninstallEvent(packageName));
//            }
//        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = (String) v.getTag();
                BusProvider.getInstance().post(new OnAppItemClickEvent(packageName));
            }
        });
    }

    @Override
    public void bind(final App item) {
        label.setApp(item);
        icon.setApp(item);
//        uninstall.setTag(item.getPackageName());
        itemView.setTag(item.getPackageName());
        bottomBar.setVisibility(item.isSystemApp() ? View.INVISIBLE : View.VISIBLE);
        systemApp.setVisibility(item.isSystemApp() ? View.VISIBLE : View.INVISIBLE);
    }

}
