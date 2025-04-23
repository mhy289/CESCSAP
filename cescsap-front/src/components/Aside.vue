<!--
 * 左侧滑动-菜单栏
-->
<template>
  <el-menu :default-openeds="['2', 'good']" style="height: 100%;" background-color="rgb(28,28,28)" text-color="#fff"
    :collapse-transition="false" :collapse="isCollapse" router>

    <!-- 标题，图标等   -->
    <div style="height: 60px;margin-left: 30px; line-height: 60px">
      <span slot="title" style="color: aliceblue;font-size: 20px" v-show="!isCollapse">高校学生学业综合评价系统</span>
    </div>

    <!-- 主页跳转 -->
    <el-menu-item index="/">
      <i class="el-icon-house"></i><span slot="title">主页</span>
    </el-menu-item>

    <!-- 前台跳转   -->
    <!-- <el-menu-item index="/">
      <i class="el-icon-house"></i><span slot="title">前台</span>
    </el-menu-item> -->

    <!-- 系统菜单栏  -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i><span slot="title">系统管理</span></template>

      <!-- <el-submenu v-show="userGroup" index="user">
        <template slot="title">账户相关</template>
        <el-menu-item index="/manage/User" v-if="menuFlags.userMenu">用户管理</el-menu-item>
      </el-submenu> -->

      <!-- 日志管理-->
      <el-submenu v-if="logGroup" index="log">
        <template slot="title">管理员界面</template>
        <el-menu-item index="/loglist" v-if="menuFlags.logMenu">日志管理</el-menu-item>
        <el-menu-item index="/noticemanage" v-if="menuFlags.noticemanageMenu">公告管理</el-menu-item>
        <el-menu-item index="/qaList" v-if="menuFlags.qaMenu">问答管理</el-menu-item>
        <el-menu-item index="/helpmange" v-if="menuFlags.helpmangeMenu">帮助管理</el-menu-item>
        <el-menu-item index="/linkList" v-if="menuFlags.linkMenu">链接管理</el-menu-item>
        <el-menu-item index="/stumanage" v-if="menuFlags.stuMenu">学生管理</el-menu-item>
        <el-menu-item index="/teachermanage" v-if="menuFlags.techerMenu">教师管理</el-menu-item>
        <el-menu-item index="/coursemanage" v-if="menuFlags.courseMenu">课程管理</el-menu-item>
        <el-menu-item index="/classmanage" v-if="menuFlags.classMenu">班级管理</el-menu-item>
        <el-menu-item index="/evaluations" v-if="menuFlags.evalMenu">评价管理</el-menu-item>
        <el-menu-item index="/warnmanage" v-if="menuFlags.warnMenu">警告管理</el-menu-item>
      </el-submenu>

      <!-- 通知与公告 -->
      <el-submenu v-if="noticeGroup" index="notice">
        <template slot="title">通知与公告</template>
        <el-menu-item index="/notice" v-if="menuFlags.noticeMenu">公告</el-menu-item>

        <el-menu-item index="/noticeReply" v-if="menuFlags.noticeReplyMenu">回复管理</el-menu-item>
      </el-submenu>

      <!-- 帮助中心 -->
      <el-submenu v-if="helpGroup" index="help">
        <template slot="title">帮助中心</template>
        <el-menu-item index="/help" v-if="menuFlags.helpMenu">常见问题</el-menu-item>
        <!-- <el-menu-item index="/helpArticle" v-if="menuFlags.helpArticleMenu">帮助文章</el-menu-item> -->
      </el-submenu>

      <!-- 友情链接 -->
      <el-submenu v-if="linkGroup" index="link">
        <template slot="title">友情链接</template>
        <el-menu-item index="/link" v-if="menuFlags.linkMenu">友情链接列表</el-menu-item>
        <!-- 友情链接列表 -->
        <el-menu-item index="/linkList" v-if="menuFlags.linkListMenu">友情链接列表</el-menu-item>
      </el-submenu>

      <!--教师功能-->
      <el-submenu v-if="teacherGroup" index="teacher">
        <template slot="title">教师界面</template>
        <!-- <el-menu-item index="/teacher" v-if="menuFlags.teacherMenu">教师主页</el-menu-item> -->
        <el-menu-item index="/TeacherClasses" v-if="menuFlags.teacherClassMenu">我的班级</el-menu-item>
        <el-menu-item index="/TeacherCourses" v-if="menuFlags.teacherCourseMenu">我的课程</el-menu-item>
        <el-menu-item index="/TeacherWarning" v-if="menuFlags.teacherWarningMenu">课程预警</el-menu-item>
        <el-menu-item index="/TeacherEvaluations" v-if="menuFlags.teacherEvaluationMenu">我的评价</el-menu-item>
      </el-submenu>

      <!-- 学园论坛 -->
      <el-submenu v-if="forumGroup" index="forum">
        <template slot="title">学园论坛</template>
        <el-menu-item index="/forum" v-if="menuFlags.forumMenu">论坛</el-menu-item>
        <!-- <el-menu-item index="/forumReply" v-if="menuFlags.forumReplyMenu">讨论区</el-menu-item> -->
      </el-submenu>

      <!-- 课程功能 -->
      <el-submenu v-if="evaluationGroup" index="evaluation">
        <template slot="title">课程综合</template>
        <!-- 自评 -->
        <el-menu-item index="/selfevaluation" v-if="menuFlags.selfevaluationMenu">自评</el-menu-item>
        <!-- 教师评价 -->
        <el-menu-item index="/PendingEvaluations" v-if="menuFlags.teacherevaluationMenu">教师评价</el-menu-item>
        <!--查看全部评价 -->
        <el-menu-item index="/evaluation" v-if="menuFlags.evaluationMenu">查看全部</el-menu-item>
        <el-menu-item index="/studentScore" v-if="menuFlags.studentScoreMenu">我的成绩</el-menu-item>
        <el-menu-item index="/courseWarning" v-if="menuFlags.courseWarningMenu">课程预警</el-menu-item>
      </el-submenu>

      <!-- 个人信息 -->
      <!-- <el-submenu v-if="Personal" index="person">
        <template slot="title">个人信息</template>
        <el-menu-item index="/gpa" v-if="menuFlags.gpaMenu">成绩</el-menu-item>
        <el-menu-item index="/kd" v-if="menuFlags.kdMenu">所属</el-menu-item>
        <el-menu-item index="/form" v-if="menuFlags.formMenu">修改信息</el-menu-item>
        <el-menu-item index="/warn" v-if="menuFlags.warnMenu">预警信息</el-menu-item>
        <el-menu-item index="/adminform" v-if="menuFlags.adminformMenu">修改管理员信息</el-menu-item>
      </el-submenu> -->

    </el-submenu>
  </el-menu>
</template>

<script>
  //import request from "@/utils/request";

  export default {
    name: "Aside",
    props: {
      isCollapse: Boolean,
    },

    /*初始化数据*/
    data() {
      return {
        /*用户角色 1为管理员*/
        role: 0,
        /*菜单打开状态*/
        menuFlags: {
          userMenu: false,
          avatarMenu: false,
          goodMenu: false,
          carouselMenu: false,
          orderMenu: false,
          categoryMenu: false,
          incomeChartMenu: false,
          incomeRankMenu: false,
          gpaMenu: false,
          kdMenu: false,
          formMenu: false,
          adminformMenu: false,
          warnMenu: false,
          noticeMenu: false,
          noticeReplyMenu: false,
          helpMenu: false,
          helpArticleMenu: false,
          linkMenu: false,
          noticemanageMenu: false,
          logMenu: false,
          qaMenu: false,
          teacherMenu: false,
          teacherClassMenu: false,
          teacherCourseMenu: false,
          forumMenu: false,
          forumReplyMenu: false,
          studentMenu: false,
          studentClassMenu: false,
          studentClassCourseMenu: false,
          courseMenu: false,
          courseClassMenu: false,
          courseClassCourseMenu: false,
          studentCourseMenu: false,
          studentCourseClassMenu: false,
          studentCourseClassCourseMenu: false,
          studentCourseClassCourseScoreMenu: false,
          studentCourseClassCourseScoreReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyReplyReplyReplyReplyMenu: false,
          studentCourseClassCourseScoreReplyReplyReplyReplyReplyReplyReplyReplyReplyMenu: false,
          //personal: false
        }
      }
    },

    computed: {
      logGroup: function () {
        return this.menuFlags.logMenu || this.menuFlags.stuMenu || this.menuFlags.techerMenu || this.menuFlags
          .courseMenu || this.menuFlags.classMenu || this.menuFlags.evalMenu || this.menuFlags.warnMenu
      },
      noticeGroup: function () {
        return this.menuFlags.noticeMenu || this.menuFlags.noticeReplyMenu || this.menuFlags.noticemanageMenu
      },
      helpGroup: function () {
        return this.menuFlags.helpMenu || this.menuFlags.helpArticleMenu || this.menuFlags.helpmangeMenu
      },
      linkGroup: function () {
        return this.menuFlags.linkMenu || this.menuFlags.linkListMenu
      },
      evaluationGroup: function () {
        return this.menuFlags.selfevaluationMenu || this.menuFlags.teacherevaluationMenu || this.menuFlags
          .evaluationMenu
      },
      userGroup: function () {
        return this.menuFlags.userMenu
      },
      forumGroup: function () {
        return this.menuFlags.forumMenu || this.menuFlags.forumReplyMenu
      },
      Personal: function () {
        return this.menuFlags.gpaMenu || this.menuFlags.kdMenu || this.menuFlags.formMenu || this.menuFlags
          .adminformMenu || this.menuFlags.warnMenu
      },
      teacherGroup: function () {
        return this.menuFlags.teacherMenu || this.menuFlags.teacherClassMenu || this.menuFlags.teacherCourseMenu
      }
    },
    async created() {

      /* this.menuFlags.userMenu = true
      this.menuFlags.categoryMenu = true
      this.menuFlags.goodMenu = true
      this.menuFlags.carouselMenu = true
      this.menuFlags.orderMenu = true
      this.menuFlags.incomeChartMenu = true
      this.menuFlags.incomeRankMenu = true
      this.menuFlags.goodMenu = true
      this.menuFlags.kdMenu = true
      this.menuFlags.gpaMenu = true
      this.menuFlags.formMenu = true
      this.menuFlags.adminformMenu = true */
      /*获取用户角色*/
      //request.post("http://localhost:8888/role").then(res => {
      //从本地读取role
      this.role = localStorage.getItem('role');
      console.log("role=" + this.role)
      //let res = await this.$http.post("/role")
      /*if (res.code === '200') {
        this.role = res.data;
        /*只有管理员有权限*/
      if (this.role == 0) {
        console.log("Please3")
        this.menuFlags.userMenu = true
        this.menuFlags.categoryMenu = true
        this.menuFlags.goodMenu = true
        this.menuFlags.carouselMenu = true
        this.menuFlags.orderMenu = true
        this.menuFlags.incomeChartMenu = true
        this.menuFlags.incomeRankMenu = true
        this.menuFlags.goodMenu = true
        this.menuFlags.kdMenu = true
        this.menuFlags.gpaMenu = true
        this.menuFlags.formMenu = true
        this.menuFlags.noticeMenu = true
        this.menuFlags.noticemanageMenu = true
        this.menuFlags.helpMenu = true
        this.menuFlags.helpArticleMenu = true
        this.menuFlags.logMenu = true
        this.menuFlags.helpmangeMenu = true
        this.menuFlags.linkMenu = true
        this.menuFlags.stuMenu = true
        this.menuFlags.techerMenu = true
        this.menuFlags.courseMenu = true
        this.menuFlags.forumMenu = true
        this.menuFlags.forumReplyMenu = true
        this.menuFlags.classMenu = true
        this.menuFlags.evalMenu = true
        this.menuFlags.warnMenu = true
      } else if (this.role == 1) {
        console.log("Please2")
        //教师权限
        this.menuFlags.userMenu = true
        this.menuFlags.categoryMenu = true
        this.menuFlags.goodMenu = true
        this.menuFlags.carouselMenu = true
        this.menuFlags.orderMenu = true
        this.menuFlags.incomeChartMenu = true
        this.menuFlags.incomeRankMenu = true
        this.menuFlags.goodMenu = true
        this.menuFlags.kdMenu = true
        this.menuFlags.gpaMenu = true
        this.menuFlags.teacherClassMenu = true
        this.menuFlags.teacherCourseMenu = true
        this.menuFlags.teacherMenu = true
        this.menuFlags.forumMenu = true
        this.menuFlags.teacherWarningMenu = true
        this.menuFlags.teacherEvaluationMenu = true
      } else if (this.role == 2) {
        console.log("Please")
        //学生权限
        this.menuFlags.kdMenu = true
        this.menuFlags.gpaMenu = true
        this.menuFlags.formMenu = true
        //this.menuFlags.warnMenu = true
        this.menuFlags.forumMenu = true
        this.menuFlags.forumReplyMenu = true
        //this.menuFlags.selfevaluationMenu = true
        this.menuFlags.teacherevaluationMenu = true
        //this.menuFlags.evaluationMenu = true
        this.menuFlags.helpMenu = true
        //this.menuFlags.helpArticleMenu = true
        // this.menuFlags.linkMenu = true
        this.menuFlags.linkListMenu = true
        this.menuFlags.noticeMenu = true
        this.menuFlags.studentScoreMenu = true
        this.menuFlags.courseWarningMenu = true

      } else {
        console.log("Noooooooooooooooooooooooooooooooooooooo")
      }
      //}*/
      //})
    }
  }

</script>
