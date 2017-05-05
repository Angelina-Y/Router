package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.module.ActionRouteMap;
import com.lzh.nonview.router.module.ActivityRouteMap;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;

import java.util.HashMap;
import java.util.Map;

public class ActionRoute extends BaseRoute<IActionRoute, ActionRouteBundleExtras> implements IActionRoute {

    @Override
    protected ActionRouteBundleExtras createExtras() {
        return new ActionRouteBundleExtras();
    }

    @Override
    protected RouteMap getRouteMap(Uri uri) {
        return RouteManager.get().getRouteMapByUri(new URIParser(uri), RouteManager.TYPE_ACTION_ROUTE);
    }

    @Override
    protected void realOpen(Context context) throws Throwable {
        ActionRouteMap real = (ActionRouteMap) routeMap;
        ActionSupport target = real.getTarget();
        Bundle data = new Bundle();
        data.putAll(bundle);
        data.putAll(extras.getExtras());
        target.onRouteTrigger(data);
    }
}
