/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-03 11:19:12
 * @lastTime: 2019-04-07 13:59:19
 */
//banner设置
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

//设置token
var token = document.cookie.split(";")[0];
console.log(token);
document.querySelector('#token').setAttribute('value', token);
//vue-resource加载文章
var app=new Vue({
    el:'#vue_item',
    data:{
        items:[],
        resource:[],
        user:{
            data:{
                academyName:{},
                schoolName:{},
                userNick:{}
            }
        },
        followpeople:{},
        ilove:{},
        collect:{},
        teamM:{},
        teamJoin:{},
        mfollow:{}
    },
    mounted:function(){
        this.get();
    },
    created(){
        this.loadData();
        this.Mefollow();
        this.BfollowMe();
    },
    methods: {
        get:function(){
            let that=this;
            this.$http.get('http://127.0.0.1:8080/user/queryMyArticleList',{
                params:{
                    accessToken:token
                }
            }).then(
                function(res){
                    new $.zui.Messager('文章加载成功', {
                        type: 'success',
                        placement: 'center',
                        icon: 'icon-ok-sign'
                    }).show();
                    that.items=res.body.data;
                   // console.log(res);
                    document.querySelector('.main-content').style.height="auto";
                },function(res){
                    if(res.body.code==1201){
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        //window.location.href='login.html'
                    }else{
                        new $.zui.Messager('网络错误或找不到服务器,请确认网站状态后重新刷新',{
                            type:'danger',
                            placement:'bottom',
                            icon:'icon-exclamation-sign'
                        }).show();
                    }          
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //当前用户信息
        loadData:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryMyInformation", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.user=res.body;
                   // console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //资源列表
        checktab:function(){
            let that=this;
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            this.$http.post('http://localhost:8080/user/queryMyCollectList', commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    that.collect=res.body.data;
                    console.log(res);
                },
                function (res) {
                    console.log(res)
                    }
                    ).catch(function (reason) {
                        console.log(reason);
            })
        },
        //我创建的团队
        checktab2:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryMyTeamList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.teamM=res.body.data;
                    console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //我加入的团队
        checktab3:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryMyJoinTeamList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.teamJoin=res.body.data;
                    console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //我的关注
        checktab4:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryMyFollowList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.mfollow=res.body.data;
                    console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //删除文章
        delete_article:function(event) {
            let articlrId = event.target.nextElementSibling.innerHTML
            //console.log(articlrId);
            let token=document.querySelector('#token').value;
            let commentForm=new FormData();
            commentForm.append('accessToken',token);
            commentForm.append('commentId',articlrId)
            this.$http.put('http://127.0.0.1:8080/article/delete/'+articlrId,commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if(res.body.code==0){
                        new $.zui.Messager('删除文章成功',{
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
                     console.log(res)
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //被关注人列表
        BfollowMe:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://localhost:8080/user/queryFollowedMeList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.followpeople=res.body.data;
                   // console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //我关注的人列表
        Mefollow:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://localhost:8080/user/queryMyFollowList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.ilove=res.body.data;
                    //console.log(res);
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        }
    },
    computed:{
        //计算被关注人数
        FollowUsernum:function(){
            return this.followpeople.length;
        },
        //计算文章数量
        ArticleNum:function(){
            return this.items.length;
        },
        //计算我喜欢的人数
        Ilove:function(){
            return this.ilove.length;
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