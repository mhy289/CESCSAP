<template>
  <div class="link-container">
    <el-table :data="linkList" style="width: 100%" border stripe v-loading="loading" element-loading-text="拼命加载中..."
      element-loading-spinner="el-icon-loading">
      <el-table-column prop="name" label="链接名称" width="150" />
      <el-table-column label="链接地址" min-width="200">
        <template slot-scope="{row}">
          <el-link :href="row.url" target="_blank">{{ row.url }}</el-link>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="200" />
      <el-table-column label="操作" width="150" fixed="right">
        <template slot-scope="{row}">
          <el-button type="text" @click="showEditDialog(row)">编辑</el-button>
          <el-popconfirm title="确认删除该链接？" @confirm="deleteLink(row.linkId)">
            <el-button slot="reference" type="text" style="color: #F56C6C">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div class="add-button">
      <el-button type="success" @click="openAddDialog">添加链接</el-button>
    </div>

    <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />

    <!-- 添加/编辑弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="currentLink" ref="linkForm" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="currentLink.name" placeholder="请输入链接名称" />
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="currentLink.url" placeholder="请输入链接地址">
            <template slot="prepend">https://</template>
          </el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="currentLink.description" placeholder="请输入链接描述" :rows="3" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitLink">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'FriendLink',
    data() {
      return {
        linkList: [],
        loading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        dialogVisible: false,
        dialogType: 'add',
        currentLink: {
          linkId: null,
          name: '',
          url: '',
          description: ''
        }
      }
    },
    computed: {
      dialogTitle() {
        return this.dialogType === 'add' ? '添加友情链接' : '编辑友情链接'
      }
    },
    mounted() {
      this.fetchLinks()
    },
    methods: {
      async fetchLinks() {
        this.loading = true
        const res = await this.$http.get('/links/page/' + this.pageNum + '/size/' + this.pageSize)
        if (res.code === 200) {
          this.total = res.data.total
          this.linkList = res.data.list

        } else {
          this.$message.error('获取数据失败')

        }
        this.loading = false
      },

      openAddDialog() {
        this.dialogType = 'add'
        this.currentLink = {
          sortOrder: this.linkList.length + 1,
          name: '',
          url: '',
          description: ''
        }
        this.dialogVisible = true
      },

      showEditDialog(row) {
        this.dialogType = 'edit'
        this.currentLink = {
          ...row
        }
        this.dialogVisible = true
      },

      async submitLink() {
        const method = this.dialogType === 'add' ? 'post' : 'put'
        const url = this.dialogType === 'add' ? '/links' : `/links/${this.currentLink.linkId}`

        const res = await this.$http[method](url, this.currentLink)
        if (res.code === 200) {
          this.$message.success('操作成功')
          this.dialogVisible = false
          this.fetchLinks()
        }
      },

      async deleteLink(id) {
        const res = await this.$http.delete(`/links/${id}`)
        if (res.code === 200) {
          this.$message.success('删除成功')
          this.fetchLinks()
        }
      },

      handleSizeChange(size) {
        this.pageSize = size
        this.fetchLinks()
      },

      handleCurrentChange(page) {
        this.pageNum = page
        this.fetchLinks()
      }
    }
  }

</script>

<style scoped>
  .link-container {
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

  .el-link {
    vertical-align: baseline;
  }

</style>
