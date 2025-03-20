<template>
    <div class="section-posts">
      <el-page-header content="返回" @back="goBack"></el-page-header>
      <el-list>
        <el-list-item v-for="post in posts" :key="post.id" @click="goToPost(post.id)" style="cursor:pointer">
          <h3>{{ post.title }}</h3>
          <p>作者：{{ post.author }} · {{ formatTime(post.createTime) }}</p>
        </el-list-item>
      </el-list>
    </div>
  </template>
  
  <script>
  import axios from 'axios'
  import dayjs from 'dayjs'
  import relativeTime from 'dayjs/plugin/relativeTime'
  dayjs.extend(relativeTime)
  export default {
    props: ['id'],
    data() {
      return {
        posts: []
      }
    },
    created() {
      axios.get(`/api/sections/${this.id}/posts`)
        .then(response => {
          this.posts = response.data
        })
        .catch(error => {
          console.error('获取帖子列表失败', error)
        })
    },
    methods: {
      goBack() {
        this.$router.push('/')
      },
      goToPost(postId) {
        this.$router.push({ name: 'PostDetail', params: { id: postId } })
      },
      formatTime(time) {
        return dayjs(time).fromNow()
      }
    }
  }
  </script>
  