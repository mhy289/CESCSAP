<template>
  <div class="notice-detail">
    <el-card class="detail-card">
      <!-- 返回按钮 -->
      <div class="back-btn">
        <el-button icon="el-icon-arrow-left" @click="$router.go(-1)">返回</el-button>
      </div>
      <!--测试按钮-->
      <div><el-button @click=test()>测试</el-button></div>

      <!-- 公告内容 -->
      <h1 class="title">{{ notice.title }}</h1>
      <div class="meta-info">
        <span class="publisher">发布人：{{ notice.publisher }}</span>
        <span class="time">发布时间：{{ formatTime(notice.createTime) }}</span>
      </div>
      <div class="content">
        <pre>{{ notice.content }}</pre>
      </div>
    </el-card>
  </div>
</template>

<script>
  //import { getNoticeDetail } from '@/api/notice'
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
    methods: {
      async fetchNoticeDetail(noticeId) {
        try {
            //consle.log("id:",noticeId)
          //const { data } = await getNoticeDetail(id)
          //this.notice = data
          let res = await this.$http.get('/notice/' + noticeId)
          if (res.code == 200) {
            console.log(res)
            this.notice = res.data
          } else {
            this.$message.error(res.msg+res.code)
            return
          }
        } catch (error) {
          console.error('获取公告详情失败:', error)
          this.$message.error('获取公告详情失败')
        }
      },
      formatTime(time) {
        return dayjs(time).format('YYYY-MM-DD HH:mm')
      },
      test() {
        console.log('id',noticeId);
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

  .back-btn {
    margin-bottom: 20px;
  }

  .title {
    text-align: center;
    margin-bottom: 30px;
    color: #303133;
  }

  .meta-info {
    text-align: center;
    margin-bottom: 30px;
    color: #909399;
  }

  .meta-info span {
    margin: 0 15px;
  }

  .content {
    font-size: 16px;
    line-height: 1.8;
    color: #606266;
  }

  pre {
    white-space: pre-wrap;
    word-wrap: break-word;
    font-family: inherit;
  }

</style>
