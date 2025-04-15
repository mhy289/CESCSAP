<template>
    <div>
      <h3>班级：{{ courseInfo.courseName }}（{{ courseInfo.description }}）</h3>
      <el-table :data="students" stripe>
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="usualScore" label="平时分" />
        <el-table-column prop="examScore" label="考试分" />
        <el-table-column prop="score" label="总分" />
        <el-table-column prop="gpa" label="绩点" />
        <el-table-column label="操作">
          <template slot-scope="{ row }">
            <el-button size="mini" type="primary" @click="openScoreDialog(row)"
              v-if="row.usualScore == null || row.examScore == null">
              评分
            </el-button>
            <el-button size="mini" type="primary" @click="msg(row)"
              v-if="row.usualScore != null && row.examScore != null">
              已评分
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 评分弹窗 -->
      <el-dialog title="录入成绩" :visible.sync="scoreDialogVisible" :modal-append-to-body="false"
        :close-on-click-modal="false" @closed="handleDialogClosed">
        <el-form :model="scoreForm" label-width="100px">
          <el-form-item label="姓名">{{ scoreForm.name }}</el-form-item>
          <el-form-item label="平时分">
            <el-input-number v-model="scoreForm.usualScore" :min="0" :max="100" />
          </el-form-item>
          <el-form-item label="考试分">
            <el-input-number v-model="scoreForm.examScore" :min="0" :max="100" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="scoreDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveScores">保存</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
    export default {
      name: 'CourseStudents',
      props: {
        courseInfo: {
          type: Object,
          required: true,
          default: () => ({
            courseId: null,
            courseName: '',
            major: ''
          })
        }
      },
      data() {
        return {
          students: [],
          scoreDialogVisible: false,
          scoreForm: {}
        }
      },
      /* async mounted() {
        this.loadStudents()
      }, */
      watch: {
        'courseInfo.courseId': {
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
              `/teacher/${teacherId}/courses/${this.courseInfo.courseId}/students`
            )
            if (res.code === 200) {
              console.log(res.data)
              this.students = res.data
            } else {
              this.$message.error('获取学生列表失败：' + res.msg)
            }
          } catch (e) {
            /* 错误处理 */
            console.error('获取学生列表异常', e)
            this.$message.error('获取学生列表异常')
          }
        },
        msg(row) {
          this.$message.info('已评分')
        },
        openScoreDialog(row) {
          this.scoreForm = {
            ...row
          }
          this.scoreDialogVisible = true
        },
        async saveScores() {
          //this.saveLoading = true // 添加加载状态
          // 计算并更新 score & gpa 由后端完成
          const res = await this.$http.post('/teacher/students/score', this.scoreForm)
          if (res.code === 200) {
            this.$message.success('保存成功')
            this.scoreDialogVisible = false
            this.loadStudents() // 直接调用子组件方法刷新
          } else {
            this.$message.error('保存失败：' + res.msg)
          }
        }
      }
    }
  
  </script>
  
  <style scoped>
    .dialog-footer {
      text-align: right;
    }
    /* .el-dialog__wrapper {
    transition: none !important;
    animation: none !important;
  } */
  
  </style>
  