$(document).ready(function(){
    $(".quotes").fadeTo("slow",1).css("transform","translateY(0px)");
    $("#SIGN_IN").click(function(){
        $("#S_IN").css({
            display:"block",
            opacity: "1"
        });
        $("#S_IN .g-f").addClass("show");
    });
    $("#SIGN_UP").click(function(){
        $("#S_UP").css({
            display:"block",
            opacity: "1"
        });
        $("#S_UP .g-f").addClass("show");
    });
    $("#S_IN .blur").click(function(){
        $("#S_IN .g-f").removeClass("show");
        $("#S_IN").css("opacity","0");
        setTimeout(function(){
            $("#S_IN").css("display","none");
        },500);
    });
    $("#S_UP .blur").click(function(){
        $("#S_UP .g-f").removeClass("show");
        $("#S_UP").css("opacity","0");
        setTimeout(function(){
            $("#S_UP").css("display","none");
        },500);
    });
    $(".slt-role").click(function(){
        var value= $(".slt-role input[name='Role']:checked").val();
        $("#role").text(value);
    });
    
    $(".g-f button").click(function(){
        $(".g-f>div:last-child").css("top","20px");
        setTimeout(function(){
            $(".g-f>div:last-child").css("top","-70px");
        },1000);
    });
});
function scrolling() {
    var s = $("body").scrollTop();
    console.log(s);
    if(s >= 780) {
        $(".imp>div:first-child").css("opacity","1");
        setTimeout(function(){
            $(".imp>div:nth-child(2)").css("opacity","1");
            setTimeout(function(){
                $(".imp>div:nth-child(3)").css("opacity","1");
                setTimeout(function(){
                    $(".imp>div:last-child").css("opacity","1");
                },500);
            },500);
        },500);
    }
}