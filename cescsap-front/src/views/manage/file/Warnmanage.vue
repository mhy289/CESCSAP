<template>
  <div class="warning-mgmt">
    <div class="toolbar">
      <el-button type="primary" @click="openAddDialog">新增预警</el-button>
    </div>

    <el-table :data="list" stripe border v-loading="loading" element-loading-text="加载中..." style="margin-top: 16px"
      v-if="list.length > 0">
      <el-table-column prop="warningId" label="ID" width="80" />
      <el-table-column prop="studentName" label="学生" />
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="teacherName" label="教师" />
      <el-table-column prop="warningContent" label="预警内容" />
      <el-table-column prop="sendDate" label="发送日期" width="180">
        <template slot-scope="{ row }">
          {{ formatDate(row.sendDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="studentResponse" label="学生回应" />
      <el-table-column label="操作" width="240">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="viewDetail(row)">查看</el-button>
          <el-button size="mini" type="primary" @click="openEditDialog(row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="deleteWarning(row.warningId)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-else description="暂无预警数据" />

    <el-pagination style="margin-top:16px; text-align:right;" :current-page="pageNum" :page-size="pageSize"
      :page-sizes="[10,20,50]" :total="total" layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <!-- 新增/编辑 弹窗 -->
    <el-dialog :title="isEdit ? '编辑预警' : '新增预警'" :visible.sync="dialogVisible" width="600px">
      <el-form :model="form" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="学生" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择学生">
            <el-option v-for="s in students" :key="s.studentId" :label="s.name" :value="s.studentId" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程">
            <el-option v-for="c in courses" :key="c.courseId" :label="c.courseName" :value="c.courseId" />
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择教师">
            <el-option v-for="t in teachers" :key="t.teacherId" :label="t.teacherName" :value="t.teacherId" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="warningContent">
          <el-input type="textarea" v-model="form.warningContent" placeholder="请输入预警内容" />
        </el-form-item>
        <el-form-item label="发送日期" prop="sendDate">
          <el-date-picker v-model="form.sendDate" type="datetime" placeholder="选择日期时间" style="width:100%;" />
        </el-form-item>
        <el-form-item label="学生回应" prop="studentResponse">
          <el-input type="textarea" v-model="form.studentResponse" placeholder="学生回应（可选）" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">
          {{ isEdit ? '更新' : '提交' }}
        </el-button>
      </span>
    </el-dialog>

    <!-- 查看详情 弹窗 -->
    <el-dialog title="预警详情" :visible.sync="detailVisible" width="500px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="学生">
          {{ detailData.studentName }}
        </el-descriptions-item>
        <el-descriptions-item label="课程">
          {{ detailData.courseName }}
        </el-descriptions-item>
        <el-descriptions-item label="教师">
          {{ detailData.teacherName }}
        </el-descriptions-item>
        <el-descriptions-item label="内容">
          {{ detailData.warningContent }}
        </el-descriptions-item>
        <el-descriptions-item label="发送日期">
          {{ formatDate(detailData.sendDate) }}
        </el-descriptions-item>
        <el-descriptions-item label="学生回应">
          {{ detailData.studentResponse || '—' }}
        </el-descriptions-item>
      </el-descriptions>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'

  export default {
    name: 'WarningManagement',
    data() {
      return {
        list: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        // 下拉数据
        students: [],
        courses: [],
        teachers: [],

        // 表单与弹窗
        dialogVisible: false,
        detailVisible: false,
        isEdit: false,
        form: {
          warningId: null,
          studentId: null,
          courseId: null,
          teacherId: null,
          warningContent: '',
          sendDate: '',
          studentResponse: ''
        },
        detailData: {},

        // 校验规则
        rules: {
          studentId: [{
            required: true,
            message: '请选择学生',
            trigger: 'change'
          }],
          courseId: [{
            required: true,
            message: '请选择课程',
            trigger: 'change'
          }],
          teacherId: [{
            required: true,
            message: '请选择教师',
            trigger: 'change'
          }],
          warningContent: [{
            required: true,
            message: '请输入内容',
            trigger: 'blur'
          }],
          sendDate: [{
            required: true,
            message: '请选择发送日期',
            trigger: 'change'
          }]
        }
      }
    },
    async mounted() {
      //this.message.$confirm('sss')
      // 并行加载下拉选项
      const [sRes, cRes, tRes] = await Promise.all([
        this.$http.get('/students'),
        this.$http.get('/courses'),
        this.$http.get('/teachers')
      ])
      this.students = sRes.data
      this.courses = cRes.data
      this.teachers = tRes.data

      this.fetchPage()
    },
    methods: {
      async fetchPage() {
        this.loading = true
        try {
          const res = await this.$http.get(
            `/warnings/page/${this.pageNum}/size/${this.pageSize}`
          )
          if (res.code === 200) {
            this.list = res.data.list
            this.total = res.data.total
          } else {
            this.$message.error('获取列表失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取列表异常')
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

      openAddDialog() {
        this.isEdit = false
        this.form = {
          warningId: null,
          studentId: null,
          courseId: null,
          teacherId: null,
          warningContent: '',
          sendDate: dayjs().format('YYYY-MM-DD HH:mm:ss'),
          studentResponse: ''
        }
        this.dialogVisible = true
      },
      async openEditDialog(row) {
        this.isEdit = true
        try {
          const res = await this.$http.get(`/warnings/${row.warningId}`)
          this.detailData = res.data
          this.form = {
            warningId: res.data.warningId,
            studentId: res.data.studentId,
            courseId: res.data.courseId,
            teacherId: res.data.teacherId,
            warningContent: res.data.warningContent,
            sendDate: dayjs(res.data.sendDate).format('YYYY-MM-DD HH:mm:ss'),
            studentResponse: res.data.studentResponse
          }
          this.dialogVisible = true
        } catch {
          this.$message.error('获取详情失败')
        }
      },
      async viewDetail(row) {
        try {
          const res = await this.$http.get(`/warnings/${row.warningId}`)
          this.detailData = res.data
          this.detailVisible = true
        } catch {
          this.$message.error('获取详情失败')
        }
      },
      async deleteWarning(id) {
        try {
          await this.$confirm('确认删除此预警？')
          const res = await this.$http.delete(`/warnings/${id}`)
          if (res.code === 200) {
            this.$message.success('删除成功')
            this.fetchPage()
          } else {
            this.$message.error('删除失败：' + res.msg)
          }
        } catch {}
      },
      submitForm() {
        this.$refs.formRef.validate(async valid => {
          if (!valid) return
          try {
            const api = this.isEdit ? `/warnings` : '/warnings'
            const method = this.isEdit ? 'put' : 'post'
            const payload = {
              ...this.form,
              sendDate: dayjs(this.form.sendDate).toISOString()
            }
            const res = await this.$http[method](api, payload)
            if (res.code === 200) {
              this.$message.success(this.isEdit ? '更新成功' : '新增成功')
              this.dialogVisible = false
              this.fetchPage()
            } else {
              this.$message.error((this.isEdit ? '更新' : '新增') + '失败：' + res.msg)
            }
          } catch (e) {
            console.error(e)
            this.$message.error('提交失败')
          }
        })
      }
    }
  }

</script>

<style scoped>
  .toolbar {
    text-align: right;
    margin-bottom: 8px;
  }

  .dialog-footer {
    text-align: right;
  }

</style>
