# 使用
## 引入
```javascript
import { UDPServer, UDPClient } from '@/uni_modules/uts-udp';
```

## 使用UDPServer监听接收消息
```javascript
// 参数1为监听的端口，参数2为接收的字节大小，单位是byte，默认1024 ，可不传
const server = new UDPServer(7000, 1024*64)
// 监听消息，参数1为接收消息的回调函数，参数2为出错的回调函数
server.listener(
	(data) => {
		console.info('收到消息', data)
		// 可调用server.send方法回复客户端
		server.send(`已收到消息：${data.msg}`, data.host, data.port);
	},
	(error) => {
		console.error(error);
	},
);
setTimeout(()=>{
	// 10秒后，停止消息接收的监听
	server.stop()
},10000)
```

## 使用UDPClient发送消息
```javascript
UDPClient.send({
    // 目标主机
	host: '255.255.255.255',
    // 目标端口
	port: 7000,
    // 发送的消息
	msg: msg.value,
    // 发送消息后，是否监听一次目标主机的回复，默认值false，若为true，则在onceReceive中回调目标主机的回复，若目标主机超时未回复，则回调onceTimeout
	enableRecive: true,
    // 等待目标主机回复消息的超时时间，单位毫秒，默认5000
	receiveTimeout: 2000,
	// 主机回复时接收的字节大小，单位是byte，默认1024
	receiveByteSize: 1024,
    // 目标主机回复消息的回调函数
	onceReceive(data) {
        console.info('收到消息', data)
    },
    // 目标主机超时未回复的回调函数
	onError(error) {
        console.error(error);
    },
    // 出错的回调函数
	onceReceiveTimeout() {
        console.warn('服务器超时未回复');
    },
	// 结束的回调函数（调用成功、失败都会执行）
	onCompleted() {
		console.info("发送结束")
	}
});
```



# 例子：
## uni-app 项目demo
* github: [uts-udp-demo](https://github.com/wen-lun/uts-udp-demo?_blank)
* 服务端server.vue

```html
<template>
    <view class="content">
        <view class="btns">
            <button v-if="!isStart" type="primary" size="mini" @click="onEnableClick">启动UDP服务</button>
            <button v-else type="warn" size="mini" @click="onDisableClick">停止UDP服务</button>
        </view>
        <view class="status">{{ isStart ? 'UDP服务已启动' : 'UDP服务未启动' }}</view>
        <view class="box">
            <view v-for="item in messages" class="item">
                <view class="info">
                    <text class="label">主机：{{ item.host }}</text>
                    <text class="label">端口：{{ item.port }}</text>
                </view>
                <view class="msg">消息：{{ item.msg }}</view>
            </view>
        </view>
    </view>
</template>

<script lang="ts" setup>
import { UDPServer } from '@/uni_modules/uts-udp';
import { ref } from 'vue';

let server: UDPServer | undefined;
const isStart = ref(false);
const messages = ref<UTSUdpData[]>([]);

function onEnableClick() {
    if (isStart.value) return;
    server = server || new UDPServer(7000);
    server.listener(
        (data) => {
            messages.value.push(data);
            // 回复客户端
            server.send(`已收到消息：${data.msg}`, data.host, data.port);
        },
        (error) => {
            console.error(error);
            isStart.value = false;
        },
    );
    isStart.value = true;
}

function onDisableClick() {
    server?.stop();
    isStart.value = false;
}
</script>

<style lang="scss" scoped>
.content {
    font-size: 15px;
}

.btns {
    padding: 10px;
    text-align: right;

    button + button {
        margin-left: 5px;
    }
}

.box {
    padding: 10px;

    .item {
        padding: 10px;

        .label + .label {
            margin-left: 10px;
        }
    }
}
</style>
```

* 客户端client.vue

```html
<template>
    <view class="content">
        <uni-easyinput v-model="msg" type="textarea" auto-height placeholder="请输入内容"></uni-easyinput>
        <button @click="onSendClick">发送消息</button>
        <view class="box">
            <view v-for="item in messages" class="item">服务器[{{ item.host }}]回复消息：{{ item.msg }}</view>
        </view>
    </view>
</template>

<script lang="ts" setup>
import { UDPClient } from '@/uni_modules/uts-udp';
import { ref } from 'vue';

const msg = ref('');
const messages = ref<UTSUdpData[]>([]);

function onSendClick() {
    UDPClient.send({
        host: '255.255.255.255',
        port: 7000,
        receiveTimeout: 2000,
        msg: msg.value,
        enableRecive: true,
        onceReceive(data) {
            messages.value.push(data);
        },
        onError(error) {
            console.error(error);
        },
        onceReceiveTimeout() {
            console.warn('服务器超时未回复');
        },
    });
}
</script>

<style lang="scss" scoped>
.content {
    font-size: 15px;
}

.box {
    padding: 10px;
}
</style>
```

## uni-app x 项目demo
* github: [uts-udp-uni-app-x-demo](https://github.com/wen-lun/uts-udp-uni-app-x-demo?_blank)

* 服务端server.uvue

```html
<template>
	<view class="content">
		<view class="btns">
			<button v-if="!isStart" type="primary" size="mini" @click="onEnableClick">启动UDP服务</button>
			<button v-else type="warn" size="mini" @click="onDisableClick">停止UDP服务</button>
		</view>
		<view class="status">{{ isStart ? "UDP服务已启动" : "UDP服务未启动" }}</view>
		<view class="box">
			<view v-for="item in messages" class="item">
				<view class="info">
					<text class="label">主机：{{ item.host }}</text>
					<text class="label">端口：{{ item.port }}</text>
				</view>
				<view class="msg">消息：{{ item.msg }}</view>
			</view>
		</view>
	</view>
</template>

<script lang="uts">
import {
	UDPServer,
	UDPData
} from "@/uni_modules/uts-udp";

var server : UDPServer | null = null;

export default {
	data() {
		return {
			messages: [] as UDPData[],
			isStart: false
		}
	},
	methods: {
		onEnableClick() {
			if (this.isStart) return;
			if (server == null) {
				server = new UDPServer(7000);
			}
			server?.listener(
				(data) => {
					this.messages.push(data)
					// 回复客户端
					server?.send(`已收到消息：${data.msg}`, data.host, data.port);
				},
				(error) => {
					console.error(error);
					this.isStart = false;
				}
			);
			this.isStart = true;
		},
		onDisableClick() {
			server?.stop();
			this.isStart = false;
		}
	}
}
</script>

<style>
	.btns {
		padding: 10px;
	}

	.box {
		padding: 10px;

	}

	.box .item {
		padding: 10px;

	}
</style>
```

* 客户端client.uvue

```html
<template>
	<view class="content">
		<textarea v-model="msg" type="textarea" auto-height placeholder="请输入内容"></textarea>
		<button @click="onSendClick">发送消息</button>
		<view class="box">
			<view v-for="item in messages" class="item">服务器[{{item.host}}]回复消息：{{ item.msg }}</view>
		</view>
	</view>
</template>

<script lang="uts">
import { UDPClient, UDPClientSendOption, UDPData } from '@/uni_modules/uts-udp';

export default {
	data() {
		return {
			msg: "",
			messages: [] as UDPData[]
		}
	},
	methods: {
		onSendClick() {
			UDPClient.send({
				host: "255.255.255.255",
				port: 7000,
				receiveTimeout: 2000,
				msg: this.msg,
				enableRecive: true,
				onceReceive(data) {
					this.messages.push(data)
				},
				onError(error) {
					console.error(error);
				},
				onceReceiveTimeout() {
					console.warn("服务器超时未回复");
				}
			} as UDPClientSendOption);
		}
	}
}
</script>

<style lang="scss" scoped>
	.box {
		padding: 10px;
	}
</style>
```

# uts-udp
### 开发文档
[UTS 语法](https://uniapp.dcloud.net.cn/tutorial/syntax-uts.html?_blank)
[UTS 原生插件](https://uniapp.dcloud.net.cn/plugin/uts-plugin.html?_blank)
[Hello UTS](https://gitcode.net/dcloud/hello-uts/-/tree/dev?_blank)