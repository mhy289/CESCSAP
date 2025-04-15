<template>
  <div>
    <h2>我的课程</h2>
    <el-table :data="courses" stripe>
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="description" label="所属学院" />
      <el-table-column label="操作">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="openClassDialog(row)">
            查看学生
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 课程学生弹窗 -->
    <el-dialog title="班级学生列表" :visible.sync="courseDialogVisible" width="80%"
      @closed="handleDialogClosed"><!-- 添加关闭事件处理 -->
      <course-students v-if="currentCourse" :course-info="currentCourse" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="courseDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import CourseStudents from '@/components/CourseStudents.vue'

  export default {
    name: 'TeacherCourses',
    components: {
      CourseStudents
    },
    data() {
      return {
        courses: [],
        courseDialogVisible: false,
        currentCourse: null
      }
    },
    async mounted() {
      const teacherId = Number(localStorage.getItem('id'))
      try {
        const res = await this.$http.get(`/teacher/courses/${teacherId}`)
        if (res.code === 200) {
          this.courses = res.data
        } else {
          this.$message.error('获取课程列表失败：' + res.msg)
        }
      } catch (e) {
        console.error('获取课程列表异常', e)
        this.$message.error('获取课程列表异常')
      }
    },
    methods: {
      openClassDialog(row) {
        this.currentCourse = row
        this.courseDialogVisible = true
      },
      handleDialogClosed() {
        this.currentCourse = null // 关闭弹窗时清空当前班级
      },
      async refreshStudents() {
        if (this.currentCourse) {
          this.courseDialogVisible = false
          await this.$nextTick()
          this.courseDialogVisible = true
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
