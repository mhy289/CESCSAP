<template>
  <div>
    <el-button @click="showNew=true">发帖</el-button><el-button @click="goBack">返回</el-button>
    <!--  -->
    <!--  -->
    <!--  -->
    <!--  -->
    <el-card v-for="post in posts" :key="post.postId" class="mb">
      <div class="post-item">
        <h3 @click="goDetail(post.postId)">{{post.postTitle}}</h3>
        <div v-if="isAdmin" class="admin-ops">
          <el-button type="danger" icon="el-icon-delete" circle size="mini" @click="deletePost(post.postId)"
            class="mt-2"></el-button>
        </div>
        <!-- 管理员可删除分区 -->
      </div>
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
    computed: {
      isAdmin() {
        // 假设 role 存在 localStorage，0 为管理员
        return Number(localStorage.getItem('role')) === 0
      }
    },
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
      goBack() {
        this.$router.go(-1)
      },
      async create() {
        let res = await this.$http.post(`/forum/partitions/${this.$route.params.partitionId}/posts`, this.form)
        if (res.code === 200) {
          this.$message.success('发帖成功')
        } else {
          this.$message.error('发帖失败')
        }
        this.showNew = false
        this.fetch()
      },
      async deletePost(postId) {
        let res = await this.$http.delete(`/forum/post/${postId}`)
        if (res.code === 200) {
          this.$message.success('删除成功')
        } else {
          this.$message.error('删除失败')
        }
        this.fetch()
      }
    }
  }

</script>

<style scoped>
  .post-item {
    line-height: 1.5;
    /* 保持标题行高 */
  }

  .admin-ops {
    /* 可选：右对齐操作按钮 */
    text-align: right;
    margin-top: 4px;
    /* 替代方案：统一模块间距 */
  }

</style>
