<template>
    <div class="notice-container">
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input v-model="searchTitle" placeholder="请输入事件内容" style="width: 300px; margin-right: 15px" clearable
          @keyup.enter.native="fetchNotices" />
        <el-button type="primary" @click="fetchNotices">搜索</el-button>
      </div>
  
      <el-table :data="logList" style="width: 100%" border stripe v-loading="loading">
        <el-table-column prop="logId" label="ID" width="80" align="center" />
        <el-table-column prop="eventType" label="事件类型" width="150" />
        <el-table-column prop="eventContent" label="事件内容" min-width="200" />
        <el-table-column prop="eventTime" label="发生时间" width="180">
          <template slot-scope="{row}">
            {{ formatTime(row.eventTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="eventUser" label="操作人" width="120" />
        <el-table-column prop="eventSource" label="来源" width="150" />
        <el-table-column prop="eventStatus" label="状态" width="100" align="center">
          <!-- <template slot-scope="{row}">
            <el-tag :type="statusTagType(row.eventStatus)">
              {{ row.eventStatus }}
            </el-tag>
          </template> -->
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="{row}">
            <el-button type="text" @click="showDetail(row)">详情</el-button>
            <el-popconfirm title="确认删除该日志？" @confirm="deleteLog(row.logId)">
              <el-button slot="reference" type="text" style="color: #F56C6C">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <el-pagination :current-page="pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>
  </template>
  
  <script>
    import dayjs from 'dayjs'
    export default {
      name: 'NoticeList',
      data() {
        return {
          searchTitle: '',
          logList: [],
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
          let res = await this.$http.get('/logs/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            console.log(res.data)
            this.total = res.data.total
            this.logList = res.data.list
          } else {
            console.error('获取公告列表失败:', res)
            this.noticeList = []
            this.total = 0
          }
        },
        async fetchNotices() {
          let res = await this.$http.get('/logs/page/' + this.pageNum + '/size/' + this.pageSize)
          if (res.code === 200) {
            console.log(res.data)
            this.total = res.data.total
            this.logList = res.data.list
          } else {
            console.error('获取公告列表失败:', res)
            this.noticeList = []
            this.total = 0
          }
        },
        showDetail(row){
          //弹窗提醒
          this.$message.info('敬请期待')
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
        async deleteLog(logId){
          let res = await this.$http.delete('/log/'+ logId)
          if(res.code === 200){
              this.$message.success('删除成功')
              this.fetchNotices()
          }else{
              this.$message.error('删除失败')
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
  