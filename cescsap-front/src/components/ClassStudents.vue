<template>
  <div>
    <el-table :data="students" stripe>
      <el-table-column prop="studentId" label="学号" width="100" />
      <el-table-column prop="name" label="姓名" width="150" />
      <el-table-column prop="gender" label="性别" width="80" />
      <el-table-column prop="birthDate" label="出生日期" width="120">
        <template slot-scope="{ row }">
          {{ formatDate(row.birthDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="major" label="专业" width="150" />
      <el-table-column prop="contact" label="联系方式" width="150" />
      <el-table-column prop="gpa" label="绩点" width="80" />
      <el-table-column label="操作" width="120">
        <template slot-scope="{ row }">
          <el-button size="mini" type="info" @click="viewScoreDetail(row.studentId)">
            成绩详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import dayjs from 'dayjs'

  export default {
    name: 'ClassStudents',
    props: {
      classId: {
        type: Number,
        required: true
      }
    },
    data() {
      return {
        students: []
      }
    },
    watch: {
      classId: {
        immediate: true,
        handler(newId) {
          if (newId) this.loadStudents()
        }
      }
    },
    methods: {
      async loadStudents() {
        const teacherId = Number(localStorage.getItem('id'))
        try {
          const res = await this.$http.get(
            `/teacher/${teacherId}/classes/${this.classId}/students`
          )
          if (res.code === 200) {
            this.students = res.data
            console.log(res.data)
          } else {
            this.$message.error('获取学生列表失败：' + res.msg)
          }
        } catch (e) {
          console.error('获取学生列表异常', e)
          this.$message.error('获取学生列表异常')
        }
      },
      formatDate(date) {
        return date ? dayjs(date).format('YYYY-MM-DD') : ''
      },
      viewScoreDetail(studentId) {
        // 跳转到成绩详情页面
        // this.$router.push({ name: 'StudentScoreDetail', params: { studentId } })
        //敬请期待
        this.$message.info('敬请期待')
      }
    }
  }

</script>

<style scoped>
</style>
