<template>
  <div class="management-container">
    <!-- 院系管理 -->
    <div class="module">
      <h2>院系管理</h2>
      <el-row>
        <el-col :span="6">
          <el-button type="primary" @click="showDepartmentDialog('add')">添加院系</el-button>
        </el-col>
      </el-row>

      <el-table :data="departments" border stripe class="mt-20">
        <el-table-column prop="departmentId" label="院系ID" width="120"></el-table-column>
        <el-table-column prop="departmentName" label="院系名称" width="200"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button size="mini" @click="showDepartmentDialog('edit', scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteDepartment(scope.row.departmentId)"
              style="margin-left: 10px">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 专业管理 -->
    <div class="module mt-40">
      <h2>专业管理</h2>
      <el-row>
        <el-col :span="6">
          <el-button type="primary" @click="showMajorDialog('add')">添加专业</el-button>
        </el-col>
      </el-row>

      <el-table :data="majors" border stripe class="mt-20">
        <el-table-column prop="majorId" label="专业ID" width="120"></el-table-column>
        <el-table-column prop="majorName" label="专业名称" width="200"></el-table-column>
        <el-table-column label="所属院系" width="200">
          <template slot-scope="scope">
            {{ getDepartmentName(scope.row.departmentId) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button size="mini" @click="showMajorDialog('edit', scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteMajor(scope.row.majorId)"
              style="margin-left: 10px">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 院系对话框 -->
    <el-dialog title="院系管理" :visible.sync="departmentDialogVisible" width="400px">
      <el-form :model="currentDepartment" :rules="departmentRules" ref="departmentForm">
        <el-form-item label="院系名称" prop="departmentName">
          <el-input v-model="currentDepartment.departmentName"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="departmentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDepartment">保存</el-button>
      </div>
    </el-dialog>

    <!-- 专业对话框 -->
    <el-dialog title="专业管理" :visible.sync="majorDialogVisible" width="400px">
      <el-form :model="currentMajor" :rules="majorRules" ref="majorForm">
        <el-form-item label="专业名称" prop="majorName">
          <el-input v-model="currentMajor.majorName"></el-input>
        </el-form-item>
        <el-form-item label="所属院系" prop="departmentId">
          <el-select v-model="currentMajor.departmentId" placeholder="请选择所属院系">
            <el-option v-for="dept in departments" :key="dept.departmentId" :label="dept.departmentName"
              :value="dept.departmentId"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="majorDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveMajor">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        departments: [], // 院系列表
        majors: [], // 专业列表

        // 院系对话框状态
        departmentDialogVisible: false,
        currentDepartment: {
          departmentId: null,
          departmentName: ''
        },
        departmentRules: {
          departmentName: [{
            required: true,
            message: '请输入院系名称',
            trigger: 'blur'
          }]
        },

        // 专业对话框状态
        majorDialogVisible: false,
        currentMajor: {
          majorId: null,
          majorName: '',
          departmentId: null
        },
        majorRules: {
          majorName: [{
            required: true,
            message: '请输入专业名称',
            trigger: 'blur'
          }],
          departmentId: [{
            required: true,
            message: '请选择所属院系',
            trigger: 'change'
          }]
        }
      };
    },

    created() {
      this.fetchData();
    },

    methods: {
      // 获取数据
      async fetchData() {
        try {
          // 并发获取院系和专业数据（提升效率）
          const [deptRes, majorRes] = await Promise.all([
            axios.get('/departments'),
            axios.get('/majors')
          ]);
          this.departments = deptRes.data;
          this.majors = majorRes.data;
        } catch (err) {
          console.error('数据获取失败', err);
          this.$message.error('数据加载失败，请重试');
        }
      },

      // ------------------------ 院系操作优化 ------------------------
      showDepartmentDialog(type, row = {}) {
        this.departmentDialogVisible = true;
        this.currentDepartment = type === 'edit' ? {
          ...row
        } : {
          departmentId: null,
          departmentName: ''
        };
      },

      async saveDepartment() {
        try {
          await this.$refs.departmentForm.validate(); // 表单验证（同步方法，无需async）
          const {
            departmentId,
            departmentName
          } = this.currentDepartment;
          const api = departmentId ? `/department` : '/department';
          const method = departmentId ? 'put' : 'post';

          await axios[method](api, {
            departmentName
          }); // 发送请求
          this.departmentDialogVisible = false;
          await this.fetchData(); // 刷新数据（使用await保证数据更新）
          this.$message.success('操作成功');
        } catch (err) {
          console.error('保存院系失败', err);
          this.$message.error('操作失败，请检查输入');
        }
      },

      async deleteDepartment(departmentId) {
        try {
          await this.$confirm('确定要删除该院系吗？', '提示', {
            type: 'warning'
          });
          await axios.delete(`/department/${departmentId}`);
          await this.fetchData();
          this.$message.success('删除成功');
        } catch (err) {
          // 捕获取消操作或删除失败
          if (err !== 'cancel') {
            console.error('删除院系失败', err);
            this.$message.error('删除失败，请重试');
          }
        }
      },

      // ------------------------ 专业操作优化 ------------------------
      showMajorDialog(type, row = {}) {
        this.majorDialogVisible = true;
        this.currentMajor = type === 'edit' ? {
          ...row
        } : {
          majorId: null,
          majorName: '',
          departmentId: null
        };
      },

      async saveMajor() {
        try {
          await this.$refs.majorForm.validate();
          const {
            majorId,
            majorName,
            departmentId
          } = this.currentMajor;
          const api = majorId ? `/major` : '/major';
          const method = majorId ? 'put' : 'post';

          await axios[method](api, {
            majorName,
            departmentId
          });
          this.majorDialogVisible = false;
          await this.fetchData();
          this.$message.success('操作成功');
        } catch (err) {
          console.error('保存专业失败', err);
          this.$message.error('操作失败，所属院系可能已被删除');
        }
      },

      async deleteMajor(majorId) {
        try {
          await this.$confirm('确定要删除该专业吗？', '提示', {
            type: 'warning'
          });
          await axios.delete(`/major/${majorId}`);
          await this.fetchData();
          this.$message.success('删除成功');
        } catch (err) {
          if (err !== 'cancel') {
            console.error('删除专业失败', err);
            this.$message.error('删除失败，请检查是否有依赖数据');
          }
        }
      },

      // 辅助方法：通过院系ID获取名称
      getDepartmentName(departmentId) {
        return this.departments.find(dept => dept.departmentId === departmentId)?.departmentName || '-';
      }
    }
  };

</script>

<style scoped>
  .management-container {
    padding: 20px;
  }

  .module {
    margin-bottom: 40px;
  }

  .mt-20 {
    margin-top: 20px;
  }

  .mt-40 {
    margin-top: 40px;
  }

  .dialog-footer {
    text-align: right;
    padding: 10px 20px;
    border-top: 1px solid #ebeef5;
  }

</style>
