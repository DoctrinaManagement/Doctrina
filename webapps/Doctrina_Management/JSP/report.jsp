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
        function ReportPagegLoad() {
            $.get("/reportspageload", {"student_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>"}, function(data, status) {
            data = JSON.parse(data);
                document.getElementById("nav").innerHTML = "<p><%=session.getAttribute("class_name")%></p><img src="+data.image+" alt='Profile' /><p>"+data.name+"</p>"
                    
                <% if ((session.getAttribute("class_role")+"").equals("Teacher")) {
                        %>
                document.getElementById("nav").innerHTML += '<span>Rate this Student<input type="number" name="rating" maxlength="1"/> / 5 </span><button type="submit" class="submit">Submit</button>'
                <%}%>
            })
        }
        
        var heading = "";
        function assignmentReports () {
        // user_id used for same auth
            $.get("/getreporttitles", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"assignmenttitles"}, function(data, status){
                var titleObj = JSON.parse(data);
                var tempTemplete = document.getElementById("ass_titles").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("assignments").innerHTML= templete(titleObj);
                show(2);
            })
        }
        
        function testReports () {
        // user_id used for same auth
            $.get("/getreporttitles", {"user_id":"<%=session.getAttribute("student_id")%>", "class_id":"<%=session.getAttribute("class_id")%>","type":"testtitles"}, function(data, status){
                var titleObj = JSON.parse(data);console.log(titleObj);
                var tempTemplete = document.getElementById("test_titles").innerHTML;
                var templete  = Handlebars.compile(tempTemplete);
                document.getElementById("tests").innerHTML= templete(titleObj);
                show(3);
            })
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
                document.getElementById("tests").innerHTML += templete(titleObj);
                
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
                document.getElementById("assignments").innerHTML += templete(titleObj);
                
                $(".asmnts_div").css("display","none");
                $(".all_asigns").css("display","block");
            })
        }
        
        function closeDiv (type){
            if (type == "test") {
                $(".test_div").css("display","block");
                $(".all_test").css("display","none") ;
            }
            else if (type == "assignment") {
                $(".asmnts_div").css("display","block");
                $(".all_asigns").css("display","none");
            }
        }
        
    </script>
</head>
<body onload="ReportPagegLoad()">
    <main class="whole" style="display: flex">
        <nav class="left-nav">
            <div class="nav-main" id="nav">
                <!--<p>HTML & CSS</p>
                <img src="../IMAGES/profile.png" alt="Profile" />
                <p>Student Name</p>
                <span>Rate this Student
                    <input type="number" name="rating" maxlength='1'/> / 5
                </span>
                <button type="submit" class="submit">Submit</button>-->
            </div>
        </nav>
        <section class="main">
            <i class="cls material-icons" onclick="window.history.back()">close</i>
            <aside class="tabs">
                <div class="select" onclick="show(1)">Videos</div>
                <div onclick="assignmentReports()">Assignments</div>
                <div onclick="testReports()">Tests</div>
                <div onclick="show(4)">Quizes</div>
                <div onclick="show(5)">Ratings</div>
            </aside>
            
            <!-- Assignment_titles -->
                <script id="ass_titles" type="text/x-handlebars-templete">
                {{#each values}}
                    <div class="total">
                        <p>Total Assignments - {{count}}</p>
                        <p>Completed Assignments - <span> {{finish}} </span></p>
                    </div>
                {{/each}}
                     <p class="heading">Assignments</p>
                     <ul class="asmnts_div">
                    {{#each titles}}
                         <li>
                          <img src="../IMAGES/a.jpg" class="asmnt_icon" alt="asimnt icon" />
                          <p id='ass_title{{tabIndex}}' onclick="getAnswersfromAssignmentReports({{id}}, {{tabIndex}})">{{title}}</p>
                       </li> 
                     {{/each}}
                     </ul>
                </script>
            <!-- /Assignment_titles -->
            
            <!-- Test_titles -->
                <script id="test_titles" type="text/x-handlebars-templete">
                    {{#each values}}
                        <div class="total">
                            <p>Total Tests - {{count}}</p>
                            <p>Completed Test - <span> {{finish}} </span></p>
                        </div>
                    {{/each}}
                         <p class="heading">Tests</p>
                         
                         <ul class="test_div">
                    {{#each titles}}
                             <li>
                              <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                              <p id="test_title{{tabIndex}}" onclick="getAnswersfromTestReports({{id}}, {{tabIndex}})">{{title}}</p>
                           </li> 
                     {{/each}}
                     </ul>
                </script>
            <!-- /Test_titles -->
            
            <!-- Assignment Answers-->
                <script id="ass_answers" type="text/x-handlebars-templete">
                    
                <ul class="all_asigns">
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
                <ul>
                </script>
            <!-- /Assignment Answers-->
            
            <!-- Test Answers-->
                <script id="test_answers" type="text/x-handlebars-templete">
                    
                <ul class="all_test">
                    <h1 class="h1_hding">{{heading}}</h1><i class="close material-icons" onclick="close('test')">close</i>
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
                <ul>
                </script>
            <!-- /Test Answers-->
            
            <div class="whole_3">
                <div class="videos">
                    <div class="total">
                        <p>Total Videos - 13</p>
                        <p>Watched Videos - <span> 8 </span></p>
                    </div>
                    <p class="heading">Videos</p>
                    <ul class="videos-div">
                        <li>
                            <img src="../IMAGES/video-icon.jpg" alt="video-icon" />
                            <p class="video-name" title="HTML-CSS-Video-1.mp4">HTML-CSS-Video-1.mp4</p>
                            <div class="last">
                                <span class="time">13.01/13.01</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                    </ul>
                </div>
                
                <div class="asmnts" id='assignments'></div>
                
                <div class="tests" id='tests'></div>
                
                <div class="quizs">
                   <div class="total">
                        <p>Total Quiz - 13</p>
                        <p>Watched Videos - <span> 8 </span></p>
                    </div>
                    <p class="heading">Quizes</p> 
                    <ul class="quiz_div">
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                        <li>
                            <img src="../IMAGES/Quiz.png" alt="Quiz-icon" />
                            <p class="video-name" title="HTML-CSS">HTML-CSS</p>
                            <div class="last">
                                <span class="time">13/15</span>
                                <div class="check">&#10004;</div>
                                <div style="clear:right"></div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="rating">
                    <p class="heading">Ratings</p>
                    <ul class="rats">
                        <li>
                            <p>June</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                        <li>
                            <p>July</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                        <li>
                            <p>Augest</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                        <li>
                            <p>September</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                        <li>
                            <p>October</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                        <li>
                            <p>November</p>
                            <div class="st_box" title="2.5">
                               &#9733;&#9733;&#9733;&#9733;&#9733;
                            </div>
                            <span style="clear:right"></span>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
    </main>
</body>
</html>