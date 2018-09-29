<template>
<Row  class-name="chat-frame-body" >
    <chat-header>
      <Button slot="left-button" to @click="$router.go(-1)" type="text" size="large" ghost shape="circle" icon="md-arrow-back" ></Button>
      <Dropdown 
        slot="right-button" 
        trigger="click" 
        placement="bottom" 
        @on-click="dropdownEvent"> 
        <Icon 
          class="head-setting" 
          type="md-more" 
          size="26" 
          color="#fff" />
        <DropdownMenu slot="list" >
          <DropdownItem name="login">登陆</DropdownItem>
          <DropdownItem name="register">注册</DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </chat-header>
    <router-view ></router-view>

    <Modal 
      v-model="loginWindow" 
      title="登陆" 
      @on-ok="loginOk" 
      @on-cancel="loginCancel" 
      :mask-closable="false">
      <Form :label-width="80">
        <FormItem label="用户名："> 
          <Input @on-enter="loginOk" 
            v-model="username"  
            placeholder="输入用户名" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="密码：">
          <Input @on-enter="loginOk" 
            v-model="password" 
            type="password" 
            placeholder="输入密码" 
            clearable 
            style="width: 90%"/>
        </FormItem>
      </Form>
    </Modal>
    <Modal 
      v-model="registerWindow" 
      title="注册" 
      @on-ok="registerOk" 
      @on-cancel="registerCancel" 
      :mask-closable="false">
      <Form :label-width="80">
        <FormItem label="用户名："> 
          <Input 
            v-model="registerUser.username" 
            placeholder="输入用户名" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="密码：">
          <Input 
            v-model="registerUser.password" 
            type="password" 
            placeholder="输入密码" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="昵称："> 
          <Input 
            v-model="registerUser.name" 
            placeholder="输入昵称" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="手机号："> 
          <Input 
            v-model="registerUser.mobile" 
            placeholder="输入手机号" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="电子邮件："> 
          <Input 
            v-model="registerUser.email" 
            placeholder="输入电子邮件" 
            clearable 
            style="width: 90%"/>
        </FormItem>
        <FormItem label="头像Url："> 
          <Input 
            v-model="registerUser.imgSrc" 
            :readonly="isUpload" 
            placeholder="输入头像Url，上传图片大小在1MB之内" 
            style="width: 90%">
          <Button slot="append" >
            <Icon 
              type="md-cloud-upload" 
              size="20"/>
            <div style="filter:alpha(opacity=0);cursor: pointer; opacity: 0; position: absolute;  width: 50px;height:34px;overflow: hidden;top:0;right:0; ">
              <input 
                type="file" 
                id="imgFile" 
                @change="uploadFile()" 
                accept="image/*" 
                style="font-size: 200px;cursor: pointer;direction: rtl ！important; float: right\9; ">
            </div>
          </Button>
          </Input>
        </FormItem>
        <img 
          id="preImg" 
          :src="preImg" 
          style="width:100%">
      </Form>
    </Modal>

 </Row>
</template>
<script>
export default {
  data(){
    return {
      username: "",
      password: "",
      loginWindow: false,
      registerWindow: false,
      registerUser: {
        status: 1,
        roleId: 8
      },
      isUpload: false,
      preImg: "",
    }
  },
  methods: {
    loginOk: function() {
      this.loginWindow = false
      const $this = this;
      if (!this.username && !this.password) {
        this.$Message.error("没有用户名密码登陆");
        return;
      }
      this.$ajax({
        method: "POST",
        url: "/sys/login",
        data: {
          username: $this.username,
          password: $this.password
        }
      }).then(function(res) {
        const data = {
          vue: $this,
          res: res
        }
        $this.$store.commit("CONNECTWS", data);
        console.log($this.$store)
        if (res.data.code == "200") {
          let loginparam = {
            user:res.data.user,
            token:res.data.accessToken
          }
          $this.$store.commit("LOGIN", loginparam);
        } else {
          $this.errorModal(res.data.msg);
        }
      });
      
    },
    loginCancel: function() {

    },
    registerOk: function() {
      const $this = this;
      this.$ajax({
        method: "post",
        url: "/sys/user/add",
        data: this.registerUser
      }).then(function(res) {
        let code = res.data.code;
        if (code == "200") {
          $this.$Message.success("注册成功");
        } else {
          $this.$Message.error(res.data.msg);
        }
      });
    },
    registerCancel: function() {

    },
    dropdownEvent: function(name) {
      switch (name) {
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
  }
};
</script>
<style scoped>
.chat-frame-body{
  overflow-x: hidden;
  height: 100%;
}


</style>
