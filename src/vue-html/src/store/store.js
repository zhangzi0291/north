import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = {
  state: {
    count: 0,
    logined: false,
    user: localStorage.getItem("user")
      ? JSON.parse(localStorage.getItem("user"))
      : {},
      token: localStorage.getItem("token")
      ? JSON.parse(localStorage.getItem("token"))
      : {},
    ws:{},
  },
  getters: {
    getUser:function(){
      return JSON.parse(localStorage.getItem("user"));
    }
  },
  mutations: {
    LOGIN(state, loginParam) {
      state.logined = true;
      localStorage.setItem("user", JSON.stringify(loginParam.user));
      if(loginParam.token){
        localStorage.setItem("token", JSON.stringify(loginParam.token));
      }
      state.user = loginParam.user;
    },
    LOGOUT(state) {
      state.logined = false;
      state.user = {};
      localStorage.setItem("user", JSON.stringify({}));
    },
  }
}
if (module.hot) {
  module.hot.accept([],() =>{
    store.hotUpdate(store)
  })
}
export default new Vuex.Store(store);
