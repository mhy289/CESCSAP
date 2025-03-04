<template>
  <div>
    <!-- 成绩表格 -->
    <el-table :data="tableData" stripe border style="width: 100%">

      <el-table-column prop="courseName" label="课程名称"></el-table-column>


      <el-table-column prop="score" label="成绩"></el-table-column>

      <el-table-column prop="teacherName" label="教师"></el-table-column>

      <el-table-column prop="examDate" label="考试时间"></el-table-column>

      <el-table-column prop="gpa" label="绩点" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.gpa }}</span>
          <el-button type="primary" icon="el-icon-refresh" circle class="table-action-button"
            @click="handleFlush(scope.row)"></el-button>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="200px">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="edit(scope.row)"></el-button>
          <el-popconfirm @confirm="del(scope.row.id)" title="确定删除该食品吗？">
            <el-button type="danger" icon="el-icon-delete" circle slot="reference"
              style="margin-left: 10px;"></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <div style="margin-top: 10px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
        :page-size="pageSize" :page-sizes="[5, 10, 20, 50]" layout="total, sizes, prev, pager, next, jumper"
        :total="total"></el-pagination>
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
        //baseApi: this.$store.state.baseApi,
        fileList: [],
        searchText: '',
        tableData: [],
        pageNum: 1,
        pageSize: 10,
        //uploadUrl: "上传接口",
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
    created() {
      this.load();
    },
    methods: {
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
    },

    handleChange(file, fileList) {
      this.fileList = fileList.slice(-1);
    }
  };

</script>


<style scoped>

.table-action-button {
  margin-left: 10px; /* 或者你需要的任何间距 */
  float: right; /* 如果需要的话，但通常这不是必需的，因为你可以通过布局来控制 */
}

</style>