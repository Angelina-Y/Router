# Router [![](https://jitpack.io/v/yjfnypeu/Router.svg)](https://jitpack.io/#yjfnypeu/Router)
一款简单的，支持在**单品、组件化、插件化**等环境下使用的路由框架。


### 什么是router框架
简单来说.即通过一行url去指定打开指定页面Activity的框架.充分做到页面间解耦.

### 我希望的router框架所能支持的功能

```
- 可自动解析标准url参数。
- 可传递bundle所支持的所有数据类型,能与原生做到无缝对接。
- 支持拦截器.方便对于部分页面跳转时需要检查是否登录.并跳转登录界面做统一配置
- 支持当路由被拦截后。操作之后恢复路由事件。如拦截后进行登录。登录后自动恢复之前被拦截的路由。
- 如果使用http/https地址时.可以直接跳转至使用浏览器
- 能支持原生跳转的各种方式,如请求码.转场动画的设置.
- 能灵活配置route规则.方便对各种开发环境灵活做兼容适配.如插件化.组件化
- 启动动作路由时。支持切换运行线程。
```

### 效果图

![route](./pics/route.gif)

### 版本依赖

- 添加gradle依赖:

添加JitPack仓库到root project下的build.gradle中：

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- 基本配置:

LatestVersion=[![](https://jitpack.io/v/yjfnypeu/Router.svg)](https://jitpack.io/#yjfnypeu/Router)

```Groovy
compile "com.github.yjfnypeu.Router:router-api:$LatestVersion"
annotationProcessor "com.github.yjfnypeu.Router:router-compiler:$LatestVersion"
```

**针对插件化环境。需要有不同的配置方式。请参考：[插件化配置说明](https://github.com/JumeiRdGroup/Router/wiki/%E6%8F%92%E4%BB%B6%E5%8C%96%E9%85%8D%E7%BD%AE)**

### 用法
[具体用法请参考wiki](https://github.com/JumeiRdGroup/Router/wiki)

## 更新日志

查看 [releases](https://github.com/yjfnypeu/Router/releases)

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031

## License
```
Copyright 2015 Haoge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
