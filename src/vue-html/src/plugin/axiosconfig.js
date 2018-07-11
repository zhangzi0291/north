import axios from 'axios'
const loading = {
  install: function (Vue) {
    const getParams = function (data) {
      let param = new URLSearchParams();
      for (var key in data) {
        if(typeof data[key]=='object'&&data[key]!=null&&data[key].length===0){
          continue
        }
        if(data[key]){
          param.append(key, data[key]);
        }
      }
      return param
    }

    // axios.defaults.baseURL = 'http://www.northzx.net:60001/';
    axios.defaults.baseURL = 'http://192.168.10.248:8000/';
    //使用cookie
    axios.defaults.withCredentials = true
    //请求前置
    axios.interceptors.request.use(config => {
      let param = new URLSearchParams();
      if (config.data) {
        config.data = getParams(config.data)
      }
      config.headers['X-Requested-With'] = 'XMLHttpRequest'
      return config
    }, error => {
      // 对请求错误做些什么
      console.log(error)
      return Promise.reject(error);
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

    Vue.prototype.$ajax = axios
  }
}
export default loading
