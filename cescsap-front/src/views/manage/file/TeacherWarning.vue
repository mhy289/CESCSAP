<template>
  <div class="teacher-warnings">
    <h2>发送/更新预警</h2>
    <el-table :data="warnings" stripe border v-loading="loading" style="margin-top: 16px">
      <el-table-column prop="warningId" label="ID" width="80" />
      <el-table-column prop="studentName" label="学生" />
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="warningContent" label="预警内容" />
      <el-table-column prop="studentResponse" label="学生回应">
        <template slot-scope="{ row }">
          {{ row.studentResponse || '暂无' }}
        </template>
      </el-table-column>
      <el-table-column prop="sendDate" label="发送日期" width="180">
        <template slot-scope="{ row }">
          {{ formatDate(row.sendDate) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="openEditDialog(row)">编辑发送</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="margin-top:16px; text-align:right;" :current-page="pageNum" :page-size="pageSize"
      :page-sizes="[10,20,50]" :total="total" layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <!-- 编辑/发送 弹窗 -->
    <el-dialog title="编辑/发送预警" :visible.sync="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px" ref="formRef">
        <el-form-item label="学生">
          <el-input v-model="form.studentName" disabled />
        </el-form-item>
        <el-form-item label="课程">
          <el-input v-model="form.courseName" disabled />
        </el-form-item>
        <el-form-item label="预警内容" prop="warningContent">
          <el-input type="textarea" v-model="form.warningContent" placeholder="请输入预警内容" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitWarning">发送/更新</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'

  export default {
    name: 'TeacherWarningManagement',
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
          studentId: null,
          courseId: null,
          teacherId: null,
          warningContent: ''
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
          const teacherId = Number(localStorage.getItem('id'))
          const res = await this.$http.get(
            `/teacher/warnings/${teacherId}/page/${this.pageNum}/size/${this.pageSize}`
          )
          if (res.code === 200) {
            console.log(res.data)
            this.warnings = res.data.list || res.data
            this.total = res.data.total || this.warnings.length
          } else {
            this.$message.error('获取预警列表失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取预警列表异常')
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
      openEditDialog(row) {
        this.form = {
          warningId: row.warningId,
          studentId: row.studentId,
          courseId: row.courseId,
          teacherId: row.teacherId,
          warningContent: row.warningContent,
          studentName: row.studentName,
          courseName: row.courseName
        }
        this.dialogVisible = true
      },
      async submitWarning() {
        try {
          const payload = {
            ...this.form
          }
          const res = await this.$http.put('/send', payload)
          if (res.code === 200) {
            this.$message.success('发送/更新成功')
            this.dialogVisible = false
            this.fetchPage()
          } else {
            this.$message.error('发送/更新失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('发送/更新异常')
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
