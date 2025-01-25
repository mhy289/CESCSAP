<template>
  <div>
    <!-- 搜索栏 -->
    <div class="demo-input-size">
      <el-input placeholder="请输入用户名" prefix-icon="el-icon-search" style="width: 250px; margin-right: 10px"
        v-model="searchParams.username"></el-input>
      <el-button round type="primary" @click="search">搜索</el-button>
      <el-button round type="info" @click="reload">重置</el-button>
    </div>

    <!-- 按钮栏 -->
    <div style="padding-top: 10px">
      <el-button style="margin: 5px; width: 100px" round type="success" @click="handleAdd">
        <i class="el-icon-circle-plus" style="padding-right: 6px"></i>新增
      </el-button>
      <!-- <el-button style="margin: 5px; width: 120px" round type="danger" @click="delBatch">
        <i class="el-icon-remove" style="padding-right: 6px"></i>批量删除
      </el-button> -->
    </div>

    <!-- 新增/编辑用户弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form label-width="100px" style="padding: 0 60px">
        <el-form-item label="用户名">
          <el-input v-model="user.username" autocomplete="off" :disabled="dialogTitle === '编辑用户'"></el-input>
        </el-form-item>
        <el-form-item label="管理权限">
          <el-select v-model="user.admin" placeholder="请选择">
            <el-option v-for="item in roleOptions" :key="item.value" :label="item.label"
              :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="user.sex" placeholder="请选择">
            <el-option v-for="item in genderOptions" :key="item.value" :label="item.label"
              :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="user.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="user.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </el-dialog>

    <!-- 用户表格列表 -->
    <el-table stripe border fixed size="medium" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" width="100"></el-table-column>
      <el-table-column prop="admin" label="管理权限" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="100"></el-table-column>
      <!-- <el-table-column label="管理权限" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.role === 'user'">用户</span>
          <span v-if="scope.row.role === 'admin'">管理员</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.gender === 'male'">男</span>
          <span v-if="scope.row.gender === 'female'">女</span>
        </template>
      </el-table-column> -->
      <el-table-column prop="phone" label="电话" width="180"></el-table-column>
      <el-table-column prop="address" label="地址" width="200"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <div class="block" style="flex: 0 0 auto">
      <el-pagination :current-page="currentPage" :page-sizes="[3, 5, 8, 10]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentPage">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "User",
    data() {
      return {
        tableData: [],
        roleOptions: [{
            value: 1,
            label: '管理员'
          },
          {
            value: 0,
            label: '用户'
          },
        ],
        genderOptions: [{
            value: 1,
            label: '男'
          },
          {
            value: 0,
            label: '女'
          },
        ],
        total: 0,
        pageSize: 5,
        currentPage: 1,
        searchParams: {
          username: '',
        },
        dialogFormVisible: false,
        dialogFormVisible2: false,
        dialogTitle: '',
        user: {},
        multipleSelection: []
      };
    },
    created() {
      this.load();
    },
    methods: {
      handleSizeChange(pageSize) {
        this.pageSize = pageSize;
        this.load();
      },
      handleCurrentPage(currentPage) {
        this.currentPage = currentPage;
        this.load();
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      async load() {
        let res = await this.$http.get('/users/current/' + this.currentPage + '/size/' + this.pageSize)
        console.log(res)
        if (res.code === 200) {
          this.tableData = res.data.list;
          this.total = res.data.total;
        }
      },
      async search() {
        console.log(this.searchParams.username)
        let res = await this.$http.post('/users/current/' + this.currentPage + '/size/' + this.pageSize,{
            username: this.searchParams.username
        } )
        console.log(res)
        if (res.code === 200) {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
            this.$message.error(res.data.msg);
        }
      },
      reload() {
        this.searchParams.username = '';
        this.load();
      },
      async save() {
        if (this.dialogTitle === '新增用户'){
            console.log(this.user)
            let res = await this.$http.post('/user', this.user)
            if (res.code === 200) {
                this.$message.success("新增成功");
                this.dialogFormVisible = false;
                this.load();
            } else {
                this.$message.error(res.data.msg);
            }
        } else if (this.dialogTitle === '编辑用户') {
            let res = await this.$http.put('/user/' + this.user.id, this.user)
            if (res.code === 200) {
                this.$message.success("编辑成功");
                this.dialogFormVisible = false;
                this.load();
            } else {
                this.$message.error(res.data.msg);
            }
        }
        /* const url = this.dialogTitle === '新增用户' ? '/user' : `/user/${this.user.id}`;
        const method = this.dialogTitle === '新增用户' ? 'post' : 'put';

        axios[method](url, this.user).then(res => {
          if (res.data.code === '200') {
            this.$message.success("操作成功");
            this.dialogFormVisible = false;
            this.load();
          } else {
            this.$message.error(res.data.msg);
          }
        }); */
      },
      handleAdd() {
        this.dialogTitle = '新增用户';
        this.dialogFormVisible = true;
        this.user = {};
      },
      handleEdit(row) {
        this.user = JSON.parse(JSON.stringify(row));
        this.dialogTitle = '编辑用户';
        this.dialogFormVisible = true;
      },
      handleDelete(id) {
        this.$confirm('确认删除该用户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete(`/user/${id}`).then(res => {
            if (res.data.code === '200') {
              this.$message.success("删除成功");
              this.load();
            } else {
              this.$message.error(res.data.msg);
            }
          });
        });
      },
      async delBatch() {
        const ids = this.multipleSelection.map(v => v.id);
        let res = await this.$http.delete()
        /* this.$confirm('确认删除这些用户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.post("/user/del/batch", ids).then(res => {
            if (res.data.code === '200') {
              this.$message.success("删除成功");
              this.load();
            } else {
              this.$message.error(res.data.msg);
            }
          });
        }); */
      }
    }
  };

</script>
