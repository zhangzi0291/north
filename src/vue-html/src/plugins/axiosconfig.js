import axios from "axios";
import router from "../router";
const loading = {
  install: function(Vue) {
    const getParams = function(data) {
      let param = new URLSearchParams();
      for (var key in data) {
        if(data[key] instanceof Date){
          param.append(key, data[key].getTime());
          continue;
        }
        if (
          typeof data[key] == "object" &&
          data[key] != null &&
          data[key].length === 0
        ) {
          continue;
        }
        if (data[key]) {
          param.append(key, data[key]);
        }
      }
      return param;
    };
    let baseURL = "http://127.0.0.1:80/";
    let wsURL = "ws://127.0.0.1:81/";
    Vue.prototype.baseURL = baseURL;
    Vue.prototype.wsURL = wsURL;
    // axios.defaults.baseURL = 'http://www.northzx.net:60001/';
    axios.defaults.baseURL = baseURL;
    //使用cookie
    axios.defaults.withCredentials = true;
    //请求前置
    axios.interceptors.request.use(
      config => {
        let param = new URLSearchParams();
        if (config.data) {
          config.data = getParams(config.data);
        }
        config.headers["X-Requested-With"] = "XMLHttpRequest";
        return config;
      },
      error => {
        // 对请求错误做些什么
        console.log(error);
        return Promise.reject(error);
      }
    );
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
        if(error.response.status != 200 || error.response.status != 302) {
          Vue.prototype.$Message.error(error.response.data.msg)
        }
        return Promise.reject(error);
      });
      next();
    });
    //请求后置
    // axios.interceptors.response.use(response => {

    //   return response;
    // }, error => {
    //   if (error.response.status === 401) {
    //     window.location.href = '/login'
    //   }
    //   return Promise.reject(error);
    // });

    Vue.prototype.$ajax = axios;
  }
};
export default loading;
