<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- 兼职介绍 开始 -->
        <section class="container">
            <div class="t-infor-wrap">
                <!-- 兼职基本信息 -->
                <section class="fl t-infor-box c-desc-content">
                    <div class="mt20 ml20">
                        <!-- <el-page-header @back="goback" content="兼职详情列表">
                        </el-page-header> -->

                        <!-- 所属分类 开始 -->
                        <section class="path-wrap txtOf hLh30" style="font-size:20px">
                            <a href="/" title class="c-999 fsize14">首页</a>
                            >
                            <a href="/message" title class="c-999 fsize14">兼职列表</a>
                            >
                            <span class="c-333 fsize14">{{ message.name }}</span>
                        </section>
                        <!-- 动态基本信息 开始 -->
                        <div style="padding-bottom: 20px;text-align: center;">
                            <h1>{{message.name}}</h1>
                            <h4 style="padding: 5px;">
                                <img class="avatar" :src="user.salt"
                                    style="width: 30px;height: 30px;border-radius: 50%;vertical-align: middle;">
                                <span style="font-size: 13px;color:brown;">{{user.nickName}}</span>
                                <span style="font-size: 12px;">{{message.gmtCreate}}</span>
                                <el-tag type="info">访问量：{{message.viewCount}}</el-tag>
                                <el-tag type="success">收藏量：{{message.collectCount}}</el-tag>
                                <el-button type="info" @click="updateCollect()" v-if="message.collect==false"
                                    size="small">
                                    未收藏
                                </el-button>
                                <el-button type="success" @click="updateCollect()" v-if="message.collect==true"
                                    size="samll">
                                    已收藏
                                </el-button>
                            </h4>
                        </div>
                        <!-- 动态基本信息 结束 -->
                        <!-- <div style="padding-bottom: 20px;text-align: center;">
                            <h1>兼职详情</h1>
                            <h4 style="padding: 5px;">
                                <el-tag type="warning">发布时间：{{message.gmtCreate}}</el-tag>
                                <el-tag type="info">访问量：{{message.viewCount}}</el-tag>
                                <el-tag type="success">收藏量：{{message.collectCount}}</el-tag>
                                <el-button type="info" @click="updateCollect()" v-if="message.collect==false"
                                    size="small">未收藏
                                </el-button>
                                <el-button type="success" @click="updateCollect()" v-if="message.collect==true"
                                    size="samll">
                                    已收藏
                                </el-button>
                            </h4>
                        </div> -->
                        <el-descriptions :column="1" border style="font-size: 20px;">
                            <el-descriptions-item label="类型" content-class-name="my-content">
                                {{message.name}}
                            </el-descriptions-item>
                            <el-descriptions-item label="地址" content-class-name="my-content"
                                label-class-name="my-label">{{message.address}}
                            </el-descriptions-item>
                            <el-descriptions-item label="联系人" content-class-name="my-content"
                                label-class-name="my-label">{{message.contactName}}
                            </el-descriptions-item>
                            <el-descriptions-item label="联系方式" content-class-name="my-content"
                                label-class-name="my-label">{{message.contact}}
                            </el-descriptions-item>
                            <el-descriptions-item label="薪资" content-class-name="my-content"
                                label-class-name="my-label">{{message.salary}}
                            </el-descriptions-item>
                            <el-descriptions-item label="详情" content-class-name="my-content"
                                label-class-name="my-label">{{message.introduction}}
                            </el-descriptions-item>
                        </el-descriptions>
                    </div>
                </section>
                <div class="clear"></div>
            </div>
        </section>
        <!-- /兼职介绍 结束 -->
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
                },//发布者信息
                user: {
                    salt: '',
                    nickName: ''
                },
                message: {
                    id: this.$route.params.id,
                    name: '',
                    introduction: '',
                    address: '',
                    salary: '',
                    contact: '',
                    contactName: '',
                    view_count: '',
                    collect_count: '',
                    collect: ''
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
            this.getMessageById()
        },
        methods: {
            // getMessageById() {
            //     messageApi.getMessageInfo(this.message.id).then(response => {
            //         this.message = response.data.data.message
            //         console.log(this.message)
            //     });
            // },
            getMessageById() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                messageApi.getMessageInfo(this.message.id, this.loginInfo.id).then(response => {
                    console.dir(response)
                    this.message = response.data.data.message
                    this.favorite = response.data.data.favorite.favorite || {}
                    this.user.salt = response.data.data.user.salt
                    this.user.nickName = response.data.data.user.nickName
                    // response.data.data.collect
                    console.log(this.favorite)
                });
            },
            //设置收藏功能 + 修改收藏collect字段
            // isCollect(message) {
            //     message.collect = !message.collect;
            //     var jsonStr = cookie.get("plate_user");
            //     //alert(jsonStr)
            //     if (jsonStr) {
            //         this.loginInfo = JSON.parse(jsonStr)
            //     }
            //     messageApi.updateCollect(3, message.id, this.loginInfo.id).then(response => {
            //         this.message = response.data.data.message
            //     })
            // },
            //设置收藏功能 + 修改收藏collect字段
            updateCollect() {
                this.message.collect = !this.message.collect;
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                this.favorite.userId = this.loginInfo.id
                this.favorite.infoId = this.message.id
                this.infoType = 1
                // trendApi.updateCollect(this.favorite, this.trend.collect, 1).then(response => {
                //   this.favorite.id = response.data.id
                //   this.$message({
                //     message: '收藏成功',
                //     type: 'success'
                //   });
                // })
                messageApi.updateCollect(this.favorite.id, this.favorite.userId, this.favorite.infoId, this.message.collect, 3).then(response => {
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
            }
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
</style>