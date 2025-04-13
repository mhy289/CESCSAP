<template>
  <div class="login-container"
    :style="{ backgroundImage: 'url(' + require('@/assets/img/back2.png') + ')', backgroundSize: 'cover', backgroundPosition: 'center' }">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
      class="demo-ruleForm bordered-form readable-text">
      <h1 class="system-title">高校综合评价系统</h1>
      <el-form-item label="登录身份" prop="role">
        <el-select v-model="ruleForm.role" placeholder="请选择登录身份" class="full-width-select">
          <el-option label="学生" value="2"></el-option>
          <el-option label="教师" value="1"></el-option>
          <el-option label="管理员" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="用户名" prop="name">
        <el-input v-model="ruleForm.name" autocomplete="off" class="circled-input" placeholder="请输入用户名">
          <template #prefix>
            <i class="el-icon-user"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off" class="circled-input"
          placeholder="请输入密码" show-password>
          <template #prefix>
            <i class="el-icon-lock"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" :loading="loading" @click="submitForm" class="submit-btn">
          登录
        </el-button>
        <el-button @click="resetForm" class="reset-btn">
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    jwtDecode
  } from "jwt-decode";

  export default {
    data() {
      return {
        loading: false,
        ruleForm: {
          name: '',
          password: '',
          role: ''
        },
        rules: {
          name: [{
              required: true,
              message: '请输入用户名',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在1到20个字符',
              trigger: 'blur'
            }
          ],
          password: [{
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 6,
              max: 20,
              message: '长度在6到20个字符',
              trigger: 'blur'
            }
          ],
          role: [{
            required: true,
            message: '请选择登录身份',
            trigger: 'change'
          }]
        }
      };
    },
    methods: {
      async submitForm() {
        try {
          // 表单验证
          await this.$refs.ruleForm.validate();
          this.loading = true;

          // 发送请求（设置5秒超时）
          const res = await this.$http.post("/login", this.ruleForm, {
            timeout: 5000
          });

          if (res.code === 200) {
            this.handleLoginSuccess(res);
          } else {
            this.handleLoginFailure(res);
          }
        } catch (error) {
          this.handleRequestError(error);
        } finally {
          this.loading = false;
        }
      },

      handleLoginSuccess(res) {
        const token = res.data;
        const user = jwtDecode(token);

        // 存储用户信息
        localStorage.setItem('token', token);
        localStorage.setItem('role', this.ruleForm.role);
        localStorage.setItem('name', this.ruleForm.name);
        localStorage.setItem('id', user.aud);

        // 跳转路由
        const redirectPath = this.getRedirectPath();
        this.$router.push('/');

        this.$message.success('登录成功');
      },

      getRedirectPath() {
        switch (this.ruleForm.role) {
          case '0':
            return '/admin/dashboard';
          case '1':
            return '/teacher/home';
          case '2':
            return '/student/profile';
          default:
            return '/';
        }
      },

      handleLoginFailure(res) {
        const errorMessage = res.message || '登录失败，请检查身份和凭证';
        this.$message.error(errorMessage);
      },

      handleRequestError(error) {
        if (!error.response) {
          if (error.code === 'ECONNABORTED') {
            this.$message.error('请求超时，请稍后重试');
          } else {
            this.$message.error('网络连接异常，请检查网络设置');
          }
        } else {
          const status = error.response.status;
          const errorMap = {
            401: '用户名或密码错误',
            403: '权限不足，请联系管理员',
            500: '服务器内部错误'
          };
          this.$message.error(errorMap[status] || `请求失败（${status}）`);
        }
      },

      resetForm() {
        this.$refs.ruleForm.resetFields();
        this.$message.success('表单已重置');
      }
    }
  };

</script>

<style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    padding: 20px;
  }

  .system-title {
    margin: 0 0 30px 0;
    font-size: 28px;
    font-weight: bold;
    color: #2c3e50;
    text-align: center;
    text-shadow: 1px 1px 2px rgba(255, 255, 255, 0.5);
  }

  .bordered-form {
    width: 100%;
    max-width: 450px;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background: rgba(255, 255, 255, 0.95);
  }

  .circled-input {
    margin-bottom: 20px;
  }

  .circled-input :deep(.el-input__inner) {
    border-radius: 24px;
    padding: 12px 20px 12px 40px;
    height: 48px;
    font-size: 16px;
  }

  .full-width-select :deep(.el-select) {
    width: 100%;
  }

  .submit-btn {
    width: 100%;
    height: 48px;
    font-size: 16px;
    border-radius: 24px;
    margin-top: 10px;
  }

  .reset-btn {
    width: 100%;
    height: 48px;
    font-size: 16px;
    border-radius: 24px;
    margin-top: 10px;
  }

  .el-form-item__label {
    font-weight: bold;
    color: #606266;
  }

  .readable-text {
    color: #303133;
    line-height: 1.6;
  }

</style>
