$(document).ready(function(){
    $(".delete").click(function(){
        $(".popup").css({"display":"block","opacity":"1"});
        setTimeout(function(){
           $(".popup>div").css("transform","scale(1)"); 
        },200);
   }) ;
   $(".popup>aside,#no").click(function(){
        $(".popup").css({"display":"none","opacity":"1"}); 
        $(".popup>div").css("transform","scale(0)");
   });
   $(".save").click(function(){
       $(".save").text("Saving Changes...");
       
   });
   
   $("ul li p").click(function(){
        if( $(this).siblings("input").is(":checked") ) {
            $(this).siblings("input").prop("checked",false);
        } else {
            $(this).siblings("input").prop("checked",true);
        }
   });
});