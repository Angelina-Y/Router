package com.lzh.nonview.router.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Default Activity Launcher for {@link com.lzh.nonview.router.route.ActivityRoute}
 */
public class DefaultActivityLauncher extends ActivityLauncher{

    @Override
    public Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, rule.getRuleClz());
        intent.putExtras(bundle);
        intent.putExtras(extras.getExtras());
        intent.addFlags(extras.getFlags());
        return intent;
    }

    @Override
    public void open(Context context) throws Exception{
        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,extras.getRequestCode());
            int inAnimation = extras.getInAnimation();
            int outAnimation = extras.getOutAnimation();
            if (inAnimation >= 0 && outAnimation >= 0) {
                ((Activity) context).overridePendingTransition(inAnimation,outAnimation);
            }
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
