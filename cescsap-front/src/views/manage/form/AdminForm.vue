<template>
  <el-form>
    <UserForm></UserForm>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
  import UserForm from './Form.vue'

  export default ({
    data() {
      return {

      }
    },
    components: {
      UserForm,
    },
    methods: {
      onSubmit() {
        console.log(this.name);
      }
    }
  })

</script>
