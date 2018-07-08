<template>
    <div>
        <Scroll ref="talkbox" class="talk-box" :height="msgBoxHeight" loading-text="加载中" :on-reach-top="topLoad">
            <template v-for="item in content">
                <Row type="flex" align="top"  >
                    <template v-if="item.isMe">
                        <Row class-name="talk-content right">
                            <div class="talk-title right">{{item.auth}}  {{item.date}}</div>
                            <span class="talk-message my-talk-color right">{{item.msg}}</span>
                        </Row>
                        <Row class-name="talk-head" >
                            <img :src='item.imgSrc?item.imgSrc:"/static/images/unknow.jpg"'>
                        </Row>
                    </template>
                    <template v-else>
                        <Row class-name="talk-head" >
                            <img :src='item.imgSrc?item.imgSrc:"/static/images/unknow.jpg"'>
                        </Row>
                        <Row class-name="talk-content">
                            <div class="talk-title">{{item.auth}}  {{item.date}}</div>
                            <span class="talk-message talk-color" >{{item.msg}}</span>
                        </Row>
                    </template>
                </Row>
            </template>
        </Scroll>
        <Row type="flex" justify="space-around" align="top">
            <Input ref="sendInput" v-model="msg" class="sendInput sendBox" size="large"></Input><Button @click='send()'  class="sendButton sendBox" type="primary" size="large">发送</Button>
        </Row>
        <Modal
            v-model="nameWindow"
            title="初始化名称"
            @on-ok="ok"
            @on-cancel="cancel">
         <Input v-model="username" placeholder="输入名称" style="width: 90%">
             <span slot="prepend">名称</span>
         </Input>
    </Modal>
    </div>
</template>
<script>
// let ws = new WebSocket("ws://localhost:8000/springboot/wchat");

export default {
  name: "chat",
  data() {
    return {
      ws: {},
      nameWindow: !!!window.localStorage.username,
      msg: "",
      msgBoxHeight: "600",
      username: window.localStorage.username,
      content: []
    };
  },
  created() {
    //设置页面高度
    const inputHeight = 65;
    this.msgBoxHeight =
      window.document.documentElement.clientHeight - inputHeight;
    window.onresize = () => {
      this.msgBoxHeight =
        window.document.documentElement.clientHeight - inputHeight;
    };
  },
  mounted() {
    const $this = this;

    this.connect();

    setTimeout(function() {
      //   $this.sendMessage(ws, "sssssssss");
    }, 1000);

    window.onkeydown = key => {
      if (key.keyCode === 13) {
        $this.send();
      }
    };

    //获取用户名
    // this.$ajax({
    //   method: "post",
    //   url: "/public/getIp"
    // }).then(function(res) {
    //   console.log(res);
    //   $this.username = res.data.ip;
    // });
  },
  methods: {
    //连接服务器
    connect: function() {
      const $this = this;
      this.ws = this.Stomp.over(
        new this.SockJS("http://www.northzx.net:60001/websocket")
        // new this.SockJS("http://127.0.0.1:8000/websocket")
      );
      let ws = this.ws;
      ws.debug = null;
      ws.connect(
        {
          username: "admin",
          password: "admin"
        },
        function connectCallback(frame) {
          // setConnected(true);
          ws.subscribe(
            "/app/subscribe",
            function(greeting) {
              //   console.log("订阅成功");
            },
            {}
          );
          ws.subscribe(
            "/chat/hello",
            function(msg) {
              var body = msg.body;
              if (body) {
                body = eval("(" + body + ")");
                console.log(body.content.auth)
                console.log($this.username)
                if (body.content.auth != $this.username) {
                  $this.netSend(body.content);
                }
              }
              $this.scrollToBottom();
            },
            {}
          );
          function errorCallBack(error) {
            // 连接失败时（服务器响应 ERROR 帧）的回调方法
            console.log("连接失败");
            setTimeout(function() {
              $this.content();
            }, 3000);
          }
        }
      );
    },
    //发送消息
    send: function() {
      if (!this.msg) {
        return;
      }
      let $this = this;
      let message = {
        auth: this.username,
        date: new Date().Format("yyyy-MM-dd hh:mm:ss"),
        msg: this.msg,
        imgSrc: "",
        isMe: true
      };

      if (this.msg) {
        this.content.push(message);
        this.sendMessage(message);
      }
      this.msg = "";

      $this.$refs.sendInput.focus();
      this.scrollToBottom();
    },
    netSend(content) {
      if (content) {
        content.isMe = content.auth == this.username;
        this.content.push(content);
      } else {
        return;
      }

      this.scrollToBottom();
    },
    //上拉加载
    topLoad: function() {
      return new Promise(resolve => {
        setTimeout(function(params) {
          resolve();
        }, 3000);
      });
    },
    //发送消息
    sendMessage: function(msg) {
      this.ws.send("/app/hello", {}, JSON.stringify(msg));
    },
    //翻页到最后
    scrollToBottom: function() {
      setTimeout(function() {
        document.getElementsByClassName(
          "ivu-scroll-container"
        )[0].scrollTop = document.getElementsByClassName(
          "ivu-scroll-container"
        )[0].scrollHeight;
      }, 50);
    },
    ok:function () {
        window.localStorage.username = this.username
    },
    cancel:function () {
        
    }

  }
};
</script>

<style lang="less" scoped>
.sendBox {
  margin: 0.5rem;
}
.sendInput {
  width: calc(100% - 7rem);
}
.sendButton {
  width: 5rem;
}
.right {
  text-align: right;
}
.talk-box {
  margin: 5px 10px 10px;
  li {
    list-style: none;
  }
  .talk-head {
    width: 3rem;
    text-align: center;
    img {
      margin-top: 10px;
      width: 40px;
      height: 40px;
      border-radius: 40px;
    }
  }
  .talk-content {
    width: calc(100% - 4rem);
    word-wrap: break-word;
    word-break: break-all;

    .talk-title {
      font-size: 1rem;
    }
    .talk-message {
      font-size: 1rem;
      padding: 5px 10px 5px;
      border-radius: 10px;
    }
    .talk-color {
      background-color: #e0e0e0;
    }
    .my-talk-color {
      background-color: #66ccff;
    }
  }
}
</style>
