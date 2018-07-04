import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
    state: {
        count: 0,
        logined:false,
        user:sessionStorage.getItem('user')?JSON.parse(sessionStorage.getItem('user')):{}
    },
    mutations: {
        LOGIN (state,user) {
            state.logined = true
            sessionStorage.setItem('user',JSON.stringify(user))
            state.user = user
        },
        LOGOUT (state) {
            state.logined = false
            state.user = {}
            sessionStorage.setItem('user',JSON.stringify({}))
        }
    }
})