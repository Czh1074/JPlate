<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程详情 开始 -->
    <section class="container">

      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="动态详情">动态详情</a>
                </section>
                <div style="text-align: right;">
                  <el-button @click="orderByViewCount()">最热</el-button>
                  <el-button @click="orderByTime()">最新</el-button>
                  <!-- 筛选开始 -->
                  <!-- 筛选结束 -->
                  <el-button type="primary" icon="el-icon-circle-plus" round>
                    <router-link :to="'/trend/addTrend'" class="fsize18 c-666"
                      style="color: aliceblue;padding: 5px 0px;">
                      发布动态
                    </router-link>
                  </el-button>
                </div>
              </div>
              <article class="ml10 mr10 pt20">

                <!-- 动态详情介绍 开始 -->
                <div>

                  <div v-for="trend in trends.items" :key="trend.trend.id" class="text item">
                    <router-link :to="'/trend/'+trend.trend.id" :title="trend.trend.project" class="c-666"
                      style="font-size: 15px; text-align: left;">
                      <div class="box">
                        <img class="avatar" :src="trend.salt" alt="">
                        <div class="name-box">
                          <span class="name">{{trend.nickName}}</span>
                          <span class="time">{{trend.trend.gmtCreate}}</span>
                        </div>
                        <div class="theme">
                          <!-- <div style="text-align: left;"> -->
                          <!-- <router-link :to="'/message/'+message.id" :title="message.project" class="fsize18 c-666"
                          style="font-size: 15px; text-align: left;">{{trend.project}}</router-link> -->
                          <!-- </div> -->
                          {{trend.trend.project}}
                        </div>
                        <div class="last" style="text-align: right;margin-left: 100px;">
                          <span class="view">访问量：{{trend.trend.viewCount}}&nbsp;&nbsp;</span>
                          <span clsss="collect">评论数: {{trend.trend.commentCount}}</span>
                        </div>
                      </div>
                    </router-link>
                  </div>

                </div>
                <!-- /课程详情介绍 结束 -->

                <!-- 课程大纲 开始-->

                <!-- /课程大纲 结束 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
              <a name="c-i" class="current" title="本周热门">本周热门</a>
            </section>
            <ul>
              <li v-for="trend in this.hotTrends" :key="trend.id" style="padding-left: 15px;">
                <div class="mt10 hLh30 txtOf tac" style="text-align: left;">

                  <span>
                    <router-link :to="'/trend/'+trend.id" :title="trend.project" class="fsize18 c-666"
                      style="font-size: 15px; text-align: left;">{{trend.project}}
                    </router-link>
                  </span>
                  <span style="text-align: right;float: right;">
                    <router-link :to="'/trend/'+trend.id" :title="trend.viewCount" class="fsize18 c-666"
                      style="font-size: 15px; text-align: left;">访问量:{{trend.viewCount}}
                    </router-link>
                  </span>
                </div>
              </li>

            </ul>

          </div>
        </aside>
        <div class="clear" />
      </div>
    </section>
    <!-- /课程详情 结束 -->

    <!-- 公共分页 开始 -->
    <div>
      <div class="paging" style="text-align: left; padding-left: 500px;">
        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
        <a :class="{undisable: !trends.hasPrevious}" href="#" title="首页" @click.prevent="gotoPage(1)">首</a>
        <a :class="{undisable: !trends.hasPrevious}" href="#" title="前一页"
          @click.prevent="gotoPage(trends.current-1)">&lt;</a>
        <a v-for="page in trends.pages" :key="page"
          :class="{current: trends.current == page, undisable: trends.current == page}" :title="'第'+page+'页'" href="#"
          @click.prevent="gotoPage(page)">{{ page }}</a>
        <a :class="{undisable: !trends.hasNext}" href="#" title="后一页"
          @click.prevent="gotoPage(trends.current+1)">&gt;</a>
        <a :class="{undisable: !trends.hasNext}" href="#" title="末页" @click.prevent="gotoPage(trends.pages)">末</a>
        <div class="clear" />
      </div>
    </div>
    <!-- 公共分页 结束 -->

  </div>
</template>

<script>
  import noticeApi from "@/api/notice"
  import messageApi from "@/api/message"
  import trendApi from "@/api/trend"
  import userApi from '@/api/login'
  import cookie from 'js-cookie'

  export default {
    data() {
      return {
        loginInfo: {
          id: '',
          salt: '',
          userNmae: '',
          nickName: '',
          sex: ''
        },
        //发布者信息
        user: {
          salt: '',
          nickName: ''
        },
        notices: [],
        message: { //添加公告信息
          name: '',
          introduction: '',
          address: '',
          salary: '',
          contact: '',
          contactName: '',
          userId: '',
        },
        trends: [],
        trend: {
          id: '',
          project: '',
          gmtCreate: '',
          exchange: '0' //0默认时间、1热度
        },
        hotTrends: [],
        avater: '',

      }
    },
    created() {
      this.getTrendList()
    },
    methods: {
      getTrendList() {
        trendApi.getTrendPageList(1, 6, this.trend.exchange).then(response => {
          this.trends = response.data.data
          this.hotTrends = response.data.data.hotTrends
          console.log(this.hotTrends)
        });
      },
      // //设置分页
      gotoPage(page) {
        trendApi.getTrendPageList(page, 6, this.trend.exchange).then(response => {
          this.trends = response.data.data
        })
      },
      //设置收藏功能 + 修改收藏collect字段
      isCollect(trend) {
        trend.collect = !trend.collect;
        var jsonStr = cookie.get("plate_user");
        //alert(jsonStr)
        if (jsonStr) {
          this.loginInfo = JSON.parse(jsonStr)
        }
        trendApi.updateCollect(3, trend.id, this.loginInfo.id).then(response => {

        })
      },//获取时间戳
      getPartTime(val) {
        //let ti = val.split(' ');
        var timearr = val.replace(" ", ":").replace(/\:/g, "-").split("-");
        var timestr = timearr[0] + "年" + Number(timearr[1]) + "月" + timearr[2] + "日";
        // console.log(4444,val)
        return timestr;
      },
      //按访问量降序排序
      orderByViewCount() {
        this.trend.exchange = 1
        this.getTrendList()
      },
      //按时间默认排序
      orderByTime() {
        this.trend.exchange = 0
        this.getTrendList()
      },

    }
  }
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 785px;
    margin-top: 20px
  }

  .box {
    display: flex;
    align-items: center;
    padding: 20px;
    box-sizing: border-box;
    width: 100%;
    /* height: 200px; */
    border: 2px solid rgb(195, 191, 191);
  }

  .box .avatar {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #666;
  }

  .box .name-box {
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 200px;
    height: 60px;
  }

  /* span {
    width: 300px !important;
    float: left !important;
    overflow: hidden !important;
    text-overflow: ellipsis !important;
  } */

  .box .theme {
    height: 20px;
    width: 180px !important;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .router-link-active {
    text-decoration: none;
    color: yellow;
  }

  a {
    text-decoration: none;
    color: white;
  }

  a:hover {
    text-decoration: none;
    color: brown;
  }
</style>