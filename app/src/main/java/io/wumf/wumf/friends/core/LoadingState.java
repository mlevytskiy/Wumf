package io.wumf.wumf.friends.core;

/**
 * Created by max on 30.05.15.
 */
public enum LoadingState {
    loading("Loading..."),
    success("Success"),
    error("Error"),
    canceled("Canceled");

    public String text;

    LoadingState(String text) {
        this.text = text;
    }
}
