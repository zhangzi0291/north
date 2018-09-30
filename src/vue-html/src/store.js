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
  getters: {
    getUser:function(){
      return JSON.parse(sessionStorage.getItem("user"));
    }
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
      state.ws={};
      const vue = data.vue;
      if(!data.accessToken||!data.user){
        vue.$Message.error("没有用户信息");
      }
      let token = data.accessToken;
      let uid = data.user.id;
      let name = data.user.name;
      let param = { token : token};
      let param2 = { 
        vue: vue,
        username: name,
        // content: content,
        userId: uid,
      };
      state.ws = new tio.ws(vue.wsURL,param,param2,new handle());
      state.ws.connect(state.ws);    
      const wsdata = {
        ws:state.ws,
        // time:new Date().getTime()
      }
      console.log(JSON.stringify(wsdata))
    }
  }
}
if (module.hot) {
  module.hot.accept([],() =>{
    store.hotUpdate(store)
  })
}
export default new Vuex.Store(store);
