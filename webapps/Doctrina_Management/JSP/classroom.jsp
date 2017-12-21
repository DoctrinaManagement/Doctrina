<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="../CSS/classroom.css" />
    <link href="https://fonts.googleapis.com/css?family=Lato|Oleo+Script|Raleway" rel="stylesheet">
    <link rel="shortcut icon" href="../IMAGES/D-logo.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//apis.google.com/js/client:plusone.js"></script>
    <script src="../JS/cors_upload.js"></script>
    <script src="../JS/upload_video.js"></script>
    <script src="./JS/player.js"></script>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../JS/classroom.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <title>DOCTRINA | Classroom page</title>
    
    <script>
    var webSocket;
    var postId;
    var commentId;
    var liketype;
    var likeId;
    var current_id;
    document.addEventListener("visibilitychange", function() {
      location.reload();
    });
    var class_id = "<%=session.getAttribute("class_id")%>";

         function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            $.get("http://basheerahameds-1508.zcodeusers.com/logout", function(data, status) {
                
            });
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
                $.get("http://basheerahameds-1508.zcodeusers.com/logout", function(data, status) {
                
                 });
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
        $.get("http://basheerahameds-1508.zcodeusers.com/permissionclassroom",function(data, status){
            if(data =="ok") {
                location.href = "http://basheerahameds-1508.zcodeusers.com/landingpage";
            }
            if(data =="done") {
                location.href = "http://basheerahameds-1508.zcodeusers.com/doctrina.index.do";
            }
        });
    
    }
    
    var onloaded = function() {
        permission();
        socket();
        setVarId("<%=session.getAttribute("user_id")%>","<%=session.getAttribute("class_id")%>");
        getVideos();
         if(document.cookie.indexOf("Name") == -1) {
            location.href = "/landingpage"
        }
        
    }
    
    function getVideos () {
        $.get("/getVideos", {"user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>"}, function(data, status){
            var videoObj = JSON.parse(data);
            var tempTemplete = document.getElementById("video-templete").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            document.getElementById("video-render").innerHTML= templete(videoObj);
            finished(videoObj.finished);
        })    
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
    var titleReg = /^[a-zA-Z0-9]{2,50}\w$/;
    var questionReg = /^[a-zA-Z0-9][\w\W]{4,255}$/;
    // Add Assignment Questions
    function AddAssignmentQuestions() {
        var title = document.getElementById("ass_title").value;
        
        if (titleReg.test(title)){
            var question = document.getElementsByClassName("ass_question");
            var questionsList = [];
            var checkTest = false;
            
            for (i = 0; question[i]; i++) {
                if (questionReg.test(question[i].innerText)) {
                    questionsList.push(question[i].innerText);
                    $("#ADDASSIGNMENT").html("<div>Submit</div>");
                    checkTest = true;
                }
                else {
                    checkTest = false;
                    break;
                }
            }
            if (checkTest == true){
                $.get("/AddAssignment", {"class_id":"<%=session.getAttribute("class_id")%>", "title":title, "questions":JSON.stringify(questionsList)}, function(data, status) {
                
                    if (data == "200") {
                        $(".blur").trigger("click");
                        $("#ADDASSIGNMENT").html("<div onclick='AddAssignmentQuestions()'>Submit</div>")
                        $("#ass-add-templete").html("<p>Add Asssignments</p><input type='text' class='srch_title' id='ass_title' placeholder='Enter the Assignment title' /><ol class='asmnts'><li><div contenteditable='true' class='ass_question' placeholder='Enter your question here...'></div><i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i><p style='clear: both'></p></li></ol><button type='text' onclick='addnewAssignment()'>+ Add new</button><span id = 'ADDASSIGNMENT'><div onclick='AddAssignmentQuestions()'>Submit</div></span>");
                        error("success");
                    }
                    else if (data == "400"){
                        error("danger");
                    }
                }) 
            }
            else {
                error("danger");
            }
        }
        else {
            error("danger");
        }
    }
    //Test Questions Add
    function AddTestQuestions() {
        var title = document.getElementById("test_title").value;
        var chckAss = false;
        
        if (titleReg.test(title)) {
            var question = document.getElementsByClassName("test_question");
            var questionsList = [];
            for (i = 0; question[i]; i++) {
                if (questionReg.test(question[i].innerText)) {
                    questionsList.push(question[i].innerText);
                    $("#ADDTEST").html("<div>Submit</div>");
                    checkAss = true;
                }
                else {
                    checkAss = false;
                    break;
                }
            }
            if (checkAss == true) {
                $.get("/AddTest", {"class_id":"<%=session.getAttribute("class_id")%>", "title":title, "questions":JSON.stringify(questionsList)}, function(data, status) {
                if (data == "200") {
                        $(".blur").trigger("click");
                        $("#ADDTEST").html("<div onclick=AddTestQuestions()>Submit</div>");
                        $("#test-add-templete").html("<p>Add Test Questions</p><input type='text' class='srch_title' id='test_title' placeholder='Enter the Test title' /><ol class='tests'><li><div contenteditable='true' class='test_question' placeholder='Enter your question here...'></div><i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i><p style='clear: both'></p></li></ol><button type='text' onclick='addnewtest()'>+ Add new</button><span id='ADDTEST'><div onclick='AddTestQuestions()'>Submit</div></span>");
                        error("success");
                    }
                    else if (data == "400"){
                        error("danger");
                    }
                })
            }
            else {
            error("danger");
            }
        }
        else {
            error("danger");
        }
    }
    
    //QuizQuestionAdd
    function AddQuizQuestions() {
    var title = document.getElementById("quiz_title").value;
    var check = false;
    
        if(titleReg.test(title)) {
            var output = [];
            
            var a = document.getElementsByClassName("quiz_question");
            var answer = document.getElementsByClassName("ans-slct");
            for (i=0; a[i]; i++) {
            	var obj = {};
            	    obj["question"] = a[i].innerText;
            	    obj["answer"] = answer[i].value;
            	    
            	    for(j = 1; j < 5 ; j++) {
                    	var b = document.getElementsByClassName("option"+j);
                		obj["option"+j] = b[i].value;
                	}
                    output.push(obj);
            }
            // Regex Checking
            for (i = 0; i < output.length; i++ ){
                if (questionReg.test(output[i].question)) {
                    for (j = 1; j <= 5; j ++) {
                        if (/^[a-zA-Z0-9][\w\W]{0,25}[a-zA-Z0-9]$/.test(output[i]["option"+j]) && /^[a-d]{1}$/.test(output[i].answer)){
                            check = true;
                        }
                        else {
                            check = false;
                            break;
                        }
                    }
                } else {
                        check = false;
                        break;
                }
            }
            
            if (check == true) {
                $("#ADDQUIZ").html("<div>Submit</div>");
                sentAddQuizQuestions(output,title);
            }
            else {
                error("info");
            }
        }
        else {
            error("danger");
        }
    }
    function sentAddQuizQuestions(array, title) {
    
        $.get("/AddQuizQuestions", {"class_id":"<%=session.getAttribute("class_id")%>","title":title, "questionAnswers":JSON.stringify(array)}, function(data, status) {
            if (data = "200") {
                $(".blur").trigger("click");
                $("#ADDQUIZ").html("<div onclick='AddQuizQuestions()'>Submit</div>");
                $("#quiz-add-templete").html("<p>Add Quiz Questions</p><input class='srch_title' id='quiz_title' type='text' placeholder='Enter the Quiz title' /><ol class='quizzes'><li><div contenteditable='true' class='quiz_question' placeholder='Enter your question here...'></div><i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i><p style='clear: both'></p><section class='options'><span>(a)</span><input type='text' class='option1'/><span>(b)</span><input type='text' class='option2'/><span>(c)</span><input type='text' class='option3'/><span>(d)</span><input type='text' class='option4'/></section><span>Answer : </span><select name='qus1' id='qus1' class='ans-slct'><option value='a'>a</option><option value='b'>b</option><option value='c'>c</option><option value='d'>d</option></select></li></ol><button type='text' onclick='addnewQuiz()'>+ Add new</button><span id = 'ADDQUIZ'><div onclick='AddQuizQuestions()'>Submit</div></span>");
                error("success");
            }
            else if (data == "400"){
                error("danger");
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
        $.get("/getQuizQuestions", {"user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>", "type":"quizs", "id":id}, function(data, status) {
            
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
            error("danger");
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
                error("success");
            }
        });
    }
    
    
    var x = setInterval(function() {
            var checking =  "<%=session.getAttribute("class_id")%>";
            if(class_id != checking) {
                location.reload();
            }
        }, 1000);
    
    function leaveClassroom() {
        $.get("/leaveClassroom", function(data, status) {
            if(data == "ok"){
                webSocket.send("all");
                location.href = "doctrina.index.do";
            }
        });
    }
    var answerReg = /^[a-zA-Z0-9][\w\W]{3,255}\w$/;
    // Answers Get
    function SendAnswersFromAssignments() {
    
        var ass_answers = [];
        var ass_takeValues = document.getElementsByClassName("AssignmentAnswers");
        var ass_check = false;
        
        for (i = 0; ass_takeValues[i]; i++) {
        var ass_obj = {};
            if (answerReg.test(ass_takeValues[i].value)){
                ass_obj["id"] = ass_takeValues[i].id;
                ass_obj["answer"] = ass_takeValues[i].value;
                ass_answers.push(ass_obj);
                ass_check = true;
            }
            else{
                ass_check = false;
                break;   
            }
        }
        if ( ass_check == true ){
        $("#SENDASS").html("<button class='submit' type='submit'>Submit</button>");
            $.post("/SendAnswers", {"answers":JSON.stringify(ass_answers), "type":"assignmentanswer"}, function(data, status) {
               
                if (data == "200") {
                    $("#SENDASS").html("<button class='submit' type='submit' onclick='SendAnswersFromAssignments()'>Submit</button>");
                    cls("assignment");
                }
            });
        }
        else {
            error("danger");
        }
    }
    
    function SendAnswersFromTests(){
        
        var test_answers = [];
        var test_takeValues = document.getElementsByClassName("TestAnswers");
        var test_check = false;
        
        for (i = 0; test_takeValues[i]; i++) {
        var test_obj = {};
            if (answerReg.test(test_takeValues[i].value)) {
                test_obj["id"] = test_takeValues[i].id;
                    test_obj["answer"] = test_takeValues[i].value;
                test_answers.push(test_obj);
                test_check = true;
            }
            else {
                test_check = false;
                break;
            }
        }
        if (test_check == true) {
        $("#SENDTEST").html("<button class='submit' type='submit' >Submit</button>");
            $.post("/SendAnswers", {"answers":JSON.stringify(test_answers), "type":"testanswer"}, function(data, status) {
                if (data == "200") {
                    $("#SENDTEST").html("<button class='submit' type='submit' onclick='SendAnswersFromTests()'>Submit</button>");
                    cls("test");
                }
            });
        }
        else {
            error("danger");
        }
    }
    
    function SendAnswersFromQuizes(){
        
        var quiz_answers = [];
        
        var b = document.getElementsByClassName("quiz_quest"); 
        for (j = 0 ;b[j] ;j++) {
        		var quiz_obj = {};
                quiz_obj["id"] = b[j].id;
            var a = document.getElementsByClassName("answer"+(j+1));
    		for (i = 0;  a[i]; i++) {
        		
              if (a[i].checked) {
                  quiz_obj["answer"] = a[i].value;
              }
       	 	}
            quiz_answers.push(quiz_obj);
    	}
        $("#SENDQUIZZ").html("<button class='submit' type='submit'>Submit</button>");
    	     $.post("/SendAnswers", {"answers":JSON.stringify(quiz_answers), "type":"quizanswer"}, function(data, status) {
            
            $("#SENDQUIZZ").html("<button class='submit' type='submit' onclick='SendAnswersFromQuizes()'>Submit</button>");
            var values = JSON.parse(data);
            alert(values.mark);
            cls("quiz");
        });
    }
    
    //  ______________feeds________________
    // scroll bar reached bottom and get the 
    function bottomReached() {
        
        if($(".feed")[0].scrollHeight === ( $(".feed").scrollTop() + $(".feed")[0].offsetHeight) || ($(".feed")[0].scrollHeight - ( $(".feed").scrollTop() + $(".feed")[0].offsetHeight)) < 50) {
            $.get("/getfeeds",{"type" : "next" },  function(data, status) {
                    var postObj = JSON.parse(data);
                    postObj = rearrange(postObj);
                    document.getElementById("posts").innerHTML += html(postObj);
            });
        }
        
    }
    
    // get feeds details initial
    
    function getFeeds() {
        $.get("/getfeeds",{"type" : "initial" },  function(data, status) {
            var postObj = JSON.parse(data);
            
            postObj = rearrange(postObj);
            document.getElementById("posts").innerHTML = html(postObj);
        });
    }
    
    function html(postObj) {
        var html = "";
        for(i = 0; i < postObj.posts.length; i++) {
            
            var tempTemplete = document.getElementById("post-templete").innerHTML;
            var templete  = Handlebars.compile(tempTemplete);
            html = html + templete(postObj.posts[i]);
        }
        return html;
    }
    
    function rearrange(postObj) {
        for(i = 0; i < postObj.posts.length; i++) {
            
            //postObj.posts[i].likers = JSON.parse(postObj.posts[i].likers);
            postObj.posts[i]["likelength"] = postObj.posts[i].likers.length;
            postObj.posts[i]["commentlength"] = postObj.posts[i].comments.length;
            if (postObj.posts[i].name == "<%=session.getAttribute("name")%>") {
                postObj.posts[i].name = "You";
            }
            postObj.posts[i]["html"] = "<i class='fa fa-heart-o' onclick=\"addLike(\'posts\',\'"+postObj.posts[i].id+"\')\" aria-hidden='true'></i><span onclick=\"addLike(\'posts\',\'"+postObj.posts[i].id+"\')\">Like</span>"
            if(postObj.posts[i].likers.indexOf("<%=session.getAttribute("name")%>") != -1) {
                postObj.posts[i]["html"] = "<i class='fa fa-heart' onclick=\"removeLike(\'posts\',\'"+postObj.posts[i].id+"\')\" aria-hidden='true'></i><span onclick=\"removeLike(\'posts\',\'"+postObj.posts[i].id+"\')\" >Like</span>";
            }
            for(j = 0; j < postObj.posts[i].comments.length; j++) {
                
                //postObj.posts[i].comments[j].likers = JSON.parse(postObj.posts[i].comments[j].likers);
                postObj.posts[i].comments[j]["likelength"] = postObj.posts[i].comments[j].likers.length;
                postObj.posts[i].comments[j]["commentlength"] = postObj.posts[i].comments[j].replies.length;
                if (postObj.posts[i].comments[j].name == "<%=session.getAttribute("name")%>") {
                    postObj.posts[i].comments[j].name = "You";
                }
                postObj.posts[i].comments[j]["html"] = "<i class='fa fa-heart-o' onclick=\"addLike(\'comments\',\'"+postObj.posts[i].comments[j].id+"\')\" aria-hidden='true'></i><span onclick=\"addLike(\'comments\',\'"+postObj.posts[i].comments[j].id+"\')\">Like</span>"
                if(postObj.posts[i].comments[j].likers.indexOf("<%=session.getAttribute("name")%>") != -1) {
                    postObj.posts[i].comments[j]["html"] = "<i class='fa fa-heart' onclick=\"removeLike(\'comments\',\'"+postObj.posts[i].comments[j].id+"\')\" aria-hidden='true'></i><span onclick=\"removeLike(\'comments\',\'"+postObj.posts[i].comments[j].id+"\')\">Like</span>";
                }
                
                for(k = 0; k < postObj.posts[i].comments[j].replies.length;  k++) {
                    
                    postObj.posts[i].comments[j].replies[k]["likelength"] = postObj.posts[i].comments[j].replies[k].likers.length;
                    
                    if (postObj.posts[i].comments[j].replies[k].name == "<%=session.getAttribute("name")%>") {
                        postObj.posts[i].comments[j].replies[k].name = "You";
                    }
                    
                    postObj.posts[i].comments[j].replies[k]["html"] = "<i class='fa fa-heart-o' onclick=\"addLike(\'replies\',\'"+postObj.posts[i].comments[j].replies[k].id+"\')\" aria-hidden='true'></i><span onclick=\"addLike(\'replies\',\'"+postObj.posts[i].comments[j].replies[k].id+"\')\" >Like</span>"
                    if(postObj.posts[i].comments[j].replies[k].likers.indexOf("<%=session.getAttribute("name")%>") != -1) {
                        postObj.posts[i].comments[j].replies[k]["html"] = "<i class='fa fa-heart' onclick=\"removeLike(\'replies\',\'"+postObj.posts[i].comments[j].replies[k].id+"\')\" aria-hidden='true'></i><span onclick=\"removeLike(\'replies\',\'"+postObj.posts[i].comments[j].replies[k].id+"\')\">Like</span>";
                    }
                }
            }
        }
        return postObj;
    }
    var messReg = /^\w[\w\W]{0,500}$/;
    
    function postbtnClick() {
        var message = document.getElementById("post_msg").value;
        $(".comment").remove();
            if(messReg.test(message)){
                $(".post_btn").attr("disabled","true");
                $.get("/addfeeds",{"user_id":"<%=session.getAttribute("user_id")%>","class_id": "<%=session.getAttribute("class_id")%>","message":message,"type" : "posts" },  function(data, status) {
                    document.getElementById("post_msg").value = "";
                    var postObj = JSON.parse(data);
                    postObj = rearrange(postObj);
                    $("#posts").prepend(html(postObj));
                    $("#post_btn").html("<button class='post_btn' onclick='postbtnClick()'>POST</button>");
                    webSocket.send("all");
                });
            }
            else {
                error("danger");
            }
    }
    
    function comment(id) {
        $(".comment").remove();
        $("#"+id).parent().parent().parent().append("<div id='c' class='comment'><textarea id='commentMsg' ></textarea><button class='post_btn' onclick='addcomment(\""+id+"\")'>POST</button><div style='clear:both'></div></div>");
        
    }
    function addcomment(post_id) {
        postId = post_id
        post_id = post_id.substring(3);
        var message = document.getElementById("commentMsg").value;
        if (messReg.test(message)){
            $(".post_btn").attr("disabled","true");
            $.get("/addfeeds",{"id":post_id,"user_id":"<%=session.getAttribute("user_id")%>","class_id": "<%=session.getAttribute("class_id")%>","message":message,"type" : "comments" },  function(data, status) {
                var commentObj = JSON.parse(data);
                var tempTemplete = document.getElementById("comment-templete").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                $(".comment").remove();
                var com = "post_comment"+postId.substring(3);
                $("#"+postId).parent().parent().parent().append(templete(commentObj));
                document.getElementById(com).innerText = (Number(document.getElementById(com).innerText)) + 1;
                $("#post_btn").html("<button class='post_btn' onclick='postbtnClick()'>POST</button>");
                webSocket.send("all");
            });
        }
        else {
            error("danger");
        }
    }
    
    
    function reply(id) {
        $(".comment").remove();
        $("#"+id).parent().parent().parent().append("<div id='c' class='reply comment'><textarea id='replyMsg' ></textarea><button class='post_btn' onclick='addreply(\""+id+"\")'>POST</button><div style='clear:both'></div></div>");
        
    }
    
    function addreply(com_id) {
        commentId = com_id
        com_id = com_id.substring(3);
        var message = document.getElementById("replyMsg").value;
        if (messReg.test(message)) {
            $(".post_btn").attr("disabled","true");
            $.get("/addfeeds",{"id":com_id,"user_id":"<%=session.getAttribute("user_id")%>","class_id": "<%=session.getAttribute("class_id")%>","message":message,"type" : "replies" },  function(data, status) {
                $("#post_btn").html("<button class='post_btn' onclick='postbtnClick()'>POST</button>");
                var replyObj = JSON.parse(data);
                var tempTemplete = document.getElementById("reply-templete").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                $(".comment").remove();
                var com = "comment_comment"+commentId.substring(3);
                $("#"+commentId).parent().parent().parent().append(templete(replyObj));
                document.getElementById(com).innerText = (Number(document.getElementById(com).innerText)) + 1;
                
                webSocket.send("all");
            });
        }
        else {
            error("danger");
        }
    }
    //ReportDetails
    function getReportDetails(user_id) {
        $.get("/getreportdetails", {"student_id":user_id}, function(data, status) {
            if(data == "200") {
                location.href="/reports";
            }
        })
    }   
    function addLike(table, id) {
        liketype = table;
        likeId = id;
        document.getElementById(liketype+likeId).innerHTML = "<i class='fa fa-heart-o'  aria-hidden='true'></i><span>Like</span>";
        $.get("/insertlike", {"type":table, "user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","id":id}, function(data, status) {
            if(data == "ok") {
                document.getElementById(liketype+likeId).innerHTML = "<i class='fa fa-heart' onclick=\"removeLike(\'"+liketype+"\',\'"+likeId+"\')\" aria-hidden='true'></i><span onclick=\"removeLike(\'"+liketype+"\',\'"+likeId+"\')\">Like</span>";
                document.getElementById(liketype+"_like"+likeId).innerText = (Number(document.getElementById(liketype+"_like"+likeId).innerText) + 1);
                var title = $("#"+liketype+"_"+likeId).attr("title");
                if(title == "") {
                    $("#"+liketype+"_"+likeId).attr("title","<%=session.getAttribute("name")%>");
                } else {
                    $("#"+liketype+"_"+likeId).attr("title",title+",<%=session.getAttribute("name")%>");
                }
                
            }
        })
    }
    
    function removeLike(table, id) {
        liketype = table;
        likeId = id;
        document.getElementById(liketype+likeId).innerHTML = "<i class='fa fa-heart' aria-hidden='true'></i><span>Like</span>";
        $.get("/removelike", {"type":table, "user_id":"<%=session.getAttribute("user_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","id":id}, function(data, status) {
            if(data == "ok") {
                document.getElementById(liketype+likeId).innerHTML = "<i class='fa fa-heart-o' onclick=\"addLike(\'"+liketype+"\',\'"+likeId+"\')\" aria-hidden='true'></i><span onclick=\"addLike(\'"+liketype+"\',\'"+likeId+"\')\">Like</span>";
                document.getElementById(liketype+"_like"+likeId).innerText = (Number(document.getElementById(liketype+"_like"+likeId).innerText) - 1);
                var title = $("#"+liketype+"_"+likeId).attr("title");
                var titleArray = title.split(",");
                titleArray.pop("<%=session.getAttribute("name")%>");
                $("#"+liketype+"_"+likeId).attr("title",titleArray);
            }
        })
    }
    
    function addnewQuiz() {//alert();
         $(".quizzes").append("<li> <div contenteditable='true' class='quiz_question' placeholder='Enter your quiz question here...'></div> <i class='fa fa-times' onclick='del_input(this,1)' aria-hidden='true'></i> <p style='clear: both'></p> <section class='options' > <span>(a)</span> <input type='text' class='option1'/> <span>(b)</span> <input type='text' class='option2'/> <span>(c)</span> <input type='text' class='option3'/> <span>(d)</span> <input type='text' class='option4'/> </section> <span>Answer : </span> <select name='qus1' id='qus1' class='ans-slct'> <option value='a' >a</option> <option value='b' >b</option> <option value='c' >c</option> <option value='d' >d</option> </select> </li>");
    };
    
    function addnewtest() {
        $(".tests").append("<li> <div contenteditable='true' class='test_question' placeholder='Enter your question here...'></div> <i class='fa fa-times' onclick='del_input(this,2)' aria-hidden='true'></i> <p style='clear: both'></p> </li>");
    }
    
    function addnewAssignment() {
        $(".asmnts").append("<li> <div contenteditable='true' class='ass_question' placeholder='Enter your question here...'></div> <i class='fa fa-times' onclick='del_input(this,2)' aria-hidden='true'></i> <p style='clear: both'></p> </li>");
    }
    
    function getVideoId(id) {
        current_id = id;
        $.get("/getvideoid", {"class_id":"<%=session.getAttribute("class_id")%>", "user_id":"<%=session.getAttribute("user_id")%>", "videoId" : id }, function(data, status) {
           var values = JSON.parse(data);
            PlayerStart(values.video_id, values.time, current_id);
            
        })
    }
    function finished(values) {
        for (i = 0; i < values.length;  i++) {
            document.getElementById("lock_"+values[i]).innerHTML = "<img src='../IMAGES/play.png' alt='play' />";
            document.getElementById("lock_"+values[i]).setAttribute("onclick", "getVideoId(\""+values[i]+"\")");
        }
    }
    
    function closeVideo() {
        stopVideo();
        location.reload();
    }
    
    function error (type) {
        if (type == "info") {
            $(".alert").html('<i class="fa fa-exclamation" aria-hidden="true"></i>Please Check the values !');
            $(".alert").css("color", "rgb(36, 146, 255)"); 
            $(".alert>i").css("background", "rgb(36, 146, 255)");
        }
        else if (type == "danger"){
            $(".alert").html('<i class="fa fa-exclamation" aria-hidden="true"></i>Some unexpected error has been occured !');
            $(".alert").css({"color":"rgb(243, 69, 65)", "width":"530px","margin:left":"-205px"}); 
            $(".alert>i").css("background", "rgb(243, 69, 65);");
        }
         else if (type == "success"){
            $(".alert").html('<i class="fa fa-check" aria-hidden="true"></i> Added Successfully !');
            $(".alert").css({"color":"rgb(56, 184, 124)", "width":"300px", "margin-left":"-185px"}); 
            $(".alert>i").css({"background":"rgb(56, 184, 124)","padding":"9px 10px"});
        }
        $(".alert").css("top","0");
        setTimeout (function(){
        $(".alert").css("top","-75px");
        },2000);
    }
    </script>
</head>

<body onload="onloaded()">
    <div class="whole">
        <!--   Header   -->
        
        <!-- Alert -->
            <div class="alert"></div>
        <!-- /Alert -->
        
        
       <header>
            <img src="../IMAGES/D.gif" alt="D logo" onclick="location.href='/doctrina.index.do'"/>
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
            
             <script id="video-templete" type="text/x-handlebars-templete">
                {{#each videos}}
                    <div>
                    <p>{{title}}</p>
                        <div class="opac" id = 'lock_{{video_id}}' >
                            <img src="../IMAGES/locked.png" alt="lock" />
                        </div>
                    </div>
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
                            <li class="cls_dlt">Delete Classroom</li>
                        </ul>
                    </div>
                    <%}
                        else {%>
                             <div class="more">
                                <ul>
                                    <li onclick="getReportDetails('<%=session.getAttribute("user_id")%>')">Details</li>
                                    <li onclick="leaveClassroom()">Leave Classroom</li>
                                </ul>
                            </div>
                        <%}
                    %>
                <ul class="ul1">
                    <li onclick="getVideos()">Video</li>
                    <li onclick="getQuizTitles()">Quiz</li>
                    <li onclick="getAssignmentTitles()">Assignment</li>
                    <li onclick="getTestTitles()">Test</li>
                    <li onclick="getFeeds()">Feeds</li>
                </ul>
                <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
                %>
                <ul>
                    <li id="stdnts" onclick="getName()">Reports</li>
                    <li class="adds">Add <i class="fa fa-plus-square-o" aria-hidden="true"></i><span style="clear:both"></span></li>
                </ul>
                <div class="drop">
                    <ul class="ul2">
                        <li> Video</li>
                        <li> Quiz Questions</li>
                        <li> Assignments</li>
                        <li> Test Questions</li>
                    </ul>
                </div>
                <%}%>
            </nav>
            <div class="show_div">
                <!--Show the video div-->
                <div class="video_div" id = 'video-render'>
                    
                </div>
                
                <section>
                    <i class='fa fa-times close' aria-hidden='true' title='Delete' onclick="closeVideo()"></i>
                    <div id="player" style="display:none"></div>
                </section>

                <!-- Assignments titles-->
                <script id="Assign_titles" type="text/x-handlebars-templete">
                    <h1>Assignment Titles</h1>  
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
                                    <input type="text" id="{{question_id}}" class="AssignmentAnswers"/>
                             </li> 
                           {{/each}}
                            <span id = 'SENDASS'><button class="submit" type="submit" onclick="SendAnswersFromAssignments()">Submit</button></span>
                        </ol>
                     </script>   
                    <div class="asign_ol" id="Assign_Questions"></div>
                </div>
                
                <!-- /Asignment div -->
                
                <!-- Quiz Div -->

                <script id="quiz_titles" type="text/x-handlebars-templete">
                    <h1>Quiz Titles</h1>  
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
                                <div class="quiz_quest" id="{{question_id}}">
                                    <span>{{tabIndex}}</span>
                                    {{question}}</div>
                                    <div>
                                        <div class="option">
                                            <span>
                                                <input type="radio" class="answer{{tabIndex}}" id="q{{tabIndex}}_opt1" name="option{{tabIndex}}" value="{{option1}}"/>
                                                <label for="q{{tabIndex}}_opt1">{{option1}}</label>
                                            </span>
                                            <span>
                                                <input type="radio" class="answer{{tabIndex}}" id="q{{tabIndex}}_opt2" name="option{{tabIndex}}" value="{{option2}}"/>
                                                <label for="q{{tabIndex}}_opt2">{{option2}}</label>
                                            </span>
                                        </div>
                                        <div class="option">
                                            <span>
                                                <input type="radio" class="answer{{tabIndex}}" id="q{{tabIndex}}_opt3" name="option{{tabIndex}}" value="{{option3}}"/>
                                                <label for="q{{tabIndex}}_opt3">{{option3}}</label>
                                            </span>
                                            <span>
                                                <input type="radio" class="answer{{tabIndex}}" id="q{{tabIndex}}_opt4" name="option{{tabIndex}}" value="{{option4}}"/>
                                                <label for="q{{tabIndex}}_opt4">{{option4}}</label>
                                            </span>
                                        </div>
                                    </div>
                             </li> 
                           {{/each}}
                            <span id = 'SENDQUIZZ'><button class="submit" type="submit" onclick="SendAnswersFromQuizes()">Submit</button></span>
                        </ol>
                     </script>
                
                <div class="quiz_div">
                    <div class="quiz_hdng" id="quiz_hdng"></div>
                    <div class="quiz_ol" id="quiz_questions" style="display:none"></div>
                </div>
                
                <!-- /Quiz Div -->
                
                <!--- Feeds Div --->
                
                <div class="feed" onscroll="bottomReached()">
                  <div class="feeds">
                     <textarea id="post_msg" class="msg" placeholder="Ask Your Doubts"></textarea>
                     <span id="post_btn"><button class="post_btn" onclick="postbtnClick()">POST</button></span>  
                     <div style="clear:both"></div>
                  </div>  
                  <div class="psts" id = "posts">
                  
                  </div>
                </div>
                
                <!-- Test div -->
                
                 <script id="Test_titles" type="text/x-handlebars-templete">
                    <h1>Test Titles</h1>
                    <ol>
                        {{#each titles}}
                            <li onclick="getTestQuestions({{id}})">{{title}}</li>
                        {{/each}}
                    </ol>
                </script>
                
                <script id="test_question" type="text/x-handlebars-templete">
                        <ol>
                           <h1 class="h1_hding">Test Questions <i class="material-icons" onclick="cls('test')">close</i> </h1>
                           {{#each questions}}
                             <li>
                                <div>
                                    <span>{{tabIndex}}</span>
                                    {{question}}</div>
                                    
                                    <p>Answer </p>
                                    <input type="text" id="{{question_id}}" class="TestAnswers"/>
                             </li> 
                           {{/each}}
                            <span id="SENDTEST"><button class="submit" type="submit" onclick="SendAnswersFromTests()">Submit</button></span>
                        </ol>
                     </script>   
                <div class="test_div">
                    <div class="test_hdng" id="test_hdng"></div>
                    <div class="test_ol" id="test_questions"></div>
                </div>
            </div>
        </div>
        
        <!-- /Test Div -->
                
        <!-- Add Videos, Assignments, Quiz, Test -->
        <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
        %>
            <div class="add">
                
                <div class="blur"></div>
    
                <!-- Assignment -->
                
                 <!-- Video -->
                 
                <div class="add-video">
                
                    <p>Add Videos</p>
                    <!--<label for="upload" id="">Upload Video</label>-->
                     <span id="signinButton" style="display:none !important" class="pre-sign-in">
                          <span
                            class="g-signin"
                            data-callback="signinCallback"
                            data-clientid="181102062803-fdebk3j2fosglc2nudniiq9ea1rab74j.apps.googleusercontent.com"
                            data-cookiepolicy="single_host_origin"
                            data-scope="https://www.googleapis.com/auth/youtube.upload https://www.googleapis.com/auth/youtube">
                          </span>
                    </span>
                    
                    <input type="file" id="upload" style="margin:auto" single  accept="video/*"/>
                    <input type="text" id="title" placeholder="Title of this video" class="vdo-name" />
                    <div id="button">Submit</div>
                   
                    <div style="margin: 0px;background: white;">
                      <div class="during-upload">
                        <p><span id="percent-transferred"></span>% done (<span id="bytes-transferred"></span>/<span id="total-bytes"></span> bytes)</p>
                        <progress id="upload-progress" max="1" value="0"></progress>
                      </div>
                    </div>
                      
                </div>
    
                <div class="add-asmnt" id="ass-add-templete">
                    <p>Add Asssignments</p>
                    <input type="text" class="srch_title" id="ass_title" placeholder="Enter the Assignment title" />
                    <ol class="asmnts">
                        <li>
                            <div contenteditable="true" class="ass_question" placeholder="Enter your question here..."></div>
                            <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i>
                            <p style="clear: both"></p>
                        </li>
                    </ol>
                    <button type="text" onclick='addnewAssignment()'>+ Add new</button>
                    <span id='ADDASSIGNMENT'><div onclick="AddAssignmentQuestions()">Submit</div></span>
                </div>
    
                <!-- Tests -->
    
                <div class="add-test" id = "test-add-templete">
                    <p>Add Test Questions</p>
                    <input type="text" class="srch_title" id="test_title" placeholder="Enter the Test title" />
                    <ol class="tests">
                        <li>
                            <div contenteditable="true" class="test_question" placeholder="Enter your question here..."></div>
                            <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i>
                            <p style="clear: both"></p>
                        </li>
                    </ol>
                    <button type="text" onclick='addnewtest()'>+ Add new</button>
                    <span id = 'ADDTEST'><div onclick="AddTestQuestions()">Submit</div>
                </div>
    
                <!-- Quiz -->
    
                <!-- Quiz -->

            <div class="add-quiz" id = "quiz-add-templete">
                <p>Add Quiz Questions</p>
                <input class="srch_title" id="quiz_title" type="text" placeholder="Enter the Quiz title" />
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
                            <option value="a">a</option>
                            <option value="b">b</option>
                            <option value="c">c</option>
                            <option value="d">d</option>
                        </select>
                    </li>
                </ol>
                <button type="text" onclick = 'addnewQuiz()'>+ Add new</button>
                <span id = 'ADDQUIZ'><div onclick="AddQuizQuestions()">Submit</div></span>
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
     <script id="post-templete" type="text/x-handlebars-templete">
            <main id='main'>
                <img src='{{image}}'>
                <h2 ><b class='name'>{{name}}</b>  has posted. </h2>
                <p>{{message}}</p>
                <div>
                    <div class='like_div'>
                        <span id='posts{{id}}'>
                            {{{html}}}
                        </span>
                        <i class='fa fa-comment-o' aria-hidden='true' onclick='comment(pos{{id}})'></i>
                        <span onclick='comment("pos{{id}}")' id="pos{{id}}">Comment</span>
                    </div>
                    <div class='cmnt'>
                        <i class='fa fa-heart' aria-hidden='true' id="posts_{{id}}" title='{{likers}}'></i><span id="posts_like{{id}}">{{likelength}}</span>
                        <i class='fa fa-comments-o' aria-hidden='true'></i><span id="post_comment{{id}}">{{commentlength}}</span>
                    </div>
                    <div style="clear:both"></div>
                </div>
                {{#each comments}} 
                    
                    <main class='comment_box'>
                        <img src='{{image}}'>
                        <h2 ><b class='name'>{{name}}</b>  has commented in your post.</h2>
                        <p>{{message}}</p>
                        <div>
                            <div class="like_div">
                                <span id='comments{{id}}'>
                                    {{{html}}}
                                </span>
                                <i class='fa fa-comment-o' aria-hidden='true' onclick='reply("com{{id}}")' id="com{{id}}"></i>
                                <span onclick='reply("com{{id}}")'>Reply</span>
                            </div>
                            <div class='cmnt'>
                                <i class='fa fa-heart' aria-hidden='true' id="comments_{{id}}" title='{{likers}}'></i><span id="comments_like{{id}}">{{likelength}}</span>
                                <i class='fa fa-comments-o' aria-hidden='true'></i><span id="comment_comment{{id}}">{{commentlength}}</span>
                            </div>
                            <div style="clear:both"></div>
                        </div>
                        {{#each replies}}
                        
                            <main class='comment_box re'>
                                <img src='{{image}}'>
                                <h2 ><b class='name'>{{name}}</b>  has replied in your comment.</h2>
                                <p>{{message}} </p>
                                <div >
                                    <div class='like_div'>
                                        <span id='replies{{id}}'>
                                            {{{html}}}
                                        </span>
                                    </div>
                                    <div class='cmnt'>
                                        <span>
                                            <i class='fa fa-heart' aria-hidden='true' id="replies_{{id}}" title='{{likers}}'></i><span id="replies_like{{id}}">{{likelength}}</span>
                                        </span>
                                    </div>
                                    <div style="clear:both"></div>
                                </div>
                            </main>
                        {{/each}}
                    </main>
                {{/each}}
            </main>
        </script>
        
        <script id="comment-templete" type="text/x-handlebars-templete">
            <main class='comment_box'>
                <img src='{{image}}'>
                <h2 ><b class='name'>{{name}}</b>  has commented in your post.</h2>
                <p>{{message}} </p>
                <div>
                    <div class='like_div'>
                        <span id='comments{{id}}'>
                            <i class='fa fa-heart-o' onclick = 'addLike("comments",{{id}})' aria-hidden='true'></i>
                            <span onclick = 'addLike("comments",{{id}})'>Like</span>
                        </span>
                        <i class='fa fa-comment-o' aria-hidden='true' onclick='reply("com{{id}}")' id="com{{id}}"></i>
                        <span onclick='reply("com{{id}}")'>Reply</span>
                    </div>
                    <div class='cmnt'>
                        <i class='fa fa-heart' id="comments_{{id}}" aria-hidden='true'></i><span id="comments_like{{id}}">{{likelength}}</span>
                        <i class='fa fa-comments-o' aria-hidden='true'></i><span id="comment_comment{{id}}">{{commentlength}}</span>
                    </div>
                    <div style="clear:both"></div>
                </div>
            </main>
        </script>
        
        <script id="reply-templete" type="text/x-handlebars-templete">
            <main class='comment_box re'>
                    <img src='{{image}}'>
                    <h2 ><b class='name'>{{name}}</b>  has replied in your comment.</h2>
                    <p>{{message}}</p>
                    <div >
                        <div class='like_div' >
                            <span id='replies{{id}}'>
                                <i class='fa fa-heart-o' onclick = 'addLike("replies",{{id}})' aria-hidden='true'></i>
                                <span onclick = 'addLike("replies",{{id}})'>Like</span>
                            </span>
                        </div>
                        <div class='cmnt'>
                            <span>
                                <i class='fa fa-heart' id="replies_{{id}}" aria-hidden='true'></i><span id="replies_like{{id}}">0</span>
                            </span>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                </main>
        </script>
        
    <script src="//apis.google.com/js/client:plusone.js"></script>
    <script src="JS/cors_upload.js"></script>
    <script src="JS/upload_video.js"></script>
    
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="176409898115-eoi5sggfanbiq08h1e6soi4vtcm30mgf.apps.googleusercontent.com">
    <div class="g-signin2" data-onsuccess="onSignIn" style="display:none"></div>
    </body>

</html>