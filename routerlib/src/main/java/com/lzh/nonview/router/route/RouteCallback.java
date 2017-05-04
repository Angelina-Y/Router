package com.lzh.nonview.router.route;

import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;

/**
 * Callback of router
 * Created by lzh on 16/9/5.
 */
public interface RouteCallback {

    /**
     * There are two types of not found exception:<br>
     *     <i>route rule</i> not found<br>
     *     <i>activity</i> not found
     * @param uri uri the uri to open
     * @param e {@link NotFoundException}
     */
    void notFound(Uri uri, NotFoundException e);

    /**
     * A callback method to notice that you had open a activity by route
     * @param uri the uri to open
     * @param clzName the activity class name that had opened
     */
    void onOpenSuccess(Uri uri,String clzName);

    /**
     * A callback method to notice that you occurs some exception.<br>
     *     exclude <i>not found</i> exception
     * @param uri the uri to open
     * @param e the exception
     */
    void onOpenFailed(Uri uri,Throwable e);

    RouteCallback EMPTY = new RouteCallback() {

        @Override
        public void notFound(Uri uri, NotFoundException e) {}

        @Override
        public void onOpenSuccess(Uri uri, String clzName) {}

        @Override
        public void onOpenFailed(Uri uri, Throwable e) {}
    };
}
