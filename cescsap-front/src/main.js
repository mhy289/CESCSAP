import Vue from 'vue'
import App from './App.vue'
import router from './router'
import LoginView from './views/LoginView.vue'

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
    localStorage.removeItem('token');
    localStorage.removeItem("name")
    localStorage.removeItem("role");
    router.push("/login")
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

// 常量定义
const LAST_ACTIVITY_KEY = 'lastActivityTime';
const TOKEN_KEY = 'authToken';
const INACTIVITY_TIMEOUT = 30 * 60 * 1000; // 30分钟

// 更新最后活动时间
function updateLastActivityTime() {
  localStorage.setItem(LAST_ACTIVITY_KEY, new Date().toISOString());
}

// 检查是否超时
function checkActivityTimeout() {
  const lastActivityTime = localStorage.getItem(LAST_ACTIVITY_KEY);
  if (lastActivityTime) {
    const lastActivity = new Date(lastActivityTime);
    const currentTime = new Date();
    if (currentTime - lastActivity > INACTIVITY_TIMEOUT) {
      // 执行退出登录逻辑
      logoutUser();
    }
  }
}

// 退出登录逻辑
function logoutUser() {
  // 删除token
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem('TOKEN_KEY');
  localStorage.removeItem('token');
  localStorage.removeItem("name")
  localStorage.removeItem("role");


  // 显示弹窗（这里使用了一个简单的alert，你可以替换为一个更复杂的UI组件）
  alert('您已超时未活动，请重新登录。');
  //关闭弹窗后重定向到登录页面




  // 重定向到登录页面（可选，取决于你的应用逻辑）
  // 注意：在实际应用中，你可能不希望在这里立即重定向，因为用户可能还没有关闭弹窗。
  // 你可以在用户关闭弹窗后再进行重定向，或者使用Vue Router的守卫来处理未登录的情况。
  router.push('/login'); // 注释掉，因为这里不适合立即重定向
}

// 设置定时检查（每分钟检查一次）
const activityCheckInterval = setInterval(checkActivityTimeout, 60000);

// 在Vue实例中添加全局方法用于更新活动时间（可选，取决于你的实现方式）
Vue.prototype.$updateActivity = updateLastActivityTime;



new Vue({
  router,
  render: h => h(App),
  mounted() {
    checkActivityTimeout();

    // 监听用户活动事件（如鼠标移动、点击、键盘输入）
    const activityEvents = ['mousemove', 'keydown', 'click', 'scroll'];
    activityEvents.forEach(event => {
      window.addEventListener(event, updateLastActivityTime);
    });
  },
  beforeDestroy() {
    clearInterval(activityCheckInterval);
    // 移除事件监听（避免内存泄漏）
    const activityEvents = ['mousemove', 'keydown', 'click', 'scroll'];
    activityEvents.forEach(event => {
      window.removeEventListener(event, updateLastActivityTime);
    });
  }
}).$mount('#app')
