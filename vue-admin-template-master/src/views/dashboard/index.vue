<template>
  <div class="home-container">
    <!-- <div class="dashboard-text">name:{{ name }}</div>
    <div class="dashboard-text">roles:<span v-for="role in roles" :key="role">{{ role }}</span></div> -->
    <div style="margin-top: 50px;font-size: 30px;">
      <!-- <img style="width: 100px; height: 100px"
        src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/05/13/badge.png" /> -->
      <h1>校园社区管理系统</h1>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'Dashboard',
    computed: {
      ...mapGetters([
        'name',
        'roles'
      ])
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .home-container {
    margin: 0;
    background: url('../../assets/lb2.jpg');
    background-size: cover;
    width: 100%;
    height: 95vh;
    display: flex;
    /*垂直方向居中*/
    /* align-items: center; */
    /*水平方向居中*/
    justify-content: center;

  }

  .dashboard {

    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>