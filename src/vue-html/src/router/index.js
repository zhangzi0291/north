import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import login from '@/components/login'
import chat from '@/components/chat/chat'
import index from '@/components/index'
import sysmenu from '@/components/sys/menu/sysmenu'
import sysrole from '@/components/sys/role/sysrole'
import sysuser from '@/components/sys/user/sysuser'
import blogindex from '@/components/blog/index'
import bloghome from '@/components/blog/home'

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
      component: login
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
