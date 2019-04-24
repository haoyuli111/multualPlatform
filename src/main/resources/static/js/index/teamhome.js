/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-10 10:24:49
 * @lastTime: 2019-04-10 10:25:46
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

//获取URL id
var url=location.search,
    obj={};

var tar=url.replace("?","");
//设置token
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);

var teamHome=new Vue({
    el:'#teamHome',
    data:{
        teammation:{},
    },
    created(){
        this.TeamList();
    },
    methods: {
        TeamList: function () {
            let self = this;
            this.$http.get("http://127.0.0.1:8080/team/queryPublic/" + tar).then(
                function (res) {
                    self.teammation = res.body.data;
                    new $.zui.Messager('加载成功', {
                        type: 'success',
                        placement: 'center',
                        icon: 'icon-ok-sign'
                    }).show();
                    console.log(res)
                },
                function (res) {
                    if (res.body.code == 1201) {
                        new $.zui.Messager('未登陆账户', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    } else {
                        new $.zui.Messager('网络错误或找不到服务器', {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                }
            ).catch(function (reason) {
                console.log(reason)
            })
        },
        //加入团队--公有
        joinTeam:function(){
            let token=document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            commentForm.append('teamId',tar);
            this.$http.post('http://localhost:8080/teamMember/joinPublicTeam/' + tar, commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        new $.zui.Messager('加入成功', {
                            type: 'success',
                            placement: 'center',
                            icon: 'icon-ok-sign'
                        }).show();
                    }else{
                        new $.zui.Messager('加入未成功，'+res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    location.reload();
                   // console.log(JSON.stringify(res))
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
        }
    },
    filters:{
        capitalize:function(value){
            let d=new Date(value);
            let times=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()+'--'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
            return times;
        }
    }
});