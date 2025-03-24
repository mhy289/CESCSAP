<template>
    <div class="notice-detail">
      <el-card class="detail-card">
        <!-- 返回按钮 -->
        <div class="back-btn">
          <el-button icon="el-icon-arrow-left" @click="$router.go(-1)">返回</el-button>
        </div>
        <!-- 公告内容 -->
        <h1 class="title">{{ notice.title }}</h1>
        <div class="meta-info">
          <span class="publisher">发布人：{{ notice.publisher }}</span>
          <span class="time">发布时间：{{ formatTime(notice.createTime) }}</span>
        </div>
        <!-- 使用 v-html 渲染格式化后的文章内容，并包裹在 .formatted-content 内 -->
        <div class="content" v-html="formattedContent"></div>
      </el-card>
    </div>
  </template>
  
  <script>
  import dayjs from 'dayjs'
  export default {
    name: 'NoticeDetail',
    data() {
      return {
        notice: {
          noticeId: '',
          title: '',
          publisher: '',
          content: '',
          createTime: ''
        }
      }
    },
    watch: {
      '$route.params.noticeId': {
        immediate: true,
        handler(newVal) {
          if (newVal) this.fetchNoticeDetail(newVal)
        }
      }
    },
    computed: {
      // 将 longtext 内容转换为 HTML 格式，并包裹在 .formatted-content 中
      formattedContent() {
        if (!this.notice.content) return ''
        // 以连续换行拆分段落
        const paragraphs = this.notice.content.split(/\n\s*\n/)
        // 每个段落内，将单个换行替换为 <br>
        const contentHtml = paragraphs
          .map(p => `<p>${p.trim().replace(/\n/g, '<br>')}</p>`)
          .join('')
        return `<div class="formatted-content">${contentHtml}</div>`
      }
    },
    methods: {
      async fetchNoticeDetail(noticeId) {
        try {
          let res = await this.$http.get('/notice/' + noticeId)
          if (res.code == 200) {
            this.notice = res.data
          } else {
            this.$message.error(res.msg + res.code)
            return
          }
        } catch (error) {
          console.error('获取公告详情失败:', error)
          this.$message.error('获取公告详情失败')
        }
      },
      formatTime(time) {
        return dayjs(time).format('YYYY-MM-DD HH:mm')
      }
    }
  }
  </script>
  
  <style scoped>
  .notice-detail {
    padding: 20px;
    max-width: 1000px;
    margin: 0 auto;
  }
  
  .detail-card {
    padding: 30px;
  }
  
  /* 返回按钮 */
  .back-btn {
    margin-bottom: 20px;
  }
  
  /* 标题、meta信息均左对齐 */
  .title {
   
    margin-bottom: 30px;
    color: #303133;
  }
  
  .meta-info {
    
    margin-bottom: 30px;
    color: #909399;
  }
  
  .meta-info span {
    margin-right: 15px;
  }
  
  /* 文章内容样式 */
  .content {
    text-align: left;
    font-size: 16px;
    line-height: 1.8;
    color: #606266;
    font-family: "Times New Roman", serif;
  }
  
  /* 使用深度选择器穿透 v-html 渲染的内容，确保所有段落都应用样式 */
  /* 如果你使用的是 Vue 3，推荐下面这种写法 */
 /*  ::v-deep .formatted-content p {
    text-indent: 2em;
    margin-bottom: 1em;
  } */
  
  /* 如果 ::v-deep 不生效，可尝试下面 Vue 2 的写法 */
 .formatted-content p {
   text-indent: 2em;
   margin-bottom: 1em;
 }
  </style>
  