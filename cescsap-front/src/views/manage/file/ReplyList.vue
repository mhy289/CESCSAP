<template>
    <div class="reply-container">
      <!-- 帖子正文 -->
      <el-card class="mb">
        <h3>1L {{ post.postAuthor }} · {{ post.createTime }}</h3>
        <p>{{ post.postContent }}</p>
      </el-card>
  
      <!-- 回复列表 -->
      <el-card
        v-for="(r, i) in replies"
        :key="r.replyId"
        class="mb"
      >
        <h4>{{ i + 2 }}L {{ r.userName }} · {{ r.replyTime }}</h4>
        <p>{{ r.content }}</p>
      </el-card>
  
      <!-- 回复输入 -->
      <el-card>
        <el-form :model="replyForm" label-position="top">
          <el-form-item label="我的回复">
            <el-input
              type="textarea"
              v-model="replyForm.content"
              :rows="3"
              placeholder="请输入回复内容"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitReply">回复</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script>
  import dayjs from 'dayjs'
  export default {
    name: 'ReplyList',
    data() {
      return {
        post: {},
        replies: [],
        replyForm: {
          content: ''
        }
      }
    },
    async mounted() {
      await this.fetchPost()
      await this.fetchReplies()
    },
    methods: {
      async fetchPost() {
        try {
          const res = await this.$http.get(
            `/forum/posts/${this.$route.params.postId}`
          )
          if (res.code === 200) {
            this.post = res.data
          } else {
            this.$message.error('获取帖子失败')
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取帖子异常')
        }
      },
      async fetchReplies() {
        try {
          const res = await this.$http.get(
            `/forum/posts/${this.$route.params.postId}/replies`
          )
          if (res.code === 200) {
            this.replies = res.data.map(r => ({
              ...r,
              // 假设后端返回 replyTime ISO 字符串
              replyTime: dayjs(r.replyTime).format('YYYY-MM-DD HH:mm')
            }))
          } else {
            this.$message.error('获取回复列表失败')
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取回复异常')
        }
      },
      async submitReply() {
        if (!this.replyForm.content.trim()) {
          this.$message.warning('回复内容不能为空')
          return
        }
        try {
          const payload = {
            content: this.replyForm.content,
            userName: localStorage.getItem('name')
          }
          const res = await this.$http.post(
            `/forum/posts/${this.$route.params.postId}/replies`,
            payload
          )
          if (res.code === 200) {
            this.$message.success('回复成功')
            this.replyForm.content = ''
            await this.fetchReplies()
          } else {
            this.$message.error('回复失败')
          }
        } catch (e) {
          console.error(e)
          this.$message.error('回复异常')
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .reply-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .mb {
    margin-bottom: 16px;
  }
  
  .el-form {
    width: 100%;
  }
  </style>
  