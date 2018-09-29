import Vue from "vue";

var IMHandler = function () {
  
    this.onopen = function (event, ws) {
      // ws.send('hello 连上了哦')
      console.log(event)
      //document.getElementById('contentId').innerHTML += 'hello 连上了哦<br>';
    }
  
    /**
     * 收到服务器发来的消息
     * @param {*} event 
     * @param {*} ws 
     */
    this.onmessage = function (event, ws, param) {
      var data = eval("("+event.data+")");
      let vue = param.vue;
      console.log(data)
      if("没有权限"==data){
        vue.$Message.error(data);
        tio.close()
      }
      if(data.chatFrom != vue.user.uid){
        vue.netSend(data)
      }
    }
  
    this.onclose = function (e, ws) {
      // error(e, ws)
    }
  
    this.onerror = function (e, ws) {
      // error(e, ws)
    }
  
    /**
     * 发送心跳，本框架会自动定时调用该方法，请在该方法中发送心跳
     * @param {*} ws 
     */
    this.ping = function (ws) {
      // log("发心跳了")
      ws.send('heartbeat')
    }
  }
  export default IMHandler