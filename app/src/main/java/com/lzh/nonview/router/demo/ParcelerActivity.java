package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.base.BaseActivity;

@RouterRule("haoge://haoge.cn/parceler")
public class ParcelerActivity extends BaseActivity {

    @Arg
    String username;
    @Arg
    String address;
    @Arg
    Size size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceler);
        TextView tv = (TextView) findViewById(R.id.parcel_print);
        tv.setText("username:" + username + "address :" + address);
    }
}
