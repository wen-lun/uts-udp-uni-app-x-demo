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
import uts.sdk.modules.utsUdp.UDPClient;
import uts.sdk.modules.utsUdp.UDPClientSendOption;
import uts.sdk.modules.utsUdp.UDPServer;
import uts.sdk.modules.utsUdp.UDPData;
open class GenPagesClient : BasePage {
    constructor(instance: ComponentInternalInstance) : super(instance) {}
    @Suppress("UNUSED_PARAMETER")
    override fun `$render`(): VNode? {
        val _ctx = this;
        val _component_button = resolveComponent("button");
        return createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
            createElementVNode("textarea", utsMapOf("modelValue" to _ctx.msg, "onInput" to fun(`$event`: InputEvent): Any {
                _ctx.msg = `$event`.detail.value;
                return `$event`.detail.value;
            }
            , "type" to "textarea", "auto-height" to "", "placeholder" to "请输入内容"), null, 40, utsArrayOf(
                "modelValue",
                "onInput"
            )),
            createVNode(_component_button, utsMapOf("onClick" to _ctx.onSendClick), utsMapOf("default" to withCtx(fun(): UTSArray<Any> {
                return utsArrayOf(
                    "发送消息"
                );
            }
            ), "_" to 1), 8, utsArrayOf(
                "onClick"
            )),
            createElementVNode("view", utsMapOf("class" to "box"), utsArrayOf(
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.messages, fun(item, _, _): VNode {
                    return createElementVNode("view", utsMapOf("class" to "item"), "服务器[" + toDisplayString(item.host) + "]回复消息：" + toDisplayString(item.msg), 1);
                }
                ), 256)
            ))
        ));
    }
    open var msg: String by `$data`;
    open var messages: UTSArray<UDPData> by `$data`;
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("msg" to "", "messages" to utsArrayOf<UDPData>());
    }
    override fun `$initMethods`() {
        this.onSendClick = fun() {
            UDPClient.send(UDPClientSendOption(host = "255.255.255.255", port = 7000, receiveTimeout = 2000, msg = this.msg, enableRecive = true, onceReceive = fun(data) {
                this.messages.push(data);
            }
            , onError = fun(error) {
                console.error(error, " at pages/client.uvue:33");
            }
            , onceReceiveTimeout = fun() {
                console.warn("服务器超时未回复", " at pages/client.uvue:36");
            }
            ));
        }
        ;
    }
    open lateinit var onSendClick: () -> Unit;
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
                return utsMapOf("box" to padStyleMapOf(utsMapOf("padding" to 10)));
            }
    }
}
