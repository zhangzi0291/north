<template>
  <Row type="flex" style="height:calc(100% - 50px)">
    <Header class="chat-header"> <img class="chat-logo" src="/images/logo.jpg"></Header>
    <Content class="chat-content">
      <Row type="flex" justify="center" :align="rowAlign" style="height:100% ">
        <Form ref="form" :model="user" :rules="rule">
          <Row type="flex" justify="center" align="middle">
            <Col span="18" class="chat-logn-input">
            <FormItem prop="username">
              <Input v-model="user.username" :value="user.username" clearable autofocus>
              <span slot="prepend">
                <Icon type="md-person" />
              </span>
              </Input>
            </FormItem>
            </Col>
            <Col span="18" class="chat-logn-input">
            <FormItem prop="password">
              <Input v-model="user.password" :type='passtype' :icon='passicon' :value="user.password" @on-click='showPassword'>
              <span slot="prepend">
                <Icon type="md-lock" />
              </span>
              </Input>
            </FormItem>
            </Col>
            <Col span="18" class="chat-logn-input">
            <Button type="primary" long @click="login">登录</Button>
            </Col>
          </Row>
        </Form>
      </Row>
    </Content>
    <Footer class="chat-footer">Power By North</Footer>
  </Row>
</template>
<script>
export default {
  name: "ChatHeader",
  data() {
    return {
      title: "在线聊天", //13字一行
      rowAlign: "middle",
      user: {
        username: localStorage.username,
        password: localStorage.password
      },
      isShow: true,
      rule: {
        username: [{ required: true, message: "用户名必填", trigger: "blur" }],
        password: [{ required: true, message: "密码必填", trigger: "blur" }]
      }
    };
  },
  computed: {
    passicon: function() {
      return this.isShow ? "md-eye" : "md-eye-off";
    },
    passtype: function() {
      return this.isShow ? "password" : "text";
    }
  },
  created() {
    this.changeAlign()
    window.onresize = () => {
      this.changeAlign()
    };
    //设置页面高度
    // const inputHeight = 65+50;
    // this.msgBoxHeight =
    //   window.document.documentElement.clientHeight - inputHeight;
    // window.onresize = () => {
    //   this.msgBoxHeight =
    //     window.document.documentElement.clientHeight - inputHeight;
    // };
  },
  mounted() { },
  methods: {
    changeAlign() {
      if (window.document.documentElement.clientHeight > 800) {
        this.rowAlign = "top"
      } else {
        this.rowAlign = "middle"
      }
    },
    showPassword() {
      this.isShow = this.isShow ? false : true;
    },
    login() {
      let flag = false;
      this.$refs.form.validate(valid => {
        if (!valid) {
          flag = true;
        }
      });
      if (flag) {
        return;
      }
      let $this = this;
      this.$ajax({
        method: "POST",
        url: "/sys/login",
        data: {
          username: $this.user.username,
          password: $this.user.password
        }
      }).then(function(res) {
        if (res.data.code == "200") {
          const data = {
            vue: $this,
            user: res.data.user
          }
          $this.$store.commit("CONNECTWS", data);
          if ($this.remember) {
            localStorage.username = $this.user.username;
            localStorage.password = $this.user.password;
          } else {
            localStorage.username = "";
            localStorage.password = "";
          }
          let loginparam = {
            user:res.data.user,
            token:res.data.accessToken
          }
          $this.$store.commit("LOGIN", loginparam);
          $this.$router.push("/chat/box");
        } else {
          $this.errorModal(res.data.msg);
        }
      });
    }
  }
};
</script>
<style lang="less">
.chat-header {
  margin-top: 1rem;
  background-color: #fff;
  // background-color: #f0f0f0;
  text-align: center;
  width: 100%;
  height: 10rem;
}

.chat-logo {
  height: 100%;
}

.chat-content {
  text-align: center;
  height: calc(100% - 10rem - 6rem);
}

.chat-logn-input {
  margin-top: 1rem;
}

.chat-footer {
  text-align: center;
  font-weight: bold;
  width: 100%;
}
</style>
