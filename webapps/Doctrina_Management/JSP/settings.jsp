<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../CSS/settings.css"/>
    <link href="https://fonts.googleapis.com/css?family=Lato|Oleo+Script" rel="stylesheet">
    <link rel="shortcut icon" href="../IMAGES/D-logo.png"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../JS/settings.js"></script>
    <title>DOCTRINA | Settings</title>
    
    <script>
        function pageLoading() {
           $.get("settings_valuesGet",{"user_id":"<%=session.getAttribute("user_id")%>"},function(data,status){
                
                var UsedCourses = JSON.parse(data);
                for (i = 0; i < UsedCourses.length; i ++) { 
                   	document.getElementById("a"+UsedCourses[i]).setAttribute("checked",true);
                }
           });
        }
        function PageClose() {
            window.history.back();
        }
        
        // SaveNotification_Settings 
        
        function getValues() {
            var checkedValue = [];
            var inputElements = document.getElementsByClassName('setting');
            for(var i=0; inputElements[i]; ++i){
                  if(inputElements[i].checked){
                        checkedValue.push(inputElements[i].value);
                       //checkedValue += (inputElements[i].value + "A");
                  }
            }
           // checkedValue = checkedValue.substring(0,checkedValue.length-1);console.log(checkedValue);
            SaveNotification_Settings(checkedValue);
        }
        
        function SaveNotification_Settings(values) {console.log(values);
            $.get("SaveNotification_Settings",{"user_id":"<%=session.getAttribute("user_id")%>","courses":JSON.stringify(values)},function(data,status){
               if(data == "save successfully") {
                   $(".save").text("Save Successfully...");
                   setTimeout(function(){
                       $(".save").text("Save Changes")
                   },1000);
               }
            });
        }
        
    </script>
</head>
<body onload="pageLoading()">
   <div class="whole">
       <!--<header>-->
       <!--    <img src="../IMAGES/D.gif" alt="D logo" />-->
       <!--</header> -->
       <div class="main">
           <div class="conts_div">
               <h1><i class="fa fa-cogs" aria-hidden="true"></i>Settings<i class="material-icons close" onclick="PageClose()">close</i></h1>
               <div class="notis">
                   <h2>Notifications</h2>
                   <ul>
                       <li><input class="setting" type="checkbox" id="a1" value="1"><label for="a1"></label><p>Maths</p></li>
                       <li><input class="setting" type="checkbox" id="a2" value="2"><label for="a2"></label><p>English</p></li>
                       <li><input class="setting" type="checkbox" id="a3" value="3"><label for="a3"></label><p>Programming</p></li>
                       <li><input class="setting" type="checkbox" id="a4" value="4"><label for="a4"></label><p>Designing</p></li>
                       <li><input class="setting" type="checkbox" id="a5" value="5"><label for="a5"></label><p>Artificial Inteligence</p></li>
                       <li><input class="setting" type="checkbox" id="a6" value="6"><label for="a6"></label><p>Science</p></li>
                       <li><input class="setting" type="checkbox" id="a7" value="7"><label for="a7"></label><p>History</p></li>
                       <li><input class="setting" type="checkbox" id="a8" value="8"><label for="a8"></label><p>Animation</p></li>
                   </ul>
                   <ul>
                       <li><input class="setting" type="checkbox" id="a9"  value="9"><label for="a9"></label><p>Medical</p></li>
                       <li><input class="setting" type="checkbox" id="a10" value="10"><label for="a10"></label><p>Engineering</p></li>
                       <li><input class="setting" type="checkbox" id="a11" value="11"><label for="a11"></label><p>Arts</p></li>
                       <li><input class="setting" type="checkbox" id="a12" value="12"><label for="a12"></label><p>Robotics</p></li>
                       <li><input class="setting" type="checkbox" id="a13" value="13"><label for="a13"></label><p>Music</p></li>
                       <li><input class="setting" type="checkbox" id="a14" value="14"><label for="a14"></label><p>Tamil</p></li>
                       <li><input class="setting" type="checkbox" id="a15" value="15"><label for="a15"></label><p>Economics</p></li>
                       <li><button class="save" type="submit" onclick="getValues()">Save Changes</button></li>
                   </ul>
               </div>
               <h3>Delete Account</h3>
               <button class="delete" type="submit">Delete My Account</button>
           </div>
       </div>
       <section class="popup">
           <aside></aside>
           <div>
               <p>Are you sure, want to Delete your Account?</p>
               <button type="submit">Yes</button>
               <button id="no" type="submit">No</button>
           </div>
       </section>
   </div> 
</body>
</html>