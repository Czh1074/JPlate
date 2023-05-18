<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- 课程详情 开始 -->
        <section class="container">

            <div class="mt20 c-infor-box" style="margin-left: 200px;">
                <article class="fl col-7">
                    <section class="mr30">
                        <div class="i-box">
                            <div>
                                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                                    <a name="c-i" class="current" title="发布中心">发布中心</a>
                                </section>
                                <section class="path-wrap txtOf hLh30" style="font-size:20px">
                                    <a href="/user" title class="c-999 fsize14">个人中心</a>
                                    &nbsp;|&nbsp;
                                    <a href="/user/trendRelease" title class="c-999 fsize14">动态发布</a>
                                    &nbsp;|&nbsp;
                                    <a href="/user/goodRelease" title class="c-999 fsize14">商品发布</a>
                                    &nbsp;|&nbsp;
                                    <a href="/user/messageRelease" title class="c-999 fsize14">兼职发布</a>
                                    &nbsp;|&nbsp;
                                    <span class="c-333 fsize14"></span>
                                </section>
                            </div>
                            <article class="ml10 mr10 pt20">

                                <!-- 动态详情介绍 开始 -->
                                <div>

                                    <div v-for="trend in trends" :key="trend.id" class="text item">
                                        <div class="c-666" style="font-size: 15px; text-align: left;">
                                            <!-- <router-link :to="'/trend/'+trend.id" :title="trend.name"> -->
                                            <div class="box" style="color: #666;">
                                                <img class="avatar" :src="loginInfo.salt" alt="">
                                                <div class="name-box">
                                                    <span class="name">{{loginInfo.nickName}}</span>
                                                    <span class="time">{{trend.gmtCreate}}</span>
                                                </div>
                                                <router-link :to="'/trend/'+trend.id" :title="trend.name">
                                                    <div class="theme">
                                                        {{trend.project}}
                                                    </div>
                                                </router-link>
                                                <div style="margin-left: 100px;">
                                                    <el-button type="danger" icon="el-icon-delete" circle
                                                        @click="deleteTrendById(trend.id)"></el-button>
                                                </div>
                                            </div>
                                            <!-- </router-link> -->
                                        </div>

                                    </div>


                                </div>
                                <!-- /课程详情介绍 结束 -->

                            </article>
                        </div>
                    </section>
                    <!-- 公共分页 开始 -->

                    <!-- 公共分页 结束 -->
                </article>
            </div>
        </section>
        <!-- /课程详情 结束 -->


    </div>
</template>

<script>
    import releaseApi from "@/api/release"
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
                user: {
                    salt: '',
                    nickName: ''
                },
                trends: [],
                trend: { //添加公告信息
                    id: '',
                    name: '',
                    introduction: '',
                    address: '',
                    salary: '',
                    contact: '',
                    contactName: '',
                    userId: '',
                },
                hotTrends: [],
                avater: '',

            }
        },
        created() {
            this.getTrendPageList()
        },
        methods: {
            getTrendPageList() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                releaseApi.getTrendPageList(1, 6, this.loginInfo.id).then(response => {
                    this.trends = response.data.data.trends
                    // this.user.salt = response.data.data.user.salt
                    // this.user.nickName = response.data.data.user.nickName
                });
            },
            // //设置分页
            gotoPage(page) {
                trendApi.getTrendPageList(page, 6).then(response => {
                    // this.trends = response.data.data
                })
            },
            //获取时间戳
            getPartTime(val) {
                //let ti = val.split(' ');
                var timearr = val.replace(" ", ":").replace(/\:/g, "-").split("-");
                var timestr = timearr[0] + "年" + Number(timearr[1]) + "月" + timearr[2] + "日";
                // console.log(4444,val)
                return timestr;
            },
            //删除动态信息
            deleteTrendById(tid) {
                console.log(tid);
                releaseApi.deleteTrendById(tid).then(response => {
                    this.getTrendPageList()
                })
            }


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
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .box :hover {
        text-decoration: none;
        color: brown;
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