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

function userInfo() {
    var token =$.cookie("gwok");
    if(!token){
        $(".noLogin").show();
        $(".loginandlogout").hide();
        return;
    }
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/ssoweb/user/getUserInfo.do",
        data:"token="+token,
        dataType:"jsonp",
        success:function(data){
            var json=JSON.parse(data);
            if(json.errorCode=="003"){
                $('#user-name-label').html("<a style=\"color: dodgerblue;\" href=\"/displayweb/personal.html\">"+json.username+"</a><span>  |   </span>");
            }else{
                $(".noLogin").show();
            }
        }
    })
}

function userInfo1() {
    var token =$.cookie("gwok");
    if(!token){
        $(".noLogin").show();
        $(".loginandlogout").hide();
        return;
    }
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/ssoweb/user/getUserInfo.do",
        data:"token="+token,
        dataType:"jsonp",
        success:function(data){
            var json=JSON.parse(data);
            if(json.errorCode=="003"){
                $('.user-name-label').text(json.username);
            }else{
                $(".noLogin").show();
            }
        }
    })
}

//每当点击就会去查询用户登录了没有，登录了就给personal.html，没登录就给login.html
function account() {
    var token =$.cookie("gwok");
    if(!token){
        location.href="/ssoweb/login.html";
        return;
    }else{
        location.href="personal.html";
    }

}


/**
 * logout,removeCookie
 */
function logout() {
    $(".loginandlogout").hide();
    $(".noLogin").show();
    $.removeCookie("gwok",{domain:'localhost',path:'/'});
}