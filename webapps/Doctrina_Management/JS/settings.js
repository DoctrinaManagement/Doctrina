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
});