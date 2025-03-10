<template>
    <div>
      <!-- 查询和筛选区域 -->
      <div class="filter-container">
        <el-input v-model="searchQuery1" placeholder="搜索课程" style="width: 200px;" @keyup.enter.native="handleSearch" />
        <el-input v-model="searchQuery2" placeholder="搜索教师" style="width: 200px;" @keyup.enter.native="handleSearch" />
  
        <el-select v-model="filterCourse" placeholder="课程筛选" clearable style="margin-left: 10px; width: 150px">
          <el-option v-for="item in courseOptions" :key="item" :label="item" :value="item" />
        </el-select>
  
        <el-select v-model="filterTeacher" placeholder="教师筛选" clearable style="margin-left: 10px; width: 150px">
          <el-option v-for="item in teacherOptions" :key="item" :label="item" :value="item" />
        </el-select>
  
        <el-date-picker v-model="filterDateRange" type="daterange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期" style="margin-left: 10px;" />
  
        <el-button type="primary" style="margin-left: 10px;" @click="handleFilter">筛选</el-button>
  
        <el-button type="success" style="margin-left: 10px;" @click="showChartDialog">
          查看统计图表
        </el-button>
      </div>
  
      <!-- 统计图表区域 -->
      <el-dialog title="成绩统计图表" :visible.sync="chartDialogVisible" width="80%" @opened="handleDialogOpened">
        <div class="chart-container">
          <el-row :gutter="20">
            <el-col :span="12">
              <div ref="barChart" style="height: 400px;"></div>
            </el-col>
            <el-col :span="12">
              <div ref="pieChart" style="height: 400px;"></div>
            </el-col>
          </el-row>
        </div>
      </el-dialog>
      <!-- <div class="chart-container">
        <el-row :gutter="20">
          <el-col :span="12">
            <div ref="barChart" style="height: 400px;"></div>
          </el-col>
          <el-col :span="12">
            <div ref="pieChart" style="height: 400px;"></div>
          </el-col>
        </el-row>
      </div> -->
  
      <!-- 成绩表格 -->
      <el-table :data="tableData" stripe border style="width: 100%" @sort-change="handleSort">
        <!-- 原有列配置 -->
        <el-table-column prop="courseName" label="课程名称" sortable="custom"></el-table-column>
        <el-table-column prop="score" label="成绩" sortable="custom"></el-table-column>
        <el-table-column prop="teacherName" label="教师"></el-table-column>
        <el-table-column prop="examDate" label="考试时间" sortable="custom"></el-table-column>
        <el-table-column prop="gpa" label="绩点" width="180">
          <!-- 原有模板内容 -->
        </el-table-column>
        <!-- 操作列 -->
      </el-table>
  
      <!-- 分页控件 -->
      <div style="margin-top: 10px;">
        <el-pagination :current-page="pageNum" :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" :total="total"
          layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </div>
  </template>
  
  <script>
    import axios from 'axios';
    import * as echarts from 'echarts';
    //import { uploadFiles } from '@api/zjmj/zdqyjg'//引入的接口文件
  
    //const url = "/api/good/";
  
    export default {
      name: "Gpa",
      data() {
        return {
          // 新增弹窗显示状态
          chartDialogVisible: false,
          //baseApi: this.$store.state.baseApi,
          fileList: [],
          searchText: '',
          tableData: [],
          pageNum: 1,
          pageSize: 10,
          // 新增筛选相关数据
          searchQuery1: '',
          searchQuery2: '',
          filterCourse: '',
          filterTeacher: '',
          filterDateRange: [],
          sortField: '',
          sortOrder: '',
          //uploadUrl: "上传接口",
          // 图表实例
          barChart: null,
          pieChart: null,
  
          // 筛选选项
          courseOptions: [],
          teacherOptions: [],
          entity: {
            studentName: '',
            courseName: '',
            sroce: '',
            teacherName: '',
            examDate: '',
            gpa: ''
          },
          total: 0,
          //dialogFormVisible: false,
          //dialogFormVisible2: false,
          //acceptAstrict: '.jpg,.jpeg,.png,.JPG,.PNG', //文件上传限制
          //imgUrl: '' //图片地址
        };
      },
      mounted() {
        this.initCharts()
        this.loadFilterOptions()
        this.fetchData()
      },
      created() {
        this.load();
      },
  
      methods: {
        // 显示图表弹窗
        showChartDialog() {
          this.chartDialogVisible = true
        },
  
        // 弹窗打开后处理图表
        handleDialogOpened() {
          this.$nextTick(() => {
            // 初始化或更新图表
            if (!this.barChart) {
              this.initCharts()
            } else {
              this.barChart.resize()
              this.pieChart.resize()
            }
            this.updateCharts()
          })
        },
  
        // 修改后的初始化图表方法
        initCharts() {
          if (this.$refs.barChart) {
            this.barChart = echarts.init(this.$refs.barChart)
          }
          if (this.$refs.pieChart) {
            this.pieChart = echarts.init(this.$refs.pieChart)
          }
        },
  
        // 修改后的更新图表方法
        updateCharts() {
          if (this.barChart && this.pieChart) {
            // 原有图表配置逻辑...
            this.barChart.setOption(barOption)
            this.pieChart.setOption(pieOption)
            // 添加窗口resize监听
            window.addEventListener('resize', this.handleChartResize)
          }
        },
  
        handleChartResize() {
          if (this.barChart && this.pieChart) {
            this.barChart.resize()
            this.pieChart.resize()
          }
        },
        handleSizeChange(pageSize) {
          //console.log(name);
          this.pageSize = pageSize;
          this.load();
        },
        handleCurrentChange(pageNum) {
          //console.log(name);
          this.pageNum = pageNum;
          this.load();
        },
        handleRecommend(food) {
          axios.get(url + "recommend", {
            params: {
              id: food.id,
              isRecommend: food.recommend,
            }
          }).then(res => {
            if (res.data.code === '200') {
              this.$message.success("修改成功");
            } else {
              this.$message.error(res.data.msg);
            }
          });
        },
        async load() {
          let thename = localStorage.getItem('name');
          let res = await this.$http.post('/students/current/' + this.pageNum + '/size/' + this.pageSize, {
            name: thename
          })
          if (res.code == 200) {
            console.log(res.data)
            this.tableData = res.data.list;
  
            this.total = res.data.total;
          } else {
            console.log(res)
            this.$message.error("查询失败");
          }
        },
  
        async flushed() {
          console.log(this.tableData[0]);
        },
        async serach() {
  
          console.log(this.searchText)
  
          let res = await this.$http.post('/dishes/current/' + this.pageNum + '/size/' + this.pageSize, {
            dishesName: this.searchText
          })
          if (res.code == 200) {
            console.log(res.data)
            this.tableData = res.data.list;
            this.total = res.data.total;
          } else {
            console.log(res)
            this.$message.error("查询失败");
          }
        },
        reset() {
          //this.searchText = '';
          this.load();
        },
        showAddDialog() {
          this.entity = {};
          this.dialogFormVisible = true;
  
        },
        async edit(food) {
          this.entity = JSON.parse(JSON.stringify(food));
          this.dialogFormVisible2 = true;
        },
        uploadFiles(file) {
          console.log(file.file.name)
          // 调用文件大小校验方法
          if (this.beforeUpload(file.file)) {
            this.entity.imgUrl = file.file.name
            this.imgUrl = '/assets/img/' + file.file.name
            //assets/img/logo.png
          }
        },
        // 删除图片
        imageRemove() {
          //let 
          this.imgUrl = ''
          this.$message({
            message: '删除图片成功',
            type: 'success'
          })
        },
        beforeUpload(file) {
          const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
          const isLt2M = file.size / 1024 / 1024 < 2;
  
          if (!isJpgOrPng) {
            this.$message.error('上传图片只能是 JPG 或 PNG 格式!');
          }
          if (!isLt2M) {
            this.$message.error('上传图片大小不能超过 2MB!');
          }
          return isJpgOrPng && isLt2M;
        },
        handleChange(file, fileList) {
          // 限制只能上传一张图片
          if (fileList.length > 1) {
            fileList.splice(0, 1); // 保留最新上传的图片
          }
          this.fileList = fileList;
        },
        async save() {
          //this.entity.imgs = uploadUrl
          console.log(this.entity);
          let res = await this.$http.post('/dish', this.entity)
          if (res.code == 200) {
            this.$message({
              type: "success",
              message: "操作成功"
            });
            this.dialogFormVisible = false;
            this.load();
          } else {
            this.$message({
              type: "error",
              message: "失败"
            });
          }
        },
        async save2() {
          //this.entity.imgs = uploadUrl
          console.log(this.entity);
          let res = await this.$http.put('/dish/' + this.entity.id, this.entity)
          console.log(res)
          if (res.code === 200) {
            this.$message.success("修改成功");
            this.dialogFormVisible2 = false;
            this.load();
          } else {
            this.$message.error("修改失败");
          }
        },
        async del(id) {
          console.log(id)
          let res = await this.$http.delete('/dish/' + id);
          if (res.code == 200) {
            this.$message({
              type: "success",
              message: "删除成功"
            });
            this.load();
          } else {
            this.$message({
              type: "error",
              message: "删除失败"
            });
          }
        },
        handleChange(file, fileList) {
          this.fileList = fileList.slice(-1)
        },
  
  
        handleSearch() {
          this.pageNum = 1
          this.fetchData()
        },
  
        handleFilter() {
          this.pageNum = 1
          this.fetchData()
        },
  
        handleSort({
          column,
          prop,
          order
        }) {
          this.sortField = prop
          this.sortOrder = order === 'ascending' ? 'asc' : 'desc'
          this.fetchData()
        },
  
        async loadFilterOptions() {
          // 获取筛选选项
          let thename = localStorage.getItem('name');
          const res = await this.$http.post('/courses/filter-options',{name: thename})
          this.courseOptions = res.data.courses
          this.teacherOptions = res.data.teachers
        },
  
        initCharts() {
          this.barChart = echarts.init(this.$refs.barChart)
          this.pieChart = echarts.init(this.$refs.pieChart)
        },
  
        updateCharts() {
          // 柱状图配置
          const barOption = {
            title: {
              text: '课程成绩分布'
            },
            tooltip: {},
            xAxis: {
              data: this.tableData.map(item => item.courseName)
            },
            yAxis: {},
            series: [{
              name: '成绩',
              type: 'bar',
              data: this.tableData.map(item => item.score)
            }]
          }
  
          // 饼图配置
          const pieOption = {
            title: {
              text: '成绩等级分布'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            series: [{
              name: '成绩等级',
              type: 'pie',
              radius: '50%',
              data: this.calculateGradeDistribution()
            }]
          }
  
          this.barChart.setOption(barOption)
          this.pieChart.setOption(pieOption)
        },
  
        calculateGradeDistribution() {
          // 成绩等级计算逻辑
          const grades = {
            '优秀': 0,
            '良好': 0,
            '中等': 0,
            '及格': 0,
            '不及格': 0
          }
  
          this.tableData.forEach(item => {
            const score = item.score
            if (score >= 90) grades['优秀']++
            else if (score >= 80) grades['良好']++
            else if (score >= 70) grades['中等']++
            else if (score >= 60) grades['及格']++
            else grades['不及格']++
          })
  
          return Object.keys(grades).map(key => ({
            name: key,
            value: grades[key]
          }))
        },
  
        async fetchData() {
          const param = {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            search1: this.searchQuery1,
            search2: this.searchQuery2,
            course: this.filterCourse,
            teacher: this.filterTeacher,
            startDate: this.filterDateRange[0],
            endDate: this.filterDateRange[1],
            sortField: this.sortField,
            sortOrder: this.sortOrder
          }
  
          const res = await this.$http.post('/student/queryCourse', {
            param : param,
          })
          this.tableData = res.data.list
          this.total = res.data.total
          this.updateCharts()
        }
      },
      watch: {
        // 在弹窗关闭时移除resize监听
        chartDialogVisible(val) {
          if (!val) {
            window.removeEventListener('resize', this.handleChartResize)
          }
        },
        filterDateRange() {
          this.handleFilter()
        }
      }
    }
  
  </script>
  
  
  <style scoped>
    .table-action-button {
      margin-left: 10px;
      /* 或者你需要的任何间距 */
      float: right;
      /* 如果需要的话，但通常这不是必需的，因为你可以通过布局来控制 */
    }
  
  
    .filter-container {
      margin-bottom: 20px;
    }
  
    .chart-container {
      margin: 20px 0;
      padding: 20px;
      background-color: #fff;
      border-radius: 4px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
    }
  
    /* 调整弹窗样式 */
    .el-dialog__body {
      padding: 20px;
    }
  
    .chart-container {
      margin: 0;
      padding: 0;
    }
  
  </style>
  