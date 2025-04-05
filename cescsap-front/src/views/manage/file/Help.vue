<template>
    <!-- 新增外层背景容器 -->
    <div class="faq-wrapper">
      <div class="faq-container">
        <div v-loading="loading" class="faq-list">
          <div v-for="item in qaList" :key="item.qaId" class="faq-item">
            <div class="question-section">
              <div class="question-header">
                <span class="question-id">#{{ item.qaId }}</span>
                <el-tag type="info" size="mini">常见问题</el-tag>
              </div>
              <div class="question-content">{{ item.question }}</div>
            </div>
  
            <div class="answer-section">
              <div class="answer-label">回答：</div>
              <div class="answer-content">{{ item.answer }}</div>
            </div>
          </div>
        </div>
  
        <!-- 分页 -->
        <el-pagination class="pagination" :current-page="pageNum" :page-sizes="[5, 10, 20, 50]" :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </div>
  </template>
  
  <script>
    export default {
      name: 'QAList',
      data() {
        return {
          qaList: [],
          loading: false,
          pageNum: 1,
          pageSize: 10,
          total: 0,
        }
      },
      mounted() {
        this.fetchQAs()
      },
      methods: {
        async fetchQAs() {
          this.loading = true
          try {
            const res = await this.$http.get('/qas/page/' + this.pageNum + '/size/' + this.pageSize)
            if (res.code === 200) {
              this.total = res.data.total
              this.qaList = res.data.list
            }
          } finally {
            this.loading = false
          }
        },
        handleSizeChange(size) {
          this.pageSize = size
          this.fetchQAs()
        },
        handleCurrentChange(page) {
          this.pageNum = page
          this.fetchQAs()
        }
      }
    }
  </script>
  
  <style scoped>
    /* 外层背景容器 */
    .faq-wrapper {
      background: #f5f7fa;
      min-height: 100vh;
      padding: 20px 0;
    }
  
    /* 将问答内容融入背景 */
    .faq-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      /* 使用半透明背景色和背景模糊，使内容更柔和 */
      background: rgba(255, 255, 255, 0.8);
      backdrop-filter: blur(6px);
      border-radius: 8px;
      /* 去掉阴影或换成轻柔的阴影 */
      box-shadow: none;
    }
  
    .faq-list {
      margin-bottom: 30px;
    }
  
    .faq-item {
      border: 1px solid #ebeef5;
      border-radius: 8px;
      margin-bottom: 16px;
      background: white;
      text-align: left;
    }
  
    .question-section {
      padding: 16px;
      border-bottom: 1px solid #f0f2f5;
      background: #f8f9fa;
      border-radius: 8px 8px 0 0;
    }
  
    .question-header {
      display: flex;
      align-items: center;
      margin-bottom: 8px;
    }
  
    .question-id {
      color: #909399;
      margin-right: 12px;
      font-size: 12px;
    }
  
    .question-content {
      font-size: 16px;
      color: #303133;
      line-height: 1.6;
      text-align: left;
    }
  
    .answer-section {
      padding: 16px;
    }
  
    .answer-label {
      color: #67C23A;
      font-weight: 500;
      margin-bottom: 8px;
      text-align: left;
    }
  
    .answer-content {
      color: #606266;
      line-height: 1.6;
      white-space: pre-wrap;
      text-align: left;
    }
  
    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: center;
    }
  
    @media (max-width: 768px) {
      .faq-wrapper {
        padding: 10px;
      }
  
      .faq-container {
        padding: 15px;
        border-radius: 6px;
      }
  
      .question-content {
        font-size: 15px;
      }
  
      .answer-content {
        font-size: 14px;
      }
    }
  </style>
  