<template>
    <div>
      <h2>待评价教师</h2>
      <el-table :data="pending" border>
        <el-table-column prop="courseId" label="课程ID" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="teacherId" label="教师ID" />
        <el-table-column prop="teacherName" label="教师姓名" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="openEval(scope.row)">
              去评价
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 自定义加载组件 -->
      <div v-if="isLoading" class="custom-loading-mask">
        <div class="custom-loading-spinner">
          <i class="el-icon-loading"></i>
          <span>加载中...</span>
        </div>
      </div>
      <el-dialog :title="'评价教师'" :visible="showEval" width="600px" :disabled="!dimensionsLoaded" @close="handleDialogClose">
        <el-form :model="evalForm" label-width="120px">
          <el-form-item label="课程名称">
            {{ evalForm.courseName }}
          </el-form-item>
          <el-form-item label="教师姓名">
            {{ evalForm.teacherName }}
          </el-form-item>
  
          <!-- 多维度打分（增加加载状态提示） -->
          <el-form-item v-for="(dim, idx) in dimensions" :key="dim.dimensionId" :label="dim.dimensionName">
            <template v-if="evalForm.evaluationDetails[idx]">
              <el-rate v-model="evalForm.evaluationDetails[idx].score" :max="5" show-text :disabled="!dimensionsLoaded" />
              <!-- 加载中禁用评分 -->
            </template>
            <template v-else>
              <span class="text-gray-400">（评价维度加载中...）</span>
            </template>
          </el-form-item>
  
          <el-form-item label="评论">
            <el-input type="textarea" v-model="evalForm.comment" placeholder="请输入评价" />
          </el-form-item>
        </el-form>
  
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleDialogClose">取消</el-button>
          <el-button type="primary" :loading="submitting" @click="submitEval">
            {{ submitting ? '提交中...' : '提交' }}
          </el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import dayjs from 'dayjs';
  
  export default {
    data() {
      return {
        pending: [],
        dimensions: [],
        showEval: false,
        submitting: false,
        isLoading: true, // 新增：全局加载状态
        dimensionsLoaded: false, // 新增：维度加载完成标记
        evalForm: {
          studentId: '',
          courseId: '',
          teacherId: '',
          comment: '',
          evaluationTime: '',
          evaluationDetails: [] // 每项 { dimensionId, score }
        }
      };
    },
    async mounted() {
      try {
        await Promise.all([this.loadPending(), this.loadDimensions()]);
        this.isLoading = false;
      } catch (error) {
        this.isLoading = false;
        console.error('数据加载出错:', error);
      }
    },
    methods: {
      async loadPending() {
        const sid = localStorage.getItem('id');
        const res = await this.$http.get(`/student/${sid}/pending-evals`);
        this.pending = res.data;
      },
      async loadDimensions() {
        const dimRes = await this.$http.get('/eval/dimensions');
        this.dimensions = dimRes.data.map((dim) => ({
          ...dim,
          defaultScore: dim.defaultScore || 0
        }));
        this.dimensionsLoaded = true;
      },
      openEval(row) {
        if (!this.dimensionsLoaded) {
          this.$message.info('评价维度加载中，请稍候...');
          return;
        }
        // 初始化表单
        // 安全初始化：确保每个维度都有对应评分项
        this.evalForm = {
          studentId: Number(localStorage.getItem('id')),
          courseId: row.courseId,
          courseName: row.courseName,
          teacherName: row.teacherName,
          teacherId: row.teacherId,
          comment: '',
          evaluationTime: dayjs().toISOString(),
          evaluationDetails: this.dimensions.map((dim, idx) => ({
            dimensionId: dim.dimensionId,
            score: this.evalForm.evaluationDetails[idx] ? this.evalForm.evaluationDetails[idx].score : 0
          }))
        };
        this.showEval = true;
      },
      handleDialogClose() {
        this.showEval = false;
      },
      async submitEval() {
        // 新增：加载中/提交中阻断
        if (!this.dimensionsLoaded || this.submitting) return;
        // 输入验证（保持原有逻辑，但增加防御）
        const allScored = this.evalForm.evaluationDetails.every(
          (d) => d && d.score !== undefined && d.score > 0
        );
        if (!allScored) {
          this.$message.warning('请对所有维度进行评分');
          return;
        }
        // 验证评论是否填写
        if (!this.evalForm.comment.trim()) {
          this.$message.warning('请输入评价内容');
          return;
        }
        if (this.submitting) return;
        this.submitting = true;
        try {
          // 构造payload（保持原有逻辑）
          const payload = {
            studentId: this.evalForm.studentId,
            courseId: this.evalForm.courseId,
            teacherId: this.evalForm.teacherId,
            comment: this.evalForm.comment,
            evaluationTime: dayjs().toISOString(),
            evaluationDetails: this.evalForm.evaluationDetails.map((d) => ({
              dimensionId: d.dimensionId,
              score: d.score
            }))
          };
          const res = await this.$http.post('/eval', payload);
          console.log(payload);
          if (res.code === 200) {
            this.$message.success('评价提交成功');
            this.showEval = false;
            await this.loadPending(); // 刷新待评价列表
          } else {
            this.$message.error(`提交失败：${res.message}`);
          }
        } catch (error) {
          // 处理网络错误
          this.$message.error(`请求出错：${error.response ? error.response.data.message : error.message}`);
        } finally {
          this.submitting = false;
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .dialog-footer {
    text-align: right;
  }
  
  .custom-loading-mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }
  
  .custom-loading-spinner {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    display: flex;
    align-items: center;
  }
  
  .custom-loading-spinner i {
    font-size: 24px;
    margin-right: 10px;
  }
  </style>    