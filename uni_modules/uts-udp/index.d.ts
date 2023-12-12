declare type UTSUdpData = {
    /** 收到消息的目标主机 */
    host: string;
    /** 收到消息的目标端口 */
    port: number;
    /** 消息长度 */
    size: number;
    /** 消息 */
    msg: string;
};

declare module '@/uni_modules/uts-udp' {
    export class UDPServer {
        /**
         * @param port 监听端口
         * @param byteSize 接收的字节大小，单位是byte，默认1024
         */
        constructor(port: number, byteSize?: number);
        /**
         * 监听接收消息
         * @param receive 收到消息的回调函数
         * @param error 出错的回调函数
         */
        listener(receive: (data: UTSUdpData) => void, error?: (e: any) => void): void;
        /**
         * 发送消息，可用于向客户端回复消息
         * @param msg 消息内容
         * @param host 目标主机
         * @param port 目标端口
         */
        send(msg: string, host: string, port: number): void;
        /** 停止监听 */
        stop(): void;
    }

    type SendOption = {
        /** 目标主机 */
        host: string;
        /** 目标端口 */
        port: Number;
        /** 发送的消息 */
        msg: string;
        /** 发送消息后，是否监听一次目标主机的回复，默认值false，若为true，则在onceReceive中回调目标主机的回复，若目标主机超时未回复，则回调onceTimeout */
        enableRecive?: boolean;
        /** 等待目标主机回复消息的超时时间，单位毫秒，默认5000 */
        receiveTimeout?: Number;
        /** 主机回复时接收的字节大小，单位是byte，默认1024 */
        receiveByteSize?: Number;
        /** 目标主机回复消息的回调函数 */
        onceReceive?: (data: UTSUdpData) => void;
        /** 目标主机超时未回复的回调函数 */
        onceReceiveTimeout?: () => void;
        /** 出错的回调函数 */
        onError?: (message: string) => void;
        /** 结束的回调函数（调用成功、失败都会执行） */
        onCompleted?: () => void;
    };

    export class UDPClient {
        /**
         * @param serverHost
         * @param serverPort
         * @param byteSize
         * @param timeout
         */
        constructor(serverHost: string, serverPort: number, byteSize: number, timeout?: number);
        /**
         * 发送消息
         * @param option 发送选项
         */
        static send(option: SendOption): void;
    }
}
