<template>
  <div class="qa-container">
    <el-table :data="qaList" style="width: 100%" border stripe v-loading="loading">
      <el-table-column prop="qaId" label="ID" width="80" align="center" />
      <el-table-column prop="question" label="常见问题" width="150" />
      <el-table-column prop="answer" label="回答" min-width="200" />
      <el-table-column label="操作" width="150" fixed="right">
        <template slot-scope="{row}">
          <el-button type="text" @click="showEditDialog(row)">编辑</el-button>
          <el-popconfirm title="确认删除该问答？" @confirm="deleteQA(row.qaId)">
            <el-button slot="reference" type="text" style="color: #F56C6C">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div class="add-button">
      <el-button type="success" @click="openAddDialog">添加问答</el-button>
    </div>

    <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />

    <!-- 添加/编辑问答弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="currentQA" ref="qaForm" label-width="80px">
        <el-form-item label="问题">
          <el-input type="textarea" v-model="currentQA.question" placeholder="请输入问题" />
        </el-form-item>
        <el-form-item label="回答">
          <el-input type="textarea" v-model="currentQA.answer" placeholder="请输入回答" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitQA">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'QAList',
    data() {
      return {
        qaList: [],
        loading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        dialogVisible: false,
        dialogType: 'add',
        currentQA: {
          qaId: null,
          question: '',
          answer: ''
        }
      }
    },
    computed: {
      dialogTitle() {
        return this.dialogType === 'add' ? '添加问答' : '编辑问答'
      }
    },
    mounted() {
      this.fetchQAs()
    },
    methods: {
      async fetchQAs() {
        this.loading = true
        try {
          const res = await this.$http.get('/qas/page/'+this.pageNum+'/size/'+this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.qaList = res.data.list
          }
        } finally {
          this.loading = false
        }
      },

      openAddDialog() {
        this.dialogType = 'add'
        this.currentQA = {
          question: '',
          answer: ''
        }
        this.dialogVisible = true
      },

      showEditDialog(row) {
        this.dialogType = 'edit'
        this.currentQA = {
          ...row
        }
        this.dialogVisible = true
      },

      async submitQA() {
        const apiUrl = this.dialogType === 'add' ? '/qas' : `/qas/${this.currentQA.qaId}`
        const method = this.dialogType === 'add' ? 'post' : 'put'

        const res = await this.$http.post('/qa',this.currentQA)
        if (res.code === 200) {
          this.$message.success('操作成功')
          this.dialogVisible = false
          this.fetchQAs()
        }
      },

      async deleteQA(id) {
        const res = await this.$http.delete(`/qas/${id}`)
        if (res.code === 200) {
          this.$message.success('删除成功')
          this.fetchQAs()
        }
      },

      handleSizeChange(size) {
        this.pageSize = size
        this.fetchQAs()
      },

      handleCurrentChange(page) {
        this.pageNum = page
        this.fetchQAs()
      }
    }
  }

</script>

<style scoped>
  .qa-container {
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
