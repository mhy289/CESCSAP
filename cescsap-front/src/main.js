import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 引入 element-ui
import ElementUI from 'element-ui'
// import '../node_modules/element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/index.css'

// @ 代表 src根目录
import '@/assets/css/style.css'

// 引入插件
Vue.use(ElementUI)

// 引入 axios
import axios from 'axios'

// 设置请求的基础路径
axios.defaults.baseURL = "http://localhost:8080"
// 定义响应拦截器
axios.interceptors.response.use(function (resp) {
  let data = resp.data
  if (data.code === 444) {
    // 跳转到登录页
    //Message.error("请先登录2")
    console.log("login_null")
    //router.push("/login")
  }
  return data
})

//定义请求拦截器
axios.interceptors.request.use(function (req) {
  console.log(req.url)
  const token = localStorage.getItem("token")
  if (token) {
    req.headers['Authorization'] = token
  }
  return req;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

Vue.prototype.$http = axios

// this.$http

Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
