<template>
  <div>
    <el-button @click="showNew=true">发帖</el-button><el-button @click="goBack">返回</el-button>
    <el-card v-for="post in posts" :key="post.postId" class="post-card" shadow="hover"
      :body-style="{ padding: '16px 20px 12px' }">
      <!-- 标题区域（精准点击区） -->
      <div class="card-header">
        <h3 @click="goDetail(post.postId)" class="title ellipsis clickable" :style="{ cursor: 'pointer' }">
          {{ post.postTitle }}
          <!-- 管理员按钮（阻止事件冒泡） -->
          <el-button v-if="isAdmin" type="danger" icon="el-icon-delete" circle size="mini"
            @click.stop="deletePost(post.postId)" class="ml-2 hover:bg-red-100"></el-button>
        </h3>
      </div>

      <!-- 卡片内容（不可点击） -->
      <div class="card-meta text-gray-500">
        <span><i class="fas fa-user mr-1"></i> {{ post.postAuthor }}</span>
        <span class="ml-auto">{{ post.createTime | formatDate }}</span>
        <span class="ml-4">浏览: {{ post.viewCount }} · 回复: {{ post.replyCount }}</span>
      </div>
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
        //增加二次确认
        this.$confirm('确认删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          // 确认删除
          let res = await this.$http.delete(`/forum/post/${postId}`)
          if (res.code === 200) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
          this.fetch()
        }).catch(() => {})
      }
    },
    filters: {
      formatDate(date) {
        // 日期格式化
        return new Date(date).toLocaleDateString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit'
        });
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

  .clickable {
    transition: color 0.15s ease;
    position: relative;
    /* 定位基准 */
  }

  /* 管理员按钮定位优化 */
  .el-button {
    position: absolute;
    right: 0;
    top: 2px;
    margin-left: 0;
  }

  .post-card {
    cursor: default;
    /* 非标题区域恢复默认光标 */
    border-radius: 12px;
    cursor: pointer;
    transition: transform 0.15s ease;
    min-height: 120px;
    /* 固定卡片高度 */

    /* &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
    } */
  }

  .card-header {
    position: relative;
    margin-bottom: 8px;
  }

  .title {
    font-size: 1.125rem;
    font-weight: 600;
    color: #1a1a1a;
    margin-bottom: 6px;
    line-height: 1.6;
  }

  .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    /* 最多显示2行 */
  }

  .admin-ops {
    position: absolute;
    top: 0;
    right: 0;
    padding: 4px;
  }

  .card-meta {
    display: flex;
    align-items: center;
    font-size: 0.875rem;
    color: #595959;
  }

  .author {
    font-weight: 500;
    color: #2f54eb;
  }

  /* 响应式适配 */
  @media (max-width: 768px) {
    .post-card {
      border-radius: 8px;
      padding: 12px;
    }

    .clickable {
      font-size: 1rem;
    }

    .title {
      font-size: 1rem;
      -webkit-line-clamp: 1;
      /* 移动端单行显示 */
    }
  }

</style>
