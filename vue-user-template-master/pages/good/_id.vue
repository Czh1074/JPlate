<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- 课程详情 开始 -->
        <section class="container">

            <!-- 商品所属分类 开始 -->
            <section class="path-wrap txtOf hLh30" style="font-size:20px">
                <a href="/" title class="c-999 fsize14">首页</a>
                >
                <a href="/good" title class="c-999 fsize14">商品列表</a>
                >
                <span class="c-333 fsize14">{{ good.name }}</span>
            </section>
            <!-- 商品基本信息 开始 -->
            <div style="padding-bottom: 20px;text-align: center;">
                <h1>{{good.name}}</h1>
                <h4 style="padding: 5px;">
                    <img class="avatar" :src="user.salt"
                        style="width: 30px;height: 30px;border-radius: 50%;vertical-align: middle;">
                    <span style="font-size: 13px;color:brown;">{{user.nickName}}</span>
                    <span style="font-size: 12px;">{{good.gmtCreate}}</span>
                    <!-- <el-tag type="info">访问量：{{good.viewCount}}</el-tag>
                    <el-tag type="success">收藏量：{{good.collectCount}}</el-tag> -->
                    <el-button type="info" @click="updateCollect()" v-if="good.collect==false" size="small">未收藏
                    </el-button>
                    <el-button type="success" @click="updateCollect()" v-if="good.collect==true" size="samll">
                        已收藏
                    </el-button>
                </h4>
            </div>
            <!-- 商品基本信息 结束 -->
            <!-- <div style="padding-bottom: 20px;text-align: center;">
                <h1>商品详情</h1>
                <h4 style="padding: 5px;">
                    <el-tag type="warning">发布时间：{{good.gmtCreate}}</el-tag>
                    <el-tag type="info">访问量：{{good.viewCount}}</el-tag>
                    <el-tag type="success">收藏量：{{good.collectCount}}</el-tag>
                    <el-button type="info" @click="updateCollect()" v-if="good.collect==false" size="small">未收藏
                    </el-button>
                    <el-button type="success" @click="updateCollect()" v-if="good.collect==true" size="samll">
                        已收藏
                    </el-button>
                </h4>
            </div> -->
            <!-- 课程基本信息 开始 -->
            <div>
                <article class="c-v-pic-wrap" style="height: 357px;">
                    <section id="videoPlay" class="p-h-video-box">
                        <img :src="good.picture" class="dis c-v-pic" style="display: block;height: 357px;width: 640px;">
                    </section>
                </article>
                <aside class="c-attr-wrap">
                    <section class="ml20 mr15">
                        <h2 class="hLh30 txtOf mt15">
                            <span class="c-fff fsize24">主题: {{good.name}}</span>
                        </h2>
                        <section class="c-attr-jg">
                            <span class="c-fff">价格：</span>
                            <b class="c-yellow" style="font-size:24px;">￥{{ good.price }}</b>
                        </section>
                        <section class="c-attr-mt c-attr-undis">
                            <span class="c-fff fsize14">详情&nbsp;
                                ：{{good.introduction}}</span>
                        </section>
                        <section class="c-attr-mt c-attr-undis">
                            <span class="c-fff fsize14">联系方式：
                                {{good.contact}}</span>
                        </section>
                        <!-- <section class="c-attr-mt c-attr-undis">
                            <span class="c-fff fsize14">联系方式： {{ 联系方式 }}&nbsp;&nbsp;&nbsp;</span>
                        </section> -->
                        <!-- <section class="c-attr-mt of">
                            <span class="ml10 vam">
                                <em class="icon18 scIcon" />
                                <a class="c-fff vam" title="收藏" href="#">收藏</a>
                            </span>
                        </section> -->
                        <!-- <section class="c-attr-mt">
                            <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
                        </section> -->
                    </section>
                </aside>
                <aside class="thr-attr-box">
                    <ol class="thr-attr-ol clearfix">
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">类别</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{ good.sort }}</h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">访问量</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{ good.viewCount }}</h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">收藏量</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{ good.collectCount }}</h6>
                            </aside>
                        </li>
                    </ol>
                </aside>
                <div class="clear" />
            </div>
        </section>
    </div>
</template>

<script>
    import noticeApi from "@/api/notice"
    import goodApi from "@/api/good"
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
                },//发布者信息
                user: {
                    salt: '',
                    nickName: ''
                },
                good: {
                    id: this.$route.params.id,
                    name: '',
                    sort: '',
                    picture: '',
                    price: '',
                    sort: '',
                    introduction: '',
                    contact: '',
                    collectCount: '',
                    viewCount: '',
                    collect: '',
                    gmtCreate: ''
                },
                collect: 'false',
                goodQuery: {
                    sort: '',
                    low: '',
                    high: '',
                    viewCount: '',
                    price: '',
                    time: '',
                    exchange: '0' //0默认时间、1热度、2价格降序、3价格升序
                },
                favorite: {
                    id: '',
                    userId: '',
                    infoId: '',
                    infoType: 1
                }
            }
        },
        created() {
            this.getGoodById()
        },
        methods: {
            getGoodById() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                goodApi.getGoodInfo(this.good.id, this.loginInfo.id).then(response => {
                    this.good = response.data.data.good
                    console.log(this.good)
                    this.good.sort = response.data.data.sortType
                    this.favorite = response.data.data.favorite.favorite || {}
                    this.user.salt = response.data.data.user.salt
                    this.user.nickName = response.data.data.user.nickName
                });
            },//设置收藏功能 + 修改收藏collect字段
            //设置收藏功能 + 修改收藏collect字段
            updateCollect() {
                this.good.collect = !this.good.collect;
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                this.favorite.userId = this.loginInfo.id
                this.favorite.infoId = this.good.id
                this.infoType = 1
                // trendApi.updateCollect(this.favorite, this.trend.collect, 1).then(response => {
                //   this.favorite.id = response.data.id
                //   this.$message({
                //     message: '收藏成功',
                //     type: 'success'
                //   });
                // })
                goodApi.updateCollect(this.favorite.id, this.favorite.userId, this.favorite.infoId, this.good.collect, 2).then(response => {
                    this.favorite.id = response.data.data.id
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                })
            },
            //返回上一页
            goback() {
                this.$router.go(-1);
            },
            //文件下载功能
            downloadFile(url, fileName, flieId, type) {
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = baseUrl + '/xxx/xxx/xxx?flieId=' + flieId;
                document.body.appendChild(link);
                link.click();
            },
        }

    };
</script>