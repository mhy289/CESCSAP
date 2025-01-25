<!--
 * 个人信息修改页面
-->
<template>
  <el-card class="card">
    <div style="text-align: center; margin-bottom: 30px"><b>修改个人信息</b></div>

    <el-form label-width="60px">
      <el-form-item label="头像">
        <el-upload class="avatar-uploader" :action="'/avatar'" :headers="headers" :show-file-list="false"
          :on-success="handleAvatarSuccess" :on-error="handleAvatarError">
          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off" :disabled="!editable"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender" placeholder="请选择性别" :disabled="!editable">
          <el-option label="男" value="male"></el-option>
          <el-option label="女" value="female"></el-option>
          <el-option label="其他" value="other"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off" :disabled="!editable"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off" :disabled="!editable"></el-input>
      </el-form-item>
      <el-button type="primary" style="margin-left: 190px; margin-top: 20px" @click="save">确 定</el-button> <el-button
        type="primary" style="margin-left: 190px; margin-top: 20px" @click="edit">编辑</el-button>
      <el-button type="primary" style="margin-left: 190px; margin-top: 20px" @click="goback">回到上一页</el-button>
    </el-form>
  </el-card>
</template>

<script>
  import axios from 'axios';


  export default {
    name: "Person",
    data() {
      return {
        form: {
          username: '',
          avatarUrl: '',
          gender: '',
          phone: '',
          address: ''
        },
        //baseApi: 'http://your-api-base-url', // 替换为你的API基础URL
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token') // 使用存储在localStorage中的token
        },
        editable: true // 控制用户名是否可编辑
      };
    },
    methods: {
      goback() {
        this.$router.go(-1)
        //this.$router.go(-1)
      },
      handleAvatarSuccess(response) {
        this.form.avatarUrl = response.data.avatarUrl; // 根据实际返回的数据结构调整
      },
      handleAvatarError(error) {
        this.$message.error('头像上传失败');
      },
      edit() {
        this.editable = !this.editable;
      },
      save() {
        /* axios.post(`${this.baseApi}/user/update`, this.form, {
            headers: this.headers
          })
          .then(response => {
            if (response.data.code === '200') {
              this.$message.success("保存成功");
              // 更新本地存储的用户信息
              localStorage.setItem('user', JSON.stringify(this.form));
              this.$emit("refresh");
              this.$router.go(0);
            } else {
              this.$message.error(response.data.msg);
            }
          })
          .catch(error => {
            this.$message.error('保存失败，请稍后重试');
          }); */
      },
      async begin() {
        let res = await this.$http.get('/user/token')
        console.log(res)
        if (res.code === 200) {
          this.form = res.data
          this.form.username = res.data.username;
          this.form.avatarUrl = res.data.avatar;
          this.form.gender = res.data.sex;
          this.form.phone = res.data.phone;
          this.form.address = res.data.address;
          this.editable = false; // 编辑模式开始
        }
      }
    },
    created() {
      this.begin();
      /* const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.form = {
          ...user
        }; // 填充用户数据
      } */
    }
  };

</script>

<style scoped>
  .card {
    width: 500px;
    margin: 80px auto;
    padding: 30px;
  }

  .avatar-uploader {
    padding-bottom: 10px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 138px;
    height: 138px;
    line-height: 138px;
    text-align: center;
  }

  .avatar {
    width: 138px;
    height: 138px;
    display: block;
  }

</style>
