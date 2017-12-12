<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="../CSS/classroom.css" />
    <link href="https://fonts.googleapis.com/css?family=Lato|Oleo+Script|Raleway" rel="stylesheet">
    <link rel="shortcut icon" href="../IMAGES/D-logo.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="../CSS/upload_video.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//apis.google.com/js/client:plusone.js"></script>
    <script src="../JS/cors_upload.js"></script>
    <script src="../JS/upload_video.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../JS/classroom.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <title>DOCTRINA | Classroom page</title>
    
    <script>
    var webSocket;
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
    
    
    //settings page
    function GotoSettings () {
        location.href="/doctrina.settings";
    }
    //notification function
    function notification_function(a){
        $.get("http://basheerahameds-1508.zcodeusers.com/notification_click",{"user_id":"<%=session.getAttribute("user_id")%>","message":a},function(data, status){
            if(data == "ok"){
                webSocket.send("<%=session.getAttribute("user_id")%>");
            }
        });
    }
    
    function permission() {
        
    }
    
    var onloaded = function() {
        socket();
        //permission();
    }
    
    var GO = function() {
        $.get("http://basheerahameds-1508.zcodeusers.com/find",{"content":document.getElementById("search_bar").value},function(data, status){
            
            var finderObj = JSON.parse(data);
            var tempTemplete = document.getElementById("find-templete").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("find-friends").innerHTML= templete(finderObj);
        });
    }
    
    function replyRequest (requester, class_id, status) {
        $.get("http://basheerahameds-1508.zcodeusers.com/ReplyRequest",{"requester":requester,"class_id":class_id,"status":status}, function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
            }
        });
    }
    // Add Assignment Questions
    function AddAssignmentQuestions() {
        var title = document.getElementById("ass_title").value;
        var question = document.getElementsByClassName("ass_question");
        var questionsList = [];
        
        for (i = 0; question[i]; i++) {
            console.log(question[i].innerText);
            questionsList.push(question[i].innerText);
        }
            $.get("/AddAssignment", {"class_id":"<%=session.getAttribute("class_id")%>", "title":title, "questions":JSON.stringify(questionsList)}, function(data, status) {
                if (data == "200") {
                    $(".more").css("display", "none");
                    alert('Success');
                }
            })
    }
    //Test Questions Add
    function AddTestQuestions() {
        var title = document.getElementById("test_title").value;
        var question = document.getElementsByClassName("test_question");
        var questionsList = [];
        
        for (i = 0; question[i]; i++) {
            console.log(question[i].innerText);
            questionsList.push(question[i].innerText);
        }
            $.get("/AddTest", {"class_id":"<%=session.getAttribute("class_id")%>", "title":title, "questions":JSON.stringify(questionsList)}, function(data, status) {
                if (data == "200") {
                    $(".more").css("display", "none");
                    alert('Success');
                }
            })
    }
    //QuizQuestionAdd
    function AddQuizQuestions() {
    var title = document.getElementById("quiz_title").value;
        var output = [];
    
        var a = document.getElementsByClassName("quiz_question");
        var b = document.getElementsByClassName("ans-slct");
        for (i=0; a[i]; i++) {
        	var obj = {};
            obj["question"] = a[i].innerText;
        	obj["answer"] = b[i].value;
            for(j = 1; j < 5 ; j++) {
            	var b = document.getElementsByClassName("option"+j);
        		obj["option"+j] = b[i].value;
        	}
            output.push(obj);
        }console.log(output);
        
        sentAddQuizQuestions(output,title);
    }
    function sentAddQuizQuestions(array, title) {
        $.get("/AddQuizQuestions", {"class_id":"<%=session.getAttribute("class_id")%>","title":title, "questionAnswers":JSON.stringify(array)}, function(data, status) {
            if (data = "200") {
                $(".more").css("display", "none");
                alert("Success...")
            }
        })
    }
    
    function getAssignmentTitles() {
        $.get("/getTitles", {"user_id":"<%=session.getAttribute("user_id")%>","class_id":"<%=session.getAttribute("class_id")%>","type":"assignmenttitles"}, function(data, status) {
            var titleObj = JSON.parse(data);
            var tempTemplete = document.getElementById("Assign_titles").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("asign_hding").innerHTML = templete(titleObj);
        });
    }
    function getTestTitles() {
        $.get("/getTitles", {"user_id":"<%=session.getAttribute("user_id")%>","class_id":"<%=session.getAttribute("class_id")%>","type":"testtitles"}, function(data, status) {
            var titleObj = JSON.parse(data);
            var tempTemplete = document.getElementById("Test_titles").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("test_hdng").innerHTML = templete(titleObj);
        });
    }
    function getQuizTitles() {
        $.get("/getTitles", {"user_id":"<%=session.getAttribute("user_id")%>","class_id":"<%=session.getAttribute("class_id")%>","type":"quiztitles"}, function(data, status) {
            var titleObj = JSON.parse(data);
            var tempTemplete = document.getElementById("quiz_titles").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("quiz_hdng").innerHTML = templete(titleObj);
        });
    }
    //GetAssignmentQuestions
    function getAssignmentQuestions (id) {
        $.get("/getAssignmentQuestions", {"user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>", "type":"assignments", "id":id}, function(data, status) {
            
            var questionsObj = JSON.parse(data);
            var tempTemplete = document.getElementById("assign_question").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("Assign_Questions").innerHTML= templete(questionsObj);
            $(".asign_hding").css("display", "none");
            $(".asign_ol").css("display", "block");
        })    
    }
    //getTestQuestions
    function getTestQuestions (id) {
        $.get("/getTestQuestions", {"user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>", "type":"tests", "id":id}, function(data, status) {
            
            var questionsObj = JSON.parse(data);
            var tempTemplete = document.getElementById("test_question").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("test_questions").innerHTML= templete(questionsObj);
            $(".test_hdng").css("display", "none");
            $(".test_ol").css("display", "block");
        })    
    }
    //getQuizQuestions
    function getQuizQuestions (id) {
        $.get("/getQuizQuestions", {"user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>", "type":"quiz", "id":id}, function(data, status) {
            
            var questionsObj = JSON.parse(data);
            var tempTemplete = document.getElementById("quiz_question").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("quiz_questions").innerHTML= templete(questionsObj);
            $(".quiz_hdng").css("display", "none");
            $(".quiz_ol").css("display", "block");
        })   
    }
    function cls(value){
        if (value =="assignment"){
            $(".asign_hding").css("display", "block");
            $(".asign_ol").css("display", "none"); 
        }
        else if (value =="test") {
            $(".test_hdng").css("display", "block");
            $(".test_ol").css("display", "none");
        }
        else if (value == "quiz") {
             $(".quiz_hdng").css("display", "block");
            $(".quiz_ol").css("display", "none");
        }
        else {
            alert("Something Wrong In close icon");
        }
    }
    
    function getName() {
        $.get("/getNames",  function(data, status) {
            var reportObj = JSON.parse(data);
            var tempTemplete = document.getElementById("reportName-templete").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("reportName").innerHTML= templete(reportObj);
        });
    }
    
    function invite(user_id) {
        $.get("/inviteStudents",{"requester":user_id},  function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
                alert("add successful");
            }
        });
    }
    
    function leaveClassroom() {
        $.get("/leaveClassroom", function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
                location.href = "doctrina.index.do";
            }
        });
    }
    
    </script>
</head>

<body onload="onloaded()">
    <div class="whole">
        <!--   Header   -->
        
       <header>
            <img src="../IMAGES/D.gif" alt="D logo" />
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
        
        <div class="main2_div">

            <!-- classrooms-->

            <nav>
                <p class="now" title="<%=session.getAttribute("class_name")%>" style="letter-spacing:2px"><%=session.getAttribute("class_name")%> </p>
                <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
                    
                                    %>
                    <div class="more">
                        <ul>
                            <li class="invt">Invite</li>
                            <li>Details</li>
                            <li class="cls_dlt">Delete Classroom</li>
                        </ul>
                    </div>
                    <%}
                        else {%>
                             <div class="more">
                                <ul>
                                    <li>Details</li>
                                    <li onclick="leaveClassroom()">Leave Classroom</li>
                                </ul>
                            </div>
                        <%}
                    %>
                <ul class="ul1">
                    <li>Video</li>
                    <li onclick="getQuizTitles()">Quiz</li>
                    <li onclick="getAssignmentTitles()">Assignment</li>
                    <li onclick="getTestTitles()">Test</li>
                    <li>Feeds</li>
                </ul>
                <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
                %>
                <ul>
                    <li id="stdnts" onclick="getName()">Reports</li>
                    <li class="adds">Add <i class="fa fa-plus-square-o" aria-hidden="true"></i><span style="clear:both"></span></li>
                </ul>
                <div class="drop">
                    <ul class="ul2">
                        <li onclick="location.href='/videoUpload'"> Video</li>
                        <li> Quiz Questions</li>
                        <li> Assignments</li>
                        <li> Test Questions</li>
                    </ul>
                </div>
                <%}%>
            </nav>
            <div class="show_div">
                <!--Show the video div-->
                <div class="video_div">
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Html and Css part-1.mp4</p>
                        <div class="opac">
                            <img src="../IMAGES/play.png" alt="play" />
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Css tricks-1.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>JS.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Jquery.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Java.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Python.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Handlebar.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Programming.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                    <div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Java Script.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div><div>
                        <iframe width="180" height="200" src="https://www.youtube.com/embed/Cfd9DOnuF9w" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <p>Design.mp4</p>
                        <div class="lock">
                            <div>
                                <img src="../IMAGES/locked.png" alt="Locked" />
                            </div>
                        </div>
                    </div>
                </div>
                <section>
                    <i class='fa fa-times close' aria-hidden='true' title='Delete'></i>
                    <div class="vdo">
                        <iframe width="100%" height="100%" src="https://www.youtube.com/embed/38Y-DxTukuY" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen></iframe>
                        <div class="links">
                            <p>Reference Links</p>
                            <a href="https://www.w3schools.com/charsets/ref_utf_dingbats.asp" target="_blank">https://www.w3schools.com/charsets/ref_utf_dingbats.asp</a>
                            <a href="http://www.way2tutorial.com/" target="_blank">http://www.way2tutorial.com/</a>
                        </div>
                    </div>
                </section>

                <!-- Assignments titles-->
                <script id="Assign_titles" type="text/x-handlebars-templete">
                    <h1>Assignments</h1>  
                    <ol>
                    {{#each titles}}
                        <li onclick="getAssignmentQuestions({{id}})">{{title}}</li>
                    {{/each}}
                    </ol>
                </script>
                
                <!--Asignment div-->
                        
                <div class="asign_div">
                    <div class="asign_hding" id="asign_hding">
                    </div>
                        
                     <script id="assign_question" type="text/x-handlebars-templete">
                        <ol>
                           <h1 class="h1_hding">Assignment Questions <i class="material-icons" onclick="cls('assignment')">close</i> </h1>
                           {{#each questions}}
                             <li>
                                <div>
                                    <span>{{tabIndex}}</span>
                                    {{question}}</div>
                                    
                                    <p>Answer </p>
                                    <input type="text" class="{{question}}"/>
                             </li> 
                           {{/each}}
                            <button class="submit" type="submit">Submit</button>
                        </ol>
                     </script>   
                    <div class="asign_ol" id="Assign_Questions"></div>
                </div>
                
                <!-- /Asignment div -->
                
                <!---    Quiz Div    -->

                <script id="quiz_titles" type="text/x-handlebars-templete">
                    <h1>Quiz</h1>  
                    <ol>
                    {{#each titles}}
                        <li onclick="getQuizQuestions({{id}})">{{title}}</li>
                    {{/each}}
                    </ol>
                </script>
                
               <script id="quiz_question" type="text/x-handlebars-templete">
                        <ol>
                           <h1 class="h1_hding">Quiz Questions <i class="material-icons" onclick="cls('quiz')">close</i> </h1>
                           {{#each questions}}
                             <li>
                                <div>
                                    <span>{{tabIndex}}</span>
                                    {{question}}</div>
                                    <div>
                                        <div class="option">
                                            <span>
                                                <input type="radio" id="q1_opt1" name="option" />
                                                <label for="q1_opt1">{{option1}}</label>
                                            </span>
                                            <span>
                                                <input type="radio" id="q1_opt2" name="option" />
                                                <label for="q1_opt2">{{option2}}</label>
                                            </span>
                                        </div>
                                        <div class="option">
                                            <span>
                                                <input type="radio" id="q1_opt3" name="option" />
                                                <label for="q1_opt3">{{option3}}</label>
                                            </span>
                                            <span>
                                                <input type="radio" id="q1_opt4" name="option" />
                                                <label for="q1_opt4">{{option4}}</label>
                                            </span>
                                        </div>
                                    </div>
                             </li> 
                           {{/each}}
                            <button class="submit" type="submit">Submit</button>
                        </ol>
                     </script>
                
                <div class="quiz_div">
                    <div class="quiz_hdng" id="quiz_hdng"></div>
                    <div class="quiz_ol" id="quiz_questions" style="display:none"></div>
                </div>

                <!-- Test div -->
                 <script id="Test_titles" type="text/x-handlebars-templete">
                    <h1>Tests</h1>
                    <ol>
                        {{#each titles}}
                            <li onclick="getTestQuestions({{id}})">{{title}}</li>
                        {{/each}}
                    </ol>
                </script>
                
                <!-- Get Questions-->
                
                <script id="test_question" type="text/x-handlebars-templete">
                        <ol>
                           <h1 class="h1_hding">Test Questions <i class="material-icons" onclick="cls('test')">close</i> </h1>
                           {{#each questions}}
                             <li>
                                <div>
                                    <span>{{tabIndex}}</span>
                                    {{question}}</div>
                                    
                                    <p>Answer </p>
                                    <input type="text" class="{{question}}"/>
                             </li> 
                           {{/each}}
                            <button class="submit" type="submit">Submit</button>
                        </ol>
                     </script>   
                <div class="test_div">
                    <div class="test_hdng" id="test_hdng"></div>
                    <div class="test_ol" id="test_questions"></div>
                </div>
            </div>
        </div>

        <!-- Add Videos, Assignments, Quiz, Test -->
        <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
        %>
            <div class="add">
                
                <div class="blur"></div>
    
                <!-- Assignment -->
    
                <div class="add-asmnt">
                    <p>Add Asssignments</p>
                    <input type="text" id="ass_title" placeholder="Enter the Assignment Heading" />
                    <ol class="asmnts">
                        <li>
                            <div contenteditable="true" class="ass_question" placeholder="Enter your question here..."></div>
                            <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i>
                            <p style="clear: both"></p>
                        </li>
                    </ol>
                    <button type="text">+ Add new</button>
                    <div onclick="AddAssignmentQuestions()">Submit</div>
                </div>
    
                <!-- Tests -->
    
                <div class="add-test">
                    <p>Add Test Questions</p>
                    <input type="text" id="test_title" placeholder="Enter the Test Heading" />
                    <ol class="tests">
                        <li>
                            <div contenteditable="true" class="test_question" placeholder="Enter your question here..."></div>
                            <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i>
                            <p style="clear: both"></p>
                        </li>
                    </ol>
                    <button type="text">+ Add new</button>
                    <div onclick="AddTestQuestions()">Submit</div>
                </div>
    
                <!-- Quiz -->
    
                <!-- Quiz -->

            <div class="add-quiz">
                <p>Add Quiz Questions</p>
                <input class="srch_title" id="quiz_title" type="text" style="margin-bottom:20px" placeholder="Enter the Quiz title" />
                <ol class="quizzes">
                    <li>
                        <div contenteditable="true" class="quiz_question" placeholder="Enter your question here..."></div>
                        <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i>
                        <p style="clear: both"></p>
                        <section class="options">
                            <span>(a)</span>
                            <input type="text" class="option1"/>
                            <span>(b)</span>
                            <input type="text" class="option2"/>
                            <span>(c)</span>
                            <input type="text" class="option3"/>
                            <span>(d)</span>
                            <input type="text" class="option4"/>
                        </section>
                        <span>Answer : </span>
                        <select name="qus1" id="qus1" class="ans-slct">
                            <option>a</option>
                            <option>b</option>
                            <option>c</option>
                            <option>d</option>
                        </select>
                    </li>
                </ol>
                <button type="text">+ Add new</button>
                <div onclick="AddQuizQuestions()">Submit</div>
            </div>
    
                <!-- Reports -->
    
                <div class="report">
                    <p>Student Names</p>
                    <ul id="reportName">
                        
                    </ul>
                </div>
                <script id = "reportName-templete" type="x-handlebars-templete">
                    {{#each Name}}
                        <li onclick="getReportDetails('{{user_id}}')">
                            <img src="{{image}}" alt="Student" /> 
                            <span>{{name}}</span>
                        </li>
                    {{/each}}
                </script>
                <!-- Invite People -->
    
                <div class="invt-ppl">
                    <p>Invite People</p>
                    <input type="text" class="invt-srch" placeholder="Search" id="search_bar" onkeyup="GO()" />
                    <i class="fa fa-search" aria-hidden="true"></i>
                    <ul id = "find-friends">
                        
                    </ul>
                    <script id="find-templete" type="text/x-handlebars-templete">
                        {{#each finders}}
                            <li>
                                <img src="{{image}}" alt="Image" />
                                <span style="margin-left:25px">{{name}}</span><br>
                                <span style="margin-left:75px;font-size:12px">{{role}}</span>
                                <button onclick="invite('{{user_id}}')">Invite</button>
                                <p style="clear: both"></p>
                            </li>
                        {{/each}}
                    </script>
                </div>
    
            </div>
            <div class="dlt_popup">
                <p>Are you sure, to delete this? </p>
                <button type="submit">Delete</button>
                <button type="submit">Cancel</button>
            </div>
            <%}%>
    </div>
     
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="176409898115-eoi5sggfanbiq08h1e6soi4vtcm30mgf.apps.googleusercontent.com">
    <div class="g-signin2" data-onsuccess="onSignIn" style="display:none"></div>
    </body>

</html>