import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/manage/Manage.vue'
import {
  Message
} from 'element-ui';
//import { component } from 'vue/types/umd';

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
      }, {
        path: '/Notice/:noticeId',
        name: 'noticedetial',
        component: () => import('../views/manage/file/NoticeList.vue'),
      }, {
        path: '/Noticemanage',
        name: 'Noticemanage',
        component: () => import('../views/manage/file/NoticeManage.vue'),
      }, {
        path: '/Help',
        name: 'help',
        component: () => import('../views/manage/file/Help.vue'),
      }, {
        path: '/Helpmange',
        name: 'helpmange',
        component: () => import('../views/manage/file/Helpmange.vue'),
      }, {
        path: '/Link',
        name: 'link',
        component: () => import('../views/manage/file/Link.vue'),
      }, {
        path: '/LinkList',
        name: 'linklist',
        component: () => import('../views/manage/file/LinkList.vue'),
      }, {
        path: '/LogList',
        name: 'loglist',
        component: () => import('../views/manage/file/LogList.vue'),
      }, {
        path: 'QaList',
        name: 'qaList',
        component: () => import('../views/manage/file/QaList.vue'),
      }, {
        path: '/Forum_old',
        name: 'forum',
        component: () => import('../views/manage/file/Forum.vue'),
      }, {
        path: '/section/:id',
        name: 'SectionPosts',
        component: () => import('../views/manage/file/SectionPosts.vue'),
      }, {
        path: '/ForumReply',
        name: 'forumReply',
        component: () => import('../views/manage/file/ForumReply.vue'),
      }, {
        path: '/Evaluation',
        name: 'evaluation',
        component: () => import('../views/manage/file/Evaluation.vue'),
      }, {
        path: '/TeacherEvaluation',
        name: 'teacherEvaluation',
        component: () => import('../views/manage/file/Teacherevaluation.vue'),
      }, {
        path: '/Gpa',
        name: 'gpa',
        component: () => import('../views/manage/file/Gpa.vue'),

      }, {
        path: '/Warn',
        name: 'warn',
        component: () => import('../views/manage/file/Warn.vue'),
      }, {
        path: '/Kd',
        name: 'kd',
        component: () => import('../views/manage/file/Kd.vue'),
      }, {
        path: '/Form',
        name: 'form',
        component: () => import('../views/manage/form/Form.vue'),
      }, {
        path: 'AdminForm',
        name: 'adminform',
        component: () => import('../views/manage/form/AdminForm.vue'),
      }, {
        path: '/StuManage',
        name: 'stuManage',
        component: () => import('../views/manage/file/StuManage.vue'),
      }, {
        path: '/TeacherManage',
        name: 'teacherManage',
        component: () => import('../views/manage/file/Teachermanage.vue'),
      }, {
        path: '/ClassManage',
        name: 'classManage',
        component: () => import('../views/manage/file/Classmanage.vue'),
      }, {
        path: '/CourseManage',
        name: 'courseManage',
        component: () => import('../views/manage/file/Coursemanage.vue'),
      }, {
        path: '/forum',
        name: 'partitionlist',
        component: () => import('../views/manage/file/PartitionList.vue'),
      }, {
        path: '/forum/partition/:partitionId',
        name: 'PostList',
        component: () => import('../views/manage/file/PostList.vue'),
        props: true
      }, {
        path: '/forum/post/:postId',
        name: 'ReplyList',
        component: () => import('../views/manage/file/ReplyList.vue'),
        props: true
      }, {
        path: '/PendingEvaluations',
        name: 'pendingEvaluations',
        component: () => import('../views/manage/file/PendingEvaluations.vue'),
      }, {
        path: '/TeacherClasses',
        name: 'teacherClasses',
        component: () => import('../views/manage/file/TeacherClasses.vue'),
      }, {
        path: '/TeacherCourses',
        name: 'teacherCourses',
        component: () => import('../views/manage/file/TeacherCourses.vue'),
      }, {
        path: '/TeacherStudents',
        name: 'teacherStudents',
        component: () => import('../views/manage/file/TeacherStudents.vue'),
      }, {
        path: '/teacher/student/:studentId/score',
        name: 'StudentScoreDetail',
        component: () => import('@/views/manage/file/StudentScoreDetail.vue'),
        props: true
      }, {
        path: '/evalmanage',
        name: 'evalmanage',
        component: () => import('../views/manage/file/Evalmanage.vue'),
      }, {
        path: '/warnmanage',
        name: 'warnmanage',
        component: () => import('../views/manage/file/Warnmanage.vue'),
      }, {
        path: '/evaluations',
        name: 'EvaluationManagement',
        component: () => import('../views/manage/file/Evaluations.vue'),
      },{
        path: '/evaluation-dimensions',
        name: 'EvaluationDimensionManagement',
        component:  () => import('../views/manage/file/EvaluationDimensionManagement.vue'),
      },{
        path: '/teacherWarning',
        name: 'TeacherWarning',
        component: () => import('../views/manage/file/TeacherWarning.vue')
      },{
        path:'/courseWarning',
        name:'CourseWarning',
        component: () => import('../views/manage/file/StudentWarning.vue')
      },{
        path:'/studentScore',
        name:'StudentScore',
        component: () => import('../views/manage/file/StudentScore.vue')
      },{
        path:'/majordepart',
        name:'majordepart',
        component: () => import('../views/manage/file/Majordepart.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
    // route level code-splitting
  }, {
    path: '/Person',
    name: 'person',
    component: () => import('../views/Person.vue')
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
