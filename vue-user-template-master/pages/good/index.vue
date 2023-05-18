<template>
    <div id="GoodList" class="bg-fa of">
        <!-- /商品列表 开始 -->
        <section class="container">
            <header class="comm-title">
                <div style="text-align: right;">
                    <h2 class="tac">
                        <span class="c-333">全部商品</span>
                    </h2>
                    <el-button type="primary" icon="el-icon-circle-plus" round>
                        <router-link :to="'/good/addGood'" class="fsize18 c-666"
                            style="color: aliceblue;padding: 5px 0px;">发布商品
                        </router-link>
                    </el-button>
                </div>
            </header>

            <section class="c-sort-box">

                <el-form ref="good" label-position="left" :model="good" label-width="80px">
                    <el-form-item label="商品类别" style="margin-top: 10px;color: brown;">
                        <el-radio-group v-model="good.sort" size="small" @change="selectGoodBySort">
                            <el-radio border label="0">全部</el-radio>
                            <el-radio border label="1">服装</el-radio>
                            <el-radio border label="2">鞋包</el-radio>
                            <el-radio border label="3">美妆</el-radio>
                            <el-radio border label="4">数码</el-radio>
                            <el-radio border label="5">书籍</el-radio>
                            <el-radio border label="6">交通工具</el-radio>
                            <el-radio border label="7">其他</el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item label="价格" style="text-align: left;color: brown;">
                        <el-row type="flex" :gutter="1">
                            <el-col :span="4" style="width: 150px;">
                                <el-input placeholder="最低价格" v-model="goodQuery.low">
                                </el-input>
                            </el-col>
                            <el-col class="line" :span="1" style="text-align: center;">
                                --
                            </el-col>
                            <el-col :span="4" style="width: 150px;">
                                <el-input placeholder="最高价格" v-model="goodQuery.high">
                                </el-input>
                            </el-col>
                            <el-col :span="4" style="width: 50px;">
                            </el-col>
                            <el-button type="primary" @click="onSubmit">查询</el-button>
                            <el-button type="default" @click="resetData()">清空</el-button>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="筛选列表" style="text-align: left;color: brown;">
                        <el-button @click="orderByViewCount()">关注度</el-button>
                        <el-button @click="orderByTime()">最新</el-button>
                        <el-button @click="orderByPrice1()">价格降序</el-button>
                        <el-button @click="orderByPrice2()">价格升序</el-button>
                    </el-form-item>
                </el-form>


                <!-- <el-form-item label="筛选列表" style="margin-top: 10px;color: brown;">
                    <el-radio-group v-model="goodQuery.viewCount" size="small">
                        <el-radio border label="0">关注度</el-radio>
                        <el-radio border label="1">最新</el-radio>
                        <el-radio border label="2">价格</el-radio>
                    </el-radio-group>
                </el-form-item> -->

                <!-- <div class="js-wrap" style="padding-left: 0px;"> -->
                <!-- <section class="fl">
                        <ol class="js-tap clearfix">
                            <li>
                                <h5 title="筛选列表"
                                    style="padding-left: 0px;text-align: center;padding-top: 11px;color: #666;">筛选列表
                                </h5>
                            </li>
                            <li>
                                <a title="关注度" href="" @click="click1()"
                                    style="background-color: antiquewhite;margin-left: 10px;width: 50px;padding: 0px 10px;text-align: center;">关注度</a>
                            </li>
                            <li>
                                <a title="最新" href="#"
                                    style="background-color: antiquewhite;margin-left: 10px;width: 50px;padding: 0px 10px;text-align: center;">最新</a>
                            </li>
                            <li>
                                <a title="价格" href="#"
                                    style="background-color: antiquewhite;margin-left: 10px;width: 50px;padding: 0px 10px;text-align: center;">价格&nbsp;
                                    <span>↓</span>
                                </a>
                            </li>
                        </ol>
                    </section> -->
                <!-- <el-tag>筛选列表</el-tag>
                    <el-button type="info">关注度</el-button>
                    <el-button type="info">最新</el-button>
                    <el-button type="info">价格</el-button> -->
                <!-- <el-button type="primary">最新</el-button>
                    <el-button type="success">价格</el-button> -->
                <!-- <el-tag type="success">关注度</el-tag>
                    <el-tag type="info">最新</el-tag>
                    <el-tag type="info">价格</el-tag> -->
                <!-- </div> -->
                <div class="mt40">
                    <article class="comm-course-list">
                        <ul class="of" id="bna">
                            <li v-for="good in goods.items" :key="good.id">
                                <div class="cc-l-wrap">
                                    <section class="course-img">
                                        <img :src="good.picture" class="img-responsive"
                                            style="display:block;height:150.467px;width: 100%;">
                                        <div class="cc-mask">
                                            <router-link :to="'/good/'+ good.id" title="查看详情" class="comm-btn c-btn-1">
                                                查看详情</router-link>
                                        </div>
                                    </section>
                                    <h3 class="hLh30 txtOf mt10">
                                        <router-link :to="'/good/'+ good.id" :title="good.name"
                                            class="course-title fsize18 c-333">名称：{{good.name}}</router-link>
                                    </h3>
                                    <!-- <h3 class="hLh30 txtOf mt10">
                                        <a href="/good/1" :title="good.price"
                                            class="course-title fsize18 c-333">价格：{{good.price}}</a>
                                    </h3> -->
                                    <section class="mt10 hLh20 of">
                                        <span class="fr jgTag bg-green">
                                            <i class="c-fff fsize12 f-fA">{{good.price}}</i>
                                            <!-- <i class="c-fff fsize12 f-fA" :v-if="good.sort=='5'">数码{{good.sort}}</i>
                                            <i class="c-fff fsize12 f-fA" :v-if="good.sort=='4'">服装{{good.sort}}</i> -->
                                        </span>
                                        <i class="fr jgTag" style="color:#999">价格</i>
                                        <span class="fl jgAttr c-ccc f-fA">
                                            <i class="c-999 f-fA">访问量：{{good.viewCount}}</i>
                                        </span>
                                    </section>
                                </div>
                            </li>
                        </ul>
                        <div class="clear"></div>
                    </article>
                </div>
                <!-- 公共分页 开始 -->
                <div>
                    <div class="paging">
                        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                        <a :class="{undisable: !goods.hasPrevious}" href="#" title="首页"
                            @click.prevent="gotoPage(1)">首</a>
                        <a :class="{undisable: !goods.hasPrevious}" href="#" title="前一页"
                            @click.prevent="gotoPage(goods.current-1)">&lt;</a>
                        <a v-for="page in goods.pages" :key="page"
                            :class="{current: goods.current == page, undisable: goods.current == page}"
                            :title="'第'+page+'页'" href="#" @click.prevent="gotoPage(page)">{{ page }}</a>
                        <a :class="{undisable: !goods.hasNext}" href="#" title="后一页"
                            @click.prevent="gotoPage(goods.current+1)">&gt;</a>
                        <a :class="{undisable: !goods.hasNext}" href="#" title="末页"
                            @click.prevent="gotoPage(goods.pages)">末</a>
                        <div class="clear" />
                    </div>
                </div>
                <!-- 公共分页 结束 -->
            </section>
        </section>
        <!-- /课程列表 结束 -->
    </div>
</template>
<script>
    import messageApi from "@/api/message"
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
                },
                messages: [],
                message: { //添加兼职信息
                    name: '',
                    introduction: '',
                    address: '',
                    salary: '',
                    contact: '',
                    contactName: '',
                    userId: ''
                },
                goods: [],
                good: {
                    id: '',
                    name: '',
                    picture: '',
                    price: '',
                    sort: '',
                    viewCount: ''
                },
                goodQuery: {
                    sort: '',
                    low: '',
                    high: '',
                    viewCount: '',
                    price: '',
                    time: '',
                    exchange: '0' //0默认时间、1热度、2价格降序、3价格升序
                },
            }
        },
        created() {
            this.getGoodList()
        },
        methods: {
            getGoodList() {
                goodApi.getGoodPageList(1, 8).then(response => {
                    this.goods = response.data.data
                });
            },
            //按照商品类别显示界面
            selectGoodBySort(i) {
                this.goodQuery.sort = i;
                goodApi.selectGoodBygoodQuery(1, 8, this.goodQuery).then(response => {
                    this.goods = response.data.data
                    console.log("实现点击类别查询")
                });
            },
            //商品类别的基础上再按照价格进行查询
            onSubmit() {
                goodApi.selectGoodBygoodQuery(1, 8, this.goodQuery).then(response => {
                    this.goods = response.data.data
                });
            },
            // //设置分页
            gotoPage(page) {
                goodApi.getGoodPageList(page, 8).then(response => {
                    this.goods = response.data.data
                })
            },
            //设置收藏功能 + 修改收藏collect字段
            isCollect(message) {
                message.collect = !message.collect;
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                messageApi.updateCollect(3, message.id, this.loginInfo.id).then(response => {

                })
            },
            //清空
            resetData() {
                this.goodQuery.low = ''
                this.goodQuery.high = ''
                this.onSubmit()
            },
            //按访问量降序排序
            orderByViewCount() {
                this.goodQuery.exchange = 1
                this.onSubmit()
            },
            //按时间默认排序
            orderByTime() {
                this.goodQuery.exchange = 0
                this.onSubmit()
            },
            //按照价格降序排序
            orderByPrice1() {
                this.goodQuery.exchange = 2
                this.onSubmit()
            },
            //按照价格升序排序
            orderByPrice2() {
                this.goodQuery.exchange = 3
                this.onSubmit()
            }

        }
    }
</script>