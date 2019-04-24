/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-03-30 13:29:20
 * @lastTime: 2019-04-06 21:39:33
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
            //alert("加载下一页内容");
            document.querySelector("#loadIndicator1").classList.add("loading");
            // $.get("/data.json",function(data){
            //     let str=data.slice(0,9);
            //     alert(JSON.stringify(str));
            // })
           /* $.post("url",function(data){
                alert("加载成功");
            })*/
        }
       // $.ajaxSettings.async=false;
    }
)

//vue模板渲染接口
let app=new Vue({
    el:"#main",
    data:{
        lists:[]
    },
    mounted:function() {
        this.get();
    },
    methods: {
        get:function(){
            let self=this;
            this.$http.get("http://127.0.0.1:8080/article/queryList").then(
                function(res){
                    self.lists=res.data;
                    new $.zui.Messager('加载成功',{
                        type:'success',
                        placement:'center',
                        icon:'icon-ok-sign'
                    }).show();
                    //markdown文章渲染
                    $(function(){
                        editormd.markdownToHTML("markdown", {
                            htmlDecode: "style,script,iframe", //可以过滤标签解码
                            emoji: true,
                            taskList: true,
                            tex: true,               // 默认不解析
                            flowChart: true,         // 默认不解析
                            sequenceDiagram: true, // 默认不解析
                            codeFold: true,
                        });
                    })
                    //console.log(res.data);
                },function(res){
                    new $.zui.Messager('网络错误或找不到服务器',{
                        type:'danger',
                        placement:'center',
                        icon:'icon-exclamation-sign'
                    }).show();
                    //alert("状态码"+res.status+"网络问题或找不到服务器");
                }
            ).catch(function (reason) {
                console.log(reason);
            })
        },
    },
    filters:{
        capitalize:function(value){
            let d=new Date(value);
            let times=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()+'--'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
            return times;
        }
    }
})
