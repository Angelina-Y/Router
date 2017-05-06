package com.lzh.nonview.router;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.route.ActionRoute;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.BrowserRoute;
import com.lzh.nonview.router.route.IActionRoute;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IBaseRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;


/**
 * Entry of Router。
 *
 * @author haoge
 */
public final class Router{

    /**
     * The key of raw uri. you can obtains it uri by this key:
     *      <li>bundle.getParcelable(Router.RAW_URI)</li>
     */
    public static final String RAW_URI = "_ROUTER_RAW_URI_KEY_";

    private Uri uri;
    private RouteCallback callback;

    private Router(@NonNull Uri uri) {
        this.uri = Utils.completeUri(uri);
    }

    /**
     * Create Router by url string
     * @param url The url to create Router
     * @return new Router
     */
    public static Router create(@NonNull String url) {
        return new Router(Uri.parse(url));
    }

    /**
     * Create Router by uri
     * @param uri the uri to create Router
     * @return new Router
     */
    public static Router create(@NonNull Uri uri) {
        return new Router(uri);
    }

    /**
     * Set a callback to notify the user when the routing were success or failure.
     * @param callback The callback you set.
     * @return Router itself
     *
     * @see Router#getCallback()
     */
    public Router setCallback (@Nullable RouteCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Obtain a callback put to use. will not be null.
     * @return if you had not set yet, it will returns a global callback obtain from {@link RouteManager#getCallback()}
     */
    private @NonNull RouteCallback getCallback () {
        return callback == null ? RouteManager.get().getCallback() : callback;
    }

    /**
     * Restore a Routing event from last uri and extras.
     * @param uri last uri
     * @param extras last extras
     * @return The restored routing find by {@link Router#getRoute()}
     */
    public static @NonNull IRoute resume(@NonNull Uri uri, @Nullable RouteBundleExtras extras) {
        IRoute route = Router.create(uri).getRoute();
        if (route instanceof ActivityRoute && extras instanceof ActivityRouteBundleExtras) {
            ((ActivityRoute) route).replaceExtras((ActivityRouteBundleExtras) extras);
        } else if (route instanceof ActionRoute && extras instanceof ActionRouteBundleExtras) {
            ((ActionRoute) route).replaceExtras((ActionRouteBundleExtras) extras);
        }
        return route;
    }

    /**
     * launch routing task.
     * @param context context to launched
     */
    public void open(@NonNull Context context) {
        getRoute().open(context);
    }

    /**
     * Get route by uri, you should get a route by this way and set some extras data before open
     * @return
     *  An IRoute object.it will be {@link BrowserRoute}, {@link ActivityRoute} or {@link ActionRoute}.<br>
     *  and it also will be {@link IRoute#EMPTY} if it not found
     */
    public @NonNull IRoute getRoute () {
        if (ActionRoute.canOpenRouter(uri)) {
            return new ActionRoute().create(uri, getCallback());
        } else if (ActivityRoute.canOpenRouter(uri)) {
            return new ActivityRoute().create(uri, getCallback());
        } else if (BrowserRoute.canOpenRouter(uri)) {
            return BrowserRoute.getInstance().setUri(uri);
        } else {
            getCallback().notFound(uri,new NotFoundException(String.format("find Route by %s failed:",uri),
                    NotFoundException.NotFoundType.SCHEME,uri.toString()));
            return IRoute.EMPTY;
        }
    }

    /**
     * <p>
     * Get {@link IBaseRoute} by uri, it could be one of {@link IActivityRoute} or {@link IActionRoute}.
     * and you can add some {@link android.os.Bundle} data and {@link RouteInterceptor} into it.
     * </p>
     * @return returns an {@link IBaseRoute} finds by uri or {@link IBaseRoute#EMPTY} for not found
     */
    public @NonNull IBaseRoute getBaseRoute() {
        IRoute route = getRoute();
        if (route instanceof IBaseRoute) {
            return (IBaseRoute) route;
        }
        getCallback().notFound(uri, new NotFoundException(String.format("find BaseRoute by %s failed:",uri),
                NotFoundException.NotFoundType.SCHEME, uri.toString()));
        return IBaseRoute.EMPTY;
    }

    /**
     * Get {@link IActivityRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActivityRoute} finds by uri or {@link IActivityRoute#EMPTY} for not found.
     */
    public @NonNull IActivityRoute getActivityRoute() {
        if (ActivityRoute.canOpenRouter(uri)) {
            return (IActivityRoute) new ActivityRoute().create(uri, getCallback());
        }

        // return an empty route to avoid NullPointException
        getCallback().notFound(uri,
                new NotFoundException(String.format("find Activity Route by %s failed:",uri),
                        NotFoundException.NotFoundType.SCHEME,uri.toString()));
        return IActivityRoute.EMPTY;
    }

    /**
     * Get {@link IActionRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActionRoute} finds by uri or {@link IActionRoute#EMPTY} for not found.
     */
    public @NonNull IActionRoute getActionRoute() {
        if (ActionRoute.canOpenRouter(uri)) {
            return (ActionRoute) new ActionRoute().create(uri, getCallback());
        }

        // return a empty route to avoid NullPointException
        getCallback().notFound(uri,
                new NotFoundException(String.format("find Action Route by %s failed:",uri),
                        NotFoundException.NotFoundType.SCHEME,uri.toString()));
        return IActionRoute.EMPTY;
    }

    /**
     * Set a global route callback to invoked when open a uri
     * @param callback can't be null
     */
    public static void setGlobalRouteCallback (@NonNull RouteCallback callback) {
        RouteManager.get().setCallback(callback);
    }

    public static void setGlobalRouteInterceptor (@NonNull RouteInterceptor interceptor) {
        RouteManager.get().setInterceptor(interceptor);
    }

    /**
     * Set to create route rules
     * @param creator Route rules creator.can't be null
     */
    public static void addRouteCreator(@NonNull RouteCreator creator) {
        RouteManager.get().addCreator(creator);
    }


}
