<template>
  <div class="teacher-evaluation">
    <el-card class="evaluation-card">
      <h2>评价教师</h2>
      <!-- 显示课程名称与教师信息 -->
      <div class="info">
        <p><strong>课程名称：</strong>{{ courseName }}</p>
        <p><strong>教师：</strong>{{ teacherName }}</p>
      </div>

      <!-- 评价表单：对每个评价角度打分，使用星级标识 -->
      <el-form :model="evaluationForm" ref="evaluationForm" label-width="150px">
        <el-form-item v-for="aspect in evaluationAspects" :key="aspect.id" :label="aspect.name">
          <!-- el-rate 默认最大值为 5 -->
          <el-rate v-model="evaluationForm.scores[aspect.id]" :max="5" show-text></el-rate>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitEvaluation">提交评价</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'TeacherEvaluation',
    data() {
      return {
        // 课程与教师信息
        courseId: '', // 可从路由参数或查询中获取
        teacherId: '', // 同上
        courseName: '',
        teacherName: '',
        // 评价角度数组，从后端获取，每项形如 { id, name }
        evaluationAspects: [],
        // 评价表单数据，用于存放每个角度的评分
        evaluationForm: {
          // 格式为 { aspectId: 分数 }
          scores: {}
        }
      }
    },
    mounted() {
      // 假设通过路由传入 courseId 与 teacherId
      //this.courseId = this.$route.query.courseId || '1'
      //this.teacherId = this.$route.query.teacherId || '1'
      this.fetchEvaluationAspects()
      //this.fetchCourseTeacherInfo()
    },
    methods: {
      // 获取评价角度

      async fetchEvaluationAspects() {
        console.log(this.teacherId,"aaaa")
        let name = localStorage.getItem("name");
        let res = await this.$http.post('/evals', {
            name : name
        })
        if(res.code==200){
            console.log(res.data)
        }else{
            console.log("获取评价数据失败")
            this.$message.error("获取评价数据失败")
        }
      },
      // 获取课程和教师信息
      fetchCourseTeacherInfo() {
        axios
          .get(`/api/courseTeacherInfo?courseId=${this.courseId}&teacherId=${this.teacherId}`)
          .then(res => {
            // 假设返回数据形如 { courseName: '高等数学', teacherName: '张老师' }
            this.courseName = res.data.courseName
            this.teacherName = res.data.teacherName
          })
          .catch(err => {
            console.error('获取课程与教师信息失败:', err)
            this.$message.error('获取课程与教师信息失败')
          })
      },
      // 提交评价
      submitEvaluation() {
        // 构造提交数据：包含 courseId、teacherId 与各角度评分
        const postData = {
          courseId: this.courseId,
          teacherId: this.teacherId,
          scores: this.evaluationForm.scores
        }
        axios
          .post('/api/evaluation/submit', postData)
          .then(res => {
            if (res.data.success) {
              this.$message.success('评价提交成功')
            } else {
              this.$message.error('提交失败：' + res.data.message)
            }
          })
          .catch(err => {
            console.error('提交评价失败:', err)
            this.$message.error('提交评价失败')
          })
      }
    }
  }

</script>

<style scoped>
  .teacher-evaluation {
    padding: 20px;
  }

  .evaluation-card {
    max-width: 600px;
    margin: 0 auto;
  }

  .info p {
    margin: 5px 0;
  }

</style>
