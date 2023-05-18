<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 动态详情 开始 -->
    <section class="container">

      <!-- 动态所属分类 开始 -->
      <section class="path-wrap txtOf hLh30" style="font-size:20px">
        <a href="/" title class="c-999 fsize14">首页</a>
        >
        <a href="/trend" title class="c-999 fsize14">动态列表</a>
        >
        <span class="c-333 fsize14">{{ trend.project }}</span>
      </section>
      <!-- 动态基本信息 开始 -->
      <div style="padding-bottom: 20px;text-align: center;">
        <h1>{{trend.project}}</h1>
        <h4 style="padding: 5px;">
          <img class="avatar" :src="user.salt"
            style="width: 30px;height: 30px;border-radius: 50%;vertical-align: middle;">
          <span style="font-size: 13px;color:brown;">{{user.nickName}}</span>
          <span style="font-size: 12px;">{{trend.gmtCreate}}</span>
          <el-tag type="info">访问量：{{trend.viewCount}}</el-tag>
          <el-tag>点赞量：{{trend.agreeCount}}</el-tag>
          <el-tag type="success">收藏量：{{trend.collectCount}}</el-tag>
          <el-button type="info" @click="updateCollect()" v-if="trend.collect==false" size="small">未收藏
          </el-button>
          <el-button type="success" @click="updateCollect()" v-if="trend.collect==true" size="samll">
            已收藏
          </el-button>
        </h4>
      </div>
      <!-- 动态基本信息 结束 -->
      <!-- 动态基本信息 开始 -->
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section id="videoPlay" class="p-h-video-box">
            <img v-if="trend.picture1 == null"
              src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/05/02/background.jpg" class="dis c-v-pic"
              style="display: block;height: 357px;width: 640px;">
            <img v-if="trend.picture1 != null" :src="trend.picture1" class="dis c-v-pic"
              style="display: block;height: 357px;width: 640px;">
          </section>
        </article>
        <aside class="c-attr-wrap" style="width: 520px;">
          <section class="thr-attr-ol clearfixml20 mr15" style="overflow-y: auto;">
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14" style="font-size: 20px;">&nbsp;&nbsp;{{trend.introduction}}</span>
            </section>
          </section>
        </aside>


        <div class="clear" />
        <el-row type='flex' :gutter="1" style="margin-top: 20px;">
          <!-- <el-col :span="3">
            <el-button class="el-icon-thumb" plain>&nbsp;点赞</el-button>
          </el-col> -->
          <!-- <el-col :span="3">
            <el-button class="el-icon-star-on" plain>&nbsp;收藏</el-button>
          </el-col> -->
          <!-- <el-col :span="3">
            <el-button class="el-icon-edit" plain>&nbsp;评论</el-button>
          </el-col> -->
        </el-row>
      </div>

      <!-- 评论区 -->

      <div class="mt50 commentHtml">
        <div>
          <h6 class="c-c-content c-infor-title" id="i-art-comment">
            <span class="commentTitle">动态评论</span>
          </h6>
          <section class="lh-bj-list pr mt20 replyhtml">
            <ul>
              <li class="unBr">
                <aside class="noter-pic">
                  <img width="50" height="50" class="picImg" :src="loginInfo.salt">
                  <!-- <img width="50" height="50" class="picImg" src="~/assets/img/avatar-boy.gif"> -->
                </aside>
                <div class="of">
                  <section class="n-reply-wrap">
                    <fieldset>
                      <textarea name="" v-model="comment.message" placeholder="输入您要评论的文字"
                        id="commentContent"></textarea>
                    </fieldset>
                    <p class="of mt5 tar pl10 pr10" style="padding-left: 0px;">
                      <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                      <input type="button" @click="addComment()" value="回复" class="lh-reply-btn"
                        style="display: block;width: 50px;height: 30px;text-align:center;">
                    </p>
                  </section>
                </div>
              </li>
            </ul>
          </section>
          <section class="">
            <section class="question-list lh-bj-list pr">
              <ul class="pr10">
                <li v-for="comment in comments.items" v-bind:key="comment.id">
                  <!-- <li v-for="(comment,index) in data.items" v-bind:key="index"> -->
                  <aside class="noter-pic">
                    <img width="50" height="50" class="picImg" :src="comment.avatar">
                  </aside>
                  <div class="of">
                    <span class="fl">
                      <font class="fsize12 c-blue">
                        {{comment.nickname}}</font>
                      <!-- <font class="fsize12 c-999 ml5"></font> -->
                    </span>
                  </div>
                  <div class="noter-txt mt5">
                    <p>评论：&nbsp;{{comment.message}}</p>
                  </div>
                  <div class="of mt5">
                    <span class="fr">
                      <font class="fsize12 c-999 ml5">{{comment.gmtCreate}}</font>
                    </span>
                  </div>
                </li>

              </ul>
            </section>
          </section>

          <!-- 公共分页 开始 -->
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a :class="{undisable: !comments.hasPrevious}" href="#" title="首页" @click.prevent="gotoPage(1)">首</a>
            <a :class="{undisable: !comments.hasPrevious}" href="#" title="前一页"
              @click.prevent="gotoPage(comments.current-1)">&lt;</a>
            <a v-for="page in comments.pages" :key="page"
              :class="{current: trend.current == page, undisable: comments.current == page}" :title="'第'+page+'页'"
              href="#" @click.prevent="gotoPage(page)">{{ page }}</a>
            <a :class="{undisable: !comments.hasNext}" href="#" title="后一页"
              @click.prevent="gotoPage(comments.current+1)">&gt;</a>
            <a :class="{undisable: !comments.hasNext}" href="#" title="末页" @click.prevent="gotoPage(trend.pages)">末</a>
            <div class="clear" />
          </div>
          <!-- 公共分页 结束 -->
        </div>
      </div>


    </section>
  </div>
</template>

<script>
  import noticeApi from "@/api/notice"
  import commentApi from "@/api/comment"
  import trendApi from "@/api/trend"
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
        //发布者信息
        user: {
          salt: '',
          nickName: ''
        },
        //动态信息
        trend: {
          id: this.$route.params.id,
          project: '',
          picture: '',
          collect: '',
          introduction: '',
          viewCount: '',
          agreeCount: '',
          CommentCount: '',
          collectCount: '',
          gmtCreate: '',
          gmtModefied: ''
        },
        comments: [],
        comment: {
          id: '',
          trendId: this.$route.params.id,
          userId: '',
          message: '',
          avatar: '',
          nickname: '',
          gmtCreate: ''
        },
        collect: 'false',
        favorite: {
          id: '',
          userId: '',
          infoId: '',
          infoType: 1
        }
      }
    },
    created() {
      this.getTrendById()
      this.getCommentList()
    },
    methods: {
      getTrendById() {
        var jsonStr = cookie.get("plate_user");
        //alert(jsonStr)
        if (jsonStr) {
          this.loginInfo = JSON.parse(jsonStr)
        }
        trendApi.getTrendInfo(this.trend.id, this.loginInfo.id).then(response => {
          console.dir(response)
          this.trend = response.data.data.trend
          this.favorite = response.data.data.favorite.favorite || {}
          this.user.salt = response.data.data.user.salt
          this.user.nickName = response.data.data.user.nickName
          // response.data.data.collect
          console.log(this.favorite)
        });
      },
      //获得评论列表
      getCommentList() {
        commentApi.getCommentPageList(1, 6, this.trend.id).then(response => {
          this.comments = response.data.data
        });
      },
      //添加评论功能
      addComment() {
        var jsonStr = cookie.get("plate_user");
        //alert(jsonStr)
        if (jsonStr) {
          this.loginInfo = JSON.parse(jsonStr)
        }
        this.comment.userId = this.loginInfo.id
        this.comment.avatar = this.loginInfo.salt
        this.comment.nickname = this.loginInfo.nickName
        commentApi.addComment(this.comment).then(response => {
          this.$message({
            message: '评论成功',
            type: 'success'
          });
          this.comment.message = ''
          this.getCommentList()
        })
      },
      //设置收藏功能 + 修改收藏collect字段
      updateCollect() {
        this.trend.collect = !this.trend.collect;
        var jsonStr = cookie.get("plate_user");
        //alert(jsonStr)
        if (jsonStr) {
          this.loginInfo = JSON.parse(jsonStr)
        }
        this.favorite.userId = this.loginInfo.id
        this.favorite.infoId = this.trend.id
        this.infoType = 1
        trendApi.updateCollect(this.favorite.id, this.favorite.userId, this.favorite.infoId, this.trend.collect, 1).then(response => {
          this.favorite.id = response.data.data.id
          this.$message({
            message: '操作成功',
            type: 'success'
          });
        })
      },
      // //设置分页
      gotoPage(page) {
        commentApi.getCommentPageList(page, 6, this.trend.id).then(response => {
          this.comments = response.data.data
        })
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
  .box {
    display: flex;
    align-items: center;
    padding: 20px;
    box-sizing: border-box;
    width: 100%;
    /* height: 200px; */
    border: 2px solid rgb(195, 191, 191);
  }

  .box .avatar {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #666;
  }
</style>