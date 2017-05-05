package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.extras.RouteBundleExtras;

public interface IRoute {

    /**
     * open route with uri by context
     * @param context The context to invoked startActivity
     */
    void open(Context context);

    IRoute EMPTY = new IRoute() {
        @Override
        public void open(Context context) {}
    };
}
