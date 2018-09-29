import table from "@/components/base/table";
import mModal from "@/components/base/modal";
import menu from "@/components/base/menu/menu";
import header from "@/components/base/header/header";
import chatHeader from "@/components/chat/chatheader";
// import SockJS from "sockjs-client";
// import Stomp from "stompjs";

const loading = {
  install: function(Vue) {
    // Vue.prototype.SockJS = SockJS;
    // Vue.prototype.Stomp = Stomp;
    Vue.component("tTable", table);
    Vue.component("mMenu", menu);
    Vue.component("mHeader", header);
    Vue.component("mModal", mModal);
    Vue.component("chatHeader", chatHeader);

    Date.prototype.Format = function(fmt) {
      //
      let o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S+": this.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          (this.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    };
    // 下划线转换驼峰
    Vue.prototype.toHump = function(name) {
      return name.replace(/\_(\w)/g, function(all, letter) {
        return letter.toUpperCase();
      });
    };
    // 驼峰转换下划线
    Vue.prototype.toLine = function(name) {
      return name.replace(/([A-Z])/g, "_$1").toLowerCase();
    };
  }
};

export default loading;
