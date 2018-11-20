<template>
  <div class='maxheigh'>
    <Layout class='maxheigh'>
      <Header>Header</Header>
      <Content class='layout-content'>

        <Card class='login-card' @keyup="login">
          <div style="text-align:center">
            <p class='login-tilte' slot="title">登录</p>
            <div class='login-content'>
              <Form ref="form" :model="user" :rules="rule">
                <FormItem prop="username">
                  <Input class='login-input' v-model="user.username" :value="user.username" clearable autofocus>
                  <span slot="prepend">
                    <Icon type="md-person" />
                  </span>
                  </Input>
                </FormItem>
                <FormItem prop="password">
                  <Input class='login-input' v-model="user.password" :type='passtype' :icon='passicon' :value="user.password" @on-click='showPassword'>
                  <span slot="prepend">
                    <Icon type="md-lock" />
                  </span>
                  </Input>
                </FormItem>
                <FormItem prop="user">
                  <Checkbox v-model="remember">记住密码</Checkbox><br>
                </FormItem>
                <FormItem prop="user" style="text-align:center">
                  <Button class='login-button' type="primary" @click="login">登录</Button>
                </FormItem>
              </Form>
            </div>
          </div>
        </Card>

      </Content>
    </Layout>
  </div>
</template>
<script>
export default {
  data() {
    return {
      remember: true,
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
  created() {
    let $this = this;
    document.onkeyup = function(e) {
      let key = e.keyCode;
      if (key == 13) {
        $this.login();
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
  methods: {
    showPassword: function() {
      this.isShow = this.isShow ? false : true;
    },
    login: function() {
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
          if ($this.remember) {
            localStorage.username = $this.user.username;
            localStorage.password = $this.user.password;
          } else {
            localStorage.username = "";
            localStorage.password = "";
          }
          let loginparam = {
            user: res.data.user
          }
          $this.$store.commit("LOGIN", loginparam);
          $this.$router.push("/home");
        } else {
          $this.$Message.error(res.data.msg);
        }
      });
    }
  }
};
</script>
<style scoped>
.layout-content {
  height: calc(100% - 24px);
}

.login-card {
  width: 300px;
  top: 30%;
  margin-left: auto;
  margin-right: auto;
}

.login-tilte {
  font-size: 30px;
  font-weight: bold;
}

.login-content {
  text-align: left;
}

.login-content>* {
  width: 266px;
  /* margin-bottom: 100px; */
}
</style>
