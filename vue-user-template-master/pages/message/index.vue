<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- 讲师列表 开始 -->
        <section class="container">
            <!-- <header class="comm-title all-teacher-title">
                <h2 class=" tac">
                    <span class="c-333">全部兼职信息
                    </span>
                </h2>

                <div style="text-align: right; height: 28px;">
                    <el-button type="primary" icon="el-icon-circle-plus" round>
                        <router-link :to="'/message/addMessage'" class="fsize18 c-666" style="color: aliceblue;">添加兼职
                        </router-link>
                    </el-button>
                </div>
            </header> -->
            <header class="comm-title">
                <div style="text-align: right;">
                    <h2 class="tac">
                        <span class="c-333">全部兼职信息</span>
                    </h2>
                    <el-button @click="orderByViewCount()">热度</el-button>
                    <el-button @click="orderByTime()">最新</el-button>
                    <el-button type="primary" icon="el-icon-circle-plus" round>
                        <router-link :to="'/message/addMessage'" class="fsize18 c-666"
                            style="color: aliceblue;padding: 5px 0px;">发布兼职
                        </router-link>
                    </el-button>
                </div>
            </header>
            <section class="c-sort-box">
                <div>
                    <!-- /无数据提示 开始-->
                    <section class="no-data-wrap" v-if="messages.total === 0">
                        <em class="icon30 no-data-ico">&nbsp;</em>
                        <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
                    </section>
                    <!-- /无数据提示 结束-->
                    <article class="i-teacher-list">
                        <ul class="of">
                            <li v-for="message in messages.items" :key="message.id">
                                <section class="i-teach-wrap">
                                    <!-- <el-link :href="['/message/'+message.id]" target="_blank">兼职类型：{{message.name}}
                                        </el-link> -->
                                    <div class="mt10 hLh30 txtOf tac" style="text-align: left;">
                                        <router-link :to="'/message/'+message.id" :title="message.project"
                                            class="fsize18 c-666" style="font-size: 15px; text-align: left;">类型:&nbsp;
                                            {{message.name}}</router-link>
                                    </div>
                                    <!-- target用来决定是否新开界面 -->
                                    <div class="mt10 hLh30 txtOf tac" style="text-align: left;">
                                        <router-link :to="'/message/'+message.id" :title="message.project"
                                            class="fsize18 c-666" style="font-size: 15px; text-align: left;">地址:&nbsp;
                                            {{message.address}}</router-link>
                                    </div>
                                    <!-- <el-link :href="['/message/'+message.id]" target="_blank">
                                            薪资：{{message.salary}}/小时
                                        </el-link> -->
                                    <div class="mt10 hLh30 txtOf tac" style="text-align: left;">
                                        <router-link :to="'/message/'+message.id" :title="message.project"
                                            class="fsize18 c-666" style="font-size: 15px; text-align: left;">薪资:&nbsp;
                                            {{message.salary}}/小时</router-link>
                                    </div>
                                    <div>
                                        <section class="mt10 hLh20 of">
                                            <!-- <span class="fr jgTag bg-green" style="background-color:darkgray"
                                                v-if="message.collect==false">
                                                <i class="c-fff fsize12 f-fA" @click="isCollect(message)">未收藏</i>
                                            </span>
                                            <span class="fr jgTag bg-green" v-if="message.collect==true">
                                                <i class="c-fff fsize12 f-fA" @click="isCollect(message)">已收藏</i>
                                            </span> -->
                                            <span class="fr jgTag">
                                                <!-- <span
                                                    style="padding: 0px 5px; line-height: 0px; background-color: darkgray;color: aliceblue;"
                                                    v-if="message.collect==false">
                                                    未收藏
                                                </span>
                                                <span
                                                    style="padding: 0px 5px; line-height: 0px; background-color:darkseagreen;color: aliceblue;"
                                                    v-if="message.collect==true">
                                                    已收藏
                                                </span> -->
                                                <!-- <el-tag type="info" style="padding: 0px 5px; line-height: 0px;"
                                                    v-if="message.collect==false">
                                                    未收藏
                                                </el-tag> -->
                                                <!-- <el-tag type="success" style="padding: 0px 5px;line-height: 0px;"
                                                    v-if="message.collect==true">
                                                    已收藏
                                                </el-tag> -->
                                                <!-- <el-button type="info" @click="isCollect(message)" style="padding: 0px;"
                                                    size="medium" v-if="message.collect==false">未收藏
                                                </el-button> -->
                                                <!-- <el-button type="success" @click="isCollect(message)" size="medium"
                                                    style="padding: 0px;" v-if="message.collect==true">
                                                    已收藏
                                                </el-button> -->
                                            </span>
                                            <span class="fl jgAttr c-ccc f-fA">
                                                <i class="c-999 f-fA">访问量:{{message.viewCount}}</i>
                                            </span>
                                        </section>
                                    </div>
                                </section>

                                <!-- <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span>兼职类型：{{message.name}}</span>
                                        <el-button style="float: right; padding: 3px 0" type="text">收藏</el-button>
                                    </div>
                                    <div v-for="o in 4" :key="o" class="text item">
                                        {{'列表内容 ' + o }}
                                    </div>
                                </el-card> -->

                            </li>
                        </ul>
                        <div class="clear"></div>
                    </article>
                </div>
                <!-- 公共分页 开始 -->
                <div>
                    <div class="paging">
                        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                        <a :class="{undisable: !messages.hasPrevious}" href="#" title="首页"
                            @click.prevent="gotoPage(1)">首</a>
                        <a :class="{undisable: !messages.hasPrevious}" href="#" title="前一页"
                            @click.prevent="gotoPage(messages.current-1)">&lt;</a>
                        <a v-for="page in messages.pages" :key="page"
                            :class="{current: messages.current == page, undisable: messages.current == page}"
                            :title="'第'+page+'页'" href="#" @click.prevent="gotoPage(page)">{{ page }}</a>
                        <a :class="{undisable: !messages.hasNext}" href="#" title="后一页"
                            @click.prevent="gotoPage(messages.current+1)">&gt;</a>
                        <a :class="{undisable: !messages.hasNext}" href="#" title="末页"
                            @click.prevent="gotoPage(messages.pages)">末</a>
                        <div class="clear" />
                    </div>
                </div>
                <!-- 公共分页 结束 -->
            </section>
        </section>
        <!-- /讲师列表 结束 -->
    </div>
</template>
<script>
    import messageApi from "@/api/message"
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
                    userId: '',
                    exchange: '0'
                }
            }
        },
        created() {
            this.getMessageList()
        },
        methods: {
            getMessageList() {
                messageApi.getMessagePageList(1, 8, this.message.exchange).then(response => {
                    this.messages = response.data.data
                    console.log(this.messages)
                });
            },
            // //设置分页
            gotoPage(page) {
                messageApi.getMessagePageList(page, 8, this.message.exchange).then(response => {
                    this.messages = response.data.data
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
            //按访问量降序排序
            orderByViewCount() {
                this.message.exchange = 1
                this.getMessageList()
            },
            //按时间默认排序
            orderByTime() {
                this.message.exchange = 0
                this.getMessageList()
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
</style>