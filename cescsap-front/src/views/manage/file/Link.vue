<template>
    <div class="link-container">
      <h2 class="title">友情链接</h2>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" v-for="link in linkList" :key="link.linkId">
          <el-card class="link-card" shadow="hover">
            <div class="card-header">
              <a :href="link.url" target="_blank" rel="noopener">{{ link.name }}</a>
            </div>
            <div class="card-body">
              {{ link.description }}
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  export default {
    name: 'FriendLinks',
    data() {
      return {
        linkList: [],
        loading: false
      }
    },
    mounted() {
      this.fetchLinks()
    },
    methods: {
      async fetchLinks() {
        this.loading = true
        try {
          const res = await this.$http.get('/links')
          if (res.code === 200) {
            this.linkList = res.data
          } else {
            this.$message.error('获取友情链接失败')
          }
        } catch (err) {
          console.error('获取友情链接异常:', err)
          this.$message.error('获取友情链接异常')
        } finally {
          this.loading = false
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .link-container {
    padding: 20px;
    max-width: 1000px;
    margin: 0 auto;
  }
  
  .title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 24px;
    color: #303133;
  }
  
  .link-card {
    margin-bottom: 20px;
    cursor: pointer;
  }
  
  .card-header a {
    font-size: 18px;
    font-weight: bold;
    color: #409EFF;
    text-decoration: none;
  }
  
  .card-header a:hover {
    text-decoration: underline;
  }
  
  .card-body {
    margin-top: 10px;
    color: #606266;
    line-height: 1.6;
    min-height: 60px;
  }
  </style>
  