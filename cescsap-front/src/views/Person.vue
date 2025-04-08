<template>
  <div class="profile-container" v-if="user">
    <el-card class="profile-card">
      <el-button type="primary" @click="goHome" icon="el-icon-back" style="margin-bottom: 20px;">
          返回主页
      </el-button>

      <h2>个人信息</h2>
      <!-- 公共字段 -->
      <el-descriptions :column="1" border>
        <el-descriptions-item label="账号ID">{{ user.account }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ user.name }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ roleLabel }}</el-descriptions-item>
      </el-descriptions>

      <!-- 管理员专属信息 -->
      <div v-if="user.role === 0" class="section">
        <h3>管理员信息</h3>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="管理员ID">{{ user.adminId }}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ user.contact }}</el-descriptions-item>
          <el-descriptions-item label="登录状态">{{ user.loginStatus === 1 ? '在线' : '离线' }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <!-- 教师专属信息 -->
      <div v-else-if="user.role === 1" class="section">
        <h3>教师信息</h3>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="教师ID">{{ user.teacherId }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ user.gender }}</el-descriptions-item>
          <el-descriptions-item label="院系">{{ user.department }}</el-descriptions-item>
          <el-descriptions-item label="出生日期">{{ formatDate(user.birthDate) }}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ user.contact }}</el-descriptions-item>
          <el-descriptions-item label="登录状态">{{ user.loginStatus === 1 ? '在线' : '离线' }}</el-descriptions-item>
        </el-descriptions>
        <h4>所授课程</h4>
        <el-tag v-for="course in user.courses" :key="course.courseId" class="course-tag"
          type="info">{{ course.courseName }}</el-tag>
        <h4>评价统计（平均分）</h4>
        <el-descriptions :column="2" border>
          <el-descriptions-item v-for="stat in user.teacherEvaluationStats" :key="stat.dimensionId"
            :label="stat.dimensionName">{{ stat.averageScore.toFixed(1) }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <!-- 学生专属信息 -->
      <div v-else-if="user.role === 2" class="section">
        <h3>学生信息</h3>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="学号">{{ user.studentId }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ user.gender }}</el-descriptions-item>
          <el-descriptions-item label="出生日期">{{ formatDate(user.birthDate) }}</el-descriptions-item>
          <el-descriptions-item label="专业">{{ user.major }}</el-descriptions-item>
          <el-descriptions-item label="班级ID">{{ user.classId }}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ user.contact }}</el-descriptions-item>
          <el-descriptions-item label="绩点">{{ user.gpa.toFixed(2) }}</el-descriptions-item>
          <el-descriptions-item label="评价状态">{{ user.evaluateStatus === 1 ? '已评价' : '未评价' }}</el-descriptions-item>
          <el-descriptions-item label="登录状态">{{ user.loginStatus === 1 ? '在线' : '离线' }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    name: 'UserProfile',
    data() {
      return {
        columns: 1,
        user: {
          name: '',
          role: ''
        }
      }
    },
    computed: {
      roleLabel() {
        switch (this.user.role) {
          case 0:
            return '管理员'
          case 1:
            return '教师'
          case 2:
            return '学生'
          default:
            return '未知'
        }
      }
    },
    mounted() {
      this.fetchProfile()
    },
    methods: {
      async fetchProfile() {
        try {
          let name = localStorage.getItem('name')
          let role = localStorage.getItem('role')
          let res = await this.$http.get('/person', { params: {name, role} })
          console.log("为什么",res)
          if (res.code ==200) {
            console.log(res.data)
            this.user = res.data
          } else {
            this.$message.error('获取用户信息失败',res.code)
          }
        } catch (e) {
          console.error('获取用户信息异常', e)
          this.$message.error('获取用户信息异常')
          this.$router.go(-1)
        }
      },
      formatDate(date) {
        return date ? dayjs(date).format('YYYY-MM-DD') : ''
      },
      goHome(){
        this.$router.push('/')
      }
    }
  }

</script>

<style scoped>
  .profile-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }

  .profile-card {
    padding: 20px;
  }

  .section {
    margin-top: 20px;
  }

  .course-tag {
    margin: 4px;
  }

</style>
