import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
// import login from '@/components/login'
// import chat from '@/components/chat/chat'
// import index from '@/components/index'
// import sysmenu from '@/components/sys/menu/sysmenu'
// import sysrole from '@/components/sys/role/sysrole'
// import sysuser from '@/components/sys/user/sysuser'
// import blogindex from '@/components/blog/index'
// import bloghome from '@/components/blog/home'

const HelloWorld = resolve => require.ensure([], () => resolve(require('@/components/HelloWorld.vue')),"HelloWorld");
const login = resolve => require.ensure([], () => resolve(require('@/components/login.vue')),"login");
const chat = resolve => require.ensure([], () => resolve(require('@/components/chat/chat.vue')),"chat");
const index = resolve => require.ensure([], () => resolve(require('@/components/index.vue')),"index");
const sysmenu = resolve => require.ensure([], () => resolve(require('@/components/sys/menu/sysmenu.vue')),"sysmenu");
const sysrole = resolve => require.ensure([], () => resolve(require('@/components/sys/role/sysrole.vue')),"sysrole");
const sysuser = resolve => require.ensure([], () => resolve(require('@/components/sys/user/sysuser.vue')),"sysuser");
const blogindex = resolve => require.ensure([], () => resolve(require('@/components/blog/index.vue')),"blogindex");
const bloghome = resolve => require.ensure([], () => resolve(require('@/components/blog/home.vue')),"bloghome");

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/',
      name: 'first',
      component: chat,
      meta:{
        title:"在线聊天",
        viewport:"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0"
      }
    },
    {
      path: '/home',
      name: 'index',
      component: index,
      children: [
        {
          path: 'index',
          name: 'index',
          component: HelloWorld,
        },
      ]
    },
    {
      path: '/sys',
      name: 'index',
      component: index,
      children: [
        {
          path: 'menu',
          name: 'sysmenu',
          component: sysmenu,
        },
        {
          path: 'role',
          name: 'sysrole',
          component: sysrole,
        },
        {
          path: 'user',
          name: 'sysuser',
          component: sysuser,
        },
      ]
    },
    {
      path: '/chat',
      name: 'chat',
      component: chat
    },
    {
      path: '/blog',
      name: 'blog',
      component: blogindex,
      children: [
        {
          path: 'home',
          name: 'home',
          component: bloghome,
        },
      ]
    }
  ]
})
