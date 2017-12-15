<html>
<head>
    <title>DOCTRINA | Home Page</title>
    <link rel="stylesheet" href="../CSS/homepage.css"/>
    <link href="https://fonts.googleapis.com/css?family=Lato|Mirza|Oleo+Script" rel="stylesheet">
    <link rel="shortcut icon" href="../IMAGES/D-logo.png"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <script src="../JS/homepage.js"></script>
 
</head>

	<style>
        .a{display:none;}
    </style>
    
    <script>
        var webSocket;
        var course
        var course_name;
        function notification_function(a){
            $.get("http://basheerahameds-1508.zcodeusers.com/notification_click",{"user_id":"<%=session.getAttribute("user_id")%>","message":a},function(data, status){
                if(data == "ok"){
                    webSocket.send("<%=session.getAttribute("user_id")%>");
                }
            });
        }
        
        function getClassroom(course_id, courseName) {
            course = course_id;
            $(".all").css("display","none");
            $(".allclass").css("display","block");
            document.getElementById("myclassroom").innerHTML="";
            document.getElementById("classroom").innerHTML="<h1 class='h1' id='course_name'></h1>";
            $.get("http://basheerahameds-1508.zcodeusers.com/getclassroom",{"user_id":"<%=session.getAttribute("user_id")%>","course_id":course_id},function(data, status){
                <%
                    if((session.getAttribute("role")+"").equals("Teacher")) {
                        %>document.getElementById("classroom").innerHTML+="<div class='title_div'><div class='title'><i class='fa fa-times-circle fa-2' aria-hidden='true' onclick='close_cls()'></i><label>Classroom Name</label><input type='text' id = 'cls_name' required autofocus/><label>Description</label><textarea maxlength='110' id='cls_des'></textarea><button type='submit' class='done_btn' onclick='done_crt()'>Done</button></div><div class='crt_div'><p>Create New Classroom</p><button type='submit' class='create_btn' onclick='crt_cls()'>+</button></div></div>";<%
                    }
                %>
                classroomRender(data,"allclass");
                history.pushState(null, null, '/course/'+courseName);
            });
            
        }
         function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
              location.href = "/landingpage";
            });
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
    function logout() {
        FB.getLoginStatus(function(ret) {
            if(ret.authResponse) {

                FB.logout(function(response) {
                   location.href = "/landingpage";
                });
            } 
        });
    }
    
    var getMyclassroom = function(userId) {
        document.getElementById("myclassroom").innerHTML="";
        document.getElementById("classroom").innerHTML="";
        $.get("http://basheerahameds-1508.zcodeusers.com/myclassroom",{"user_id":userId},function(data, status){
                history.pushState(null,null,"/myclassroom");
                classroomRender(data,"myclass");
            });
    }
    
    var classroomRender = function(data,state) {
        var classroomObj = JSON.parse(data)
        var tempTemplete = document.getElementById("clasroom-templete").innerHTML;
        var templete  = Handlebars.compile(tempTemplete);
        if (state == "allclass") {    
            document.getElementById("classroom").innerHTML += templete(classroomObj);
            document.getElementById("course_name").innerHTML = classroomObj.courseName[0].courseName;
            course_name = classroomObj.courseName[0].courseName;
        } else {
            document.getElementById("myclassroom").innerHTML += templete(classroomObj);
        }
        var color_array=["#73efbb","#e2d55e","#ff89a9","#c1f97f","#ffa3a3","#e8aaf3"];
        var color_num = 0;
        for (i = 1; i <= classroomObj.classrooms.length; i++) {
            
            if(state == "allclass") {
                document.getElementById("join"+i).innerHTML="<button onclick='Class_JoinRequest("+classroomObj.classrooms[i-1].classroom_id+","+i+")'>Join</button>";
                
                if(classroomObj.classrooms[i-1].status == "inside") {
                    document.getElementById("join"+i).innerHTML="<button>Joined</button>";
                }
                else if (classroomObj.classrooms[i-1].status == "pending") {
                    document.getElementById("join"+i).innerHTML="<button>Request sent</button>";
                }
            }
            else {
                document.getElementById("join"+i).innerHTML=classroomObj.classrooms[i-1].course_name;
            }
            if(color_num > 5) {
                color_num = 0;
            }
            document.getElementById(i).style.background = color_array[color_num];
            color_num++;
        }
    } 
    var onloaded = function() {
        <%=session.getAttribute("course")%> = "<%=session.getAttribute("course")%>"
        <%=session.getAttribute("function")%>
    	socket();
    	//myclassroom();
    }

        var socket = function() {
    
        document.getElementById("profile_image").style.background = "url('<%=session.getAttribute("image")%>') 100%/100% 100%";
        if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
           return;
        }
        // Create a new instance of the websocket
        webSocket = new WebSocket("ws://basheerahameds-1508.zcodeusers.com//websocket/<%=session.getAttribute("user_id")%>");

        webSocket.onmessage = function(event){
            document.getElementById("dotPoint").innerHTML = "";
            document.getElementById("InviteDot").innerHTML ="";
            var notificationObj = JSON.parse(event.data)
            var notifi = notificationObj.notifications;
            //var notificati = notifi.reverse();
            var tempTemplete = document.getElementById("notification-templete").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            //notificationObj.notifications = notifi.reverse();
            document.getElementById("notified").innerHTML= templete(notificationObj);
            
            var requestTemplete = document.getElementById("request-details").innerHTML;
            var Compile_requestTemplete = Handlebars.compile(requestTemplete);
            //notificationObj.notifications = notifi.reverse();

            <% if ((session.getAttribute("role")+"").equals("Teacher")){
                %>document.getElementById("invited").innerHTML= Compile_requestTemplete(notificationObj);<%
            }%>
            //var key = Object.keys();
            var dot = false;
            var notification_count = notificationObj.requests.length;
            if(notification_count != 0) {
                dot = true;
                document.getElementById("InviteDot").innerHTML = "<div class='dot2'></div>" ;
            }
            for (i = 0; i < notificationObj.notifications.length; i++) {
                
                if(notificationObj.notifications[i].status == "true") {
                    dot = true;
                    notification_count++;
                    document.getElementById("noti"+notificationObj.notifications[i].tabIndex).style.background = "#EEEEEE";
                }
                else{
                    document.getElementById("noti"+notificationObj.notifications[i].tabIndex).removeAttribute("onclick");
                }
            }
            
            if(dot ==true) {
                dot = false;
                document.getElementById("dotPoint").innerHTML = "<span class='dot'><p>"+notification_count+"</p></span>";
            }
        };
        var interval = setInterval(function() {if (webSocket.readyState === webSocket.OPEN) {
                var msg = "<%=session.getAttribute("user_id")%>";
                webSocket.send(msg);
                clearInterval(interval);
        }}, 1000);
        webSocket.onclose = function(event){
            
        };
    }

    function crt_cls() {
        $(".crt_div").css("top","-275px");
    }
   
    function done_crt(){
    //Regex
    var classname_reg = /^[^_\W]{3,25}$/;
    var des_reg = /^.{5,50}$/;
    
        var class_name = $("#cls_name").val();
        var des = $("#cls_des").val();
    console.log(class_name + des);
        if(class_name.match(classname_reg) && des.match(des_reg)) {
            $.get("http://basheerahameds-1508.zcodeusers.com/createclassroom",{"user_id":"<%=session.getAttribute("user_id")%>","classroom_name":class_name,"classroom_description":des,"course_id":course},function(data, status){
                if(data == "200") {
                    webSocket.send("all");
                    getClassroom(course, course_name);
                }
                else if (data == "400"){
                    error('info');
                }
                else if (data ="403" || data == "401") {
                    error('danger');
                }
            });
        }
        else {
            error('danger');
        }
    }
    function error (type) {
        if (type == "info") {
           $(".alert").css("color", "rgb(0, 146, 255)"); 
           $(".alert>i").css("background", "rgba(0, 146, 255,0.8)");
        }
        else if (type == "danger"){
           $(".alert").css("color", "rgb(255, 0, 0)"); 
           $(".alert>i").css("background", "#rgba(255, 0, 0, 0.7)");
        }
        
        $(".alert").css("top","0");
        setTimeout (function(){
        $(".alert").css("top","-75px");
        },2000);
    }
    
    function close_cls(){
        $(".crt_div").css("top","0px");
    }
    
    function GotoSettings () {
        location.href="/doctrina.settings";
    }
    
    function Class_JoinRequest (classId, id ) {
        document.getElementById("join"+id).innerHTML="<button>Request sent</button>";
        $.get("http://basheerahameds-1508.zcodeusers.com/Classroom_JoinRequest",{"requester":"<%=session.getAttribute("user_id")%>","class_id":classId},function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
            }
        });
    }
    
    function replyRequest (requester, class_id, status) {
        $.get("http://basheerahameds-1508.zcodeusers.com/ReplyRequest",{"requester":requester,"class_id":class_id,"status":status}, function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
            }
        });
    }
    
    function gotoClassroom(class_id) {
        $.get("http://basheerahameds-1508.zcodeusers.com/enterClassroom",{"class_id":class_id, "user_id" : "<%=session.getAttribute("user_id")%>" }, function(data, status) {
            if(data == "200" ) {
                location.href = "http://basheerahameds-1508.zcodeusers.com/classroom";
            }
        });
    }
    
    </script>
<body onload="onloaded()">
    <div class="whole">
        
        <!-- Header -->
        
       <header>
            <img src="../IMAGES/D.gif" alt="D logo" />
            <div class="hdr">
                <div class="cls">
                    <label class="la1 select">All Classroom</label>
                    <label class="la2" onclick="getMyclassroom('<%=session.getAttribute("user_id")%>')">My Classroom</label>
                </div>     
                
                <div class="header_div">
                   <div>
                        <i class="fa fa-bell-o" aria-hidden="true"></i>
                        <p id="dotPoint"></p>
                        <div class="noti">
                            <span>Notifications</span>
                            <p id="InviteDot"></p>
                            <% if ((session.getAttribute("role")+"").equals("Teacher")) {
                                %><img src="../IMAGES/invite.png" title="Invites" alt="Invites" /><%
                            }%>
                            
                            <div style="clear: both"></div>
                   
                            <aside class="note-inv">
                                <div>
                                    <ul class="noti_conts" id="notified"></ul>
                                </div>
                   
                                <!-- Invites Only -->
                                <% if ((session.getAttribute("role")+"").equals("Teacher")) {
                                    %><div>
                                        <ul class="invites" id="invited">
                                        </ul>
                                      </div><%
                                }%>
                                
                            </aside>
                        </div>
                    </div>
               <div>
               
                   <div class="profile" id="profile_image"></div>
                   <div class="pro">
                       <img src="<%=session.getAttribute("image")%>" alt="profile" />
                       <div>
                           <p><%=session.getAttribute("name")%></p>
                           <p><%=session.getAttribute("role")%></p>
                           <%
                                if((session.getAttribute("login")+"").equals("fb")) {
                                    %><button><span id="fblogout" onclick="logout()"><a class="fb_button fb_button_medium"><span class="fb_button_text">Logout</span></a></span>
                                    </button><%
                                }
                                else{
                                    %><button onclick="signOut()">Logout</button><%
                                }
                            %>
                           <p onclick="GotoSettings()">My Account</p>
                       </div>
                   </div>
               </div>
            </div>
            </div>
            <script id="notification-templete" type="text/x-handlebars-templete">

              {{#each notifications}}           
                 <li tabindex="{{tabIndex}}" id="noti{{tabIndex}}" onclick="notification_function('{{message}}')">
                    <img src="{{image}}"/>
                    <div class="contents" >
                        <p><span>{{name}}</span> comment this</p>
                        <p >{{{message}}}</p>
                    </div>
                 </li>
              {{/each}}
            </script>
            
            <script id="request-details" type="text/x-handlebars-templete">
                {{#each requests}}
                    <li>
                        <img src="{{image}}" alt="profile" />
                        <div class="acpt-dnd">
                            <p><span>{{name}}</span> requests you to join the {{classroom_name}} classroom</p>
                            <p><span onclick="replyRequest('{{requester}}','{{classroom_id}}','Accept')">Accept</span><span onclick="replyRequest('{{requester}}','{{classroom_id}}','Decline')">Decline</span></p>
                        </div>
                    </li>
                {{/each}}
            </script>
            
          <span style="clear:both"></span>
       </header> 
       
       <!--Main div-->
       
       <section class="box_div">
           
           <!-- Catagory of class-->
           
            <main class="all">
                
                <div onclick="getClassroom('1','Maths')">
                    <img src="../IMAGES/tags/maths.jpg" alt="Maths" />
                    <div>Maths</div>
                </div>
                
                <div onclick="getClassroom('2','English')">
                    <img src="../IMAGES/tags/english.jpg" alt="English" />
                    <div>English</div>
                </div>
                
                <div onclick="getClassroom('3','Programming')">
                    <img src="../IMAGES/tags/programming.jpg" alt="Programming" />
                    <div>Programming</div>
                </div>
                
                <div onclick="getClassroom('4','Designing')">
                    <img src="../IMAGES/tags/designing.jpg" alt="Designing" />
                    <div>Designing</div>
                </div>
                
                <div onclick="getClassroom('5','Artificial Inteligence')">
                    <img src="../IMAGES/tags/ai.jpg" alt="AI" />
                    <div>Artificial Inteligence</div>
                </div>
                
                <div onclick="getClassroom('6','Science')">
                    <img src="../IMAGES/tags/science.jpg" alt="Science" style="box-shadow: 0 0 30px rgba(0, 0, 0, 0.08) inset" />
                    <div>Science</div>
                </div>
                
                <div onclick="getClassroom('7','History')">
                    <img src="../IMAGES/tags/history.jpg" alt="History" />
                    <div>History</div>
                </div>
                
                 <div onclick="getClassroom('8','Animation')">
                    <img src="../IMAGES/tags/animation.jpg" alt="Animation" />
                    <div>Animation</div>
                </div>
                
                <div onclick="getClassroom('9','Medical')">
                    <img src="../IMAGES/tags/medical.jpg" alt="Medical" />
                    <div>Medical</div>
                </div>
                
                <div onclick="getClassroom('10','Engineering')">
                    <img src="../IMAGES/tags/engineer.jpg" alt="Engineering" />
                    <div>Engineering</div>
                </div>
                
                
                <div onclick="getClassroom('11','Arts')">
                    <img src="../IMAGES/tags/art.jpg" alt="Arts" />
                    <div>Arts</div>
                </div>
                
                <div onclick="getClassroom('12','Robotics')"> 
                    <img src="../IMAGES/tags/robotics.jpg" alt="Robotics" />
                    <div>Robotics</div>
                </div>
                
                <div onclick="getClassroom('13','Music')">
                    <img src="../IMAGES/tags/music.jpg" alt="Music" />
                    <div>Music</div>
                </div>
                 
                <div onclick="getClassroom('14','Tamil')">
                    <img src="../IMAGES/tags/tamil.jpg" alt="Tamil" />
                    <div>Tamil</div>
                </div>
                
                <div onclick="getClassroom('15','Economics')">
                    <img src="../IMAGES/tags/economic.jpg" alt="Economic" />
                    <div>Economics</div>
                </div>
               
                <p style="clear: both"></p>
                
            </main>
           
           <!-- All classroom -->
           
           <div class="allclass" id="classroom">
               <!--<span class="back"><i class="material-icons">arrow_back</i> Back</span>-->
                
           </div>
           
           <!--  My classroom  -->
           
           <div class="width_div" id="myclassroom">
              
           </div>
       </section>
    </div>
    <!-- Alert -->

    <div class="alert">
           <i class="fa fa-exclamation" aria-hidden="true"></i>
           Some unexpected error has been occured !
    </div>
       
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="176409898115-eoi5sggfanbiq08h1e6soi4vtcm30mgf.apps.googleusercontent.com">
    <div class="g-signin2 a" data-onsuccess="onSignIn"></div>
    <script id="clasroom-templete" type="text/x-handlebars-templete">
        {{#each classrooms}}           
                 <div class='new' id="{{tabIndex}}">
                    <div class='newclass' >
                        <img src='../IMAGES/classs.png' onclick='gotoClassroom({{classroom_id}})'>
                        <p onclick='gotoClassroom({{classroom_id}})'>{{classroom_name}}</p>
                        <p id="join{{tabIndex}}"></p>
                    </div>
                    <img src="../IMAGES/info.png" tabindex="{{tabIndex}}" class="info"> 
                    <div class='des' tabindex='2'> 
                        <p>Created by</p> 
                        <p>{{name}}</p> 
                        <div class='show_des'>{{classroom_description }}</div> 
                    </div> 
                </div>
              {{/each}}
             
    </script>
</body>
</html>