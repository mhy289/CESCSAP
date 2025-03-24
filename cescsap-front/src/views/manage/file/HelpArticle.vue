<template>
    <div class="notice-container">
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input v-model="searchTitle" placeholder="请输入公告标题" style="width: 300px; margin-right: 15px" clearable
          @keyup.enter.native="fetchNotices" />
        <el-button type="primary" @click="fetchNotices">搜索</el-button>
      </div>
  
      <!-- 公告表格 -->
      <el-table :data="noticeList" style="width: 100%" border stripe v-loading="loading">
        <el-table-column prop="title" label="标题" width="300">
          <template slot-scope="{ row }">
            <router-link :to="`/notice/${row.noticeId}`">{{ row.title }}</router-link>
          </template>
        </el-table-column>
        <el-table-column prop="publisher" label="发布人" width="150" />
        <el-table-column prop="createTime" label="发布时间" width="200">
          <template slot-scope="{ row }">
            {{ formatTime(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" @click="editNotice(row.noticeId)">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteNotice(row.noticeId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 添加公告按钮 -->
      <div class="add-button">
        <el-button type="success" @click="openAddNoticeDialog">添加公告</el-button>
      </div>
  
      <!-- 分页 -->
      <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
  
      <!-- 添加公告弹窗 -->
      <el-dialog title="添加公告" :visible.sync="addNoticeDialogVisible">
        <el-form :model="newNotice" ref="noticeForm" label-width="80px">
          <el-form-item label="标题">
            <el-input v-model="newNotice.title" placeholder="请输入公告标题" />
          </el-form-item>
          <el-form-item label="内容">
            <el-input type="textarea" v-model="newNotice.content" placeholder="请输入公告内容" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addNoticeDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddNotice">提交</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
    import dayjs from 'dayjs'
    export default {
      name: 'NoticeList',
      data() {
        return {
          searchTitle: '',
          noticeList: [],
          loading: false,
          pageNum: 1,
          pageSize: 10,
          total: 0,
          // 控制添加公告弹窗显示
          addNoticeDialogVisible: false,
          // 新增公告数据，字段对应后端 POJO Notice
          newNotice: {
            title: '',
            content: '',
            publisher: ''
            // createTime 由后端设置
          }
        }
      },
      mounted() {
        this.load()
      },
      methods: {
        async load() {
          // 默认分页查询所有公告
          let res = await this.$http.get('/notices/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            this.total = res.data.total
            this.noticeList = res.data.list
          } else {
            console.error('获取公告列表失败:', res)
            this.noticeList = []
            this.total = 0
          }
        },
        async fetchNotices() {
          this.loading = true
          try {
            let res = await this.$http.get(
              '/notices/title/' +
              this.searchTitle +
              '/page/' +
              this.pageNum +
              '/size/' +
              this.pageSize
            )
            if (res.code === 200) {
              this.total = res.data.total
              this.noticeList = res.data.list
            } else {
              console.error('获取公告列表失败:', res)
              this.noticeList = []
              this.total = 0
            }
          } catch (error) {
            console.error('获取公告列表失败:', error)
          } finally {
            this.loading = false
          }
        },
        formatTime(time) {
          return dayjs(time).format('YYYY-MM-DD HH:mm')
        },
        handleSizeChange(size) {
          this.pageSize = size
          this.fetchNotices()
        },
        handleCurrentChange(page) {
          this.pageNum = page
          this.fetchNotices()
        },
        // 编辑公告，具体实现根据需求自行调整
        editNotice(noticeId) {
          this.$router.push(`/notice/edit/${noticeId}`)
        },
        // 删除公告
        deleteNotice(noticeId) {
          this.$confirm('此操作将永久删除该公告, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            try {
              let res = await this.$http.delete('/notices/' + noticeId)
              if (res.code === 200) {
                this.$message.success('删除成功')
                this.fetchNotices()
              } else {
                this.$message.error('删除失败')
              }
            } catch (error) {
              console.error('删除公告失败:', error)
              this.$message.error('删除失败')
            }
          }).catch(() => {
            // 取消删除操作
          })
        },
        // 打开添加公告弹窗
        openAddNoticeDialog() {
          this.addNoticeDialogVisible = true
          // 重置公告表单数据
          this.newNotice = {
            title: '',
            content: '',
            publisher: '',
            createTime: ''
          }
        },
        articleHtml() {
          // 将原始文本转换为 HTML 格式
          return this.rawArticle.replace(/\n/g, '<br>');
        },
        formatDate(date) {
          const year = date.getFullYear();
          const month = (date.getMonth() + 1).toString().padStart(2, '0');
          const day = date.getDate().toString().padStart(2, '0');
          const hours = date.getHours().toString().padStart(2, '0');
          const minutes = date.getMinutes().toString().padStart(2, '0');
          const seconds = date.getSeconds().toString().padStart(2, '0');
          return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        // 提交添加公告
        async submitAddNotice() {
          // 可在此处添加表单校验
          if (!this.newNotice.title || !this.newNotice.content) {
            this.$message.error('请填写完整的公告信息')
            console.log(this.newNotice)
            return
          }
          var name = localStorage.getItem("name")
          console.log(name)
          this.newNotice.publisher=name
          this.newNotice.createTime=this.formatDate(new Date())
          try {
            let res = await this.$http.post('/notice', this.newNotice)
            if (res.code === 200) {
              this.$message.success('添加公告成功')
              this.addNoticeDialogVisible = false
              // 刷新公告列表
              this.fetchNotices()
            } else {
              this.$message.error('添加公告失败')
            }
          } catch (error) {
            console.error('添加公告失败:', error)
            this.$message.error('添加公告失败')
          }
        }
      }
    }
  
  </script>
  
  <style scoped>
    .notice-container {
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
  