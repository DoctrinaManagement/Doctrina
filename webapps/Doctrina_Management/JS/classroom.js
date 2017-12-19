$(document).ready(function() {
    //  -----------------------onload----------------------------

    $(".asign_div,.quiz_div,.test_div,.feed").css("display", "none");
    // --------------------------------- quiz hding ----------------------------
    
    $(".quiz_hd>li").click(function(){
       $(".quiz_hd").css("display","none");
       $(".quiz_qn,.quiz_div>button").css("display","block");
       
    });
    $(".cls").click(function(){
       $(".quiz_hd").css("display","block");
       $(".quiz_qn,.quiz_div>button").css("display","none");
    });

    // -------------------------notification and profile--------------------

    i = 0;
    $(".header_div i").click(function() {
        if (i == 0) {
            $(".noti").css({
                "left": "-234px"
            });
            $(".pro").css({
                "right": "-250px"
            });
            i++;
        } else {
            $(".noti").css({
                "left": "340px"
            });
            i = 0;
        }
    });
    $(".box_div,.show_div,.quiz_div,nav").click(function() {
        $(".noti").css({
            "left": "234px"
        });
    });
    j = 0;
    $(".profile").click(function() {
        if (j == 0) {
            $(".pro").css({
                "right": "19px"
            });
            $(".noti").css({
                "left": "340px"
            });
            j++;
        } else {
            $(".pro").css({
                "right": "-350px"
            });
            j = 0;
        }
    });
    $(".box_div,.show_div,.quiz_div,nav").click(function() {
        $(".pro").css({
            "right": "-350px"
        });
    });
    $(".noti>span").click(function(){
        $(".note-inv").css("transform","translateX(0)")
    });
    $(".noti>img").click(function(){
        $(".note-inv").css("transform","translateX(-50.5%)")
    });

    // ------------------------- video -----------------------------

    
    // $(".close,nav,header").click(function() {
    //     $(".video_div+section").css({
    //         "display": "none"
    //     });
    //     $(".video_div").css({
    //         "padding": "20px 61px"
    //     });
    // });
    $(".adds").click(function() {
        $(".drop").toggleClass("dd-show")
    });


    // -------------------------------- Selected ----------------------------------

    $(".ul1 li").click(function() {
        $(this).addClass('selected').siblings().removeClass('selected');
    });
    //---------------------------------Video Div----------------------------------- 

    $(".ul1 li:nth-child(1)").click(function() {
        $(".video_div").css("display", "block");
        $(".quiz_div,.asign_div,.test_div,.feed").css("display", "none");
    });

    //---------------------------------Asignment Div----------------------------------- 

    $(".ul1 li:nth-child(2)").click(function() {
        $(".video_div, .asign_div, .test_div,.feed").css("display", "none");
        $(".quiz_div").css("display", "block");
    });

    // ----------------------------------Quiz------------------------------------------
    
    $(".ul1 li:nth-child(3)").click(function() {
        $(".video_div,.quiz_div,.test_div,.feed").css("display", "none");
        $(".asign_div").css("display", "block");
    });
    
    // --------------------------------Feeds------------------------------------------
    
    $(".ul1 li:nth-child(5)").click(function() {
        $(".video_div,.quiz_div,.test_div,.asign_div").css("display", "none");
        $(".feed").css("display", "block");
    });
    
    //---------------------------------test--------------------------------------------
    
    $(".ul1 li:nth-child(4)").click(function() {
        $(".video_div,.asign_div,.quiz_div,.feed").css("display", "none");
        $(".test_div").css("display", "block");
    });
    // ---------------------------------- Add -----------------------------------------

    $(".ul2>li, #stdnts, .invt").click(function() {
        $(".add").css({
            "display": "block",
            "opacity": "1"
        });
    });
    $(".blur").click(function() {
        $(".add").css("opacity", "0");
        setTimeout(function() {
            $(".add").css("display", "none");
        }, 200);
        $(".add-video").css("transform", "scale(0)");
        $(".invt-ppl>ul").css("display","none") ;
    });

    // -------------------------------Add videos --------------------------------------

    $(".ul2>li:first-child").click(function() {
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function() {
            $(".add-video, .blur").css("transform", "scale(1)");
        }, 50);
    });

    // ------------------------------Add Quizzes -----------------------------------

    $(".ul2>li:nth-child(2)").click(function() {
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function() {
            $(".add-quiz, .blur").css("transform", "scale(1)");
        }, 50);
    });


    // ------------------------------Add Assignments -----------------------------------

    $(".ul2>li:nth-child(3)").click(function() {
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function() {
            $(".add-asmnt, .blur").css("transform", "scale(1)");
        }, 50);
    });

    // ------------------------------Add Tests -----------------------------------

    $(".ul2>li:last-child").click(function() {
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function() {
            $(".add-test, .blur").css("transform", "scale(1)");
        }, 50);
    });
    

    // ------------------------------Add Tests -----------------------------------

    $("#stdnts").click(function() {
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function() {
            $(".report, .blur").css("transform", "scale(1)");
        }, 50);
    });

    // ------------------------------------asignment clicks---------------------------

    $(".asign_hding li:first-child,.asign_hding li:nth-child(2),.asign_hding li:nth-child(3)").click(function() {
        $(".asign_hding").css("display", "none");
        $(".asign_ol").css("display", "block");
    });
    $(".asign_ol>ol i").click(function() {
        $(".asign_hding").css("display", "block");
        $(".asign_ol").css("display", "none");
    });
    
    

    // ------------------------------------- more ----------------------------------------
    n = 0;
    $("nav>p+i").click(function() {
        if (n == 0) {
            $(".more").css("display", "block");
            n++;
        } else {
            $(".more").css("display", "none");
            n = 0;
        }
    });
    $(".show_div, .ul1, .ul1+ul, .drop, header, .more>ul>li").click(function() {
        n=0;
        $(".more").css("display", "none")
    });
    
    
    
    // --------------------------- Invite Actions -----------------------------------
    
    $(".invt").click(function(){
        $(".add>div").css("transform", "scale(0)");
        setTimeout(function(){
            $(".add .invt-ppl,.blur").css("transform", "scale(1)")
        },50);
    });
    $(".invt-srch").click(function(){
        GO();
       $(".invt-ppl>ul").css("display","block") ;
    });
    
    

});

function del_input(elmnt,n) {
    $(elmnt).parent().css("display", "none");
    $(elmnt).prev("div").removeAttr("class");
    $(elmnt).next("div").removeAttr("class");
}