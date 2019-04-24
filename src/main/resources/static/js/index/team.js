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

})

var team=new Vue({
    el:'#team',
    data:{
        teamlist:{}
    },
    created(){
        this.teamList();
    },
    methods:{
        teamList:function(){
            let self=this;
            let visual='0';
            this.$http.get("http://127.0.0.1:8080/team/queryAllList",{
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