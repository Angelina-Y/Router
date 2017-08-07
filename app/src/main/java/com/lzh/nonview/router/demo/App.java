package com.lzh.nonview.router.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.haoge.studio.RouterRuleCreator;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.ActivityLauncher;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.demo.action.SayHelloAction;
import com.lzh.nonview.router.demo.launcher.CustomActionLauncher;
import com.lzh.nonview.router.demo.launcher.CustomActivityLauncher;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.launcher.DefaultActivityLauncher;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.HashMap;
import java.util.Map;

@ActivityLauncher(DefaultActivityLauncher.class)
@RouteConfig(
        baseUrl = "haoge://page/",
        pack = "com.haoge.studio",
        activityLauncher = CustomActivityLauncher.class,
        actionLauncher = CustomActionLauncher.class
        )
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 添加route规则创建器

        Router.addRouteCreator(new RouteInit());
        Router.addRouteCreator(new RouterRuleCreator());
        Router.setGlobalRouteInterceptor(new RouteInterceptor() {

            @Override
            public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
                return !DataManager.INSTANCE.isLogin();
            }

            @Override
            public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
                Toast.makeText(App.this, "未登录.请先登录", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(context,LoginActivity.class);
                loginIntent.putExtra("uri",uri);
                loginIntent.putExtra("extras",extras);
                context.startActivity(loginIntent);
            }
        });

        // 对Router设置Activity Route Callback,作辅助功能
        Router.setGlobalRouteCallback(new RouteCallback() {

            @Override
            public void notFound(Uri uri, NotFoundException e) {
                Toast.makeText(App.this, e.getNotFoundName() + " not find", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenSuccess(Uri uri, RouteRule rule) {
                // 可在此进行route追踪
                Toast.makeText(App.this, String.format("Launch routing task %s success", rule.getRuleClz()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenFailed(Uri uri, Throwable e) {
                Toast.makeText(App.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RouteInit implements RouteCreator {

        @Override
        public Map<String, ActivityRouteRule> createActivityRouteRules() {
            Map<String,ActivityRouteRule> routes = new HashMap<>();
            routes.put("jumei://main",
                    new ActivityRouteRule(ParamsActivity.class)
                            .addParam("price", RouteRule.FLOAT)
                            .addParam("bookName", RouteRule.STRING)
                            .addParam("books", RouteRule.STRING_LIST)
                            .addParam("prices", RouteRule.INT_LIST)
                            .setLauncher(DefaultActivityLauncher.class)
            );
            return routes;
        }

        @Override
        public Map<String, ActionRouteRule> createActionRouteRules() {
            Map<String, ActionRouteRule> routes = new HashMap<>();
            routes.put("haoge://action/support", new ActionRouteRule(SayHelloAction.class));
            return routes;
        }
    }
}
