<template>
  <div>
    
    <chat-box :chatBoxHeight="msgBoxHeight" :content="content" :user="user"></chat-box>
    <Row type="flex" justify="space-around" align="top">
      <Input ref="sendInput" @on-enter="sendMsg()" v-model="msg" class="sendInput sendBox" size="large"/>
      <Button @click='sendMsg()' class="sendButton sendBox" type="primary" size="large">发送</Button>
    </Row>
    
  </div>
</template>
<script>


import chatBox from "@/components/chat/chatbox";
export default {
  name: "Chat",
  components: {
    chatBox,
  },
  data() {
    return {
      title: "在线聊天", //13字一行
      msgNum: 0,
      isNowWindow: document.hidden,
      ws: undefined,
      msg: "",
      msgBoxHeight: "600",
      username: "",
      password: "",
      loginWindow: false,
      registerWindow: false,
      user: {
        name: "",
        uid: "",
        gid: ""
      },
      registerUser: {
        status: 1,
        roleId: 8
      },
      content: [],
      preImg: "",
      token: ""
    };
  },
  created() {
    //设置页面高度
    const inputHeight = 100;
    this.msgBoxHeight =
      window.document.documentElement.clientHeight - inputHeight;
    window.onresize = () => {
      this.msgBoxHeight =
        window.document.documentElement.clientHeight - inputHeight;
    };
   
  },
  mounted() {
    const $this = this;
    document.addEventListener("visibilitychange", function() {
      $this.isNowWindow = document.hidden;
      if (!document.hidden) {
        document.title = $this.title;
      }
    });

  },
  methods: {
    //连接服务器
    connect: function() {
      const $this = this;
      if (!this.username && !this.password) {
        this.$Message.error("没有用户名密码登陆");
        return;
      }
      if(!!$this.ws){
        $this.ws.close();
      }
      this.$ajax({
        method: "POST",
        url: "/sys/login",
        data: {
          username: $this.username,
          password: $this.password
        }
      }).then(function(res) {

        if (res.data.code == "200") {
          $this.token = res.data.accessToken;
          $this.user.uid = res.data.user.id;
          $this.user.name = res.data.user.name;
          let param = { token : $this.token};
          let param2 = { 
            vue: $this,
            username: $this.user.name,
            content:$this.content,
            userId: $this.user.uid,
          };
          $this.ws = new tio.ws($this.wsURL,param,param2,new handle());
          $this.ws.connect($this.ws);
        } else {
          $this.errorModal(res.data.msg);
        }
      });
    },
    //发送消息
    sendMsg: function() {
      if (!this.msg) {
        return;
      }
      if (this.checkWS()) {
        return;
      }
      let $this = this;
      let message = {
        chatMsg: this.msg,
        chatFrom: this.user.uid,
        chatTo: "",
        updateTime: new Date().Format("yyyy-MM-dd hh:mm:ss"),
        chatType: "",
        chatMsgType: 0 
      }
      if (this.msg) {
          this.ws.send(JSON.stringify(message));
          this.content.push(message);
      }
      this.msg = "";

      $this.$refs.sendInput.focus();

    },
    //发送网络消息
    netSend: function(content) {
      if (content) {
        // content.isMe = content.auth == this.user.name;
        this.content.push(content);
      } else {
        return;
      }
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
    // sendMessage: function(msg) {
    //   this.ws.send("/app/msg", {}, JSON.stringify(msg));
    // },
    //检查websocket连接
    checkWS: function() {
      if (!this.ws) {
        this.$Message.error("未连接服务器");
      }
      return !this.ws;
    },
    uploadFile: function() {
      let $this = this;
      let imgFile = document.getElementById("imgFile").files[0];
      if (imgFile.size > 1024 * 1024 * 1) {
        $this.$Message.error("图片大小在1MB之内");
        return;
      }
      let reader = new FileReader();
      reader.readAsDataURL(imgFile);
      reader.onloadend = function() {
        let dataURL = reader.result;
        $this.registerUser.imgSrc = dataURL;
        $this.preImg = dataURL;
        $this.isUpload = true;
      };
    },
  },
  watch: {
    isNowWindow: function(newVal, oldVal) {
      if (!newVal) {
        this.msgNum = 0;
      }
    }
  }
};
</script>
<style lang="less" scoped>
.dropdown {
  //     width: 100px;
  .ivu-select-dropdown {
    width: 1000px;
  }
}

.sendBox {
  margin: 0.5rem;
}

.sendInput {
  width: calc(100% - 7rem);
}

.sendButton {
  width: 5rem;
}

</style>
