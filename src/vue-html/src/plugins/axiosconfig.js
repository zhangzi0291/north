import axios from "axios";
import router from "../router/index";
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
    Vue.prototype.baseURL = baseURL;
    // axios.defaults.baseURL = 'http://www.northzx.net:60001/';
    axios.defaults.baseURL = baseURL;
    //使用cookie 需要注意set-cookie中是否有 HttpOnly; SameSite=none
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
    //响应前置
    axios.interceptors.response.use(response => {
      if(response.data.code==500){
        Vue.prototype.$Message.error(response.data.msg)
        throw new Error(response.data.msg)
      }
      return response;
    }, error => {
      if(error.response == undefined){
        Vue.prototype.$Message.error("服务器无响应")
      }
      if (error.response.status === 401) {
        return router.push({path: '/login',})
      }

      if(error.response.status != 200 || error.response.status != 302) {
        if(error.response.data.status==500){
          Vue.prototype.$Message.error(
            error.response.data.error
            +"<br>"+
            error.response.data.message
          )
        }
        if(!!error.response.data.msg){
          Vue.prototype.$Message.error(error.response.data.msg)
        }

      }
      return Promise.reject(error);
    });
    
    router.beforeEach((to, from, next) => {
      let username = sessionStorage.getItem('username');
      let password = sessionStorage.getItem('password');
      if (username && password) {
        
      }
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
