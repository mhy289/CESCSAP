<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="id">
      <el-input v-model="form.id"></el-input>
    </el-form-item>
    <el-form-item label="名称">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="联系方式">
      <el-input type="textarea" v-model="form.contact"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          id: '',
          password: '',
          contact: '',
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.$emit('submit', this.name);
      }
    }
  }

</script>
