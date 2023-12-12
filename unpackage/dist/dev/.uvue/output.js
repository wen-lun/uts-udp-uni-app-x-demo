'use strict';

require('vue');

const { registerUTSInterface, initUTSProxyClass, initUTSProxyFunction, initUTSPackageName, initUTSIndexClassName, initUTSClassName } = uni;
const name = 'utsUdp';
const moduleName = 'jk-uts-udp';
const moduleType = '';
const errMsg = ``;
const is_uni_modules = true;
const pkg = /*#__PURE__*/ initUTSPackageName(name, is_uni_modules);



/*#__PURE__*/ initUTSProxyClass(Object.assign({ moduleName, moduleType, errMsg, package: pkg, class: initUTSClassName(name, 'UDPDataByJs', is_uni_modules) }, {"constructor":{"params":[{"name":"address","type":"InetAddress"},{"name":"host","type":"String"},{"name":"port","type":"Int"},{"name":"size","type":"Int"},{"name":"msg","type":"String"}]},"methods":{},"staticMethods":{},"props":["address","host","port","size","msg"],"staticProps":[]} ));
/*#__PURE__*/ initUTSProxyClass(Object.assign({ moduleName, moduleType, errMsg, package: pkg, class: initUTSClassName(name, 'UDPServerByJs', is_uni_modules) }, {"constructor":{"params":[{"name":"port","type":"Int"},{"name":"byteSize","type":"Number","default":1024}]},"methods":{"listenerByJs":{"async":false,"params":[{"name":"receive","type":"UTSCallback"},{"name":"error","type":"UTSCallback"}],"returnOptions":{}},"sendByJs":{"async":false,"params":[{"name":"msg","type":"string"},{"name":"host","type":"string"},{"name":"port","type":"Int"}],"returnOptions":{}},"stopByJs":{"async":false,"params":[],"returnOptions":{}}},"staticMethods":{},"props":["port","byteSize","socket","isListener"],"staticProps":[]} ));
/*#__PURE__*/ initUTSProxyClass(Object.assign({ moduleName, moduleType, errMsg, package: pkg, class: initUTSClassName(name, 'UDPClientByJs', is_uni_modules) }, {"constructor":{"params":[]},"methods":{},"staticMethods":{"sendByJs":{"async":false,"params":[{"name":"option","type":"UTSSDKModulesUtsUdpUDPClientSendOptionJSONObject"}],"returnOptions":{}}},"props":[],"staticProps":[]} ));
