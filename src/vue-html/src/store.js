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
      : {}
  },
  mutations: {
    LOGIN(state, loginParam) {
      state.logined = true;
      sessionStorage.setItem("user", JSON.stringify(loginParam.user));
      if(loginParam.token){
        sessionStorage.setItem("token", JSON.stringify(loginParam.token));
      }
      state.user = loginParam.user;
    },
    LOGOUT(state) {
      state.logined = false;
      state.user = {};
      sessionStorage.setItem("user", JSON.stringify({}));
    }
  }
}
if (module.hot) {
  module.hot.accept([],() =>{
    store.hotUpdate(store)
  })
}
export default new Vuex.Store(store);
