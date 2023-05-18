<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- 公告栏介绍 开始 -->
        <section class="container">
            <div class="t-infor-wrap">
                <!-- 公告基本信息 -->
                <section class="fl t-infor-box c-desc-content">
                    <div class="mt20 ml20">
                        <!-- 所属分类 开始 -->
                        <section class="path-wrap txtOf hLh30" style="font-size:20px">
                            <a href="/" title class="c-999 fsize14">首页</a>
                            >
                            <a href="/notice" title class="c-999 fsize14">公告栏</a>
                            >
                            <span class="c-333 fsize14">{{ notice.project }}</span>
                        </section>
                        <!-- /所属分类 结束 -->
                        <div style="padding-bottom: 20px;text-align: center;">
                            <h1>{{notice.project}}</h1>
                            <h4 style="padding: 5px;">
                                <img class="avatar" :src="user.salt"
                                    style="width: 30px;height: 30px;border-radius: 50%;vertical-align: middle;">
                                <span style="font-size: 13px;color:brown;">{{user.nickName}}</span>
                                <span style="font-size: 12px;">{{notice.gmtCreate}}</span>
                                <el-tag type="info">访问量：{{notice.viewCount}}</el-tag>
                            </h4>
                        </div>
                        <span style="font-size: 18px;" v-html="notice.introduction"></span>
                        <span style="font-size: 14px;">附件【<a :href="notice.appendix1"
                                style="color: #666;">{{this.appendix}}
                            </a>】</span>
                        <!-- <a href="https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/04/30/毕设后端.docx">{{notice.appendix1}}</a> -->
                    </div>
                </section>
                <div class="clear"></div>
            </div>
        </section>
        <!-- 公告介绍 结束 -->

    </div>
</template>
<script>
    import noticeApi from "@/api/notice"
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
                notice: {
                    id: this.$route.params.id,
                    project: '',
                    introduction: '',
                    appendix1: '',
                    appendix2: '',
                    appendix3: '',
                    view_count: '',
                    collect_count: ''
                },
                appendix:''
            }
        },
        created() {
            this.getNoticeById()
        },
        methods: {
            getNoticeById() {
                noticeApi.getNoticeInfo(this.notice.id).then(response => {
                    this.notice = response.data.data.notice
                    this.user.salt = response.data.data.user.salt
                    this.user.nickName = response.data.data.user.nickName
                    //截取文件的名称
                    //1.找到最后一个'/'的位置
                    let start = this.notice.appendix1.lastIndexOf('/')
                    //2.截取
                    this.appendix = this.notice.appendix1.slice(start + 1)
                });
            },
            isCollect(notice) {
                notice.collect = !notice.collect;
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                noticeApi.updateCollect(3, notice.id, this.loginInfo.id).then(response => {
                    this.notice = response.data.data.notice
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
<style>
    .my-label {
        background: #21cd88;
    }

    .my-content {
        background: #ecf5ff;
        color: #409EFF;
        width: 900px;
        border-color: aquamarine;
        border-width: 2px;
    }

    .a {
        color: #666
    }
</style>