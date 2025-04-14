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
    <el-dialog title="班级学生列表" :visible.sync="classDialogVisible" width="80%"
      @closed="handleDialogClosed"><!-- 添加关闭事件处理 -->
      <class-students v-if="currentClass" :class-info="currentClass" />
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
      },
      handleDialogClosed() {
        this.currentClass = null // 关闭弹窗时清空当前班级
      },
      async refreshStudents() {
        if (this.currentClass) {
          this.classDialogVisible = false
          await this.$nextTick()
          this.classDialogVisible = true
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
