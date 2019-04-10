/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-03-30 13:29:20
 * @lastTime: 2019-03-30 13:30:32
 */

 //导航栏
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

//日期更新
function getNow(s) {
    return s < 10 ? '0' + s : s;
}

var myDate = new Date();
//获取当前年
var year = myDate.getFullYear();
//获取当前月
var month = myDate.getMonth() + 1;
//获取当前日
var date = myDate.getDate();
var h = myDate.getHours(); //获取当前小时数(0-23)
var m = myDate.getMinutes(); //获取当前分钟数(0-59)
var s = myDate.getSeconds();

var now = year + '-' + getNow(month) + "-" + getNow(date) + " " + getNow(h) + ':' + getNow(m) + ":" + getNow(s);

$(document).ready(function () {
    $("#time").append(now);
})

//滚动加载
$(window).scroll(
    function(){
        let scrollTop=$(this).scrollTop();
        let scrollHeight=$(document).height();
        let windowHeight=$(this).height();
        if(scrollTop+windowHeight==scrollHeight){
            alert("加载下一页内容");
            document.querySelector("#loadIndicator1").classList.add("loading");
           /* $.post("url",function(data){
                alert("加载成功");
            })*/
        }
        $.ajaxSettings.async=false;
    }
)