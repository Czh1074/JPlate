<template>
    <div class="noticeForm">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2 style="font-size: 35px;color:brown;">公告栏</h2>
            </div>
            <div v-for="notice in notices.items" :key="notice.id" class="text item" style="font-size: 15px;">
                <router-link :to="'/notice/'+notice.id" :title="notice.project" class="fsize18 c-666"
                    style="font-size: 15px; text-align: left;">
                    {{notice.project}}</router-link>
                <!-- <span style="color: brown;padding: 0;">11</span> -->
                <!-- <span v-html="notice.introduction"></span>    实现福本文在界面的展示  -->
                <span class="fr jgTag">
                    <span style="font-size: 12px;">发布时间：{{ getPartTime(notice.gmtCreate) }}</span>
                    <!-- <el-button type="info" @click="isCollect(notice)" style="padding: 0px;" size="medium">未收藏
                    </el-button>
                    <el-button type="success" @click="isCollect(notice)" size="medium" style="padding: 0px;"
                        v-if="notice.collect==true">
                        已收藏
                    </el-button> -->
                </span>
                <el-divider></el-divider>
            </div>
        </el-card>
        <!-- 公共分页 开始 -->
        <div>
            <div class="paging">
                <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                <a :class="{undisable: !notices.hasPrevious}" href="#" title="首页" @click.prevent="gotoPage(1)">首</a>
                <a :class="{undisable: !notices.hasPrevious}" href="#" title="前一页"
                    @click.prevent="gotoPage(notices.current-1)">&lt;</a>
                <a v-for="page in notices.pages" :key="page"
                    :class="{current: notices.current == page, undisable: notices.current == page}"
                    :title="'第'+page+'页'" href="#" @click.prevent="gotoPage(page)">{{ page }}</a>
                <a :class="{undisable: !notices.hasNext}" href="#" title="后一页"
                    @click.prevent="gotoPage(notices.current+1)">&gt;</a>
                <a :class="{undisable: !notices.hasNext}" href="#" title="末页"
                    @click.prevent="gotoPage(notices.pages)">末</a>
                <div class="clear" />
            </div>
        </div>
        <!-- 公共分页 结束 -->
    </div>
</template>
<script>
    import noticeApi from "@/api/notice"
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
                notices: [],
                message: { //添加公告信息
                    name: '',
                    introduction: '',
                    address: '',
                    salary: '',
                    contact: '',
                    contactName: '',
                    userId: '',
                }
            }
        },
        created() {
            this.getNoticeList()
        },
        methods: {
            getNoticeList() {
                noticeApi.getNoticePageList(1, 8).then(response => {
                    this.notices = response.data.data
                    console.log(this.messages)
                });
            },
            // //设置分页
            gotoPage(page) {
                noticeApi.getNoticePageList(page, 8).then(response => {
                    this.notices = response.data.data
                })
            },
            //设置收藏功能 + 修改收藏collect字段
            isCollect(notice) {
                notice.collect = !notice.collect;
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                noticeApi.updateCollect(3, message.id, this.loginInfo.id).then(response => {

                })
            },//获取时间戳
            getPartTime(val) {
                //let ti = val.split(' ');
                var timearr = val.replace(" ", ":").replace(/\:/g, "-").split("-");
                var timestr = timearr[0] + "年" + Number(timearr[1]) + "月" + timearr[2] + "日";
                // console.log(4444,val)
                return timestr;
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
        display: block;
        content: "";
    }

    .clearfix:after {
        clear: both;
    }

    .box-card {
        width: 1000px;
        height: 620px;
        font-size: 20px;
    }

    .noticeForm {
        position: relative;
        width: 1000px;
        height: 720px;
        margin-top: 20px;
        margin-bottom: 20px;
        margin-left: 400px;
    }

    a:hover {
        color: deepskyblue;
    }
</style>