import Vue from "vue";
import Vuex from "vuex";
import handle from "@/components/chat/handle.js";

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
    },
    CONNECTWS(state,data){
      const vue = data.vue;
      const res = data.res;
      if (res.data.code == "200") {
          let token = res.data.accessToken;
          let uid = res.data.user.id;
          let name = res.data.user.name;
          let param = { token : token};
          let param2 = { 
            vue: vue,
            username: name,
            // content: content,
            userId: uid,
          };
          state.ws = new tio.ws(vue.wsURL,param,param2,new handle());
          console.log(state.ws)
          state.ws.connect(state.ws);
        }
       
    }
  }
}
if (module.hot) {
  module.hot.accept([],() =>{
    store.hotUpdate(store)
  })
}
export default new Vuex.Store(store);
