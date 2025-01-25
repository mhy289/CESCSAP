<template>
  <el-container>
    <el-header>Login</el-header>
    <el-main>
      <el-form :model="loginForm" @submit.native.prevent="handleLogin">
        <el-form-item label="Username" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">Login</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
  import {
    mapActions
  } from 'vuex';

  export default {
    data() {
      return {
        loginForm: {
          username: '',
          password: ''
        }
      };
    },
    methods: {
      ...mapActions(['login']),
      handleLogin() {
        this.login(this.loginForm)
          .then(user => {
            this.$router.push({
              path: this.$route.query.redirect || '/'
            });
          })
          .catch(error => {
            console.error('Login error:', error);
          });
      }
    }
  };

</script>

<style scoped>
  /* 添加一些简单的样式 */
  .el-container {
    max-width: 400px;
    margin: 100px auto;
  }

</style>
