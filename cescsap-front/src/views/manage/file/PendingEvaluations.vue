<template>
  <div>
    <h2>待评价教师</h2>
    <el-table :data="pending" border>
      <el-table-column prop="courseId" label="课程ID" />
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="teacherId" label="教师ID" />
      <el-table-column prop="teacherName" label="教师姓名" />
      <el-table-column label="操作">
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click="openEval(row)">
            去评价
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="评价教师" :visible.sync="showEval" width="600px">
      <el-form :model="evalForm" label-width="120px">
        <el-form-item label="课程名称">
          {{ evalForm.courseName }}
        </el-form-item>
        <el-form-item label="教师姓名">
          {{ evalForm.teacherName }}
        </el-form-item>

        <!-- 多维度打分 -->
        <el-form-item v-for="(dim, idx) in dimensions" :key="dim.dimensionId" :label="dim.dimensionName">
          <el-rate v-model="evalForm.evaluationDetails[idx].score" :max="5" show-text />
        </el-form-item>

        <el-form-item label="评论">
          <el-input type="textarea" v-model="evalForm.comment" placeholder="请输入评价" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="showEval = false">取消</el-button>
        <el-button type="primary" @click="submitEval">
          提交
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    data() {
      return {
        pending: [],
        dimensions: [],
        showEval: false,
        evalForm: {
          studentId: '',
          courseId: '',
          teacherId: '',
          comment: '',
          evaluationTime: '',
          evaluationDetails: [] // 每项 { dimensionId, score }
        }
      }
    },
    async mounted() {
      await this.loadPending()
      // 预先加载所有评价维度
      const dimRes = await this.$http.get('/eval/dimensions')
      console.log(dimRes)
      this.dimensions = dimRes.data
    },
    methods: {
      async loadPending() {
        const sid = localStorage.getItem('id')
        const res = await this.$http.get(`/student/${sid}/pending-evals`)
        this.pending = res.data
      },
      openEval(row) {
        // 初始化表单
        this.evalForm = {
          studentId: localStorage.getItem('id'),
          courseId: row.courseId,
          courseName: row.courseName,
          teacherName: row.teacherName,
          teacherId: row.teacherId,
          comment: '',
          evaluationTime: dayjs().format(),
          evaluationDetails: this.dimensions.map(dim => ({
            dimensionId: dim.dimensionId,
            score: 0
          }))
        }
        this.showEval = true
      },
      async submitEval() {
        // 构造请求体
        const payload = {
          ...this.evalForm,
          // 可能后端需要嵌套 evaluationDetails
          evaluationDetails: this.evalForm.evaluationDetails
        }
        const res = await this.$http.post(
          `/student/${this.evalForm.studentId}/eval`,
          payload
        )
        if (res.code === 200) {
          this.$message.success('评价成功')
          this.showEval = false
          this.loadPending()
        } else {
          this.$message.error('评价失败')
        }
      }
    }
  }

</script>

<style scoped>
  .dialog-footer {
    text-align: right;
  }

</style>
