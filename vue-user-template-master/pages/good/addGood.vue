<template>

    <div class="Messageform">
        <!-- 商品所属分类 开始 -->
        <section class="path-wrap txtOf hLh30" style="font-size:20px">
            <a href="/" title class="c-999 fsize14">首页</a>
            >
            <a href="/good" title class="c-999 fsize14">商品列表</a>
        </section>
        <!-- /商品所属分类 结束 -->
        <h2 style="text-align: center;padding-bottom: 30px;">添加商品信息</h2>
        <el-form ref="good" :model="good" label-width="80px" label-position="left">
            <el-form-item label="名称">
                <el-input v-model="good.name"></el-input>
            </el-form-item>
            <!-- <el-form-item label="兼职地址">
                <el-input v-model="message.address"></el-input>
            </el-form-item> -->
            <el-form-item label="介绍">
                <el-input type="textarea" v-model="good.introduction"></el-input>
            </el-form-item>
            <el-form-item label="图片">

                <!-- ref:唯一标识
                    auto-upliad：自动上传
                    limit:限制每次只能传一个文件
                    action:后端接口地址
                    name：input的值 -->

                <el-upload ref="upload" :auto-upload="false" :on-success="fileUploadSuccess" :on-error="fileUploadError"
                    :disabled="importBtnDisabled" :limit="1" :action="'http://localhost:8222/oss/fileoss/addFile'"
                    :on-preview="handlePreview" name="file" :on-change="getFileList">
                    <el-button slot="trigger" size="small" type="primary">添加文件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload()">
                        {{fileUploadBtnText }}</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="价格">
                <el-input v-model="good.price"></el-input>
            </el-form-item>
            <el-form-item label="类别" prop="resource">
                <el-radio-group v-model="good.sort">
                    <el-radio label="1">服装</el-radio>
                    <el-radio label="2">鞋包</el-radio>
                    <el-radio label="3">美妆</el-radio>
                    <el-radio label="4">数码</el-radio>
                    <el-radio label="5">书籍</el-radio>
                    <el-radio label="6">工具</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="good.contact"></el-input>
            </el-form-item>
            <!-- <el-form-item label="联系方式">
                <el-input v-model="message.contact"></el-input>
            </el-form-item> -->
            <el-form-item>
                <el-button type="primary" @click="addGood">立即创建</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
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
                //测试富文本插件的数据定义
                BASE_API: process.env.BASE_API, // 接口API地址
                OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
                fileUploadBtnText: '上传到服务器', // 按钮文字
                importBtnDisabled: false, // 按钮是否禁用,
                loading: false,
                loginInfo: {
                    id: '',
                    salt: '',
                    userNmae: '',
                    nickName: '',
                    sex: ''
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
            }
        },
        methods: {
            onSubmit() {
                console.log('submit!');
            },//返回上一页
            goback() {
                this.$router.go(-1);
            },
            //添加兼职信息
            addGood() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                this.good.userId = this.loginInfo.id

                goodApi.addGood(this.good).then(response => {
                    this.$message({
                        message: '添加成功',
                        type: 'success'
                    });
                    this.good = ""
                })


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
                    this.good.picture = response.data.url;
                    // this.good.picture = response.data.url.substring(index, response.data.url.length)
                    this.fileUploadBtnText = '导入成功'
                    this.loading = false
                    this.$message({
                        type: 'success',
                        message: response.message
                    })
                }
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
</style>