<template>
  <div class="partition-container">
    <div class="header">
      <h2>论坛分区</h2>
      <!-- 只有管理员才显示“新建分区”按钮 -->
      <el-button v-if="isAdmin" type="primary" @click="openNewDialog" icon="el-icon-plus">
        新建分区
      </el-button>
    </div>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="8" v-for="p in partitions" :key="p.partitionId">
        <el-card class="partition-card" shadow="hover">
          <div class="card-header">
            <h3 @click="viewPosts(p.partitionId)" class="title">{{ p.partitionTitle }}</h3>
            <!-- 管理员可删除分区 -->
            <el-button v-if="isAdmin" type="danger" icon="el-icon-delete" circle size="mini"
              @click="deletePartition(p.partitionId)"></el-button>
          </div>
          <div class="card-body">
            <p>{{ p.partitionContent }}</p>
            <small class="time">{{ formatDate(p.partitionTime) }}</small>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 新建分区弹窗 -->
    <el-dialog title="新建分区" :visible.sync="newDialogVisible">
      <el-form :model="newPartition" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="newPartition.partitionTitle" placeholder="请输入分区标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="newPartition.partitionContent" placeholder="请输入分区描述" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="newDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="createPartition">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    name: 'PartitionList',
    data() {
      return {
        partitions: [],
        newDialogVisible: false,
        newPartition: {
          partitionTitle: '',
          partitionContent: ''
        }
      }
    },
    computed: {
      isAdmin() {
        // 假设 role 存在 localStorage，0 为管理员
        return Number(localStorage.getItem('role')) === 0
      }
    },
    mounted() {
      this.fetchPartitions()
    },
    methods: {
      async fetchPartitions() {
        try {
          const res = await this.$http.get('/forum/partitions')
          if (res.code === 200) {
            this.partitions = res.data
          } else {
            this.$message.error('获取分区列表失败')
          }
        } catch (e) {
          console.error('获取分区列表异常', e)
          this.$message.error('获取分区列表异常')
        }
      },
      formatDate(date) {
        return date ? dayjs(date).format('YYYY-MM-DD HH:mm') : ''
      },
      viewPosts(partitionId) {
        this.$router.push({
          path: `/forum/partition/${partitionId}`
        })
      },
      openNewDialog() {
        this.newDialogVisible = true
        this.newPartition = {
          partitionTitle: '',
          partitionContent: ''
        }
      },
      async createPartition() {
        if (!this.newPartition.partitionTitle || !this.newPartition.partitionContent) {
          this.$message.error('请填写完整信息')
          return
        }
        try {
          const res = await this.$http.post('/forum/partitions', this.newPartition)
          if (res.code === 200) {
            this.$message.success('分区创建成功')
            this.newDialogVisible = false
            this.fetchPartitions()
          } else {
            this.$message.error('分区创建失败')
          }
        } catch (e) {
          console.error('创建分区异常', e)
          this.$message.error('创建分区异常')
        }
      },
      async deletePartition(partitionId) {
        this.$confirm('此操作将删除该分区及其所有帖子，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            const res = await this.$http.delete(`/forum/partitions/${partitionId}`)
            if (res.code === 200) {
              this.$message.success('删除成功')
              this.fetchPartitions()
            } else {
              this.$message.error('删除失败')
            }
          } catch (e) {
            console.error('删除分区异常', e)
            this.$message.error('删除分区异常')
          }
        }).catch(() => {})
      }
    }
  }

</script>

<style scoped>
  .partition-container {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }

  .partition-card {
    cursor: pointer;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .title {
    margin: 0;
    color: #409EFF;
  }

  .card-body {
    margin-top: 10px;
  }

  .time {
    color: #909399;
    font-size: 12px;
  }

</style>
