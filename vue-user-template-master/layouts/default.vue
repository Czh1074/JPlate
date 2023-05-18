<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <span id="logo" style="width:100px;height:100px;padding-top:10px;padding-right:75px">
          <a href="/" title="集美大学校园社区平台">
            <img src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/%E6%AF%95%E8%AE%BE%E5%9B%BE%E7%89%87/badge.png"
              alt="集美大学校园社区平台">
          </a>
        </span>
        <!-- <h1 id="logo">
          <a href="#" title="集美大学校园社区平台">
            <img src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/%E6%AF%95%E8%AE%BE%E5%9B%BE%E7%89%87/badge.png" width="60%" height="60%" alt="集美大学校园社区平台">
          </a>
        </h1> -->
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/trend" tag="li" active-class="current">
              <a>动态空间</a>
            </router-link>
            <router-link to="/good" tag="li" active-class="current">
              <a>商品交易区</a>
            </router-link>
            <router-link to="/message" tag="li" active-class="current">
              <a>兼职分享区</a>
            </router-link>
            <router-link to="/notice" tag="li" active-class="current">
              <a>公告栏</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <li v-if="loginInfo.nickName" id="is-login-one" class="mr10">
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
              <a href="/user" title>
                <img :src="loginInfo.salt" width="30" height="30" class="vam picImg" alt>
                <span id="userName" class="vam disIb">{{ loginInfo.nickName }}</span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <!-- <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside> -->
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->

    <nuxt />

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="https://www.jmu.edu.cn/" title="集美大学" target="_blank">集美大学</a>
            </li>
            <li>
              <a href="http://lib.jmu.edu.cn/" title="集美大学" target="_blank">集美大学图书馆</a>
            </li>
            <li>
              <a href="http://cwc.jmu.edu.cn/" title="集美大学" target="_blank">集美大学财务处</a>
            </li>
            <li>
              <a href="http://cec.jmu.edu.cn/" title="集美大学" target="_blank">集美大学计算机工程学院</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：151592510745(福建) </span>
                <span>Email：czh1074@163.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©版权均归集美大学计算机工程学院所有</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
  import "~/assets/css/reset.css";
  import "~/assets/css/theme.css";
  import "~/assets/css/global.css";
  import "~/assets/css/web.css";

  import cookie from 'js-cookie'
  import userApi from '@/api/login'

  export default {
    data() {
      return {
        token: '',
        loginInfo: {
          id: '',
          salt: '',
          userNmae: '',
          nickName: '',
          sex: ''
        }
      }
    },

    created() {
      this.showInfo()
    },

    methods: {
      showInfo() {
        //debugger
        var jsonStr = cookie.get("plate_user");
        //alert(jsonStr)
        if (jsonStr) {
          this.loginInfo = JSON.parse(jsonStr)
        }
      },

      logout() {
        //debugger
        cookie.set('plate_user', "", { domain: 'localhost' })
        cookie.set('plate_token', "", { domain: 'localhost' })

        //跳转页面
        window.location.href = "/"
      }
    }
  }
</script>