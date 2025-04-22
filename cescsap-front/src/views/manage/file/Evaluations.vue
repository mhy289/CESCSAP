<template>
  <div class="evaluation-mgmt">
    <el-button type="primary" @click="openAddDialog">新增评价</el-button>
    <el-button type="text" @click="$router.push('/evaluation-dimensions')">
      → 管理评价维度
    </el-button>

    <el-table :data="list" border stripe v-loading="loading" style="margin-top: 16px">
      <el-table-column prop="evaluationId" label="ID" width="80" />
      <el-table-column prop="studentName" label="学生" />
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="teacherName" label="教师" />
      <el-table-column prop="comment" label="评论" />
      <el-table-column prop="evaluationScore" label="分数" />
      <el-table-column prop="evaluationTime" :formatter="formatTime" label="时间" width="180" />
      <el-table-column label="操作" width="220">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="viewDetail(row)">查看</el-button>
          <el-button size="mini" type="primary" @click="openEditDialog(row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="deleteEval(row.evaluationId)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="margin-top: 16px; text-align: right;" :current-page="pageNum" :page-size="pageSize"
      :page-sizes="[10,20,50]" :total="total" layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <!-- 新增/编辑 弹窗 -->
    <el-dialog :title="isEdit ? '编辑评价' : '新增评价'" :visible.sync="dialogVisible" width="700px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="学生">
          <el-select v-model="form.studentId" placeholder="请选择学生">
            <el-option v-for="s in students" :key="s.studentId" :label="s.name" :value="s.studentId" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程">
          <el-select v-model="form.courseId" placeholder="请选择课程">
            <el-option v-for="c in courses" :key="c.courseId" :label="c.courseName" :value="c.courseId" />
          </el-select>
        </el-form-item>
        <el-form-item label="教师">
          <el-select v-model="form.teacherId" placeholder="请选择教师">
            <el-option v-for="t in teachers" :key="t.teacherId" :label="t.teacherName" :value="t.teacherId" />
          </el-select>
        </el-form-item>
        <el-form-item label="评论">
          <el-input type="textarea" v-model="form.comment" placeholder="请输入评论" />
        </el-form-item>

        <!-- 多维度打分 -->
        <el-form-item label="维度评分">
          <el-table :data="form.evaluationDetails" border size="small">
            <el-table-column prop="dimensionName" label="维度" />
            <el-table-column label="分数">
              <template slot-scope="{ row }">
                <el-rate v-model="row.score" :max="5" show-text />
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">
          {{ isEdit ? '更新' : '提交' }}
        </el-button>
      </span>
    </el-dialog>

    <!-- 查看明细 弹窗 -->
    <el-dialog title="评价明细" :visible.sync="detailVisible" width="600px">
      <el-table :data="detailData" border size="small">
        <el-table-column prop="dimensionName" label="维度" />
        <el-table-column prop="score" label="分数" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'

  export default {
    name: 'EvaluationManagement',
    data() {
      return {
        list: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        // 弹窗相关
        dialogVisible: false,
        detailVisible: false,
        isEdit: false,
        form: {
          evaluationId: null,
          studentId: null,
          courseId: null,
          teacherId: null,
          comment: '',
          evaluationTime: '',
          evaluationDetails: []
        },
        detailData: {},

        // 下拉来源
        students: [],
        courses: [],
        teachers: [],
        dimensions: []
      }
    },
    async mounted() {
      await this.fetchLists()
      this.fetchPage()
    },
    methods: {
      // 加载下拉数据
      async fetchLists() {
        let [sRes, cRes, tRes, dRes] = await Promise.all([
          this.$http.get('/students'),
          this.$http.get('/courses'),
          this.$http.get('/teachers'),
          this.$http.get('/eval/dimensions')
        ])
        console.log(sRes.data, cRes.data, tRes.data, dRes.data)
        this.students = sRes.data
        this.courses = cRes.data
        this.teachers = tRes.data
        this.dimensions = dRes.data
      },
      // 分页查询
      async fetchPage() {
        this.loading = true;
        const maxRetries = 3; // 最大重试次数
        const retryDelay = 2000; // 每次重试之间的等待时间，单位：毫秒

        for (let attempt = 0; attempt < maxRetries; attempt++) {
          try {
            const res = await this.$http.get(
              `/evaluations/page/${this.pageNum}/size/${this.pageSize}`
            );
            // 检查响应状态
            if (res.code === 200) {
              // 检查返回数据的格式是否符合预期
              if (res.data && typeof res.data === 'object' && 'list' in res.data && 'total' in res.data) {
                this.list = res.data.list;
                this.total = res.data.total;
              } else {
                console.error('返回的数据格式不符合预期:', res.data);
              }
              this.loading = false;
              return; // 请求成功，退出重试循环
            } else {
              console.error(`第 ${attempt + 1} 次请求返回非 200 状态码:`, res.code, res.msg);
            }
          } catch (error) {
            // 处理网络错误或其他异常
            console.error(`第 ${attempt + 1} 次请求发生错误:`, error);
          }

          // 如果不是最后一次尝试，等待一段时间后重试
          if (attempt < maxRetries - 1) {
            await new Promise(resolve => setTimeout(resolve, retryDelay));
          }
        }

        // 所有重试都失败了
        console.error('所有重试均失败，无法获取数据。');
        this.loading = false;
      },
      formatTime(row, column, cellValue, index) {
        return dayjs(cellValue).format('YYYY-MM-DD HH:mm')
      },
      handleSizeChange(sz) {
        this.pageSize = sz
        this.fetchPage()
      },
      handleCurrentChange(p) {
        this.pageNum = p
        this.fetchPage()
      },

      // 打开新增
      openAddDialog() {
        this.isEdit = false
        this.form = {
          evaluationId: null,
          studentId: null,
          courseId: null,
          teacherId: null,
          comment: '',
          evaluationTime: dayjs().format(),
          evaluationDetails: this.dimensions.map(d => ({
            dimensionId: d.dimensionId,
            dimensionName: d.dimensionName,
            score: 0
          }))
        }
        this.dialogVisible = true
      },
      // 打开编辑
      async openEditDialog(row) {
        this.isEdit = true
        const res = await this.$http.get(`/evaluations/${row.evaluationId}`)
        this.detailData = res.data
        // 填表单
        this.form = {
          evaluationId: res.data.evaluationId,
          studentId: res.data.studentId,
          courseId: res.data.courseId,
          teacherId: res.data.teacherId,
          comment: res.data.comment,
          evaluationTime: res.data.evaluationTime,
          evaluationDetails: res.data.evaluationDetails.map(ed => ({
            edId: ed.edId,
            dimensionId: ed.dimensionId,
            dimensionName: ed.evaluationDimension.dimensionName,
            score: ed.score
          }))
        }
        this.dialogVisible = true
      },
      // 查看明细
      async viewDetail(row) {
        const res = await this.$http.get(`/eval/${row.evaluationId}`)
        console.log(res)
        if (res.code === 200) {
          this.detailData = res.data
          this.detailVisible = true
        } else {
          this.$message.error('获取失败')
        }
      },
      // 删除
      async deleteEval(id) {
        await this.$confirm('确认删除？')
        await this.$http.delete(`/evaluations/${id}`)
        this.$message.success('删除成功')
        this.fetchPage()
      },
      // 提交
      async submitForm() {
        const api = this.isEdit ? '/evaluations' : '/evaluations'
        const method = this.isEdit ? 'put' : 'post'
        const payload = {
          ...this.form
        }
        await this.$http[method](api, payload)
        this.$message.success(this.isEdit ? '更新成功' : '新增成功')
        this.dialogVisible = false
        this.fetchPage()
      }
    }
  }

</script>

<style scoped>
  .dialog-footer {
    text-align: right;
  }

</style>
