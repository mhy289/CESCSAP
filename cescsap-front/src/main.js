import Vue from 'vue'
import App from './App.vue'
import router from './router'
import LoginView from './views/LoginView.vue'

// 引入 element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import ElementPlus from 'element-plus'
//import { ElLoading } from 'element-plus' // 新增导入

// 引入全局样式
import '@/assets/css/style.css'

// 引入 axios
import axios from 'axios'

Vue.use(ElementUI)
Vue.prototype.$http = axios
//app.use(ElementPlus) // 确保包含 ElLoading

// —— 1. 前端异常上报相关 ——
// 基础路径
axios.defaults.baseURL = "http://localhost:8080"
// 上报函数：将错误信息 POST 到后端
function reportError(payload) {
    //使用基础路径

  fetch('http://localhost:8080/log/clienterr', {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json',
      // 如果你需要鉴权，可加上 Authorization
      'Authorization': localStorage.getItem('token') || ''
    },
    body: JSON.stringify({
      timestamp: new Date().toISOString(),
      url: window.location.href,
      userAgent: navigator.userAgent,
      ...payload
    })
  }).catch(e => {
    // 上报失败，控制台打印即可，避免二次抛错
    console.error('上报客户端异常失败：', e)
  })
}

// 全局同步脚本错误捕获
window.onerror = function(message, source, lineno, colno, error) {
  reportError({
    type: 'error',
    message,
    stack: error?.stack,
    file: source,
    line: lineno,
    column: colno
  })
}

// 全局 Promise 未捕获异常
window.onunhandledrejection = function(event) {
  const err = event.reason
  reportError({
    type: 'unhandledrejection',
    message: err?.message || String(err),
    stack: err?.stack
  })
}

// —— 2. Axios 拦截器 ——



// 请求拦截：自动带 token
axios.interceptors.request.use(req => {
  const token = localStorage.getItem("token")
  if (token) req.headers['Authorization'] = token
  return req
}, error => Promise.reject(error))

// 响应拦截：业务码 444 跳登录，同时上报
axios.interceptors.response.use(resp => {
  const data = resp.data
  if (data.code === 444) {
    reportError({
      type: 'auth',
      message: 'Token 无效或已过期，需重新登录',
      stack: ''
    })
    localStorage.removeItem('token')
    localStorage.removeItem("name")
    localStorage.removeItem("role")
    //router.push("/login")
    let isLoggingOut = false; // 全局标记
    if (router.currentRoute && router.currentRoute.path !== '/login') { // 关键判断
        isLoggingOut = true;
        router.push('/login').finally(() => {
            isLoggingOut = false;
        });
      }
  }
  return data
}, err => {
  // 网络或服务器异常也上报
  reportError({
    type: 'axios',
    message: err.message,
    stack: err.stack,
    config: {
      url: err.config?.url,
      method: err.config?.method,
      data: err.config?.data
    },
    response: {
      status: err.response?.status,
      data: err.response?.data
    }
  })
  return Promise.reject(err)
})

// —— 3. 活动超时登出逻辑 ——


// 常量定义
const LAST_ACTIVITY_KEY = 'lastActivityTime';
const TOKEN_KEY = 'authToken';
const INACTIVITY_TIMEOUT = 30 * 60 * 1000; // 30 分钟

// 更新最后活动时间
function updateLastActivityTime() {
  localStorage.setItem(LAST_ACTIVITY_KEY, new Date().toISOString());
}

// 检查是否超时
function checkActivityTimeout() {
  const last = localStorage.getItem(LAST_ACTIVITY_KEY);
  if (last && (new Date() - new Date(last) > INACTIVITY_TIMEOUT)) {
    logoutUser();
  }
}

// 退出登录
function logoutUser() {
  // 清理
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem('token');
  localStorage.removeItem("name");
  localStorage.removeItem("role");

  // 弹窗提示
  alert('您已超时未活动，请重新登录。');
  // 跳登录页
  router.push('/login');
}

// 每分钟检查一次
const activityCheckInterval = setInterval(checkActivityTimeout, 60_000);

// 挂载到 Vue 原型，方便组件手动调用
Vue.prototype.$updateActivity = updateLastActivityTime

// —— 4. Vue 实例化 —— 

Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App),
    mounted() {
      // 初次检查
      checkActivityTimeout();
  
      // 监听用户活动
      ['mousemove','keydown','click','scroll'].forEach(evt => {
        window.addEventListener(evt, updateLastActivityTime);
      });  // ← 显式分号，防止后续被错误解析
    },
    beforeDestroy() {
      clearInterval(activityCheckInterval);
      ['mousemove','keydown','click','scroll'].forEach(evt => {
        window.removeEventListener(evt, updateLastActivityTime);
      });
    }
  }).$mount('#app');
  
