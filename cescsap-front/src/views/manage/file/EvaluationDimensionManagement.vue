<template>
  <div class="dim-mgmt">
    <el-button type="primary" @click="openAddDialog">新增维度</el-button>
    <el-button type="text" @click="$router.go(-1)">
      → 返回
    </el-button>
    <el-table :data="list" border stripe v-loading="loading" style="margin-top:16px">
      <el-table-column prop="dimensionId" label="ID" width="80" />
      <el-table-column prop="dimensionName" label="维度名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="180">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteDim(row.dimensionId)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="margin-top:16px; text-align:right;" :current-page="pageNum" :page-size="pageSize"
      :page-sizes="[10,20,50]" :total="total" layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <el-dialog :title="isEdit ? '编辑维度' : '新增维度'" :visible.sync="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="维度名称">
          <el-input v-model="form.dimensionName" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">
          {{ isEdit ? '保存' : '新增' }}
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'EvaluationDimensionManagement',
    data() {
      return {
        list: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,

        dialogVisible: false,
        isEdit: false,
        form: {
          dimensionId: null,
          dimensionName: '',
          description: ''
        }
      }
    },
    async mounted() {
      this.fetchPage()
    },
    methods: {
      async fetchPage() {
        this.loading = true
        const res = await this.$http.get(
          `/eval/dimensions/page/${this.pageNum}/size/${this.pageSize}`
        )
        this.loading = false
        if (res.code === 200) {
          this.list = res.data.list
          this.total = res.data.total
        }
      },
      handleSizeChange(sz) {
        this.pageSize = sz
        this.fetchPage()
      },
      handleCurrentChange(p) {
        this.pageNum = p
        this.fetchPage()
      },
      openAddDialog() {
        this.isEdit = false
        this.form = {
          dimensionId: null,
          dimensionName: '',
          description: ''
        }
        this.dialogVisible = true
      },
      openEditDialog(row) {
        this.isEdit = true
        this.form = {
          ...row
        }
        this.dialogVisible = true
      },
      async deleteDim(id) {
        await this.$confirm('确认删除此维度？')
        await this.$http.delete(`/eval/dimensions/${id}`)
        this.$message.success('删除成功')
        this.fetchPage()
      },
      async submitForm() {
        const api = this.isEdit ?
          `/eval/dimensions/${this.form.dimensionId}` :
          '/eval/dimensions'
        const method = this.isEdit ? 'put' : 'post'
        let res = await this.$http[method](api, this.form)
        if (res.code === 200) {
          this.$message.success(this.isEdit ? '更新成功' : '新增成功')
        }else{
            this.$message.error(res.msg)
        }
        this.dialogVisible = false
        this.fetchPage()
      }
    }
  }

</script>

<style scoped>
  .dialog-footer {
    text-align: right;
  }

</style>
