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

const gisstatistic = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/rework/gisstatistic.vue")),
    "gisstatistic"
  );

const reworklist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/rework/reworklist.vue")),
    "reworklist"
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
      path: "/rework",
      name: "index",
      component: index,
      children: [
        {
          path: "gisstatistic",
          name: "gisstatistic",
          component: gisstatistic
        },
        {
          path: "reworklist",
          name: "reworklist",
          component: reworklist
        },
      ]
    },
    {
      path: "/gisstatistic",
      name: "gisstatistic",
      component: gisstatistic
    },
    {
      path: "/reworklist",
      name: "reworklist",
      component: reworklist
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
