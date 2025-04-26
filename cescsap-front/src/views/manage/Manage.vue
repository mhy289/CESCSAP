<!--
 * 后台管理 - 基础布局
-->
<template>
  <div style="height: 100%">

    <el-container style="height: 100%">

      <!--菜单侧边栏-->
      <el-aside width="sideWidth + 'px'">
        <Aside :is-collapse="isCollapse"></Aside>
      </el-aside>

      <el-container>
        <!--导航栏-->
        <el-header style="border-bottom: 1px solid;">
          <Header :collapse-icon="collapseIcon" :collapse-title="collapseTitle" @collapse="handleCollapse" :user="user">
          </Header>
        </el-header>

        <!-- 点击按钮 -->
        <el-button v-if="role==0" type="primary" @click="handleClick">Button</el-button>
        <!--首页展示，读取ip地址-->




        <el-main :class="{bk: $route.path=='/manage/home'}">
          <router-view @refresh="getUser" />
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<style>
  .el-header {
    background-color: #ffffff;
    color: #000000;
    line-height: 60px;
  }

  .el-aside {
    background-color: #101010;
    color: #000000;
    height: 100%;
  }

  .bk {
    width: 100%;
    /*项目背景图*/
    background: url("@/assets/img/back.jpg") center center no-repeat;
    background-size: 100% 100%;
  }

</style>

<script>
  import Aside from "@/components/Aside";
  import Header from "@/components/Header";

  export default {
    //页面初始配置
    data() {
      return {
        user: {},
        isCollapse: false,
        sideWidth: 250,
        collapseIcon: "el-icon-s-fold",
        collapseTitle: "收缩",
        role: localStorage.getItem('role')
      };
    },
    //组件
    components: {
      Aside,
      Header,
    },

    //方法
    methods: {
      //折叠方法
      handleCollapse() {
        this.isCollapse = !this.isCollapse;
        if (this.isCollapse) {
          //点击收缩按钮
          this.sideWidth = 64;
          this.collapseIcon = "el-icon-s-unfold";
          this.collapseTitle = "展开";
        } else {
          //点击展开按钮
          this.sideWidth = 250;
          this.collapseIcon = "el-icon-s-fold";
          this.collapseTitle = "收缩";
        }
      },
      //获取用户信息方法
      getUser() {
        let username = localStorage.getItem("user") ?
          JSON.parse(localStorage.getItem("user")).username :
          "";
        if (username) {
          //请求用户信息
          this.request.get("/userinfo/" + username).then((res) => {
            //赋值用户信息
            this.user = res.data;
          });
        }
      },
      async handleClick() {
        // 点击事件
        //console.log(res);
        console.log(localStorage.getItem('role'));
        let res = await this.$http.get('/admin/check')
        if (res.code == 200) {
          alert('成功')
        } else if (res.code == 202) {
          alert('赢一半')
        } else if (res.code == 201) {
          alert('输')
        } else {
          alert('失败')
        }

        this.$updateActivity();
      }
    },
    created() {
      this.getUser();
    },
  };

</script>
