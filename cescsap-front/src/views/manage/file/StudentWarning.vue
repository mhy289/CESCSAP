<template>
  <div class="student-warnings">
    <h2>我的预警</h2>
    <el-table :data="warnings" stripe border v-loading="loading" style="margin-top: 16px">
      <el-table-column prop="warningId" label="ID" width="80" />
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="teacherName" label="教师" />
      <el-table-column prop="warningContent" label="预警内容" />
      <el-table-column prop="sendDate" label="发送日期" width="180">
        <template slot-scope="{ row }">
          {{ formatDate(row.sendDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="studentResponse" label="我的回应" />
      <el-table-column label="操作" width="120">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="openResponseDialog(row)">
            回应
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="margin-top:16px; text-align:right;" :current-page="pageNum" :page-size="pageSize"
      :page-sizes="[10,20,50]" :total="total" layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <!-- 学生回应 弹窗 -->
    <el-dialog title="回应预警" :visible.sync="dialogVisible" width="400px">
      <el-form :model="form" label-width="100px" ref="formRef">
        <el-form-item label="预警ID">
          <el-input v-model="form.warningId" disabled />
        </el-form-item>
        <el-form-item label="回应内容" prop="studentResponse">
          <el-input type="textarea" v-model="form.studentResponse" placeholder="请输入回应" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitResponse">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'

  export default {
    name: 'StudentWarning',
    data() {
      return {
        warnings: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        dialogVisible: false,
        form: {
          warningId: null,
          studentResponse: ''
        }
      }
    },
    async mounted() {
      await this.fetchPage()
    },
    methods: {
      async fetchPage() {
        this.loading = true
        try {
          const studentId = Number(localStorage.getItem('id'))
          const res = await this.$http.get(
            `/student/warnings/${studentId}/page/${this.pageNum}/size/${this.pageSize}`
          )
          if (res.code === 200) {
            this.warnings = res.data.list || res.data
            this.total = res.data.total || this.warnings.length
          } else {
            this.$message.error('获取预警失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取预警异常')
        } finally {
          this.loading = false
        }
      },
      handleSizeChange(sz) {
        this.pageSize = sz
        this.fetchPage()
      },
      handleCurrentChange(p) {
        this.pageNum = p
        this.fetchPage()
      },
      formatDate(dt) {
        return dt ? dayjs(dt).format('YYYY-MM-DD HH:mm') : ''
      },
      openResponseDialog(row) {
        this.form = {
          warningId: row.warningId,
          studentResponse: row.studentResponse || ''
        }
        this.dialogVisible = true
      },
      async submitResponse() {
        try {
          const payload = {
            ...this.form
          }
          const res = await this.$http.put('/response', payload)
          if (res.code === 200) {
            this.$message.success('回应成功')
            this.dialogVisible = false
            this.fetchPage()
          } else {
            this.$message.error('回应失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('回应异常')
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
