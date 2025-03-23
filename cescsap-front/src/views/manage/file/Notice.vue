<template>
    <div class="notice-container">
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input
          v-model="searchTitle"
          placeholder="请输入公告标题"
          style="width: 300px; margin-right: 15px"
          clearable
          @keyup.enter.native="fetchNotices"
        />
        <el-button type="primary" @click="fetchNotices">搜索</el-button>
      </div>
  
      <!-- 公告卡片列表 -->
      <div v-loading="loading" class="notice-list">
        <el-card
          class="notice-card"
          v-for="notice in noticeList"
          :key="notice.noticeId"
          shadow="hover"
        >
          <div class="card-header">
            <router-link :to="`/notice/${notice.noticeId}`" class="notice-title">
              {{ notice.title }}
            </router-link>
          </div>
          <div class="card-meta">
            <span class="publisher">发布人：{{ notice.publisher }}</span>
            <span class="create-time">发布时间：{{ formatTime(notice.createTime) }}</span>
          </div>
        </el-card>
      </div>
  
      <!-- 分页 -->
      <el-pagination
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
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
        total: 0
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
  
  .notice-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .notice-card {
    padding: 15px;
    border-radius: 4px;
    transition: box-shadow 0.3s;
    cursor: pointer;
  }
  
  .notice-card:hover {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }
  
  .card-header {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .notice-title {
    color: #409eff;
    text-decoration: none;
  }
  
  .notice-title:hover {
    text-decoration: underline;
  }
  
  .card-meta {
    font-size: 14px;
    color: #999;
    display: flex;
    justify-content: space-between;
  }
  
  .el-pagination {
    margin-top: 20px;
    text-align: right;
  }
  </style>
  