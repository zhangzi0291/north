<template>
  <div>
    <Scroll ref="talkbox" class="talk-box" :height="msgBoxHeight" loading-text="加载中" :on-reach-top="topLoad">
      <template v-for="item in content">
        <Row type="flex" align="top">
          <template v-if="item.isMe">
            <Row class-name="talk-content right">
              <div class="talk-title right">{{item.auth}} {{item.date}}</div>
              <span class="talk-message my-talk-color right">{{item.msg}}</span>
            </Row>
            <Row class-name="talk-head">
              <img :src='item.imgSrc?item.imgSrc:"/static/images/unknow.jpg"'>
            </Row>
          </template>
          <template v-else>
            <Row class-name="talk-head">
              <img :src='item.imgSrc?item.imgSrc:"/static/images/unknow.jpg"'>
            </Row>
            <Row class-name="talk-content">
              <div class="talk-title">{{item.auth}} {{item.date}}</div>
              <span class="talk-message talk-color">{{item.msg}}</span>
            </Row>
          </template>
        </Row>
      </template>
    </Scroll>
    <Row type="flex" justify="space-around" align="top">
      <Input ref="sendInput" v-model="msg" class="sendInput sendBox" size="large"></Input>
      <Button @click='send()' class="sendButton sendBox" type="primary" size="large">发送</Button>
    </Row>
    <Modal v-model="nameWindow" title="登陆" @on-ok="ok" @on-cancel="cancel" :mask-closable="false">
      <Form :label-width="80">
        <FormItem label="用户名："> 
          <Input v-model="username" placeholder="输入用户名" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="密码：">
          <Input v-model="password" type="password" placeholder="输入密码" clearable style="width: 90%"></Input>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
// let ws = new WebSocket("ws://localhost:8000/springboot/wchat");

export default {
  name: "chat",
  data() {
    return {
      ws: undefined,
      nameWindow: true,//!!!window.localStorage.username,
      msg: "",
      msgBoxHeight: "600",
      username: "",
      password: "",    
      user:{
        name:"",
        uid:"",
      },
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
    //   $this.user.name = res.data.ip;
    // });
  },
  methods: {
    //连接服务器
    connect: function() {
      const $this = this;
      if(!!!this.username&&!!!this.password){
         this.$Message.error("没有用户名密码登陆");
         return
      }
      const sock = new this.SockJS("http://127.0.0.1:8000/websocket");
      // const sock =new this.SockJS("http://www.northzx.net:60001/websocket");
      this.ws = this.Stomp.over(sock);
      let ws = this.ws;
      ws.debug = null;

      ws.connect(
        {
          username: $this.username,
          password: $this.password
        },
        function connectCallback(frame) {
          //获取登陆后的用户信息
          ws.subscribe(
            "/app/subscribe",
            function(msg) {
              let body = eval("("+msg.body+")")
              $this.user.name = body.name
              $this.user.uid = body.uid
            },
            {}
          );
          //获取系统推送信息
          ws.subscribe(
            "/sys/msg",
            function(msg) {
               let body = msg.body;
               if(body) {
                 $this.$Message.error(body);
               }
            },
            {}
          );
          //获取聊天信息
          ws.subscribe(
            "/chat/msg",
            function(msg) {
              let body = msg.body;
              if (body) {
                body = eval("(" + body + ")");
                if (body.content.auth != $this.user.name) {
                  $this.netSend(body.content);
                }
              }
              $this.scrollToBottom();
            },
            {}
          );

        },
        function errorCallBack(error) {
          console.log("连接失败");
          //3秒后重连
          setTimeout(function() {
            $this.connect();
          }, 3000);
        }
      );

    },
    //发送消息
    send: function() {
      if (!this.msg) {
        return;
      }
      if(this.checkWS()){
        return
      }
      let $this = this;
      let message = {
        auth: this.user.name,
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
    //发送网络消息
    netSend: function(content) {
      if (content) {
        content.isMe = content.auth == this.user.name;
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
      this.ws.send("/app/msg", {}, JSON.stringify(msg));
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
    //检查websocket连接
    checkWS: function() {
      if(!!!this.ws){
        this.$Message.error("未连接服务器");
      }
      return !!!this.ws
    },
    ok: function() {
      this.connect();
    },
    cancel: function() {
      this.checkWS()
    }

  },
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
