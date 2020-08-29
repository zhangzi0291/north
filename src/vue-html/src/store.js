import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = {
  state: {
    count: 0,
    logined: false,
    user: sessionStorage.getItem("user")
      ? JSON.parse(sessionStorage.getItem("user"))
      : {},
      token: sessionStorage.getItem("token")
      ? JSON.parse(sessionStorage.getItem("token"))
      : {},
    ws:{},
  },
  getters: {
    getUser:function(){
      return JSON.parse(sessionStorage.getItem("user"));
    }
  },
  mutations: {
    
  }
}
if (module.hot) {
  module.hot.accept([],() =>{
    store.hotUpdate(store)
  })
}
export default new Vuex.Store(store);
