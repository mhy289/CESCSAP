<template>
  <div class="forum-home">
    <el-row :gutter="20">
      <el-col :span="6" v-for="section in sections" :key="section.id">
        <el-card shadow="hover" @click.native="goToSection(section.id)" style="cursor:pointer">
          <div class="section-title">{{ section.partitionTitle }}</div>
          <div class="section-desc">{{ section.partitionContent }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        sections: []
      }
    },
    created() {
        load()
      /* axios.get('/api/sections')
        .then(response => {
          this.sections = response.data
        })
        .catch(error => {
          console.error('获取分区数据出错', error)
        }) */
    },
    methods: {
      goToSection(id) {
        this.$router.push({
          name: 'SectionPosts',
          params: {
            id
          }
        })
      },
      async load() {
        const res = await axios.$http.get('/sections')
        if(res.code==200){
            // 获取数据成功
            console.log(res)
            this.sections = res.data
        }else{
            //提示错误
            console.error('获取分区数据出错', res.msg)
            this.$message.error(res.msg)
        }
      }
    }
  }

</script>

<style scoped>
  .section-title {
    font-size: 18px;
    font-weight: bold;
  }

  .section-desc {
    font-size: 14px;
    color: #666;
  }

</style>
