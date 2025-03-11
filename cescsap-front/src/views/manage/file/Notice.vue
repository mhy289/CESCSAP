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
      <el-table-column label="操作" width="120">
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click="$router.push(`/notice/${row.noticeId}`)">
            查看
          </el-button>
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
  //import { getNoticeList } from '@/api/notice'
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
      //this.fetchNotices()
      this.load()
    },
    methods: {
      async load() {
        //默认分页查询所有公告
        let res = await this.$http.get('/notices/page/' + this.pageNum + '/size/' + this.pageSize)
        if (res.code == 200) {
          //this.total = res.data.total
          this.noticeList = res.data.list
          console.log(res)
          console.log(res.data)
          console.log(res.list)
          console.log(res.total)
          console.log(this.noticeList)
        } else {
          console.error('获取公告列表失败:', res)
          this.noticeList = []
          this.total = 0
        }
      },
      async fetchNotices() {
        this.loading = true
        try {
          let res = await this.$http.get('/notices/title/' + this.searchTitle + '/page/' + this.pageNum + '/size/' +
            this.pageSize)
          /* const params = {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            title: this.searchTitle
          } */
          /* const {
            data
          } = await getNoticeList(params) */
          if (res.code == 200) {
            this.total = res.data.total
            this.noticeList = res.data.list
            console.log(this.noticeList)
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

  .el-pagination {
    margin-top: 20px;
    text-align: right;
  }

</style>
