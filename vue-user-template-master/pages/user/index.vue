<template>

    <div class="Messageform">

        <!-- <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
            text-color="black" active-text-color="black" style="text-align: center;margin-bottom: 20px;width: 350px;"> -->
        <!-- <el-menu-item index="3"> -->
        <div style="padding: 10px;display: inline-block;">
            <router-link :to="'/user'" class="fsize18 c-666">
                个人主页
            </router-link>
        </div>
        <div style="padding: 10px;display: inline-block;">
            <router-link :to="'/user/release'" class="fsize18 c-666">
                发布中心
            </router-link>
        </div>
        <div style="padding: 10px;display: inline-block;">
            <router-link :to="'/user/favorite'" class="fsize18 c-666">
                收藏夹
            </router-link>
        </div>
        <!-- /商品所属分类 结束 -->
        <h2 style="text-align: center;padding-bottom: 30px;">个人信息</h2>
        <el-form ref="good" :model="good" label-width="80px" label-position="left">
            <el-form-item label="用户id">
                <el-input v-model="loginInfo.id" disabled="false"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="loginInfo.username" disabled="false"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="loginInfo.nickName"></el-input>
            </el-form-item>
            <el-form-item label="简介">
                <el-input v-model="loginInfo.introduction"></el-input>
            </el-form-item>
            <!-- <el-form-item label="原密码">
                <el-input v-model="prePassword"></el-input>
            </el-form-item> -->
            <el-form-item label="密码">
                <el-input v-model="loginInfo.password"></el-input>
            </el-form-item>
            <el-form-item label="头像">
                <section class="course-img">
                    <img class="avatar" :src="loginInfo.salt" alt="">
                </section>
            </el-form-item>
            <el-form-item>

                <!-- ref:唯一标识
                    auto-upliad：自动上传
                    limit:限制每次只能传一个文件
                    action:后端接口地址
                    name：input的值 -->

                <el-upload ref="upload" :auto-upload="false" :on-success="fileUploadSuccess" :on-error="fileUploadError"
                    :disabled="importBtnDisabled" :limit="1" :action="'http://localhost:8222/oss/fileoss/addFile'"
                    :on-preview="handlePreview" name="file" :on-change="getFileList">
                    <el-button slot="trigger" size="small" type="primary">更换头像</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload()">
                        {{fileUploadBtnText }}</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="注册时间">
                <el-input v-model="loginInfo.gmtCreate" disabled="false"></el-input>
            </el-form-item>
            <!-- <el-form-item label="联系方式">
                <el-input v-model="message.contact"></el-input>
            </el-form-item> -->
            <el-form-item>
                <el-button type="primary" @click="updateUser()">修改</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>

    import messageApi from "@/api/message"
    import user from "@/api/user"
    import goodApi from "@/api/good"
    import userApi from '@/api/login'
    import cookie from 'js-cookie'

    export default {
        data() {
            return {
                //测试富文本插件的数据定义
                BASE_API: process.env.BASE_API, // 接口API地址
                OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
                fileUploadBtnText: '上传到服务器', // 按钮文字
                importBtnDisabled: false, // 按钮是否禁用,
                loading: false,
                loginInfo: {
                    id: '',
                    salt: '',
                    username: '',
                    nickname: '',
                    password: '',
                    introduction: '',
                    userRole: '',
                    gmtCreate: ''
                },
                good: {
                    // id: this.$route.params.id,
                    name: '',
                    sort: '',
                    picture: '',
                    price: '',
                    sort: '',
                    introduction: '',
                    contact: '',
                    collectCount: 0,
                    viewCount: 0,
                    collect: 0,
                    gmtCreate: ''
                },
                activeIndex: '1',
                activeIndex2: '1'
            }
        },
        created() {
            this.getUser()
        },
        methods: {
            getUser() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                user.getUser(this.loginInfo.id).then(response => {
                    // this.$message({
                    //     message: '添加成功',
                    //     type: 'success'
                    // });
                    this.loginInfo = this.response.data.data.user

                })
            },
            //修改用户信息
            updateUser() {
                user.updateUser(this.loginInfo).then(response => {
                    this.$message({
                        message: '添加成功',
                        type: 'success'
                    });
                    //debugger
                    cookie.set('plate_user', "", { domain: 'localhost' })
                    cookie.set('plate_token', "", { domain: 'localhost' })

                    //跳转页面
                    window.location.href = "/login"
                })
            },
            onSubmit() {
                console.log('submit!');
            },//返回上一页
            goback() {
                this.$router.go(-1);
            },
            submitUpload() {
                this.importBtnDisabled = true
                this.loading = true
                this.$refs.upload.submit();
                // js: document.getElementById("upload").submit()
            },
            fileUploadSuccess(response) {
                console.dir(response);
                //获取文件名称
                var index = response.data.url.lastIndexOf('/') + 1
                console.log(response.data.url.substring(index, response.data.url.length))
                if (response.success === true) {
                    this.loginInfo.salt = response.data.url;
                    // this.good.picture = response.data.url.substring(index, response.data.url.length)
                    this.fileUploadBtnText = '导入成功'
                    this.loading = false
                    this.$message({
                        type: 'success',
                        message: response.message
                    })
                }
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            fileUploadError(response) {
                this.importBtnDisabled = false
                this.fileUploadBtnText = '导入失败'
                this.loading = false
                this.$message({
                    type: 'error',
                    message: '导入失败'
                })
            }
        }
    }
</script>
<style>
    .Messageform {
        position: relative;
        width: 700px;
        height: 750px;
        top: 20px;
        left: 400px;
    }

    a:hover {
        text-decoration: none;
        color: brown;
    }

    .avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        border: 1px solid #666;
    }
</style>