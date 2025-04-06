<template>
  <div class="teacher-container">
    <!-- 教师表格 -->
    <el-table :data="teacherList" style="width: 100%" border stripe v-loading="loading">
      <el-table-column prop="teacherId" label="工号" width="100">
        <template slot-scope="{ row }">
          {{ row.teacherId }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="150" />
      <el-table-column prop="password" label="密码" width="100" />
      <el-table-column prop="department" label="院系" width="150" />
      <el-table-column prop="contact" label="联系方式" width="150" />
      <el-table-column prop="gender" label="性别" width="100" />
      <el-table-column label="操作" width="180">
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click="openEditTeacherDialog(row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteTeacher(row.teacherId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加教师按钮 -->
    <div class="add-button">
      <el-button type="success" @click="openAddTeacherDialog">添加教师</el-button>
    </div>

    <!-- 分页 -->
    <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />

    <!-- 添加教师弹窗 -->
    <el-dialog title="添加教师" :visible.sync="addTeacherDialogVisible">
      <el-form :model="newTeacher" ref="teacherForm" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="newTeacher.name" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="院系">
          <el-input v-model="newTeacher.department" placeholder="请输入院系" />
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="newTeacher.contact" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="newTeacher.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addTeacherDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAddTeacher">提交</el-button>
      </span>
    </el-dialog>

    <!-- 编辑教师弹窗 -->
    <el-dialog title="编辑教师" :visible.sync="editTeacherDialogVisible">
      <el-form :model="editTeacher" ref="editTeacherForm" label-width="80px">
        <!-- 通常工号不编辑，如需编辑可取消注释 -->
        <!--
          <el-form-item label="工号">
            <el-input v-model="editTeacher.teacherId" disabled />
          </el-form-item>
          -->
        <el-form-item label="姓名">
          <el-input v-model="editTeacher.name" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="院系">
          <el-input v-model="editTeacher.department" placeholder="请输入院系" />
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="editTeacher.contact" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editTeacher.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editTeacherDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditTeacher">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    name: 'TeacherList',
    data() {
      return {
        teacherList: [],
        loading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        // 控制添加教师弹窗显示
        addTeacherDialogVisible: false,
        // 新增教师数据
        newTeacher: {
          name: '',
          password: '123456', // 默认密码
          department: '',
          contact: '',
          gender: ''
        },
        // 控制编辑教师弹窗显示
        editTeacherDialogVisible: false,
        // 编辑教师数据
        editTeacher: {
          teacherId: '',
          name: '',
          department: '',
          contact: '',
          gender: ''
        }
      }
    },
    mounted() {
      this.load()
    },
    methods: {
      async load() {
        // 默认分页查询所有教师
        try {
          let res = await this.$http.get('/teachers/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.teacherList = res.data.list
          } else {
            console.error('获取教师列表失败:', res)
            this.teacherList = []
            this.total = 0
          }
        } catch (error) {
          console.error('获取教师列表失败:', error)
        }
      },
      async fetchTeachers() {
        this.loading = true
        try {
          let res = await this.$http.get('/teachers/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.teacherList = res.data.list
          } else {
            console.error('获取教师列表失败:', res)
            this.teacherList = []
            this.total = 0
          }
        } catch (error) {
          console.error('获取教师列表失败:', error)
        } finally {
          this.loading = false
        }
      },
      handleSizeChange(size) {
        this.pageSize = size
        this.fetchTeachers()
      },
      handleCurrentChange(page) {
        this.pageNum = page
        this.fetchTeachers()
      },
      openAddTeacherDialog() {
        this.addTeacherDialogVisible = true
        // 重置教师表单数据
        this.newTeacher = {
          teacherId: '',
          name: '',
          password: '123456',
          department: '',
          contact: '',
          gender: ''
        }
      },
      async submitAddTeacher() {
        // 表单校验
        if (!this.newTeacher.name || !this.newTeacher.department || !this.newTeacher.gender) {
          this.$message.error('请填写完整的教师信息')
          return
        }
        try {
          console.log("为什么", this.newTeacher)
          let res = await this.$http.post('/teacher', this.newTeacher)
          if (res.code === 200) {
            this.$message.success('添加教师成功')
            this.addTeacherDialogVisible = false
            this.fetchTeachers()
          } else {
            this.$message.error(res.message + '添加教师失败，错误代码：' + res.code)
          }
        } catch (error) {
          console.error('添加教师失败:', error)
          this.$message.error('添加教师失败')
        }
      },
      openEditTeacherDialog(row) {
        // 深拷贝当前行数据到 editTeacher
        this.editTeacher = Object.assign({}, row)
        this.editTeacherDialogVisible = true
      },
      async submitEditTeacher() {
        // 表单校验
        if (!this.editTeacher.name || !this.editTeacher.department || !this.editTeacher.gender) {
          this.$message.error('请填写完整的教师信息')
          return
        }
        try {
          let res = await this.$http.put('/teacher', this.editTeacher)
          if (res.code === 200) {
            this.$message.success('编辑教师成功')
            this.editTeacherDialogVisible = false
            this.fetchTeachers()
          } else {
            this.$message.error('编辑教师失败')
          }
        } catch (error) {
          console.error('编辑教师失败:', error)
          this.$message.error('编辑教师失败')
        }
      },
      async deleteTeacher(teacherId) {
        this.$confirm('此操作将永久删除该教师, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            let res = await this.$http.delete('/teacher/' + teacherId)
            if (res.code === 200) {
              this.$message.success('删除成功')
              this.fetchTeachers()
            } else {
              this.$message.error('删除失败')
            }
          } catch (error) {
            console.error('删除教师失败:', error)
            this.$message.error('删除失败')
          }
        }).catch(() => {
          // 取消删除操作
        })
      }
    }
  }

</script>

<style scoped>
  .teacher-container {
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
