var check_var;
function check_but(str) {
    check_var = str;
}
var Setrole = 'Teacher';

function roleSet(x) {
    
    Setrole = x;
}
window.fbAsyncInit = function() {
FB.init({
  appId      : '1761766334129049',
  cookie     : true,
  xfbml      : true,
  version    : 'v2.10'
});
  
FB.AppEvents.logPageView();   
  
};

(function(d, s, id){
 var js, fjs = d.getElementsByTagName(s)[0];
 
 if (d.getElementById(id)) {return;}
 js = d.createElement(s); js.id = id;
 js.src = "https://connect.facebook.net/en_US/sdk.js";
 fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function checkLoginState() {
var user_id,name,image,role;
  FB.getLoginStatus(function(response) {
    
    FB.api('/me', function(response) {
      user_id = response.id;
      name = response.name;
    });
    FB.api('/me/picture?type=normal', function (response) {

     image = response.data.url;
     var data;
        if(check_var == "signup") {
            data = {
                "user_id" : user_id,
                   "name" : name,
               "email_id" : "No Email",
                  "image" : image,
                   "role" : Setrole,
                  "login" : "fb"
            }
        }
        else {
            data = {
                "user_id" : user_id,
                   "name" : name,
               "email_id" : "No Email",
                  "image" : image,
                  "login" : "fb"
            }
        }
      ajax(data);
    }); 
  });
  
  
}
function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();  //var profile = googleUser.reloadAuthResponse();
  console.log(Setrole);
    
    var data1;
    if(check_var == "signup") {
        data1 = { 
            "user_id" : profile.getId(),
               "name" : profile.getName(),
           "email_id" : profile.getEmail(),
              "image" : profile.getImageUrl(),
               "role" : Setrole,
              "login" : "google"
        }
    }
    else {
        data1 = { 
            "user_id" : profile.getId(),
               "name" : profile.getName(),
           "email_id" : profile.getEmail(),
              "image" : profile.getImageUrl(),
              "login" : "google"
        }
    }
  ajax(data1);
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
    });
}

function logout() {
    FB.getLoginStatus(function(ret) {
        if(ret.authResponse) {
            FB.logout(function(response) {

            });
        } 
    });
}

function ajax(profile) {
    console.log(profile);
    var URL;
    if(check_var == "signup") {
        URL = "/doctrina.signup";
    }
    else {
        URL = "/doctrina.signin";
    }
    $.get(URL,profile,function(data,status){
        //response
        if (data == "Signin Successfull") {

            location.href = "/doctrina.index.do";
        }
        else {
            if(profile.login == "fb") {
                logout();
            }
            else if(profile.login == "google") {
                signOut();
            }
            error(data);
        }
    }); 
}

function check() {
    FB.getLoginStatus(function(response) {
        if(response["status"] == "connected") {
            checkLoginState();
        }
        
    });
}
function error (data) {
    $(".g-f>div:last-child").text(data);
    $(".g-f>div:last-child").css("top","20px");
    setTimeout(function(){
        $(".g-f>div:last-child").css("top","-70px");
    },1000);
}
