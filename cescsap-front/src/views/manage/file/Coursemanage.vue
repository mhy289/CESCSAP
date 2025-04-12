<template>
  <div class="course-container">
    <!-- 课程表格 -->
    <el-table :data="courseList" style="width: 100%" border stripe v-loading="loading">
      <el-table-column prop="courseId" label="课程编号" width="100">
        <template slot-scope="{ row }">
          {{ row.courseId }}
        </template>
      </el-table-column>
      <el-table-column prop="courseName" label="课程名称" width="150" />
      <el-table-column prop="description" label="课程描述" width="250" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="teacherId" label="授课教师编号" width="120" />
      <el-table-column prop="teacherName" label="授课教师" width="100" />
      <el-table-column label="操作" width="240">
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click="openEditCourseDialog(row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteCourse(row.courseId)">删除</el-button>
          <el-button type="info" size="mini" @click="openCourseDetailDialog(row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加课程按钮 -->
    <div class="add-button">
      <el-button type="success" @click="openAddCourseDialog">添加课程</el-button>
    </div>
    <div class="add-button">
      <el-button type="success" @click="checkCourse">验证课程合法性</el-button>
    </div>

    <!-- 分页 -->
    <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />

    <!-- 添加课程弹窗 -->
    <el-dialog title="添加课程" :visible.sync="addCourseDialogVisible">
      <el-form :model="newCourse" ref="courseForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="newCourse.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input type="textarea" v-model="newCourse.description" placeholder="请输入课程描述" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model.number="newCourse.credit" placeholder="请输入学分" />
        </el-form-item>
        <el-form-item label="授课教师编号">
          <el-input v-model="newCourse.teacherId" placeholder="请输入授课教师编号" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCourseDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAddCourse">提交</el-button>
      </span>
    </el-dialog>

    <!-- 编辑课程弹窗 -->
    <el-dialog title="编辑课程" :visible.sync="editCourseDialogVisible">
      <el-form :model="editCourse" ref="editCourseForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="editCourse.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input type="textarea" v-model="editCourse.description" placeholder="请输入课程描述" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model.number="editCourse.credit" placeholder="请输入学分" />
        </el-form-item>
        <el-form-item label="授课教师编号">
          <el-input v-model="editCourse.teacherId" placeholder="请输入授课教师编号" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editCourseDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditCourse">提交</el-button>
      </span>
    </el-dialog>

    <!-- 课程详情弹窗：管理该课程的学生 -->
    <el-dialog title="课程详情 - 学生管理" :visible.sync="courseDetailDialogVisible" width="80%">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="已选学生" name="enrolled">
          <div style="margin-bottom: 10px;">
            <el-button type="danger" size="mini" @click="batchDeleteEnrolled">批量删除</el-button>
          </div>
          <el-table :data="enrolledStudents" style="width: 100%" border stripe
            @selection-change="handleEnrolledSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="studentId" label="学号" width="100"></el-table-column>
            <el-table-column prop="name" label="姓名" width="150"></el-table-column>
            <el-table-column prop="major" label="专业" width="150"></el-table-column>
            <!-- <el-table-column prop="score" label="成绩" width="150"></el-table-column> -->
            <el-table-column label="操作" width="120">
              <template slot-scope="{ row }">
                <el-button type="danger" size="mini" @click="deleteEnrolledStudent(row.studentId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="待选学生" name="notEnrolled">
          <div style="margin-bottom: 10px;">
            <el-button type="success" size="mini" @click="batchAddNotEnrolled">批量添加</el-button>
          </div>
          <el-table :data="notEnrolledStudents" style="width: 100%" border stripe
            @selection-change="handleNotEnrolledSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="studentId" label="学号" width="100"></el-table-column>
            <el-table-column prop="name" label="姓名" width="150"></el-table-column>
            <el-table-column prop="major" label="专业" width="150"></el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button @click="courseDetailDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'CourseList',
    data() {
      return {
        courseList: [],
        loading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        // 控制添加/编辑课程弹窗显示
        addCourseDialogVisible: false,
        editCourseDialogVisible: false,
        newCourse: {
          courseName: '',
          description: '',
          credit: 0,
          teacherId: ''
        },
        editCourse: {
          courseId: '',
          courseName: '',
          description: '',
          credit: 0,
          teacherId: ''
        },
        // 课程详情相关数据
        courseDetailDialogVisible: false,
        currentCourse: {},
        enrolledStudents: [],
        notEnrolledStudents: [],
        selectedEnrolledStudentIds: [],
        selectedNotEnrolledStudentIds: [],
        activeTab: 'enrolled'
      }
    },
    mounted() {
      this.load();
    },
    methods: {
      async load() {
        try {
          let res = await this.$http.get('/courses/page/' + this.pageNum + '/size/' + this.pageSize);
          if (res.code === 200) {
            this.total = res.data.total;
            this.courseList = res.data.list;
          } else {
            console.error('获取课程列表失败:', res);
            this.courseList = [];
            this.total = 0;
          }
        } catch (error) {
          console.error('获取课程列表失败:', error);
        }
      },
      async fetchCourses() {
        this.loading = true;
        try {
          let res = await this.$http.get('/courses/page/' + this.pageNum + '/size/' + this.pageSize);
          if (res.code === 200) {
            this.total = res.data.total;
            this.courseList = res.data.list;
          } else {
            console.error('获取课程列表失败:', res);
            this.courseList = [];
            this.total = 0;
          }
        } catch (error) {
          console.error('获取课程列表失败:', error);
        } finally {
          this.loading = false;
        }
      },
      async checkCourse() {
        let res = await this.$http.get('/courses/check');
        if (res.code === 200) {
          this.$message.success('课程检查成功');
          //this.fetchCourses();
        } else {
          this.$message.error('课程检查失败');
        }
      },
      handleSizeChange(size) {
        this.pageSize = size;
        this.fetchCourses();
      },
      handleCurrentChange(page) {
        this.pageNum = page;
        this.fetchCourses();
      },
      openAddCourseDialog() {
        this.addCourseDialogVisible = true;
        this.newCourse = {
          courseName: '',
          description: '',
          credit: 0,
          teacherId: ''
        };
      },
      async submitAddCourse() {
        if (!this.newCourse.courseName || !this.newCourse.description || !this.newCourse.teacherId) {
          this.$message.error('请填写完整的课程信息');
          return;
        }
        try {
          let res = await this.$http.post('/courses', this.newCourse);
          if (res.code === 200) {
            this.$message.success('添加课程成功');
            this.addCourseDialogVisible = false;
            this.fetchCourses();
          } else {
            this.$message.error('添加课程失败，错误代码：' + res.code);
          }
        } catch (error) {
          console.error('添加课程失败:', error);
          this.$message.error('添加课程失败');
        }
      },
      openEditCourseDialog(row) {
        this.editCourse = Object.assign({}, row);
        this.editCourseDialogVisible = true;
      },
      async submitEditCourse() {
        if (!this.editCourse.courseName || !this.editCourse.description || !this.editCourse.teacherId) {
          this.$message.error('请填写完整的课程信息');
          return;
        }
        try {
          let res = await this.$http.put('/courses', this.editCourse);
          if (res.code === 200) {
            this.$message.success('编辑课程成功');
            this.editCourseDialogVisible = false;
            this.fetchCourses();
          } else {
            this.$message.error('编辑课程失败');
          }
        } catch (error) {
          console.error('编辑课程失败:', error);
          this.$message.error('编辑课程失败');
        }
      },
      async deleteCourse(courseId) {
        this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            let res = await this.$http.delete('/courses/' + courseId);
            if (res.code === 200) {
              this.$message.success('删除成功');
              this.fetchCourses();
            } else {
              this.$message.error('删除失败');
            }
          } catch (error) {
            console.error('删除课程失败:', error);
            this.$message.error('删除课程失败');
          }
        }).catch(() => {
          // 取消删除操作
        });
      },
      // 课程详情功能
      openCourseDetailDialog(course) {
        this.currentCourse = course;
        this.courseDetailDialogVisible = true;
        this.loadEnrolledStudents();
        this.loadNotEnrolledStudents();
      },
      async loadEnrolledStudents() {
        // 调用 API 获取当前课程已选学生列表
        try {
          let res = await this.$http.get('/course/' + this.currentCourse.courseId + '/students');
          if (res.code === 200) {
            console.log(res.data)
            this.enrolledStudents = res.data;
          } else {
            this.$message.error('获取已选学生列表失败');
          }
        } catch (error) {
          console.error('获取已选学生列表失败:', error);
        }
      },
      async loadNotEnrolledStudents() {
        // 调用 API 获取当前课程未选学生列表
        try {
          let res = await this.$http.get('/course/' + this.currentCourse.courseId + '/no_students');
          if (res.code === 200) {
            this.notEnrolledStudents = res.data;
          } else {
            this.$message.error('获取未选学生列表失败');
          }
        } catch (error) {
          console.error('获取未选学生列表失败:', error);
        }
      },
      handleEnrolledSelectionChange(selected) {
        this.selectedEnrolledStudentIds = selected.map(item => item.studentId);
      },
      handleNotEnrolledSelectionChange(selected) {
        this.selectedNotEnrolledStudentIds = selected.map(item => item.studentId);
      },
      async batchDeleteEnrolled() {
        if (this.selectedEnrolledStudentIds.length === 0) {
          this.$message.warning('请选择要删除的学生');
          return;
        }
        try {
          let res = await this.$http.delete('/courses/' + this.currentCourse.courseId + '/students', {
            data: this.selectedEnrolledStudentIds
          });
          if (res.code === 200) {
            this.$message.success('批量删除成功');
            this.loadEnrolledStudents();
            this.loadNotEnrolledStudents();
          } else {
            this.$message.error('批量删除失败');
          }
        } catch (error) {
          console.error('批量删除失败:', error);
        }
      },
      async batchAddNotEnrolled() {
        if (this.selectedNotEnrolledStudentIds.length === 0) {
          this.$message.warning('请选择要添加的学生');
          return;
        }
        try {
          let res = await this.$http.post('/courses/' + this.currentCourse.courseId + '/students', this
            .selectedNotEnrolledStudentIds);
          if (res.code === 200) {
            this.$message.success('批量添加成功');
            this.loadEnrolledStudents();
            this.loadNotEnrolledStudents();
          } else {
            this.$message.error('批量添加失败');
          }
        } catch (error) {
          console.error('批量添加失败:', error);
        }
      },
      // 单个删除已选学生
      async deleteEnrolledStudent(studentId) {
        try {
          let res = await this.$http.delete('/course/' + this.currentCourse.courseId + '/student/' + studentId);
          if (res.code === 200) {
            this.$message.success('删除成功');
            this.loadEnrolledStudents();
            this.loadNotEnrolledStudents();
          } else {
            this.$message.error('删除失败');
          }
        } catch (error) {
          console.error('删除失败:', error);
        }
      }
    }
  }

</script>

<style scoped>
  .course-container {
    padding: 20px;
  }

  .add-button {
    margin: 20px 0;
    text-align: right;
  }

  .el-pagination {
    margin-top: 20px;
    text-align: right;
  }

</style>
