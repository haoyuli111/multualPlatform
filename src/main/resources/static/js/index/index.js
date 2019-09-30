/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-17 08:50:37
 * @lastTime: 2019-04-23 15:15:28
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

//根据token显示导航栏个人信息
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);

var app = new Vue({
    el: '#app',
    data: {
        user: {
            userAvatar: {}
        }
    },
    created() {
        this.init();
    },
    watch: {
        //登陆头像监听
        'user.userAvatar': function () {
            var defalutimg = document.querySelector('#header_nav_img').src;
            console.log(defalutimg);
        }
    },
    methods: {
        init: function () {
            //获取个人登陆信息
            let self = this;
            let token = document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/user/queryMyInformation", {
                params: {
                    accessToken: token
                }
            }).then(
                function (res) {
                    self.user = res.body.data;
                    //初始化导航栏头像
                    document.querySelector('#nav_user').style.display = 'block';
                    document.querySelector('#login').style.display = 'none';
                    console.log(res);
                },
                function (res) {
                    if (res.body.code == 1201) {
                        document.querySelector('#nav_user').style.display = 'none'
                    }
                    console.log(res);
                }
            ).catch(function (reason) {
                console.log(reason);
            })
            //console.log(token);
        },
        //鼠标移入显示个人信息导航栏
        UserMove: function () {
            document.querySelector("#header_nav_menu").style.height = "auto";
        },
        //移除收回个人信息导航栏
        UserOut: function () {
            document.querySelector("#header_nav_menu").style.height = "0";
        },
        //退出登陆
        quit_log: function () {
            var token = document.querySelector('#token').value;
            let commentForm = new FormData();
            commentForm.append('accessToken', token);
            this.$http.put('http://127.0.0.1:8080/user/logout', commentForm, {
                'Content-Type': 'Multipart/form-data'
            }).then(
                function (res) {
                    if (res.body.code == 0) {
                        new $.zui.Messager('退出成功', {
                            type: 'success',
                            placement: 'center',
                            icon: 'icon-ok-sign'
                        }).show();
                        window.location.href = 'index.html'
                    } else {
                        new $.zui.Messager('退出账号失败，错误原因:' + res.body.message, {
                            type: 'danger',
                            placement: 'center',
                            icon: 'icon-exclamation-sign'
                        }).show();
                    }
                    //console.log(JSON.stringify(res))
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
            )
        }
    },
})