<template>
  <div class="forum-home">
    <el-row :gutter="20">
      <el-col :span="6" v-for="section in sections" :key="section.id">
        <el-card shadow="hover" @click.native="goToSection(section.id)" style="cursor:pointer">
          <div class="section-title">{{ section.partitionTitle }}</div>
          <div class="section-desc">{{ section.partitionContent }}</div>
          <!--最近更新时间-->
          <div class="section-updated-at">{{ section.partitionTime }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        sections: []
      }
    },
    created() {
        this.load()
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
        console.log('什么情况')
        const res = await this.$http.get('/sections')
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
  
 .section-updated-at {
    font-size: 12px;
    color: #999;
    margin-top: 5px;
 
 }


</style>
