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
import uts.sdk.modules.utsUdp.UDPServer;
import uts.sdk.modules.utsUdp.UDPData;
open class GenPagesServer : BasePage {
    constructor(instance: ComponentInternalInstance) : super(instance) {}
    @Suppress("UNUSED_PARAMETER")
    override fun `$render`(): VNode? {
        val _ctx = this;
        val _component_button = resolveComponent("button");
        return createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "btns"), utsArrayOf(
                if (isTrue(!_ctx.isStart)) {
                    createVNode(_component_button, utsMapOf("key" to 0, "type" to "primary", "size" to "mini", "onClick" to _ctx.onEnableClick), utsMapOf("default" to withCtx(fun(): UTSArray<Any> {
                        return utsArrayOf(
                            "启动UDP服务"
                        );
                    }), "_" to 1), 8, utsArrayOf(
                        "onClick"
                    ));
                } else {
                    createVNode(_component_button, utsMapOf("key" to 1, "type" to "warn", "size" to "mini", "onClick" to _ctx.onDisableClick), utsMapOf("default" to withCtx(fun(): UTSArray<Any> {
                        return utsArrayOf(
                            "停止UDP服务"
                        );
                    }
                    ), "_" to 1), 8, utsArrayOf(
                        "onClick"
                    ));
                }
            )),
            createElementVNode("view", utsMapOf("class" to "status"), toDisplayString(if (_ctx.isStart) {
                "UDP服务已启动";
            } else {
                "UDP服务未启动";
            }
            ), 1),
            createElementVNode("view", utsMapOf("class" to "box"), utsArrayOf(
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.messages, fun(item, _, _): VNode {
                    return createElementVNode("view", utsMapOf("class" to "item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "info"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "label"), "主机：" + toDisplayString(item.host), 1),
                            createElementVNode("text", utsMapOf("class" to "label"), "端口：" + toDisplayString(item.port), 1)
                        )),
                        createElementVNode("view", utsMapOf("class" to "msg"), "消息：" + toDisplayString(item.msg), 1)
                    ));
                }
                ), 256)
            ))
        ));
    }
    open var messages: UTSArray<UDPData> by `$data`;
    open var isStart: Boolean by `$data`;
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("messages" to utsArrayOf<UDPData>(), "isStart" to false);
    }
    override fun `$initMethods`() {
        this.onEnableClick = fun() {
            if (this.isStart) {
                return;
            }
            if (server == null) {
                server = UDPServer(7000);
            }
            server?.listener(fun(data){
                this.messages.push(data);
                server?.send("""已收到消息：${data.msg}""", data.host, data.port);
            }
            , fun(error){
                console.error(error, " at pages/server.uvue:48");
                this.isStart = false;
            }
            );
            this.isStart = true;
        }
        ;
        this.onDisableClick = fun() {
            server?.stop();
            this.isStart = false;
        }
        ;
    }
    open lateinit var onEnableClick: () -> Unit;
    open lateinit var onDisableClick: () -> Unit;
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>>
            get() {
                return normalizeCssStyles(utsArrayOf(
                    styles0
                ), utsArrayOf(
                    GenApp.styles
                ));
            }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("btns" to padStyleMapOf(utsMapOf("padding" to 10)), "box" to padStyleMapOf(utsMapOf("padding" to 10)), "item" to utsMapOf(".box " to utsMapOf("padding" to 10)));
            }
    }
}
