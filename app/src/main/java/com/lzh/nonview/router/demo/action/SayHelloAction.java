package com.lzh.nonview.router.demo.action;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.lzh.nonview.router.anno.RouteExecutor;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.executors.MainThreadExecutor;
import com.lzh.nonview.router.route.ActionSupport;

@RouteExecutor(MainThreadExecutor.class)
@RouterRule({"haoge://haoge.cn/hello"})
public class SayHelloAction extends ActionSupport {
    @Override
    public void onRouteTrigger(Context context, Bundle bundle) {
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
    }
}

