/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-01 16:29:31
 * @lastTime: 2019-04-07 21:11:30
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

//设置是否可见的开关
// let count = 0;
// document.querySelector("#exampleInputcheck").addEventListener('click', function () {
//     if (count % 2 == 1) {
//         document.querySelector("#exampleInputcheck").setAttribute("value", 0);
//         console.log(visual)
//     } else {
//         document.querySelector("#exampleInputcheck").setAttribute("value", 1);
//         console.log(visual);
//     }
//     count++;
// })

//提交个人资料表单
// document.querySelector("#form-button1").addEventListener('click', function () {
//     let UserToken = document.querySelector('#token').value;
//     let academyId = document.querySelector('#academyId').value;
//     let schoolId = document.querySelector('#schoolId').value;
//     let userEmail = document.querySelector("#userEmail").value;
//     let userName = document.querySelector('#userName').value;
//     let userNick = document.querySelector("#userNick").value;
//     let userNumber = document.querySelector("#userNumber").value;
//     let visual = document.querySelector("#exampleInputcheck").value;
//     $.ajax({
//         type: 'POST',
//         url: 'http://localhost:8080/user/infor',
//         data: {
//             accessToken: UserToken,
//             academyId: academyId,
//             schoolId: schoolId,
//             userEmail: userEmail,
//             userName: userName,
//             userNick: userNick,
//             userNumber: userNumber,
//             visual: visual
//         },
//         success: function (data) {
//             if (data.code == 0) {
//                 new $.zui.Messager('发布成功', {
//                     type: 'success',
//                     placement: 'center',
//                     icon: 'icon-ok-sign'
//                 }).show();
//             } else {
//                 new $.zui.Messager('发布未成功，' + data.message, {
//                     type: 'danger',
//                     placement: 'center',
//                     icon: 'icon-exclamation-sign'
//                 }).show();
//             }
//         },
//         error: function (data) {
//             if (data.responseJSON.code == 1201) {
//                 new $.zui.Messager('未登陆账号，即将跳转', {
//                     type: 'danger',
//                     placement: 'center',
//                     icon: 'icon-exclamation-sign'
//                 }).show();
//                 window.location.href = 'login.html'
//             } else {
//                 new $.zui.Messager('网络错误或未找到服务器，请检查网络后重新刷新', {
//                     type: 'danger',
//                     placement: 'center',
//                     icon: 'icon-exclamation-sign'
//                 }).show();
//             }
//         }
//     })
//     return false;
// })
//设置开启隐藏计数
let num = 1;
let num2 = 1;
let count=0;

let app = new Vue({
    el: '#app',
    data: {
        avatar: 'image/index/man3.webp',
        school:{},
        college:{}
    },
    mounted:function(){
        this.get();
    },
    created(){
        this.loadData();
    },
    methods: {
        get:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            //let formData=new FormData();
            //formData.append('accessToken',token);
            this.$http.get("http://127.0.0.1:8080/school/queryList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.school=res.body;
                    //console.log(JSON.stringify(res));
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //加载学院option
        loadData:function(){
            let self=this;
            let token=document.querySelector('#token').value;
            this.$http.get("http://127.0.0.1:8080/academy/queryList", {
                params: {
                    accessToken: token
                }
            }).then(
                function(res){
                    self.college=res.body;
                    //console.log(JSON.stringify(res));
                },function(res){
                    console.log(res);
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //获取学校的值
        changeSchool:function(){
            let School=document.querySelector('#schoolId').value;
            console.log(School);
        },
        changeCollage:function(){
            let collage=document.querySelector('#academyId').value;
            console.log(collage);
        },
        setAvatar:function(){
            this.$refs.avatarInput.click()
        },
        changeImage(e) {
            var file = e.target.files[0]
            var reader = new FileReader()
            var that = this
            reader.readAsDataURL(file)
            reader.onload = function(e) {
              that.avatar = this.result
            }
          },
        //上传头像
        edit: function () {
            //获取img名称
            // let imgname = document.querySelector("#imgname").src;
            // let filename;
            // if (imgname.indexOf("/") > 0) {
            //     filename = imgname.substring(imgname.lastIndexOf('/') + 1, imgname.length);
            // } else {
            //     filename = imgname;
            // }
            let token=document.querySelector('#token1').value;
            if (this.$refs.avatarInput.files.length !== 0) {
                let image = new FormData()
                image.append('avatar', this.$refs.avatarInput.files[0])
                this.$http.put('http://127.0.0.1:8080/user/updateAvatar/man2.webp',{
                    accessToken:token,
                    //file:image
                },{
                    headers:{
                        "Content-Type": "multipart/form-data"
                    }
                }).then((response) =>{
                    console.log(response)
                    })
            }
        },
        //设置是否可见的开关
        Switch:function(){
            if (count % 2 == 1) {
            document.querySelector("#exampleInputcheck").setAttribute("value", 0);
             } else {
            document.querySelector("#exampleInputcheck").setAttribute("value", 1);
             }
             count++;
        },
        //提交表单1
        form1:function(){
            let UserToken = document.querySelector('#token').value;
            let academyId = document.querySelector('#academyId').value;
            let schoolId = document.querySelector('#schoolId').value;
            let userEmail = document.querySelector("#userEmail").value;
            let userName = document.querySelector('#userName').value;
            let userNick = document.querySelector("#userNick").value;
            let userNumber = document.querySelector("#userNumber").value;
            let visual = document.querySelector("#exampleInputcheck").value;
            //console.log(schoolId);
            let formData=new FormData();
            formData.append('accessToken',UserToken);
            formData.append('academyId',academyId);
            formData.append('schoolId',schoolId);
            formData.append('userEmail',userEmail);
            formData.append('userName',userName);
            formData.append('userNick',userNick);
            formData.append('userNumber',userNumber);
            formData.append('visual',visual);
            this.$http.post('http://127.0.0.1:8080/user/infor',formData,{'Content-Type':'Multipart/form-data'}).then(
                function(res){
                    if(res.body.code==0){
                        new $.zui.Messager('修改信息成功',{
                            type:'success',
                            placement:'center',
                            icon:'icon-ok-sign'
                        }).show();
                    }else{
                        new $.zui.Messager('修改失败，错误原因:'+res.body.message,{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                    }
                   // console.log(JSON.stringify(res))
                },
                function (res) {
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
                    //console.log(JSON.stringify(res))
                }
            ).catch(function(reason){
                console.log(reason);
            })
        },
        //展开
        clickfoll: function () {
            //设置点击开启隐藏
            if (num % 2 == 1) {
                document.querySelector('#holder').style.height = 'auto';
            } else {
                document.querySelector('#holder').style.height = '0px';
            }
            num++;
        },
        clickfoll2: function () {
            if (num2 % 2 == 1) {
                document.querySelector('#second-holder').style.height = 'auto';
                document.querySelector('#sm-img').style.display = "none";
            } else {
                document.querySelector('#second-holder').style.height = '0px';
                document.querySelector('#sm-img').style.display = "inline-block";
            }
            num2++;
        }
    },
})