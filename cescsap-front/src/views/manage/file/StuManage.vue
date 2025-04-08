<template>
    <div class="student-container">
      <!-- 学生表格 -->
      <el-table :data="studentList" style="width: 100%" border stripe v-loading="loading">
        <el-table-column prop="studentId" label="学号" width="50">
          <template slot-scope="{ row }">
            {{ row.studentId }}
          </template>
        </el-table-column>
        <el-table-column prop="account" label="账号" width="100" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="password" label="密码" width="100" />
        <el-table-column prop="birthDate" label="出生日期" width="100">
          <template slot-scope="{ row }">
            {{ formatTime(row.birthDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="classId" label="班级" width="75" />
        <el-table-column prop="contact" label="联系方式" width="100" />
        <el-table-column prop="gpa" label="绩点" width="50" />
        <el-table-column prop="major" label="专业" width="150" />
        <el-table-column prop="gender" label="性别" width="100" />
        <el-table-column label="操作" width="180">
          <template slot-scope="{ row }">
            <!-- 修改为打开编辑弹窗 -->
            <el-button type="primary" size="mini" @click="openEditStudentDialog(row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteStudent(row.studentId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 添加学生按钮 -->
      <div class="add-button">
        <el-button type="success" @click="openAddStudentDialog">添加学生</el-button>
      </div>
      <div class="add-button">
        <el-button type="success" @click="checkMajor">检测合法性</el-button>
      </div>
      <div class="add-button">
        <el-button type="success" @click="average">计算绩点</el-button>
      </div>
  
      <!-- 分页 -->
      <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
  
      <!-- 添加学生弹窗 -->
      <el-dialog title="添加学生" :visible.sync="addStudentDialogVisible">
        <el-form :model="newStudent" ref="studentForm" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="newStudent.name" placeholder="请输入学生姓名" />
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="newStudent.major" placeholder="请输入专业" />
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="newStudent.classId" placeholder="请输入班级" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="newStudent.gender" placeholder="请选择性别">
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addStudentDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddStudent">提交</el-button>
        </span>
      </el-dialog>
  
      <!-- 编辑学生弹窗 -->
      <el-dialog title="编辑学生" :visible.sync="editStudentDialogVisible">
        <el-form :model="editStudent" ref="editStudentForm" label-width="80px">
          <!-- 学号一般不编辑，如果需要编辑可以取消注释 -->
          <!--
          <el-form-item label="学号">
            <el-input v-model="editStudent.studentId" placeholder="请输入学号" disabled/>
          </el-form-item>
          -->
          <el-form-item label="姓名">
            <el-input v-model="editStudent.name" placeholder="请输入学生姓名" />
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="editStudent.major" placeholder="请输入专业" />
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="editStudent.classId" placeholder="请输入班级" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="editStudent.gender" placeholder="请选择性别">
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editStudentDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEditStudent">提交</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import dayjs from 'dayjs'
  export default {
    name: 'StudentList',
    data() {
      return {
        searchName: '',
        studentList: [],
        loading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        // 控制添加学生弹窗显示
        addStudentDialogVisible: false,
        // 新增学生数据
        newStudent: {
          name: '',
          password: '123456',
          birthDate: dayjs().format('YYYY-MM-DD'),
          classId: '',
          contact: '666',
          gpa: 0,
          major: '',
          gender: ''
        },
        // 控制编辑学生弹窗显示
        editStudentDialogVisible: false,
        // 编辑学生数据
        editStudent: {
          studentId: '',
          name: '',
          major: '',
          classId: '',
          gender: ''
        }
      }
    },
    mounted() {
      this.load()
    },
    methods: {
      async load() {
        // 默认分页查询所有学生
        try {
          let res = await this.$http.get('/students/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.studentList = res.data.list
          } else {
            console.error('获取学生列表失败:', res)
            this.studentList = []
            this.total = 0
          }
        } catch (error) {
          console.error('获取学生列表失败:', error)
        }
      },
      async fetchStudents() {
        this.loading = true
        try {
          let res = await this.$http.get('/students/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.studentList = res.data.list
          } else {
            console.error('获取学生列表失败:', res)
            this.studentList = []
            this.total = 0
          }
        } catch (error) {
          console.error('获取学生列表失败:', error)
        } finally {
          this.loading = false
        }
      },
      handleSizeChange(size) {
        this.pageSize = size
        this.fetchStudents()
      },
      handleCurrentChange(page) {
        this.pageNum = page
        this.fetchStudents()
      },
      formatTime(time) {
        return dayjs(time).format('YYYY-MM-DD')
      },
      // 打开编辑学生弹窗，并复制当前行数据
      openEditStudentDialog(row) {
        this.editStudent = Object.assign({}, row)
        this.editStudentDialogVisible = true
      },
      // 提交编辑学生操作
      async submitEditStudent() {
        // 可在此处添加表单校验
        if (!this.editStudent.name || !this.editStudent.major || !this.editStudent.gender) {
          this.$message.error('请填写完整的学生信息')
          return
        }
        try {
          // 调用接口更新学生数据，假设接口为 PUT /student
          let res = await this.$http.put('/student', this.editStudent)
          if (res.code === 200) {
            this.$message.success('编辑学生成功')
            this.editStudentDialogVisible = false
            // 刷新学生列表
            this.fetchStudents()
          } else {
            this.$message.error('编辑学生失败')
          }
        } catch (error) {
          console.error('编辑学生失败:', error)
          this.$message.error('编辑学生失败')
        }
      },
      // 删除学生
      async deleteStudent(studentId) {
        this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            let res = await this.$http.delete('/student/' + studentId)
            if (res.code === 200) {
              this.$message.success('删除成功')
              this.fetchStudents()
            } else {
              this.$message.error('删除失败')
            }
          } catch (error) {
            console.error('删除学生失败:', error)
            this.$message.error('删除失败')
          }
        }).catch(() => {
          // 取消删除操作
        })
      },
      // 打开添加学生弹窗
      openAddStudentDialog() {
        this.addStudentDialogVisible = true
        // 重置学生表单数据
        this.newStudent = {
          studentId: '',
          name: '',
          major: '',
          gender: ''
        }
      },
      // 提交添加学生
      async submitAddStudent() {
        // 可在此处添加表单校验
        if (!this.newStudent.name || !this.newStudent.major || !this.newStudent.gender) {
          this.$message.error('请填写完整的学生信息')
          return
        }
        try {
          let res = await this.$http.post('/student', this.newStudent)
          if (res.code === 200) {
            this.$message.success('添加学生成功')
            this.addStudentDialogVisible = false
            // 刷新学生列表
            this.fetchStudents()
          } else {
            this.$message.error('添加学生失败', res.code)
          }
        } catch (error) {
          console.error('添加学生失败:', error)
          this.$message.error('添加学生失败')
        }
      },
      // 检测合法性
      async checkMajor() {
        let res = await this.$http.get('/student/major')
        if (res.code === 200) {
          this.$message.success('检测合法性成功', res.data)
        } else {
          this.$message.error('检测合法性失败')
        }
        this.load();
      },
      // 计算绩点
      async average() {
        let res = await this.$http.get('/students/averageGPA')
        console.log(res)
        if (res.code === 200) {
          this.$message.success('计算绩点成功', res.data)
        } else {
          this.$message.error('计算绩点失败', res.code)
        }
        this.load();
      }
    }
  }
  </script>
  
  <style scoped>
  .student-container {
    padding: 20px;
  }
  
  .search-bar {
    margin-bottom: 20px;
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
  