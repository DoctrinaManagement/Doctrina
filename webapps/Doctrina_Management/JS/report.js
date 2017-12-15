$(document).ready(function(){
    
    //  ------------------------ tabs -------------------------

    $(".tabs>div").click(function(){
        $(this).addClass('select').siblings().removeClass('select');
    });
    
    //  ----------------- assignment click -----------------
    
    // $(".asmnts_div>li").click(function(){
    //     $(".asmnts_div").css("display","none");
    //     $(".all_asigns").css("display","block");
    // });
    // $(".close").click(function(){
    //     $(".asmnts_div").css("display","block");
    //     $(".all_asigns").css("display","none");
    // });
    
    // ---------------- Quizdiv click ---------------
    // $(".test_div>li").click(function(){
    //   $(".test_div").css("display","none");
    //   $(".all_test").css("display","block") ;
    // });
    // $(".close1").click(function(){
    //     $(".test_div").css("display","block");
    //     $(".all_test").css("display","none") ;
    // });
    
});
