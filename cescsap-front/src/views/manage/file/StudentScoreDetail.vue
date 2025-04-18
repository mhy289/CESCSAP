<template>
  <div class="score-detail-container">
    <!-- 学生基本信息 -->
    <el-card class="student-info">
      <div class="info-header">
        <h2>{{ studentInfo.name }} 的成绩详情</h2>
        <el-tag type="info">学号: {{ studentInfo.studentId }}</el-tag>
      </div>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="班级">{{ studentInfo.className }}</el-descriptions-item>
        <el-descriptions-item label="专业">{{ studentInfo.major }}</el-descriptions-item>
        <el-descriptions-item label="平均成绩">{{ avgScore }}</el-descriptions-item>
        <el-descriptions-item label="总绩点">{{ studentInfo.gpa }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 课程成绩表格 -->
    <el-table :data="courseScores" stripe highlight-current-row style="margin-top: 20px">
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column label="平时成绩" align="center">
        <template slot-scope="{ row }">
          {{ row.usualScore || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="考试成绩" align="center">
        <template slot-scope="{ row }">
          {{ row.examScore || '--' }}
        </template>
      </el-table-column>
      <el-table-column prop="score" label="总成绩" align="center" />
      <el-table-column prop="gpa" label="课程绩点" align="center" />
      <el-table-column label="考试日期" width="120">
        <template slot-scope="{ row }">
          {{ row.examDate | dateFormat }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    props: ['studentId'],
    data() {
      return {
        studentInfo: {},
        courseScores: [],
        loading: true
      }
    },
    computed: {
      /* avgScore() {
        const validScores = this.courseScores.filter(s => s.score != null)
        if (validScores.length === 0) return '--'
        return (validScores.reduce((sum, s) => sum + s.score, 0) / validScores.length).toFixed(1)
      },
      totalGPA() {
        const validGPA = this.courseScores.filter(s => s.gpa != null)
        if (validGPA.length === 0) return '--'
        return (validGPA.reduce((sum, s) => sum + s.gpa, 0)).toFixed(2)
      } */
    },
    filters: {
      dateFormat(val) {
        return val ? dayjs(val).format('YYYY-MM-DD') : '--'
      }
    },
    async created() {
      await this.loadData()
    },
    methods: {
      async loadData() {
        try {
          const [studentRes, scoresRes] = await Promise.all([
            this.$http.get(`/student/${this.studentId}`),
            this.$http.get(`/student/${this.studentId}/scores`)
          ])

          if (studentRes.code === 200) {
            this.studentInfo = studentRes.data
          }

          if (scoresRes.code === 200) {
            this.courseScores = scoresRes.data
          }
        } catch (e) {
          this.$message.error('数据加载失败')
        } finally {
          this.loading = false
        }
      }
    }
  }

</script>

<style scoped>
  .score-detail-container {
    padding: 20px;
  }

  .student-info {
    margin-bottom: 20px;
  }

  .info-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }

  .info-header h2 {
    margin-right: 20px;
    margin-bottom: 0;
  }

</style>
