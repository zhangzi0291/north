import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";

const HelloWorld = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/HelloWorld.vue")),
    "HelloWorld"
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

const chatframe = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/chat/chatframe.vue")),
    "chatframe"
  );
const chatbox = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/chat/chat.vue")),
    "chat"
  );
const chatindex = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/chat/index.vue")),
    "chatindex"
  );
const chatlogin = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/chat/login.vue")),
    "chatlogin"
  );

const blogindex = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/blog/index.vue")),
    "blogindex"
  );
const bloghome = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/blog/home.vue")),
    "bloghome"
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
      component: chatframe,
      meta: {
        title: "在线聊天",
        viewport:
          "width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0"
      }
    },
    {
      path: "/home",
      name: "index",
      component: index,
      children: [
        {
          path: "index",
          name: "index",
          component: HelloWorld
        }
      ]
    },
    {
      path: "/sys",
      name: "index",
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
        }
      ]
    },
    {
      path: "/chat",
      name: "chat",
      component: chatframe,
      children: [
        {
          path: "box",
          name: "chatbox",
          component: chatbox,
        },
        {
          path: "index",
          name: "chatindex",
          component: chatindex,
        },
        {
          path: "login",
          name: "chatlogin",
          component: chatlogin,
        },
      ]
    },
    {
      path: "/blog",
      name: "blog",
      component: blogindex,
      children: [
        {
          path: "home",
          name: "home",
          component: bloghome
        }
      ]
    }
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
