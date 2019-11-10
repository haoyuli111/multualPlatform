/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-18 08:13:12
 * @lastTime: 2019-04-18 09:09:35
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

//设置token
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);
//设置开关计数
var count=0;

var teammake=new Vue({
    el:'#teammake',
    data:{
        teamType:{}
    },
    created(){
        this.teamtype();
    },
    methods:{
        //创建团队
        formset:function(){
            let teamName=document.querySelector('#teamName').value;
            let password=document.querySelector('#password').value;
            let description=document.querySelector('#description').value;
            let number=document.querySelector('#number').value;
            let type=document.querySelector('#type').value;
            let visual=document.querySelector('#visual').value;
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('teamName', teamName);
            commentForm.append('password', password);
            commentForm.append('description', description);
            commentForm.append('number', number);
            commentForm.append('type', type);
            commentForm.append('visual', visual);
            this.$http.post('http://127.0.0.1:8080/team/publish',commentForm,{
                'Content-Type': 'Multipart/form-data'
            }).then(
                function(res){
                    if (res.body.code == 0) {
                        new $.zui.Messager('创建成功', {
                            type: 'success',
                            placement: 'center',
                            icon: 'icon-ok-sign'
                        }).show();
                        window.location.href='team.html';
                    }else{
                        new $.zui.Messager('创建未成功，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    console.log(res)
                },
                function(res){
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
                    console.log(res)
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //加载队伍类型
        teamtype:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/teamType/queryList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.teamType=res.body;
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
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //设置是否可见的开关
        Switch:function(){
            if (count % 2 == 1) {
            document.querySelector("#visual").setAttribute("value", 0);
             } else {
            document.querySelector("#visual").setAttribute("value", 1);
             }
             count++;
        },
        //获取类型的值
        changeType:function(){
            let School=document.querySelector('#type').value;
            //console.log(School);
        },
    }
})