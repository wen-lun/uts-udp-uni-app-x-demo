package uni.UNIBC1FF7C;
import io.dcloud.uniapp.*;
import io.dcloud.uniapp.extapi.*;
import io.dcloud.uniapp.framework.*;
import io.dcloud.uniapp.runtime.*;
import io.dcloud.uniapp.vue.*;
import io.dcloud.uniapp.vue.shared.*;
import io.dcloud.unicloud.*;
import io.dcloud.uts.*;
import io.dcloud.uts.Map;
import io.dcloud.uts.Set;
import io.dcloud.uts.UTSAndroid;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
import io.dcloud.uniapp.appframe.AppConfig;
import uts.sdk.modules.utsUdp.UDPServer;
import uts.sdk.modules.utsUdp.UDPData;
import io.dcloud.uniapp.vue.createSSRApp;
import io.dcloud.uniapp.extapi.exit as uni_exit;
import io.dcloud.uniapp.extapi.showToast as uni_showToast;
var firstBackTime: Number = 0;
open class GenApp : BaseApp {
    constructor(instance: ComponentInternalInstance) : super(instance) {
        onLaunch(fun(_: OnLaunchOptions) {
            console.log("App Launch", " at App.uvue:5");
        }
        , instance);
        onAppShow(fun(_: OnShowOptions) {
            console.log("App Show", " at App.uvue:8");
        }
        , instance);
        onHide(fun() {
            console.log("App Hide", " at App.uvue:11");
        }
        , instance);
        onLastPageBackPress(fun() {
            console.log("App LastPageBackPress", " at App.uvue:14");
            if (firstBackTime == 0) {
                uni_showToast(ShowToastOptions(title = "再按一次退出应用", position = "bottom"));
                firstBackTime = Date.now();
                setTimeout(fun(){
                    firstBackTime = 0;
                }, 2000);
            } else if (Date.now() - firstBackTime < 2000) {
                firstBackTime = Date.now();
                uni_exit(null);
            }
        }
        , instance);
        onExit(fun() {
            console.log("App Exit", " at App.uvue:30");
        }
        , instance);
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>>
            get() {
                return normalizeCssStyles(utsArrayOf(
                    styles0
                ));
            }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("uni-row" to padStyleMapOf(utsMapOf("flexDirection" to "row")), "uni-column" to padStyleMapOf(utsMapOf("flexDirection" to "column")));
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(name = "", inheritAttrs = true, props = Map(), propsNeedCastKeys = utsArrayOf(), emits = Map(), components = Map(), styles = GenApp.styles);
}
, fun(instance): GenApp {
    return GenApp(instance);
}
);
var server: UDPServer? = null;
val GenPagesServerClass = CreateVueComponent(GenPagesServer::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(name = "", inheritAttrs = true, props = Map(), propsNeedCastKeys = utsArrayOf(), emits = Map(), components = Map(), styles = GenPagesServer.styles);
}
, fun(instance): GenPagesServer {
    return GenPagesServer(instance);
}
);
val GenPagesClientClass = CreateVueComponent(GenPagesClient::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(name = "", inheritAttrs = true, props = Map(), propsNeedCastKeys = utsArrayOf(), emits = Map(), components = Map(), styles = GenPagesClient.styles);
}
, fun(instance): GenPagesClient {
    return GenPagesClient(instance);
}
);
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass);
    return object : UTSJSONObject() {
        var app = app
    };
}
fun main(app: IApp) {
    defineAppConfig();
    definePageRoutes();
    (createApp()["app"] as VueApp).mount(app);
}
open class UniAppConfig : AppConfig {
    override var name: String = "uts-udp-uni-app-x-demo";
    override var appid: String = "__UNI__BC1FF7C";
    override var versionName: String = "1.0.0";
    override var versionCode: String = "100";
    override var uniCompileVersion: String = "3.98";
    constructor(){}
}
fun definePageRoutes() {
    __uniRoutes.push(PageRoute(path = "pages/server", component = GenPagesServerClass, meta = PageMeta(isQuit = true), style = utsMapOf("navigationBarTitleText" to "客户端")));
    __uniRoutes.push(PageRoute(path = "pages/client", component = GenPagesClientClass, meta = PageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "客户端")));
}
val __uniTabBar: Map<String, Any?>? = utsMapOf("list" to utsArrayOf(
    utsMapOf("text" to "服务端", "pagePath" to "pages/server"),
    utsMapOf("text" to "客户端", "pagePath" to "pages/client")
));
val __uniLaunchPage: Map<String, Any?> = utsMapOf("url" to "pages/server", "style" to utsMapOf("navigationBarTitleText" to "客户端"));
@Suppress("UNCHECKED_CAST")
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/server";
    __uniConfig.globalStyle = utsMapOf("navigationBarTextStyle" to "black", "navigationBarTitleText" to "uni-app x", "navigationBarBackgroundColor" to "#F8F8F8", "backgroundColor" to "#F8F8F8");
    __uniConfig.tabBar = __uniTabBar as Map<String, Any>?;
    __uniConfig.conditionUrl = "";
    __uniConfig.uniIdRouter = Map();
}
fun getApp(): GenApp {
    return getBaseApp() as GenApp;
}
