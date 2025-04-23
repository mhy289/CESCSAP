<template>
  <div class="score-by-term">
    <!-- 学期选择 -->
    <el-select v-model="selectedTerm" placeholder="请选择学期" @change="onTermChange"
      style="width: 200px; margin-bottom: 16px;">
      <el-option v-for="term in terms" :key="term" :label="term" :value="term" />
    </el-select>

    <!-- 分页表格 -->
    <el-table :data="scoreList" stripe border v-loading="loading" style="width: 100%; margin-bottom: 24px;">
      <el-table-column prop="courseName" label="课程" />
      <el-table-column prop="usualScore" label="平时成绩" width="120" />
      <el-table-column prop="examScore" label="考试成绩" width="120" />
      <el-table-column prop="score" label="总成绩" width="120" />
    </el-table>

    <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="total" :page-sizes="[5,10,20]"
      :page-size="pageSize" :current-page="pageNum" @size-change="onPageSizeChange" @current-change="onPageChange"
      style="text-align: right; margin-bottom: 24px;" />

    <!-- 查看图表按钮 -->
    <div style="margin-bottom: 16px;">
      <el-button type="primary" @click="chartDialogVisible = true">
        查看统计图
      </el-button>
    </div>

    <!-- 图表弹窗 -->
    <el-dialog title="成绩统计" :visible.sync="chartDialogVisible" width="60%" @open="onDialogOpen" @close="onDialogClose">
      <div style="text-align: center; margin-bottom: 16px;">
        <el-button :type="activeChart==='pie' ? 'primary' : 'default'" @click="switchChart('pie')">饼图</el-button>
        <el-button :type="activeChart==='bar' ? 'primary' : 'default'" @click="switchChart('bar')">柱状图</el-button>
      </div>

      <div class="charts">
        <div ref="pieChart" class="chart" v-show="activeChart==='pie'"></div>
        <div ref="barChart" class="chart" v-show="activeChart==='bar'"></div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="chartDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import * as echarts from 'echarts'

  export default {
    name: 'ScoreByTerm',
    data() {
      return {
        terms: [
          '2020-2021学年度上学期', '2020-2021学年度下学期',
          '2021-2022学年度上学期', '2021-2022学年度下学期',
          '2022-2023学年度上学期', '2022-2023学年度下学期',
          '2023-2024学年度上学期', '2023-2024学年度下学期',
          '2024-2025学年度上学期', '2024-2025学年度下学期',
          '2025-2026学年度上学期', '2025-2026学年度下学期',
          '2026-2027学年度上学期', '2026-2027学年度下学期',
          '2027-2028学年度上学期', '2027-2028学年度下学期',
          '2028-2029学年度上学期', '2028-2029学年度下学期',
          '2029-2030学年度上学期', '2029-2030学年度下学期',
          '2030-2031学年度上学期', '2030-2031学年度下学期'
        ],
        selectedTerm: null,
        scoreList: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        chartDialogVisible: false,
        activeChart: 'pie',
        chartInstances: {
          pieChart: null,
          barChart: null
        }
      }
    },
    mounted() {
      this.selectedTerm = this.terms[9]
      this.fetchScores()
    },
    methods: {
      async fetchScores() {
        this.loading = true
        try {
          const res = await this.$http.get(
            `/student/${localStorage.getItem('id')}/scoress`, {
              params: {
                term: this.selectedTerm,
                pageNum: this.pageNum,
                pageSize: this.pageSize
              }
            }
          )
          if (res.code === 200) {
            const {
              list,
              total
            } = res.data
            this.scoreList = list
            this.total = total
          } else {
            this.$message.error('获取成绩失败：' + res.msg)
          }
        } catch (e) {
          console.error(e)
          this.$message.error('获取成绩异常')
        } finally {
          this.loading = false
        }
      },
      onTermChange() {
        this.pageNum = 1
        this.fetchScores()
      },
      onPageChange(page) {
        this.pageNum = page
        this.fetchScores()
      },
      onPageSizeChange(size) {
        this.pageSize = size
        this.fetchScores()
      },

      switchChart(type) {
        this.activeChart = type
        // 两个都已初始化，直接 resize + 渲染
        this.chartInstances[`${type}Chart`].resize()
        // 等 DOM 切换完成后，再 resize + 渲染
        this.$nextTick(() => {
          const key = type === 'pie' ? 'pieChart' : 'barChart'
          const inst = this.chartInstances[key]
          if (inst) {
            inst.resize()
            this.renderChart()
          }
        })
      },

      onDialogOpen() {
        // 弹窗打开时，chartDialogVisible 已经 true，v-if 渲染了容器
        this.$nextTick(() => {
          // 一次性初始化 两个实例
          ['pieChart', 'barChart'].forEach(ref => {
            const dom = this.$refs[ref]
            if (dom) {
              // dispose 如果已存在
              this.chartInstances[ref]?.dispose()
              this.chartInstances[ref] = echarts.init(dom)
            }
          })
          // 首次用 activeChart 渲染
          this.renderChart()
          window.addEventListener('resize', this.onResize)
        })
      },
      onDialogClose() {
        window.removeEventListener('resize', this.onResize)
        // 销毁所有实例
        /* ['pieChart', 'barChart'].forEach(ref => {
          this.chartInstances[ref]?.dispose()
          this.chartInstances[ref] = null
        }) */
      },
      initInstance(refName) {
        const dom = this.$refs[refName];
        if (dom) {
          this.chartInstances[refName]?.dispose();
          this.chartInstances[refName] = echarts.init(dom);
        }
      },
      onResize() {
        // 自适应所有实例
        ['pieChart', 'barChart'].forEach(ref => {
          this.chartInstances[ref]?.resize()
        })
      },

      renderChart() {
        const key = this.activeChart + 'Chart'
        const inst = this.chartInstances[key]
        if (!inst) return

        inst.resize()

        if (!this.scoreList.length) {
          inst.clear()
          return
        }

        const data = this.scoreList.map(i => ({
          name: i.courseName,
          value: i.score
        }))
        const names = data.map(i => i.name)
        const vals = data.map(i => i.value)

        if (this.activeChart === 'pie') {
          inst.setOption({
            title: {
              text: '各科总成绩占比',
              left: 'center'
            },
            tooltip: {
              trigger: 'item'
            },
            series: [{
              type: 'pie',
              radius: '50%',
              data
            }]
          })
        } else {
          inst.setOption({
            title: {
              text: '各科总成绩柱状图',
              left: 'center'
            },
            tooltip: {
              trigger: 'axis'
            },
            xAxis: {
              type: 'category',
              data: names
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              type: 'bar',
              data: vals,
              barWidth: '50%'
            }]
          })
        }
      }
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.onResize)
      Object.values(this.chartInstances).forEach(i => i?.dispose())
    }
  }

</script>

<style scoped>
  .charts {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 400px;
  }

  .chart {
    flex: 1;
    height: 100%;
    margin: 0 8px;
  }

  .dialog-footer {
    text-align: right;
  }

</style>
