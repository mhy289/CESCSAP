<!--
 * 左侧滑动-菜单栏
-->
<template>
  <el-menu :default-openeds="['2', 'good']" style="height: 100%;" background-color="rgb(28,28,28)" text-color="#fff"
    :collapse-transition="false" :collapse="isCollapse" router>

    <!-- 标题，图标等   -->
    <div style="height: 60px;margin-left: 30px; line-height: 60px">
      <span slot="title" style="color: aliceblue;font-size: 20px" v-show="!isCollapse">后台管理</span>
    </div>

    <!-- 主页跳转 -->
    <el-menu-item index="/manage/home">
      <i class="el-icon-house"></i><span slot="title">主页</span>
    </el-menu-item>

    <!-- 前台跳转   -->
    <el-menu-item index="/">
      <i class="el-icon-house"></i><span slot="title">前台</span>
    </el-menu-item>

    <!-- 系统菜单栏  -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i><span slot="title">系统管理</span></template>
      <el-submenu v-show="userGroup" index="user">

        <template slot="title">账户相关</template>
        <el-menu-item index="/manage/User" v-if="menuFlags.userMenu">用户管理</el-menu-item>
      </el-submenu>

      
      

      <el-submenu v-if="GoodGroup" index="good">
        <template slot="title">前台管理</template>
        <el-menu-item index="/manage/Foods" v-if="menuFlags.goodMenu">
          食品管理
        </el-menu-item>
        <el-menu-item index="/manage/order" v-if="menuFlags.orderMenu">订单管理</el-menu-item>
      </el-submenu>

      <!-- 个人信息 -->
      <el-submenu v-if="Personal" index="person">
        <template slot="title">个人信息</template>
        <el-menu-item index="/gpa" v-if="menuFlags.gpaMenu">成绩</el-menu-item>
        <el-menu-item index="/manage/kd" v-if="menuFlags.kdMenu">所属</el-menu-item>
        <el-menu-item index="/form" v-if="menuFlags.formMenu">修改信息</el-menu-item>
        <el-menu-item index="/adminform" v-if="menuFlags.adminformMenu">修改管理员信息</el-menu-item>
      </el-submenu>

      <el-submenu v-if="incomeGroup" index="income">
        <template slot="title">销售额统计</template>
        <el-menu-item index="/manage/incomeChart" v-if="menuFlags.incomeChartMenu">图表分析</el-menu-item>
        <el-menu-item index="/manage/incomeRank" v-if="menuFlags.incomeRankMenu">收入排行榜</el-menu-item>
      </el-submenu>

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
          adminformMenu:false
          //personal: false
        }
      }
    },

    computed: {
      userGroup: function () {
        return this.menuFlags.userMenu
      },
      GoodGroup: function () {
        return this.menuFlags.goodMenu || this.menuFlags.orderMenu || this.menuFlags.categoryMenu || this.menuFlags
          .carouselMenu
      },
      incomeGroup: function () {
        return this.menuFlags.incomeChartMenu || this.menuFlags.incomeRankMenu
      },
      Personal: function () {
        return this.menuFlags.gpaMenu || this.menuFlags.kdMenu || this.menuFlags.formMenu || this.menuFlags.adminformMenu
      }
    },
    async created() {

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
      this.menuFlags.adminformMenu = true
      /*获取用户角色*/
      //request.post("http://localhost:8888/role").then(res => {
      //从本地读取role
      console.log(this.role)
      //let res = await this.$http.post("/role")
      /*if (res.code === '200') {
        this.role = res.data;
        /*只有管理员有权限*/
        if (this.role === 0) {
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
        } else if (this.role === 1) {

        }
      //}*/
      //})
    }
  }

</script>
