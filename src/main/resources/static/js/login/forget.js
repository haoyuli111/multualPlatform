/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-03-27 15:15:18
 * @lastTime: 2019-04-03 21:18:40
 */
//短信验证码
let code = document.querySelector("#code");
code.onclick = function () {
    let phone = document.querySelector("#exampleInputPhone").value;
    //console.log(phone);
    if (phone != '') {
        let phone = document.querySelector("#exampleInputPhone").value;
        $.post("http://127.0.0.1:8080/user/sendCode", {
            "mobile": phone
        }, function (data) {
            new $.zui.Messager('提示消息:'+JSON.stringify(data),{
                type:'success',
                placement:'center',
                icon:'icon-ok-sign'
            }).show();
            //alert(JSON.stringify(data));
            //设置button效果
            code.classList.add("disabled", "colse");
            let time = 60;
            let timer = setInterval(() => {
                if (time == 0) {
                code.classList.remove("colse", "disabled");
                code.setAttribute("value", "获取验证码");
            } else {
                code.value = time + "秒";
                time--;
            }
        }, 1000);
        })
        return false;

    } else {
        new $.zui.Messager('手机号码不能为空',{
            type:'danger',
            placement:'center',
            icon:'icon-exclamation-sign'
        }).show();
        //alert("手机号码不能为空")
    }
}

//忘记密码提交
let ReadyRegister = document.querySelector("#ReadyRegister");

ReadyRegister.onclick = function () {
    // let username = document.querySelector("#exampleInputAccount2").value;
    let userpassword = document.querySelector("#exampleInputPassword").value;
    let confirmPassword = document.querySelector("#confirmPassword").value;
    let userphone = document.querySelector("#exampleInputPhone").value;
    let usercode = document.querySelector("#exampleInputCode").value;
    if (userpassword && confirmPassword && userphone && usercode != '') {
        $.ajax({
            url:"http://127.0.0.1:8080/user/forget",
            type:"put",
            data:{
                "password": userpassword,
                "confirmPassword": confirmPassword,
                "mobile": userphone,
                "code": usercode
            },
            method:"put",
            success:function(data){
                new $.zui.Messager('提示消息:'+JSON.stringify(data),{
                    type:'success',
                    placement:'center',
                    icon:'icon-ok-sign'
                }).show();
                //alert(JSON.stringify(data))
                //window.location.href="login.html"
            },
            error:function(res){
                new $.zui.Messager('网络错误或找不到服务器,错误信息',{
                    type:'danger',
                    placement:'center',
                    icon:'icon-exclamation-sign'
                }).show();
               // alert(JSON.stringify(res));
            }
        })
        return false;

    } else {
        alert("信息未填写完整")
    }
}