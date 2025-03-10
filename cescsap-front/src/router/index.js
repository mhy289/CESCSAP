import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/manage/Manage.vue'
import {
  Message
} from 'element-ui';

Vue.use(VueRouter)


const routes = [{
    path: '/',
    name: 'syshome',
    component: HomeView,
    //redirect: "/",
    meta: {
      title: '后台',
      path: '后台',
      requireAuth: true
    },
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('../views/manage/Home.vue'),
      meta: {
        title: '首页',
        path: '首页',
        requireAuth: true
      }
    }, {
        path: '/Notice',
        name: 'notice',
        component: () => import('../views/manage/file/Notice.vue'),
    },{
        path: '/NoticeReply',
        name:'noticeReply',
        component: () => import('../views/manage/file/NoticeReply.vue'),
    },{
        path: '/Help',
        name:'help',
        component: () => import('../views/manage/file/Help.vue'),
    },{
        path:'/Link',
        name:'link',
        component: () => import('../views/manage/file/Link.vue'),
    },{
        path:'/LinkList',
        name:'linklist',
        component: () => import('../views/manage/file/LinkList.vue'),
    },{
        path:'/Forum',
        name:'forum',
        component: () => import('../views/manage/file/Forum.vue'),
    },{
        path:'/ForumReply',
        name:'forumReply',
        component: () => import('../views/manage/file/ForumReply.vue'),
    },{
        path:'/Evaluation',
        name:'evaluation',
        component: () => import('../views/manage/file/Evaluation.vue'),
    },{
        path: '/Gpa',
        name: 'gpa',
        component: () => import('../views/manage/file/Gpa.vue'),
        
    },{
        path:'/Warn',
        name:'warn',
        component: () => import('../views/manage/file/Warn.vue'),
    },{
        path:'/Kd',
        name:'kd',
        component: () => import('../views/manage/file/Kd.vue'),
    },{
        path:'/Form',
        name:'form',
        component: () => import('../views/manage/form/Form.vue'),
    },{
        path:'AdminForm',
        name:'adminform',
        component: () => import('../views/manage/form/AdminForm.vue'),
    }]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
    // route level code-splitting
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/AboutView.vue')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  // 首先判断是否需要登录
  if (to.name === 'login' || to.name === 'Register') {
    // 如果是登录或注册页面，则直接通过
    next();
  } else {
    // 获取token
    let token = localStorage.getItem('token');
    if (token) {
      // 如果存在token，则继续导航
      next();
    } else {
      // 如果不存在token，则显示错误并导航到登录页面
      Message.error("请先登录");
      next({
        name: 'login'
      }); // 使用name或path都可以，这里使用name更清晰
      // 注意：这里不需要再调用router.push("/login")，因为next已经处理了
    }
  }

  // 如果目标路由与当前路由相同，理论上不需要做额外处理，因为上面的逻辑已经处理了所有情况
  // 但如果你确实需要在这种情况下做一些操作（虽然不常见），你应该将这部分逻辑移到上面
  // 例如，检查查询参数或hash是否有变化，然后决定是否继续

  // 注意：console.log(to, from, next) 应该放在逻辑处理之前或之后，而不是穿插在中间
  // 这里我将它放在最后，仅用于调试
  console.log(to, from); // 注意：next函数不应该被console.log，因为它是一个函数
});

export default router
