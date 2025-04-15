<template>
  <div>
    <h2>我的班级</h2>
    <el-table :data="classes" stripe>
      <el-table-column prop="className" label="班级名称" />
      <el-table-column prop="major" label="专业" />
      <el-table-column label="操作">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="openClassDialog(row)">
            查看学生
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 班级学生弹窗 -->
    <el-dialog title="班级学生列表" :visible.sync="classDialogVisible" :close-on-click-modal="false"
      @closed="currentClass = null" custom-class="responsive-dialog">
      <!-- 班级基本信息 -->
      <div v-if="currentClass" style="margin-bottom: 16px;">
        <strong>班级名称：</strong>{{ currentClass.className }}&nbsp;&nbsp;
        <strong>专业：</strong>{{ currentClass.major }}
      </div>

      <class-students v-if="currentClass" :class-id="currentClass.classId" />

      <span slot="footer" class="dialog-footer">
        <el-button @click="classDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import ClassStudents from '@/components/ClassStudents.vue'

  export default {
    name: 'TeacherClasses',
    components: {
      ClassStudents
    },
    data() {
      return {
        classes: [],
        classDialogVisible: false,
        currentClass: null
      }
    },
    async mounted() {
      const teacherId = Number(localStorage.getItem('id'))
      try {
        const res = await this.$http.get(`/teacher/classes/${teacherId}`)
        if (res.code === 200) {
          console.log(res.data)

          this.classes = res.data
        } else {
          this.$message.error('获取班级列表失败：' + res.msg)
        }
      } catch (e) {
        console.error('获取班级列表异常', e)
        this.$message.error('获取班级列表异常')
      }
    },
    methods: {
      openClassDialog(row) {
        this.currentClass = row
        this.classDialogVisible = true
      }
    }
  }

</script>

<style scoped>
  .dialog-footer {
    text-align: right;
  }

  /* 全局或 scoped 下加上下面样式 */
  .responsive-dialog .el-dialog {
    /* 取消固定宽度，改为自动宽度 */
    width: auto !important;
    /* 设置最大宽度，防止过宽 */
    max-width: 90vw !important;
    /* 设置最小宽度，防止过窄 */
    min-width: 40vw !important;
  }

  /* 如果需要对话框内容高度自适应，可调整 .el-dialog__body */
  .responsive-dialog .el-dialog__body {
    max-height: 70vh;
    /* 根据需要调整最大高度 */
    overflow-y: auto;
  }

</style>
