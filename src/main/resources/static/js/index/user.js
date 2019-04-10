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

//vue-resource加载文章
var app=new Vue({
    el:'#vue_item',
    data:{
        items:[],
        resource:[],
    },
    mounted:function(){
        this.get();
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
                    that.items=res.data;
                    console.log(res.data);
                    document.querySelector('.main-content').style.height="auto";
                },function(res){
                    if(res.body.code==1201){
                        new $.zui.Messager('未登陆账号，即将跳转', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                        window.location.href='login.html'
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
        checktab:function(){
            //alert("success")
            this.$http.get('data.json').then(
                function(res){
                   this.resource=res;
                   console.log(res);
                },function(res){
                    new $.zui.Messager('网络错误或找不到服务器,错误信息',{
                        type:'danger',
                        placement:'bottom',
                        icon:'icon-exclamation-sign'
                    }).show();
                }
            )
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