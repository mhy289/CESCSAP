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
          <el-rate v-model="evaluationForm.scores[aspect.id]" :max="5" show-text>
          </el-rate>
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
        // 评价对象所属的课程与教师信息（从后端数据中提取）
        courseId: '',
        teacherId: '',
        courseName: '',
        teacherName: '',
        // 评价角度数组，每项形如 { id, name }
        evaluationAspects: [],
        // 评价表单数据，用于存放每个角度的评分
        evaluationForm: {
          // 格式为 { aspectId: 分数 }
          scores: {}
        }
      }
    },
    mounted() {
      // 可从路由或其它方式获取当前评价的课程和教师标识
      // 例如：this.courseId = this.$route.query.courseId; this.teacherId = this.$route.query.teacherId;
      // 这里示例直接调用接口获取评价数据
      this.fetchEvaluationData()
    },
    methods: {
      // 获取评价数据，包括评价角度、课程信息和教师信息
      async fetchEvaluationData() {
        try {
          let name = localStorage.getItem("name")
          // 这里采用 POST 请求，将 name 作为参数传递（根据实际后端接口调整）
          let res = await this.$http.post('/evals', {
            name: name
          })
          if (res.code === 200) {
            // 假设返回数据结构：{ data: [ [ { ... }, { ... }, ... ], [ ... ] ], msg: "success", code: 200 }
            const groups = res.data
            if (groups && groups.length > 0 && groups[0].length > 0) {
              // 取第一组第一个评价对象，用于获取课程、教师及评价角度信息
              let firstEC = groups[0][0]
              // 设置课程和教师信息
              this.courseId = firstEC.courseId
              this.teacherId = firstEC.teacherId
              if (firstEC.course) {
                this.courseName = firstEC.course.courseName
              }
              if (firstEC.teacher) {
                this.teacherName = firstEC.teacher.name
              }
              // 从 firstEC.evaluation 数组中构造评价角度
              if (firstEC.evaluation && firstEC.evaluation.length > 0) {
                this.evaluationAspects = firstEC.evaluation.map(item => {
                  return {
                    id: item.evaluationId, // 此处以 evaluationId 作为角度的唯一标识
                    name: item.comment // 使用 comment 字段作为角度名称（例如“上课状态”、“互动”、“课后作业”）
                  }
                })
                // 初始化每个角度的分数（默认 0 分）
                this.evaluationAspects.forEach(aspect => {
                  this.$set(this.evaluationForm.scores, aspect.id, 0)
                })
              }
            } else {
              this.$message.error("评价数据为空")
            }
          } else {
            this.$message.error("获取评价数据失败")
          }
        } catch (error) {
          console.error("获取评价数据失败:", error)
          this.$message.error("获取评价数据失败")
        }
      },
      // 提交评价数据
      async submitEvaluation() {
        // 构造提交数据，包含 courseId、teacherId 与各评价角度的分数
        const postData = {
          courseId: this.courseId,
          teacherId: this.teacherId,
          scores: this.evaluationForm.scores
        }
        try {
          let res = await axios.post('/api/evaluation/submit', postData)
          if (res.data.success) {
            this.$message.success("评价提交成功")
          } else {
            this.$message.error("提交失败：" + res.data.message)
          }
        } catch (error) {
          console.error("提交评价失败:", error)
          this.$message.error("提交评价失败")
        }
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
