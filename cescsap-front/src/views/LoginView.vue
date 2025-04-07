<template>
  <div class="login-container"
    :style="{ backgroundImage: 'url(' + require('@/assets/img/back2.png') + ')', backgroundSize: 'cover', backgroundPosition: 'center' }">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
      class="demo-ruleForm bordered-form readable-text">
      <!-- 表单项内容 -->
      <h1 class="system-title" style="color: black;">高校综合评价系统</h1>
      <el-form-item label="登录身份">
        <el-select v-model="ruleForm.role" placeholder="请选择登录身份">
          <el-option label="学生" value="2"></el-option>
          <el-option label="教师" value="1"></el-option>
          <el-option label="管理员" value="0"></el-option>
        </el-select>
      </el-form-item>
      <div class="input-group">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="ruleForm.name" autocomplete="off" class="circled-input"></el-input>
        </el-form-item>
      </div>
      <div class="input-group">
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off" class="circled-input"></el-input>
        </el-form-item>
      </div>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        //
        ruleForm: {
          name: '',
          password: '',
          role: '' // 新增的登录身份属性
        },
        rules: {
          name: [{
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }],
          role: [{
            required: true,
            message: '请选择登录身份',
            trigger: 'change'
          }] // 新增的验证规则
        },
      };
    },
    methods: {
      async submitForm() {
        console.log(this.ruleForm);
        // 在这里根据role的值进行不同的登录处理
        let res = await this.$http.post("/login", this.ruleForm)
        if (res.code === 200) {
          console.log('登录成功');
          console.log(res.data)
          // 跳转到首页
          localStorage.setItem('token', res.data); // 存储token
          localStorage.setItem('role', this.ruleForm.role); // 存储role
          localStorage.setItem('name', this.ruleForm.name); //存储name
          this.$router.push('/')
          //this.$router.push('/teacher');
        } else {
          console.log('登录失败');
          console.log(res.data)
          //提示登录失败
          this.$message.error('登录失败');
          // 跳转到登录页并提示登录失败
          //this.$router.push('/login');
        }
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    },
  };

</script>

<style scoped>
  .login-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    padding: 20px;
    box-sizing: border-box;
  }

  .system-title {
    margin-bottom: 20px;
    font-size: 24px;
    color: white;
    /* 根据背景图调整文字颜色以确保可读性 */
    text-align: center;
  }

  .input-group {
    margin-bottom: 20px;
  }

  .circled-input .el-input__inner {
    border-radius: 20px;
    /* 圆形边框半径 */
    padding: 10px 20px;
    border: 1px solid #cccccc;
    /* 边框颜色，可根据需要调整 */
    box-sizing: border-box;
  }

  .circled-input .el-input__inner:focus {
    border-color: #409EFF;
    /* 聚焦时边框颜色 */
    outline: none;
  }

  .bordered-form {
    border: 1px solid #dcdcdc;
    padding: 20px;
    border-radius: 5px;
  }

  .readable-text {
    /* 设置一个确保文本可读性的背景颜色 */
    background-color: rgba(255, 255, 255, 0.8);
    /* 半透明白色背景 */
    /* 或者使用纯色背景，例如：background-color: #ffffff; */
    /* 确保文本颜色与背景形成高对比度 */
    color: #000000;
    /* 黑色文本 */
    /* 如果文本颜色也是动态的，你可能需要根据背景动态调整文本颜色 */
  }

</style>
