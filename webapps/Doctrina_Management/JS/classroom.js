$(document).ready(function(){
    //  -----------------------onload----------------------------
    
    $(".asign_div,.quiz_div,.test_div").css("display","none");
    
    
    // -------------------------notification and profile--------------------
    
    i=0;
    $(".header_div i").click(function(){
       if(i==0){
           $(".noti").css({"left":"-234px"}) ;
           $(".pro").css({"right":"-250px"}) ;
           i++;
       }
       else{
           $(".noti").css({"left":"234px"}) ;
            i=0;
       }
    });
    $(".box_div,.show_div,.quiz_div").click(function(){
        $(".noti").css({"left":"234px"}) ;
    });
    j=0;
    $(".profile").click(function(){
       if(j==0){
           $(".pro").css({"right":"19px"}) ;
            $(".noti").css({"left":"234px"}) ;
           j++;
       }
       else{
           $(".pro").css({"right":"-250px"}) ;
            j=0;
       }
    });
    $(".box_div,.show_div,.quiz_div").click(function(){
        $(".pro").css({"right":"-250px"});
    });
    
    // ------------------------- video -----------------------------
    
    $(".video_div div").click(function(){
        $(".video_div").css({"padding":"0"});
        // $(".box_div").css("background","rgba(0, 0, 0, 0.5)");
        $(".video_div+section").css({"display":"block"});
    });
    $(".close").click(function(){
        $(".video_div+section").css({"display":"none"});
        $(".video_div").css({"padding":"20px 61px"});
    });
    l=0;
    $("nav ul:nth-child(3) li:last-child").click(function(){
        l++;
        if(l==1){
            $(".drop").css({"display":"block"});
        }
        else{
            $(".drop").css({"display":"none"});
            l=0;
        }
    });
    
    
    // -------------------------------- Selected ----------------------------------
    
    $(".ul1 li").click(function(){
        $(this).addClass('selected').siblings().removeClass('selected');
    });
    //---------------------------------Video Div----------------------------------- 
    
    $(".ul1 li:nth-child(1)").click(function(){
        $(".video_div").css("display","block");
        $(".quiz_div").css("display","none");
        $(".asign_div").css("display","none");
        $(".test_div").css("display","none");
    });
    
    //---------------------------------Asignment Div----------------------------------- 
    
    $(".ul1 li:nth-child(2)").click(function(){
        $(".video_div").css("display","none");
        $(".quiz_div").css("display","block");
        $(".asign_div").css("display","none");
        $(".test_div").css("display","none");
    });
    
    // ----------------------------------Quiz------------------------------------------
    $(".ul1 li:nth-child(3)").click(function(){
        $(".video_div").css("display","none");
        $(".quiz_div").css("display","none");
        $(".asign_div").css("display","block");
        $(".test_div").css("display","none");
    });
    //---------------------------------test--------------------------------------------
    $(".ul1 li:nth-child(4)").click(function(){
        $(".video_div").css("display","none");
        $(".quiz_div").css("display","none");
        $(".asign_div").css("display","none");
        $(".test_div").css("display","block");
    });
    
    // ---------------------------------- Add -----------------------------------------
    
    $(".ul2>li, #stdnts").click(function(){
        $(".add").css({
            "display":"block",
            "opacity":"1"
        });
    });
    $(".blur, .add>div>div:last-child").click(function(){
        $(".add").css("opacity","0");
        setTimeout(function(){
            $(".add").css("display","none");
        },200);
        $(".add-video").css("transform","scale(0)");
    }); 
    
    // -------------------------------Add videos --------------------------------------
    
    $(".ul2>li:first-child").click(function(){
        $(".add>div").css("transform","scale(0)");
        setTimeout(function(){
            $(".add-video, .blur").css("transform","scale(1)");
        },50);
    });
    
     // ------------------------------Add Quizzes -----------------------------------
    
    $(".ul2>li:nth-child(2)").click(function(){
        $(".add>div").css("transform","scale(0)");
        setTimeout(function(){
            $(".add-quiz, .blur").css("transform","scale(1)");
        },50);
    });
    $(".add-quiz>button").click(function(){
        $(".quizzes").append("<li> <div contenteditable='true' placeholder='Enter your quiz question here...'></div> <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i> <p style='clear: both'></p> <section class='options'> <span>(a)</span> <input type='text' /> <span>(b)</span> <input type='text' /> <span>(c)</span> <input type='text' /> <span>(d)</span> <input type='text' /> </section> </li>");
    });
    
    // ------------------------------Add Assignments -----------------------------------
    
    $(".ul2>li:nth-child(3)").click(function(){
        $(".add>div").css("transform","scale(0)");
        setTimeout(function(){
            $(".add-asmnt, .blur").css("transform","scale(1)");
        },50);
    });
    $(".add-asmnt>button").click(function(){
        $(".asmnts").append("<li> <div contenteditable='true' placeholder='Enter your question here...'></div> <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i> <p style='clear: both'></p> </li>");
    });
    
    // ------------------------------Add Tests -----------------------------------
    
    $(".ul2>li:last-child").click(function(){
        $(".add>div").css("transform","scale(0)");
        setTimeout(function(){
            $(".add-test, .blur").css("transform","scale(1)");
        },50);
    });
    $(".add-test>button").click(function(){
        $(".tests").append("<li> <div contenteditable='true' placeholder='Enter your question here...'></div> <i class='fa fa-times' onclick='del_input(this)' aria-hidden='true'></i> <p style='clear: both'></p> </li>");
    });
    
    // ------------------------------Add Tests -----------------------------------
    
    $("#stdnts").click(function(){
        $(".add>div").css("transform","scale(0)");
        setTimeout(function(){
            $(".report, .blur").css("transform","scale(1)");
        },50);
    });
    
    // ------------------------------------asignment clicks---------------------------
    
   $(".asign_hding li:first-child,.asign_hding li:nth-child(2),.asign_hding li:nth-child(3)").click(function(){
      $(".asign_hding").css("display","none");
      $(".asign_ol").css("display","block"); 
   });
   $(".asign_ol>ol>i").click(function(){
       $(".asign_hding").css("display","block");
       $(".asign_ol").css("display","none")
   });
    
});
function del_input(elmnt) {
    $(elmnt).parent().css("display","none");
}






