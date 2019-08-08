//这是注册的下一步点击按钮，点击会发送数据给相应的接口检查，若注册成功会返回注册成功的html，若失败，会返回响应的错误提示
function registnext() {
    var $form = $('#regist');
    $.ajax({
        type:"POST",
        url:$form.attr("action"),
        data:$form.serialize(),
        dataType:"json",
        success:function (data) {
            var inner=$('#inner');
            if(data['errorCode']=="001"){
               inner.text("图片验证码错误");
            }
            if(data['errorCode']=="003"){
                inner.text("密码不符合规范");
            }
            if(data['errorCode']=="004"){
                inner.text("用户名不符合规范");
            }
            if(data['errorCode']=="005"){
               inner.text("注册失败");
            }
            if(data['errorCode']=="007"){
               inner.text("用户名已存在");
            }
            if(data['errorCode']=="006"){
                window.location.href='register1.html';
            }
        },
        error:function (data) {
            // var json= JSON.stringify(data);
            alert("网络异常");
        }

    })
}
// function common() {
//     var $form = $('#form');
//     $.ajax({
//         type:"POST",
//         url:$form.attr("action"),
//         data:$form.serialize(),
//         dataType:"json",
//         success:function (data) {
//             if(data['readyState']==4){
//                 alert("0");
//             }
//
//             if(data['"errorCode"']=="001"){
//                 window.location.href='register1';
//                 alert('卧槽');
//             }
//             alert(data);
//         },
//         error:function (data) {
//             var json= JSON.stringify(data);
//             alert(json);
//         }
//
//     })
// }

//每当点击就会去查询用户登录了没有，登录了就给personal.html，没登录就给login.html
function account() {

}
