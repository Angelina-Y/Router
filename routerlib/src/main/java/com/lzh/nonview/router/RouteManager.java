package com.lzh.nonview.router;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.ActionRouteMap;
import com.lzh.nonview.router.module.ActivityRouteMap;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A container to manage some global config.
 * Created by lzh on 16/9/8.
 */
public final class RouteManager {
    private static RouteManager manager = new RouteManager();
    private RouteManager() {}
    public static RouteManager get() {
        return manager;
    }

    /**
     * A global route interceptor
     */
    private RouteInterceptor globalInterceptor;
    /**
     * global route callback
     */
    private RouteCallback globalCallback;
    // provide a empty callback to make it more safely
    private RouteCallback EmptyCallback = RouteCallback.EMPTY;
    private boolean shouldReload;// if should be reload routeMap.
    /**
     * A container to contains all of route rule creator,compat with some complex scene;
     */
    private List<RouteCreator> creatorList = new ArrayList<>();
    /**
     * A map to contains all of route rule created by creatorList
     */
    private Map<String,ActivityRouteMap> activityRouteMap = new HashMap<>();
    private Map<String,ActionRouteMap> actionRouteMap = new HashMap<>();

    void setCallback (RouteCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null");
        }
        this.globalCallback = callback;
    }

    void setInterceptor (RouteInterceptor interceptor) {
        this.globalInterceptor = interceptor;
    }

    RouteInterceptor getInterceptor () {
        return this.globalInterceptor;
    }

    void addCreator (RouteCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Route creator should not be null");
        }
        this.creatorList.add(creator);
        shouldReload = true;
    }

    /**
     * Get a global callback you had set before,if not set,this should be a empty callback returns
     * @return a global callback to use,if not set ,it should be a empty callback instead.will not be null
     */
    RouteCallback getCallback() {
        return globalCallback == null ? EmptyCallback : globalCallback;
    }

    public RouteInterceptor getGlobalInterceptor() {
        return globalInterceptor;
    }

    Map<String,ActivityRouteMap> getActivityRouteMap() {
        obtainRouteRulesIfNeed();
        return activityRouteMap;
    }

    Map<String, ActionRouteMap> getActionRouteMap() {
        obtainRouteRulesIfNeed();
        return actionRouteMap;
    }

    public RouteMap getRouteMapByUri(URIParser parser) {
        String route = parser.getScheme() + "://" + parser.getHost();
        Map<String, ActivityRouteMap> routes = getActivityRouteMap();
        String wrap = Utils.wrapScheme(route);
        if (routes.containsKey(wrap)) {
            return routes.get(wrap);
        }
        String unWrap = Utils.unwrapScheme(wrap);
        return routes.get(unWrap);
    }

    private void obtainRouteRulesIfNeed() {
        if (shouldReload) {
            activityRouteMap.clear();
            actionRouteMap.clear();
            int count = creatorList == null ? 0 : creatorList.size();
            for (int i = 0; i < count; i++) {
                addAll(activityRouteMap, creatorList.get(i).createActivityRouteRules());
                addAll(actionRouteMap, creatorList.get(i).createActionRouteRules());
            }
            shouldReload = false;
        }
    }

    private <T,R> void addAll(Map<T,R> src, Map<T,R> target) {
        if (target != null && src != null) {
            src.putAll(target);
        }
    }
}
