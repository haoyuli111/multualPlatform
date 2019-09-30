/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-10 08:54:27
 * @lastTime: 2019-04-19 19:11:46
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

});

//设置token
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);

var team=new Vue({
    el:'#team',
    data:{
        teamlist:{},
        teamsearch:{
            teamId:{}
        }
    },
    created(){
        this.teamList();
    },
    methods:{
        teamList:function(){
            let self=this;
            let visual='0';
            this.$http.get("http://www.lichuachua.com:8082/team/queryAllList",{
                params:{
                    visual:visual
                }
            }).then(
                function(res){
                    self.teamlist=res.body.data;
                    new $.zui.Messager('加载成功',{
                        type:'success',
                        placement:'center',
                        icon:'icon-ok-sign'
                    }).show();
                    console.log(res);
                },function(res){
                    new $.zui.Messager('网络错误或找不到服务器',{
                        type:'danger',
                        placement:'center',
                        icon:'icon-exclamation-sign'
                    }).show();
                    console.log(res)
                }
            ).catch(function (reason) {
                console.log(reason);
            })
        },
        //搜索队伍名
        search:function(){
            let self=this;
            let teamName=document.querySelector('#teamName').value;
            let token=document.querySelector('#token').value
            this.$http.get("http://www.lichuachua.com:8082/team/queryTeamByTeamName/"+teamName,{
                params:{
                    accessToken:token,
                }
            }).then(
                function(res){
                    if(res.body.code==0){
                        new $.zui.Messager('正在进入该团队',{
                            type:'success',
                            placement:'center',
                            icon:'icon-ok-sign'
                        }).show();
                        self.teamsearch=res.body.data;
                        window.setTimeout(function(){
                            window.location.href='teamhome.html?'+self.teamsearch.teamId;
                        },2000)
                    }else{
                        new $.zui.Messager('搜索信息错误，'+res.body.message,{
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
            ).catch(function (reason) {
                console.log(reason);
            })
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