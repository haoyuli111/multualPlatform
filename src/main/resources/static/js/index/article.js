/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-01 19:03:05
 * @lastTime: 2019-04-07 23:21:16
 */
$(function () {
    //超过一定高度导航添加类名
    var nav = $("header"); //得到导航对象  
    var win = $(window); //得到窗口对象  
    var sc = $(document); //得到document文档对象。  
    win.scroll(function () {
        if (sc.scrollTop() >= 100) {
            nav.addClass("on");
        } else {
            nav.removeClass("on");
        }
    })

    //移动端展开nav
    $('#navToggle').on('click', function () {
        $('.m_nav').addClass('open');
    })
    //关闭nav
    $('.m_nav .top .closed').on('click', function () {
        $('.m_nav').removeClass('open');
    })

    //二级导航  移动端
    $(".m_nav .ul li").click(function () {
        $(this).children("div.dropdown_menu").slideToggle('slow')
        $(this).siblings('li').children('.dropdown_menu').slideUp('slow');
    });

})
//获取URL id
let url=location.search,
    obj={};

let tar=url.replace("?","");
//设置token
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);

var app=new Vue({
    el:"#app",
    data:{
        article:{
            data:{
                publisherNick:{},
                publisherAvatar:{},
                articleType:{},
                title:{},
                content:{},
                accessory:{},
                updatedAt:{},
                articleLikeVOList:{
                    likeUserId:{},
                    userNick:{}
                },
                articleCommentVOList:{
                    createdAt:{},
                    senderName:{},
                    content:{},
                    id:{}
                }
            }
        },
        followpeople:{},
        otherarticle:{}
    },
    mounted:function(){
        this.get();
        this.other_article();
        this.followuser();
    },
    methods: {
        get:function(){
            let self=this;
            //
            this.$http.get("http://127.0.0.1:8080/article/query/"+tar).then(
                function(res){
                    self.article=res.data;
                    new $.zui.Messager('加载成功',{
                        type:'success',
                        placement:'center',
                        icon:'icon-ok-sign'
                    }).show();

                    //markdown文章渲染
                    $(function(){
                        editormd.markdownToHTML("markdown", {
                            // placeholder:"第一行为标题，此处编辑您要发布的内容",
                            path:'../../lib/',
                            htmlDecode: "style,script,iframe", //可以过滤标签解码
                            emoji:true,
                            taskList: true,
                            tex: true,               // 默认不解析
                            flowChart: true,         // 默认不解析
                            sequenceDiagram: true, // 默认不解析
                            codeFold: true,
                        });
                    })
                    console.log(res.data);
                },function(res){
                    new $.zui.Messager('网络错误或找不到服务器',{
                        type:'danger',
                        placement:'center',
                        icon:'icon-exclamation-sign'
                    }).show();
                    //console.log("状态码"+res.status+"网络错误或找不到服务器");
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //作者的其他文章
        other_article:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/article/queryArticleListByArticleId/"+tar, {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.otherarticle=res.body.data;
                    //console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //举报文章
        report_article: function () {
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('articleId',tar);
            this.$http.post('http://127.0.0.1:8080/inform/article/publish/' + tar, commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        new $.zui.Messager('举报成功', {
                            type: 'success',
                            placement: 'center',
                            icon: 'icon-ok-sign'
                        }).show();
                    }else{
                        new $.zui.Messager('举报未成功，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    console.log(JSON.stringify(res))
                },
                function (res) {
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href = 'login.html'
                    } else {
                        new $.zui.Messager('网络错误或未找到服务器，请检查网络后重新刷新', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    console.log(JSON.stringify(res))
                }
            ).catch(function (reason) {
                console.log(reason);
            })
        },
        //发布评论
        putcomment:function(){
            let content=document.querySelector('#comment-content').value;
            let token=document.querySelector('#token').value;
            //console.log(token);
            let formData=new FormData();
            formData.append('accessToken',token);
            formData.append('content',content);
            formData.append('parentId',tar)
            this.$http.post('http://127.0.0.1:8080/artircle/comment/publish',formData,{'Content-Type':'Multipart/form-data'}).then(
                function(res){
                    if(res.body.code==1001){
                        new $.zui.Messager('未填写内容，请重新填写评论',{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                    }else if(res.body.code==0){
                        new $.zui.Messager('发布成功，刷新加载新评论',{
                            type:'success',
                            placement:'center',
                            icon:'icon-ok-sign'
                        }).show();
                        window.location.reload();
                    }else{
                        new $.zui.Messager('评论失败，错误原因:'+res.body.message,{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                    }
                },function(res){
                    if(res.body.code==1201){
                        new $.zui.Messager('未登陆账户，正在跳转',{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                        window.location.href='login.html'
                    }else{
                    new $.zui.Messager('网络错误或找不到服务器',{
                        type:'danger',
                        placement:'center',
                        icon:'icon-exclamation-sign'
                    }).show();
                    }
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //删除评论
        delete_comment:function(event){
            let commentId=event.target.nextElementSibling.innerHTML
            //console.log(event.target.nextElementSibling.innerHTML);
            let token=document.querySelector('#token').value;
            let commentForm=new FormData();
            commentForm.append('accessToken',token);
            commentForm.append('commentId',commentId)
            this.$http.delete('http://127.0.0.1:8080/artircle/comment/delete/'+commentId,{body:commentForm},{
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if(res.body.code==0){
                        new $.zui.Messager('删除评论成功',{
                            type:'success',
                            placement:'center',
                            icon:'icon-ok-sign'
                        }).show();
                        window.location.reload();
                    }
                    console.log(res)
                },function(res){
                    if(res.body.code==1201){
                        new $.zui.Messager('未登陆账户，正在跳转',{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                        window.location.href='login.html'
                    }else{
                        new $.zui.Messager('网络错误或找不到服务器',{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                    }
                   // console.log(res)
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //举报评论
        report: function (event) {
            let commentId=event.target.previousElementSibling.innerHTML;
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('commentId', commentId)
            this.$http.post('http://127.0.0.1:8080/inform/comment/publish/' + commentId, commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        new $.zui.Messager('举报成功', {
                            type: 'success',
                            placement: 'center',
                            icon: 'icon-ok-sign'
                        }).show();
                        console.log(res)
                    }else{
                        new $.zui.Messager('举报未成功，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                },
                function (res) {
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href = 'login.html'
                    } else {
                        new $.zui.Messager('网络错误或未找到服务器，请检查网络后重新刷新', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        console.log(res)
                    }
                }
            ).catch(function (reason) {
                console.log(reason);
            })
         },
        //点赞文章
        article_love: function () {
            let token = document.querySelector('#token').value;
            let articleId =location.search.replace('?',"");
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('commentId', articleId)
            this.$http.put('http://127.0.0.1:8080/article/likes/' + tar,commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function(res){
                    if(res.body.code==0){
                        if(res.body.data==0){
                            let spanlove=document.querySelector('#article-love');
                            spanlove.innerHTML="已点赞";
                            spanlove.style.color="#e83737"
                        }else{
                            let spanlove=document.querySelector('#article-love');
                            spanlove.innerHTML="点赞";
                            spanlove.style.color="#353535;"
                        }
                    }else{
                        new $.zui.Messager('点赞失败，错误原因:' + res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    console.log(JSON.stringify(res))
                },
                function(res){
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账户，正在跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href = 'login.html'
                    } else {
                        new $.zui.Messager('网络错误或找不到服务器', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                }
            )
        },
        //关注作者
        follow:function(){
            let token=document.querySelector('#token').value;
            let userId=document.querySelector('#userid').innerHTML;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('userId',userId);
            this.$http.post('http://127.0.0.1:8080/follow/'+userId, commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        // new $.zui.Messager('关注成功', {
                        //     type: 'success',
                        //     placement: 'center',
                        //     icon: 'icon-ok-sign'
                        // }).show();
                        if(res.body.data==0){
                            let FollowArticle=document.querySelector('#FollowArticle');
                            FollowArticle.innerHTML='已关注'
                        }else{
                            let FollowArticle=document.querySelector('#FollowArticle');
                            FollowArticle.innerHTML='关注'
                        }
                    }else{
                        new $.zui.Messager('关注未成功，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    console.log(res)
                },
                function (res) {
                    console.log(res)
                }
            ).catch(function (reason) {
                console.log(reason);
            })
        },
        //关注作者列表
        followuser:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryFollowedMeList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.followpeople=res.body.data;
                    //console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //下载资源
        downloadR:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/teamResource/download/"+tar, {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    // if (res.body.code == 0) {
                    //     new $.zui.Messager('正在下载', {
                    //         type: 'success',
                    //         placement: 'center',
                    //         icon: 'icon-ok-sign'
                    //     }).show();
                    // }else{
                    //     new $.zui.Messager('下载失败，'+res.body.message, {
                    //         type: 'danger',
                    //         placement: 'center',
                    //         icon: 'icon-exclamation-sign'
                    //     }).show();
                    // }
                    console.log(res);
                },function(res){
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href = 'login.html'
                    } else {
                        new $.zui.Messager('网络错误或未找到服务器，请检查网络后重新刷新', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    //console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //收藏文章
        collectA:function(){
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('articleId ', tar)
            this.$http.post('http://127.0.0.1:8080/article/collect/' + tar, commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        if(res.body.data==0){
                            let spancollect=document.querySelector('#article-collect');
                            spancollect.innerHTML="已收藏";
                            spancollect.style.color="#e83737"
                        }else{
                            let spancollect=document.querySelector('#article-collect');
                            spancollect.innerHTML="收藏";
                            spancollect.style.color="#353535;"
                        }
                        console.log(res)
                    }else{
                        new $.zui.Messager('收藏失败，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                },
                function (res) {
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href = 'login.html'
                    } else {
                        new $.zui.Messager('网络错误或未找到服务器，请检查网络后重新刷新', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        console.log(res)
                    }
                }
            ).catch(function (reason) {
                console.log(reason);
            })
        }
    },
    computed:{
        //计算被关注人数
        FollowUser:function(){
            return this.followpeople.length;
        }
    },
    filters:{
        capitalize:function(value){
            let d=new Date(value);
            let times=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()+'--'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
            return times;
        }
    }
})
