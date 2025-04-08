<template>
  <div>
    <el-button @click="showNew=true">发帖</el-button>
    <el-card v-for="post in posts" :key="post.postId" class="mb">
      <h3 @click="goDetail(post.postId)">{{post.postTitle}}</h3>
      <p>作者：{{post.postAuthor}} · {{post.createTime}} · 浏览：{{post.viewCount}} · 回复：{{post.replyCount}}</p>
    </el-card>
    <!-- 发帖弹窗略，同之前，只多一个标题字段 -->
    <el-dialog title="发帖" :visible.sync="showNew">
      <el-form :model="form">
        <el-form-item label="标题">
          <el-input v-model="form.postTitle" />
        </el-form-item>
        <el-form-item label="内容">
            <el-input v-model="form.postContent" />
        </el-form-item>
    </el-form>
      <span slot="footer">
        <el-button @click="showNew=false">取消</el-button>
        <el-button type="primary" @click="create">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data: () => ({
      posts: [],
      showNew: false,
      form: {
        postTitle: '',
        postContent: '',
        //从本地获取
        postAuthor: localStorage.getItem('name')
      }
    }),
    mounted() {
      this.fetch()
    },
    methods: {
      async fetch() {
        let res = await this.$http.get(`/forum/partitions/${this.$route.params.partitionId}/posts`)
        this.posts = res.data
      },
      goDetail(postId) {
        this.$router.push(`/forum/post/${postId}`)
      },
      async create() {
        let res = await this.$http.post(`/forum/partitions/${this.$route.params.partitionId}/posts`, this.form)
        if(res.code === 200) {
            this.$message.success('发帖成功')
        }
        else{
            this.$message.error('发帖失败')
        }
        this.showNew = false
        this.fetch()
      },
    }
  }

</script>
