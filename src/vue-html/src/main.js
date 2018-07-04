// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import store from './store/store'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css';
import commons from '@/plugin/commons'
import axiosconfig from '@/plugin/axiosconfig'
import axios from 'axios'
import {globalMethods} from '@/plugin/mixin.js'

Vue.config.debug = true;
Vue.config.productionTip = false
Vue.use(commons)
Vue.use(iView)
Vue.use(axiosconfig)
Vue.mixin(globalMethods)

router.beforeEach((to, from, next) => {
  let username = sessionStorage.getItem('username');
  let password = sessionStorage.getItem('password');
  if (username && password) {

  }
  axios.interceptors.response.use(response => {
    
    return response;
  }, error => {
    if (error.response.status === 401) {
      return next({path: '/login',})
    }
    if(error.response.status === 403) {
      app.errorModal(error.response.data.message)
    }
    return Promise.reject(error);
  });
  next();
});




/* eslint-disable no-new */
const app = new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})

