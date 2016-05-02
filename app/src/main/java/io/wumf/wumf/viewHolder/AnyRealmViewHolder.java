package io.wumf.wumf.viewHolder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.realm.RealmViewHolder;

/**
 * Created by max on 27.04.16.
 */
public abstract class AnyRealmViewHolder<T> extends RealmViewHolder {

    public AnyRealmViewHolder(ViewGroup parent, @LayoutRes int layout) {
        super(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    public abstract void bind(T item);

}
