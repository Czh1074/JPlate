<template>
    <div class="app-container">

        <el-form ref="notice" :model="notice" label-width="80px">
            <el-form-item label="公告主题">
                <el-input :width="300" v-model="notice.project"></el-input>
            </el-form-item>
            <el-form-item label="公告简介">
                <tinymce :height="360" v-model="notice.introduction" />
            </el-form-item>


            <el-form-item label="公告附件">

                <!-- ref:唯一标识
                    auto-upliad：自动上传
                    limit:限制每次只能传一个文件
                    action:后端接口地址
                    name：input的值 -->

                <el-upload ref="upload" :auto-upload="false" :on-success="fileUploadSuccess" :on-error="fileUploadError"
                    :disabled="importBtnDisabled" :limit="1" :action="BASE_API+'/oss/fileoss/addFile'"
                    :on-preview="handlePreview" name="file" :on-change="getFileList">
                    <el-button slot="trigger" size="small" type="primary">添加附件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload()">
                        {{fileUploadBtnText }}</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="submitNotice()">添加公告</el-button>
            </el-form-item>
        </el-form>

    </div>

</template>


<script>
    //引入调用notice.js文件
    import notice from '@/api/notice/notice'
    import oss from '@/api/oss/oss'
    import Tinymce from '@/components/Tinymce'
    import { mapGetters } from 'vuex'

    export default {
        //声明组件
        components: { Tinymce },
        computed: {
            ...mapGetters([
                'id',
            ])
        },
        //写代码的核心位置
        data() { //定义变量和初始值
            return {
                //测试富文本插件的数据定义
                BASE_API: process.env.BASE_API, // 接口API地址
                OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
                fileUploadBtnText: '上传到服务器', // 按钮文字
                importBtnDisabled: false, // 按钮是否禁用,
                loading: false,
                notice: {
                    introduction: "",
                    project: "",
                    appendix1: "",
                    appendix2: '',
                    userId: this.id
                },
                loginInfo: {
                    id: '',
                    salt: '',
                    userNmae: '',
                    nickName: '',
                    sex: ''
                },
                fileList: [],
                filename: ""
            }
        },
        mounted() {

        },
        created() { //页面渲染之前执行，一般调用methos定义的方法

        },
        methods: { //具体的创建方法，调用notice.js定义的方法
            handlePreview(e) {
                // console.log(e)
                // console.log(e.data.url.lastIndexOf('/'))
            },
            getFileList(e) {
                console.log(e.url)
                this.fileList.push(e.url)
            },
            submitNotice() {
                this.notice.userId = this.id
                this.importBtnDisabled = true
                this.loading = true

                let promise = notice.addNotice(this.notice).then(response => {
                    this.$message({
                        type: 'success',
                        message: response.message
                    })
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
                    this.notice.appendix1 = response.data.url;
                    this.notice.appendix2 = response.data.url.substring(index, response.data.url.length)
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
        },
    }

</script>

<style scoped>
    .tinymce-container {
        line-height: 29px;
    }
</style>