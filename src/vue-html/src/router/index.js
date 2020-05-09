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

const partnerinfolist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/partnerinfo/partnerinfolist.vue")),
    "partnerinfolist"
  );
const employeeinfolist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/employeeinfo/employeeinfolist.vue")),
    "employeeinfolist"
  );
const materiallist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/material/materiallist.vue")),
    "materiallist"
  );
const wiredscheduledplanlist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/wiredscheduledplan/wiredscheduledplanlist.vue")),
    "wiredscheduledplanlist"
  );
const wirelessscheduledplanlist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/wirelessscheduledplan/wirelessscheduledplanlist.vue")),
    "wirelessscheduledplanlist"
  );
  
const statchartlist = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/statchart/statchartlist.vue")),
    "statchartlist"
  );
const statchart = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/statchart/statchart.vue")),
    "statchart"
  );
const demo = resolve =>
  require.ensure(
    [],
    () => resolve(require("@/components/demo/demo.vue")),
    "demo"
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
        },
        {
          path: "index2",
          name: "homeindex2",
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
        }
      ]
    },
    {
      path: "/rework",
      name: "reworkindex",
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
      path: "/partnerinfo",
      name: "partnerinfoindex",
      component: index,
      children: [
        {
          path: "list",
          name: "partnerinfolist",
          component: partnerinfolist
        },
      ]
    },
    {
      path: "/employeeinfo",
      name: "employeeinfoindex",
      component: index,
      children: [
        {
          path: "list",
          name: "employeeinfolist",
          component: employeeinfolist
        },
      ]
    },
    {
      path: "/material",
      name: "materialindex",
      component: index,
      children: [
        {
          path: "list",
          name: "materiallist",
          component: materiallist
        },
      ]
    },
    {
      path: "/wired",
      name: "wiredindex",
      component: index,
      children: [
        {
          path: "list",
          name: "wiredscheduledplanlist",
          component: wiredscheduledplanlist
        },
      ]
    },
    {
      path: "/wireless",
      name: "wirelessindex",
      component: index,
      children: [
        {
          path: "list",
          name: "wirelessscheduledplanlist",
          component: wirelessscheduledplanlist
        },
      ]
    },
    {
      path: "/statchart",
      name: "statchartindex",
      component: index,
      children: [
        {
          path: "list",
          name: "statchartlist",
          component: statchartlist
        },
        {
          path: "chart",
          name: "statchart",
          component: statchart
        },
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
