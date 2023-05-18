<template>

    <div class="Messageform">
        <!-- 所属分类 开始 -->
        <section class="path-wrap txtOf hLh30" style="font-size:20px">
            <a href="/" title class="c-999 fsize14">首页</a>
            >
            <a href="/message" title class="c-999 fsize14">兼职列表</a>
        </section>
        <!-- /所属分类 结束 -->
        <h2 style="text-align: center;padding-bottom: 30px;">添加兼职信息</h2>
        <el-form ref="message" :model="message" label-width="80px">
            <el-form-item label="兼职类型">
                <el-input v-model="message.name"></el-input>
            </el-form-item>
            <el-form-item label="兼职地址">
                <el-input v-model="message.address"></el-input>
            </el-form-item>
            <el-form-item label="薪资">
                <el-input v-model="message.salary"></el-input>
            </el-form-item>
            <el-form-item label="联系人">
                <el-input v-model="message.contactName"></el-input>
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="message.contact"></el-input>
            </el-form-item>
            <el-form-item label="兼职介绍">
                <el-input type="textarea" v-model="message.introduction"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addMessage">立即创建</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
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
                message: { //添加兼职信息
                    name: '',
                    introduction: '',
                    address: '',
                    salary: '',
                    contact: '',
                    contactName: '',
                    userId: ''
                }
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
            addMessage() {
                var jsonStr = cookie.get("plate_user");
                //alert(jsonStr)
                if (jsonStr) {
                    this.loginInfo = JSON.parse(jsonStr)
                }
                this.message.userId = this.loginInfo.id

                messageApi.addMessage(this.message).then(response => {
                    this.$message({
                        message: '添加成功',
                        type: 'success'
                    });
                    this.message = ""
                })


            }
        }
    }
</script>
<style>
    .Messageform {
        position: relative;
        width: 700px;
        height: 650px;
        top: 20px;
        left: 400px;
    }
</style>