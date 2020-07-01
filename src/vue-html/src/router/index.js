import Vue from "vue";
import Router from "vue-router";
// import Home from "./views/Home.vue";

const HelloWorld = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/HelloWorld.vue")),
    "HelloWorld"
  );
const homeindex = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/home/index.vue")),
    "homeindex"
  );
  
const login = resolve =>
  require.ensure([], () => resolve(require("@/components/login.vue")), "login");
const index = resolve =>
  require.ensure([], () => resolve(require("@/components/index.vue")), "index");
const sysmenu = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/sys/menu/sysmenu.vue")),
    "sysmenu"
  );
const sysrole = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/sys/role/sysrole.vue")),
    "sysrole"
  );
const sysuser = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/sys/user/sysuser.vue")),
    "sysuser"
  );
const syslog = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/sys/log/syslog.vue")),
    "syslog"
  );
Vue.use(Router);

const router = new Router({
  // mode: 'history',
  routes: [
    {
      path: "/login",
      name: "login",
      component: login
    },
    {
      path: "/",
      name: "first",
      component: login,
      meta: {
        // title: "在线聊天",
        viewport:
          "width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0"
      }
    },
    {
      path: "/home",
      name: "homeindex",
      component: index,
      children: [
        {
          path: "index",
          name: "homeindex",
          component: homeindex
        }
      ]
    },
    {
      path: "/sys",
      name: "sysindex",
      component: index,
      children: [
        {
          path: "menu",
          name: "sysmenu",
          component: sysmenu
        },
        {
          path: "role",
          name: "sysrole",
          component: sysrole
        },
        {
          path: "user",
          name: "sysuser",
          component: sysuser
        },
        {
          path: "log",
          name: "syslog",
          component: syslog
        }
        
      ]
    },
    
  ]
});

router.beforeEach((to, from, next) => {
  let username = sessionStorage.getItem('username');
  let password = sessionStorage.getItem('password');
  if (username && password) {

  }
  if (to.meta.title) {
    document.title = to.meta.title
  }

  next();
});

export default router
