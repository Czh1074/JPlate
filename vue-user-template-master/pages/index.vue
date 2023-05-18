<template>
  <!-- 热门动态 开始 -->
  <div>
    <div id="bannerList">
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门动态</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="banner in bannerList" :key="banner.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img v-if="banner.picture1" :src="banner.picture1" class="img-responsive" alt="平台热榜"
                        style="display:block;height:150.467px;width: 100%;">
                      <img v-if="banner.picture1==null"
                        src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/05/02/background.jpg"
                        class="img-responsive" alt="平台热榜" style="display:block;height:150.467px;width: 100%;">
                      <div class="cc-mask">
                        <!-- <a href="'/trend/' + banner.id" title="点击进入" class="comm-btn c-btn-1">点击进入</a> -->
                        <router-link :to="'/trend/' + banner.id" title="点击进入" class="comm-btn c-btn-1">点击进入
                        </router-link>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a href="#" v-text="banner.project" class="course-title fsize18 c-333"></a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <!-- <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">{{banner.id}}</i>
                      </span> -->
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">访问量:{{banner.viewCount}}</i>
                        |
                        <i class="c-999 f-fA">收藏量:{{banner.collectCount}}</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
            </article>
          </div>
        </section>
      </div>

      <!-- 商品热门开始 -->

      <!-- 热门动态 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门商品</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="good in goods" :key="good.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img v-if="good.picture" :src="good.picture" class="img-responsive" alt="平台热榜"
                        style="display:block;height:150.467px;width: 100%;">
                      <img v-if="good.picture==null"
                        src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/05/02/background.jpg"
                        class="img-responsive" alt="平台热榜" style="display:block;height:150.467px;width: 100%;">
                      <div class="cc-mask">
                        <!-- <a href="'/trend/' + banner.id" title="点击进入" class="comm-btn c-btn-1">点击进入</a> -->
                        <router-link :to="'/good/' + good.id" title="点击进入" class="comm-btn c-btn-1">点击进入
                        </router-link>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a href="#" v-text="good.name" class="course-title fsize18 c-333"></a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <!-- <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">{{banner.id}}</i>
                      </span> -->
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">访问量:{{good.viewCount}}</i>
                        |
                        <i class="c-999 f-fA">收藏量:{{good.collectCount}}</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
            </article>
          </div>
        </section>
      </div>
      <!-- 商品人热门结束 -->

    </div>
  </div>


</template>

<script>
  import banner from "@/api/banner"

  export default {
    data() {
      return {
        swiperOption: {
          //配置分页
          pagination: {
            el: '.swiper-pagination'//分页的dom节点
          },
          navigation: {
            nextEl: '.swiper-button-next',//下一页dom节点
            prevEl: '.swiper-button-prev'//前一页dom节点
          }
        },
        bannerList: [],
        goods: []
      }
    },
    created() {
      this.initDataBanner()
      this.initGood()
    },
    //查询热门动态
    methods: {
      initDataBanner() {
        banner.getListBanner().then(response => {
          this.bannerList = response.data.data.trends
          console.log(this.bannerList)
        })
      },
      initGood() {
        banner.getGoodList().then(response => {
          this.goods = response.data.data.goods
        })
      }
    }
  }
</script>