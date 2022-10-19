package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;

public abstract class BaseCard{
    private View view;
    private Context context;

    protected abstract void mapping();

    public abstract View getView();

    protected void onCreate() {
        mapping();
        setListenerEvent();
    }

    protected abstract void setListenerEvent();
}
