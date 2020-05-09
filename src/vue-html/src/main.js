import Vue from "vue";
import App from "./App.vue";
import router from "./router/index";
import store from "./store/store";
import "./plugins/iview";
import commons from "./plugins/commons";
import axiosconfig from "./plugins/axiosconfig";
import { globalMethods } from "./plugins/mixin";

Vue.config.productionTip = false;
Vue.config.debug = true;
Vue.use(commons);
Vue.use(axiosconfig);
Vue.mixin(globalMethods);



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
