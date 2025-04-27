<template>
  <div class="teacher-evals" v-loading="loading">
    <h2>我的评价统计</h2>
    <el-card class="stats-card" shadow="hover">
      <div>
        <strong>
          总体平均分：
          {{ stats.overallAvg != null ? stats.overallAvg.toFixed(1) : '-' }}
        </strong>
      </div>
      <el-tag v-for="dim in stats.dimensionAvgs" :key="dim.dimensionId" style="margin:4px" type="info">
        {{ dim.dimensionName }}：{{ dim.avgScore.toFixed(1) }}
      </el-tag>
    </el-card>

    <h2 style="margin-top:20px;">评价详情</h2>
    <el-table :data="evals" stripe border style="width: 100%; margin-bottom: 10px;" v-loading="loading">
      <el-table-column prop="studentName" label="学生" width="120" />
      <el-table-column prop="courseName" label="课程" width="120" />
      <el-table-column prop="evaluationScore" label="总体得分" width="100" />
      <el-table-column prop="comment" label="总体评价" width="180" />
      <el-table-column prop="evaluationTime" label="时间" width="180">
        <template slot-scope="{ row }">
          {{ formatDate(row.evaluationTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="showDetails(row)">
            查看细节
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="text-align:right; margin-top:10px" layout="total, prev, pager, next" :total="total"
      :page-size="pageSize" :current-page="pageNum" @current-change="onPageChange" />

    <!-- 细节弹窗 -->
    <el-dialog title="评价维度明细" :visible.sync="detailDialogVisible" width="50%">
      <el-table :data="detailList" stripe border style="width: 100%">
        <el-table-column prop="dimensionName" label="维度" />
        <el-table-column prop="score" label="得分" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible=false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    name: 'TeacherEvaluations',
    data() {
      return {
        stats: {
          overallAvg: 0,
          dimensionAvgs: []
        },
        evals: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        detailDialogVisible: false,
        detailList: []
      }
    },
    mounted() {
      const tid = localStorage.getItem('id')
      this.fetchStats(tid)
      this.fetchEvals(tid)
    },
    methods: {
      async fetchStats(teacherId) {
        const res = await this.$http.get(`/stats/teacher/${teacherId}`)
        if (res.code === 200) this.stats = res.data
      },
      async fetchEvals(teacherId) {
        this.loading = true
        try {
          const res = await this.$http.get(`/evals/teacher/${teacherId}/page/${this.pageNum}/size/${this.pageSize}`)
          if (res.code === 200) {

            console.log(res)
            this.evals = res.data.list
            this.total = res.data.total
          }
        } catch (err) {
          console.log(err)
          this.$message.error('错误')
        } finally {
          console.log('finally')
          this.loading = false
          console.log('finally000')
        }
      },
      onPageChange(page) {
        this.pageNum = page
        this.fetchEvals(localStorage.getItem('id'))
      },
      formatDate(t) {
        return t ? dayjs(t).format('YYYY-MM-DD HH:mm') : ''
      },
      async showDetails(row) {
        const res = await this.$http.get(
          `/teacher/${localStorage.getItem('id')}/evaluations/details/${row.evaluationId}`
        )
        if (res.code === 200) {
          console.log(res)
          this.detailList = res.data
          this.detailDialogVisible = true
        }
      }
    }
  }

</script>

<style scoped>
  .stats-card {
    padding: 16px;
    margin-bottom: 20px;
  }

  .dialog-footer {
    text-align: right;
  }

</style>
