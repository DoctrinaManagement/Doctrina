<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../CSS/report.css"/>
    <link href="https://fonts.googleapis.com/css?family=Lato|Raleway" rel="stylesheet">
    <link rel="shortcut icon" href="../IMAGES/D-logo.png"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">   
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../JS/report.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
    <title>DOCTRINA | Report page</title>
    
    <script>
        function ReportPageLoad() {
         if(document.cookie.indexOf("Name") == -1) {
            location.href = "/landingpage"
        }
        videosGet();
            $.get("/reportspageload", {"student_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>"}, function(data, status) {
            data = JSON.parse(data);
                document.getElementById("nav").innerHTML = "<p><%=session.getAttribute("class_name")%></p><img src="+data.image+" alt='Profile' /><p>"+data.name+"</p>"
                    
                <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
                        %>
                document.getElementById("nav").innerHTML += '<button type="submit" class="rtng" onclick="ratingDiv()">Rate This Student</button>'
                <%}%>
            })
        }
        function videosGet() {
            $.get("/getVideoReports", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"videos"}, function(data, status){
                var titleObj = JSON.parse(data);console.log(titleObj.values);
                document.getElementById("video_title").innerHTML = "<p>Total Videos - "+titleObj.values.count+"</p><p>Watched Videos - <span> "+titleObj.values.finish+" </span></p>"
                
                var tempTemplete = document.getElementById("videoReports").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("reportVideos").innerHTML= templete(titleObj);
                console.log(titleObj);
                finishCheck(titleObj.finished , "video");
                VideoTime(titleObj.timeStatus);
                show(1);
            })
        }
        function VideoTime(time) {
            for(i = 0; i < time.length; i++) {
                document.getElementById("time"+time[i].id).innerHTML = "<span class='time'>"+time[i].time+"/"+time[i].total_time+"</span>";
            }
        }
        
        var heading = "";
        function assignmentReports () {
        // user_id used for same auth
            $.get("/getreporttitles", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"assignmenttitles"}, function(data, status){
                var titleObj = JSON.parse(data);
                var values = titleObj.values[0];
                document.getElementById("ass_total").innerHTML = "<p>Total Assignments - "+values.count+"</p><p>Completed Assignments - <span>"+values.finish+"</span></p>";
                
                var tempTemplete = document.getElementById("ass_titles").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("asstitle_ul").innerHTML= templete(titleObj);
                finishCheck(titleObj.finished, "Assignment");
                var arrObj = rearrangeTabIndex(titleObj);
                onclickSet(arrObj, "Assignment");
                show(2);
            })
        }
        
        function testReports () {
        // user_id used for same auth
            $.get("/getreporttitles", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"testtitles"}, function(data, status){
                var titleObj = JSON.parse(data);
                var tempTemplete = document.getElementById("test_titles").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                var values = titleObj.values[0];
                document.getElementById("testtitle_ul").innerHTML = templete(titleObj);
                document.getElementById("test_total").innerHTML = "<p>Total Tests - "+values.count+"</p><p>Completed Tests - <span>"+values.finish+"</span></p>";
                finishCheck(titleObj.finished, "Test");
                var arrObj = rearrangeTabIndex(titleObj);
                onclickSet(arrObj, "Test");
                show(3);
            })
        }
        
        function quizReports() {
            $.get("/getreporttitles", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"quiztitles"}, function(data, status){
                var titleObj = JSON.parse(data);
                var tempTemplete = document.getElementById("quiz_titles").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("quizs").innerHTML = templete(titleObj);
                finishCheck(titleObj.finished, "quiz");
                QuizMark(titleObj.mark);
                show(4);
            })
        }
        function QuizMark(mark) {
            var keys = Object.keys(mark);
            for(i = 0; i < keys.length; i++) {
                document.getElementById("mark_"+keys[i]).innerHTML = "<span class='time'>"+mark[keys[i]].marks+"/"+mark[keys[i]].total+"</span>";
            }
        }
        
        function onclickSet (obj, type) {
            for(i = 0; i < obj.length; i++) {
                var a = type+obj[i].id;
                document.getElementById(a).setAttribute("onclick", "getAnswersfrom"+type+"Reports(\""+obj[i].id+"\",\""+obj[i].tabIndex+"\")");
                }
        }
        
        function rearrangeTabIndex(Obj) {
            var out = [];
            var finishedId = 0 ;
            for(i = 0; i < Obj.titles.length ; i++) {
                
                if(Obj.titles[i].id == Obj.finished[finishedId]) {
                    var temp = {};
                    temp["id"] = Obj.finished[finishedId];
                    temp["tabIndex"] = Obj.titles[i].tabIndex;
                    out.push(temp);
                    finishedId++;
                }
            }
            return out;
        }
        
        function finishCheck(finishID, type) {
            for (i = 0; i < finishID.length; i++) {
               document.getElementById(type+finishID[i]).innerHTML += "<div class='check'>&#10004;</div><div style='clear:right'></div>";
            }
        }
        // Titles Show 
        function show(n) {
            $(".whole_3>div").css("display","none")
            $(".whole_3>div:nth-child("+n+")").css("display","block")
        }
        //test question answer
        function getAnswersfromTestReports(id, tabIndex) {
            heading = document.getElementById("test_title"+tabIndex).innerText;
            
            $.get("/getreportanswers", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","id":id,"type":"tests"}, function(data, status){
                var titleObj = JSON.parse(data);
                titleObj["heading"] = heading;
                var tempTemplete = document.getElementById("test_answers").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("testAnswers").innerHTML = templete(titleObj);
                
                $(".test_div").css("display","none");
                $(".all_test").css("display","block");
            })
        }
        //assignment question answer
         function getAnswersfromAssignmentReports(id, tabIndex) {
            heading = document.getElementById("ass_title"+tabIndex).innerText;
            
            $.get("/getreportanswers", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","id":id,"type":"assignments"}, function(data, status){
                var titleObj = JSON.parse(data);
                titleObj["heading"] = heading;
                var tempTemplete = document.getElementById("ass_answers").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("assignmentAnswers").innerHTML = templete(titleObj);
                
                $(".asmnts_div").css("display","none");
                $(".all_asigns").css("display","block");
            })
        }
        
        function closeDiv (type){
            if (type == "test") {
                $(".test_div").css("display","block");
                $(".all_test").css("display","none");
            }
            else if (type == "assignment") {
                $(".asmnts_div").css("display","block");
                $(".all_asigns").css("display","none");
            }
        }
        function ratingDiv() {
            $(".rtng-pp").css("top","0");
        }
        
        function RatingSubmit (){
            var title = $("#ratingTitle").val();
            var description = $("#description").val();
            var value = $("#rating").val();
            var rating = Number(value).toFixed(2);
            
            var titleReg = /^[a-zA-Z]{3,15}$/;
            var desReg = /^[\w\W]{5,100}$/;
            console.log(rating);
            if (rating <= 5 && rating >= 0 && titleReg.test(title) && desReg.test(description)) {console.log(description);
               $.get("/ratingsubmit", {"user_id":"<%=session.getAttribute("user_id")%>", "student_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>", "title":title, "description":description, "rating":rating}, function(data, status) {
                    if(data == "200") {
                        error("success");
                    }
                    else if (data == "400"){
                        $(".alert").html('<i class="fa fa-exclamation" aria-hidden="true"></i>You have already rated this Student!!');
                        $(".alert").css("color", "rgb(36, 146, 255)"); 
                        $(".alert>i").css("background", "rgb(36, 146, 255)");
                        $(".alert").css("top","0");
                        setTimeout (function(){
                        $(".alert").css("top","-75px");
                        },2000);
                    }
                }); 
            }
            else {
                error("info");
            }
        }
        
        function getRating (){
            $.get("/getrating", {"student_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>"}, function(data, status){
                var ratingObj = JSON.parse(data);console.log(ratingObj);
                var tempTemplete = document.getElementById("rating-templete").innerHTML;
                var templete = Handlebars.compile(tempTemplete);
                document.getElementById("rating_ul").innerHTML = templete(ratingObj);
                RatingStar(ratingObj.ratings);
                show(5);
            })
        }
        function RatingStar(rating) {
            for (i = 0; i < rating.length; i++) {
                var ratingMark = rating[i].rating;
                var percent = (ratingMark/5) * 100;
                document.getElementById("rating"+rating[i].tabIndex).style = "background:linear-gradient(to right , gold "+percent+"%, lightgrey 0); background-clip: text; -webkit-background-clip: text;";
            }
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
<body onload="ReportPageLoad()">
    <!-- Alert -->
            <div class="alert"></div>
    <!-- /Alert -->
    
    <main class="whole" style="display: flex">
        <nav class="left-nav">
            <div class="nav-main" id="nav"></div>
        </nav>
        <section class="main">
            <i class="cls material-icons" onclick="window.history.back()">close</i>
            <aside class="tabs">
                <div class="select" onclick="show(1)">Videos</div>
                <div onclick="assignmentReports()">Assignments</div>
                <div onclick="testReports()">Tests</div>
                <div onclick="quizReports()">Quizes</div>
                <div onclick="getRating()">Ratings</div>
            </aside>
            
            <!-- VideosReport -->
                <script id="videoReports" type="text/x-handlebars-templete">
                    {{#each titles}}
                        <li id="video{{id}}">
                            <img src="../IMAGES/video-icon.jpg" alt="video-icon" />
                            <p class="video-name" title="{{title}}">{{title}}</p>
                            <div class="last" id="time{{id}}"></div>
                        </li>
                    {{/each}}
                </script>
            <!-- /VideoReport -->
            <!-- Assignment_titles -->
                <script id="ass_titles" type="text/x-handlebars-templete">
                    {{#each titles}}
                         <li id="Assignment{{id}}">
                          <img src="../IMAGES/a.jpg" class="asmnt_icon" alt="asimnt icon" />
                          <p id='ass_title{{tabIndex}}'>{{title}}</p>
                       </li> 
                     {{/each}}
                </script>
            <!-- /Assignment_titles -->
            
            <!-- Test_titles -->
                <script id="test_titles" type="text/x-handlebars-templete">
                    {{#each titles}}
                             <li id="Test{{id}}">
                              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                              <p id="test_title{{tabIndex}}">{{title}}</p>
                           </li> 
                     {{/each}}
                </script>
            <!-- /Test_titles -->
            
            <!-- quiztitles -->
            <script id="quiz_titles" type="text/x-handlebars-templete">
                    
                    {{#each values}}
                        <div class="total">
                            <p>Total Quiz - {{count}}</p>
                            <p>Completed Quiz - <span> {{finish}} </span></p>
                        </div>
                    {{/each}}
                    <p class="heading">Quizes</p> 
                        <ul class="quiz_div">
                            {{#each titles}}
                                    <li id="quiz{{id}}">
                                        <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                                        <p class="video-name" title="{{title}}">{{title}}</p>
                                <span id='mark_{{id}}'></span>
                             {{/each}}
                             </li>
                        </ul>
                </script>
                           
            <!-- /quiztitles -->
            
            <!-- Assignment Answers-->
                <script id="ass_answers" type="text/x-handlebars-templete">
                    
                    <h1 class="h1_hding">{{heading}}</h1><i class="close material-icons" onclick="closeDiv('assignment')">close</i>
                    <span style="clear:right"></span>
                    
                    {{#each questionanswers}}
                     <li>
                        <div class="qn">
                            <span class="s_no">{{tabIndex}}</span>
                            {{question}}
                        </div>
                        <p class="p_cont">Answer</p>
                        <div class="ans">{{answer}}</div>
                        <span style="clear:right"></span>
                    </li>
                    {{/each}}
                </script>
            <!-- /Assignment Answers-->
            
            <!-- Test Answers-->
                <script id="test_answers" type="text/x-handlebars-templete">
                    
                    <h1 class="h1_hding">{{heading}}</h1><i class="close material-icons" onclick="closeDiv('test')">close</i>
                    <span style="clear:right"></span>
                    
                    {{#each questionanswers}}
                     <li>
                        <div class="qn">
                            <span class="s_no">{{tabIndex}}</span>
                            {{question}}
                        </div>
                        <p class="p_cont">Answer</p>
                        <div class="ans">{{answer}}</div>
                        <span style="clear:right"></span>
                    </li>
                    {{/each}}
                </script>
            <!-- /Test Answers-->
            
            <!-- Rating -->
                <Script id="rating-templete" type="text/x-handlebars-templete">
                    {{#each ratings}}
                     <li tabindex="{{tabIndex}}">
                            <p>{{title}}</p>
                            <div class="st_box" id="rating{{tabIndex}}" title={{rating}}>
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                            <div class="des">
                                <p>Your Performance Description : </p>
                                <span>{{description}}</span>
                            </div>
                        </li>
                    {{/each}}
                </script>
            <!-- /Ratiing -->
            
            <div class="whole_3">
                <div class="videos">
                    <div class="total" id="video_title"></div>
                    <p class="heading">Videos</p>
                    <ul class="videos-div" id="reportVideos"></ul>
                </div>
                
                <div class="asmnts" id='assignments'>
                    <div class="total" id="ass_total"></div>
                    <p class="heading">Assignments</p>
                    <ul class="asmnts_div" id="asstitle_ul"></ul>    
                    <ul class="all_asigns" id="assignmentAnswers"></ul>
                </div>
                
                <div class="tests" id='tests'>
                        <div class="total" id="test_total"></div>
                        <p class="heading">Tests</p>
                        <ul class="test_div" id="testtitle_ul"></ul>
                        <ul class="all_test" id="testAnswers"></ul>
                </div>
                
                <div class="quizs" id="quizs"></div>
                
                 <div class="rating">
                    <p class="heading">Ratings</p>
                    <ul class="rats" id='rating_ul'></ul>
                </div>
                    
            </div>
        </section>
    </main>
    <div class="rtng-pp">
        <p>Rating</p>
        <input type="text" id ="ratingTitle" class="rtng-ttl" placeholder="Title" />
        <textarea class="rtng-des" id="description" placeholder="Write your description about this student..." ></textarea>
        <span>Give the rating here </span>
        <input type="number" id="rating" name="rating"/> / 5
        <button type="submit" class="sbmt" onclick="RatingSubmit()">Submit</button>
    </div>
</body>
</html>