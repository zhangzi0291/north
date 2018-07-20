<template>
  <div>
    <chat-header>
        <Dropdown slot="left-button" trigger="click" placement="bottom" @on-click="dropdownEvent"> 
          <Icon class="head-setting" type="android-more-horizontal" size="26" color="#fff" ></Icon>
          <DropdownMenu slot="list" >
              <DropdownItem name="login">登陆</DropdownItem>
              <DropdownItem name="register">注册</DropdownItem>
          </DropdownMenu>
        </Dropdown>
    </chat-header>
    <Scroll ref="talkbox" class="talk-box" :height="msgBoxHeight" loading-text="加载中" :on-reach-top="topLoad">
      <template v-for="item in content">
        <Row type="flex" align="top">
          <template v-if="item.isMe">
            <Row class-name="talk-content right">
              <div class="talk-title right">{{item.auth}} {{item.date}}</div>
              <div class="talk-message my-talk-color right"  v-html="item.msg"></div>
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
              <div class="talk-message talk-color" v-html="item.msg"></div>
            </Row>
          </template>
        </Row>
      </template>
    </Scroll>
    <Row type="flex" justify="space-around" align="top">
      <Input ref="sendInput" v-model="msg" class="sendInput sendBox" size="large"></Input>
      <Button @click='send()' class="sendButton sendBox" type="primary" size="large">发送</Button>
    </Row>
    <Modal v-model="loginWindow" title="登陆" @on-ok="loginOk" @on-cancel="loginCancel" :mask-closable="false">
      <Form :label-width="80">
        <FormItem label="用户名："> 
          <Input v-model="username" placeholder="输入用户名" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="密码：">
          <Input v-model="password" type="password" placeholder="输入密码" clearable style="width: 90%"></Input>
        </FormItem>
      </Form>
    </Modal>
    <Modal v-model="registerWindow" title="注册" @on-ok="registerOk" @on-cancel="registerCancel" :mask-closable="false">
      <Form :label-width="80">
        <FormItem label="用户名："> 
          <Input v-model="registerUser.username" placeholder="输入用户名" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="密码：">
          <Input v-model="registerUser.password" type="password" placeholder="输入密码" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="昵称："> 
          <Input v-model="registerUser.name" placeholder="输入昵称" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="手机号："> 
          <Input v-model="registerUser.mobile" placeholder="输入手机号" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="电子邮件："> 
          <Input v-model="registerUser.email" placeholder="输入电子邮件" clearable style="width: 90%"></Input>
        </FormItem>
        <FormItem label="头像Url："> 
          <Input v-model="registerUser.imgSrc" :readonly="isUpload" placeholder="输入头像Url，上传图片大小在1MB之内" style="width: 90%">
            <Button slot="append" >
              <Icon type="android-upload" size="18">
              </Icon>
              <div style="filter:alpha(opacity=0);cursor: pointer; opacity: 0; position: absolute;  width: 50px;height:34px;overflow: hidden;top:0;right:0; ">
                <input type="file" id="imgFile" @change="uploadFile()" accept="image/*" style="font-size: 200px;cursor: pointer;direction: rtl ！important; float: right\9; "/>
              </div>
            </Button>
          </Input>
        </FormItem>
        <img id="preImg" :src="preImg" style="width:100%">
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
      title:"在线聊天",//13字一行
      msgNum:0,
      isNowWindow:document.hidden,
      ws: undefined,
      loginWindow: false,//!!!window.localStorage.username,
      registerWindow: false,
      msg: "",
      msgBoxHeight: "600",
      username: "",
      password: "",    
      user:{
        name:"",
        uid:"",
        gid:"",
      },
      registerUser:{
        status:1,
        roleId:8
      },
      content: [],
      preImg:"",
      isUpload:false,
    };
  },
  created() {
    //设置页面高度
    const inputHeight = 65+50;
    this.msgBoxHeight =
      window.document.documentElement.clientHeight - inputHeight;
    window.onresize = () => {
      this.msgBoxHeight =
        window.document.documentElement.clientHeight - inputHeight;
    };
    
  },
  mounted() {
    const $this = this;
    document.addEventListener("visibilitychange",function(){
      $this.isNowWindow = document.hidden
      if(!!!document.hidden){
        document.title = $this.title
      }
    })

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
      const sock = new this.SockJS(this.baseURL+"/websocket");
      // const sock =new this.SockJS("http://www.northzx.net:60001/websocket");
      this.ws = this.Stomp.over(sock);
      let ws = this.ws;
      // ws.debug = null;

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
              console.log(body)
              $this.user.name = body.name
              $this.user.uid = body.uid
              $this.user.imgSrc = body.imgSrc
            },
            {}
          );
          //获取系统推送信息
          ws.subscribe(
            "/sys/msg",
            function(msg) {
               let body = msg.body;
               if(body) {
                  let body = eval("("+msg.body+")")
                  $this.$Message.error(body.msg);
                  if(body.code == 401){
                    ws.disconnect();
                    $this.ws = undefined;
                  }
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
                  if($this.isNowWindow){
                    $this.msgNum++
                  }
                  if($this.msgNum>0){
                    document.title=$this.title+"(未读消息数"+$this.msgNum+")"
                  }
                  
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
        imgSrc: this.user.imgSrc,
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
    uploadFile:function () {
      let $this = this;
      let imgFile = document.getElementById('imgFile').files[0];
      if(imgFile.size>1024*1024*1){
        $this.$Message.error("图片大小在1MB之内");
        return;
      }
      let reader = new FileReader();
      reader.readAsDataURL(imgFile)
      reader.onloadend = function () {
        let dataURL = reader.result;
        $this.registerUser.imgSrc = dataURL
        $this.preImg = dataURL
        $this.isUpload = true
      };
    },
    loginOk: function() {
      this.connect();
    },
    loginCancel: function() {
      this.checkWS()
    },
    registerOk: function(){
      const $this = this;
      this.$ajax({
        method: "post",
        url: "/sys/user/add",
        data: this.registerUser
      }).then(function(res) {
        console.log(res);
        let code = res.data.code
        if(code == "200"){
          $this.$Message.success("注册成功");
        }else{
          $this.$Message.error(res.data.msg);
        }
      });
    },
    registerCancel: function(){
      this.checkWS()
    },
    dropdownEvent: function(name){
      switch (name){
        case "register":
          this.registerWindow = true;
          break;
        case "login":
          this.loginWindow = true;
          break;
        default:
          break;

      }
    }
  },
  watch: {
    isNowWindow:function (newVal, oldVal) {
      if(!!!newVal){
        this.msgNum = 0;
      }
    }
  }
};
</script>
<style lang="less" scoped>
.dropdown{
//     width: 100px;
  .ivu-select-dropdown{
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
      display:inline-block; 
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
