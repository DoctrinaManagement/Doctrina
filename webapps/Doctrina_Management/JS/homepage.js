$(document).ready(function(){
    
    // ---------------------------------- onload -----------------------------------
    
    $(".main2_div,.width_div,.allclass").css("display","none");
   
    //  ------------------------------- classroom create --------------------------------------
    
    $(".create_btn").click(function(){
       $(".crt_div").css("top","-275px");
    });
   
    var num=-1,n=0;limit=0;
    // var array=["#e8aaf3","#73efbb","#ff89a9","#c1f97f","#fff27c","#ffa3a3"];
    var array=["#73efbb","#e2d55e","#ff89a9","#c1f97f","#ffa3a3","#e8aaf3"];
    $(".done_btn").click(function(){
        num++;n++;limit++;
        $(".title_div").before("<div class='new'><div class='newclass' style='background:"+array[num]+"' onclick='hide()'><img src='../IMAGES/classs.png'><p id='name"+n+"'></p><button type='submit'>Join</button></div> <img src='../IMAGES/info.png' tabindex='0' class='info' onclick='des(this)'> <div class='des' tabindex='2'> <p>Created by</p> <p>Selva</p> <div class='show_des'></div> </div> </div>"); 
        
        $(".new>.newclass button").click(function(){
            $(this).text("Joined");
            $(this).css("background","white");
        });
        
        $(".crt_div").css("top","0");
        if(num==5){
            num=-1;
        }
        if(limit==12){
            $(".title_div").css("display","none");
        }
        var txt=$(".title input:text").val();
        $("#name"+n).text(txt);
            $(".title input").val('');
            $(".title textarea").val('');
    });
    

    // ------------------------------ notification and dropdowns --------------------------- 
    i=0; j=0;
    $(".header_div i").click(function(){
       if(i==0){
            $(".noti").css({"left":"-234px"}) ;
            $(".pro").css({"right":"-290px"}) ;
            i++;
       }
       else{
            $(".noti").css({"left":"234px"}) ;
            i=0;
       }
    });
    $(".profile").click(function(){
       if(j==0){
            $(".pro").css({"right":"19px"}) ;
            $(".noti").css({"left":"234px"}) ;
            j++;
       }
       else{
            $(".pro").css({"right":"-290px"}) ;
            j=0;
       }
    });
    $(".box_div").click(function(){
        $(".noti").css({"left":"234px"}) ; i=0;
        $(".pro").css({"right":"-290px"}); j=0;
    });
    $(".title i").click(function(){
        $(".crt_div").css("top","0px");
    });
    $(".dlt").click(function(){
        (".new").css("display","none"); 
    });
    
    // ---------------------------------- video ----------------------------------------
    
    $(".video_div div").click(function(){
        $(".video_div").css({"padding":"0"});
        $(".video_div section").css({"background":"#ccc","display":"block","position":"fixed"});
    });
    
    $(".close").click(function(){
        $(".video_div section").css({"display":"none"});
        $(".video_div").css({"padding":"20px 61px"});
    });
    
    l=0;
    $("nav ul li:last-child").click(function(){
        l++;
        if(l==1){
            $(".drop").css({"display":"block"});
        }
        else{
            $(".drop").css({"display":"none"});
            l=0;
        }
    });
    
    $(".la2").click(function(){
        $(".la2").addClass("select");
        $(".la1").removeClass("select");
        $(".all").css("display","none");
        $(".allclass").css("display","none");
        $(".width_div").css("display","block");
    });
    
    $(".la1").click(function(){
        history.pushState(null, null, '/doctrina.index.do');
        $(".la1").addClass("select");
        $(".la2").removeClass("select");
        $(".all").css("display","block");
        $(".allclass").css("display","none");
        $(".width_div").css("display","none");
    });
    
    
    // var a=prompt("enter the number's of classrooms");
    var a=6;
    var array=["#73efbb","#ffdd7c","#ff89a9","#c1f97f","#ffa3a3","#e8aaf3"];
    for(i=0;i<=a-1;i++){
        $(".width_div").append("<div class='new'><div class='newclass' style='background:"+array[i]+"' onclick='hide()'><img src='../IMAGES/classs.png'><p title='HTML ,CSS , JS, JAVA jfhsjdfhsjd'>javascript, java,html,css,mysql,creater</p><p>Maths</p><button type='submit'>Join</button></div><img src='../IMAGES/info.png' tabindex='0' class='info' onclick='des(this)'><div class='des' tabindex='2'> <p>Created by</p> <p>Selva</p> <div class='show_des'></div> </div></div>"); 
        
        // -------------------------------- delete classroom---------------------------------
    
        $(".dlt").click(function(){
            $(this).parent().css("display","none");
        });
    }
        // p tag overflow
    
        // var len = $(".newclass img+p").text().length;
        // if(len<=30){
        //     $(".newclass img+p").css({'overflow': 'hidden','text-overflow': 'ellipsis'});
        // }
        // else{
        //     $(".newclass img+p").css({'white-space':'nowrap','overflow': 'hidden','text-overflow': 'ellipsis'});
        // }
        
    
      // --------------------------------------- join ----------------------------------------
    
    // $(".new>.newclass button").click(function(){
    //     alert("hi");
    //     $(this).text("Joined");
    //     $(this).css("background","white");
    // });
    
    // -------------- go to create class rooms ----------------------------
    
    // $(".all>div").click(function(){
    //   $(".all").css("display","none");
    //   $(".allclass").css("display","block");
    // });
    $("header>img").click(function(){
        history.pushState(null, null, '/doctrina.index.do');
        $("#myclassroom").html("");
        $(".la1").addClass("select");
        $(".la2").removeClass("select");
        $(".allclass").css("display","none");
        $(".all").css("display","block");
        $("#myclassroom").css("display","none");
    });
    
    setTimeout(function(){
        $(".box_div").scrollTop(0);
    },1000);
    
    
    // ------------------------- Notification & Invites ------------------------
    
    $(".noti>span").click(function(){
        $(".note-inv>div:first-child").css("display","block");
        $(".note-inv>div:last-child").css("display","none");
    });
    $(".noti>img").click(function(){
        $(".note-inv>div:last-child").css("display","block");
        $(".note-inv>div:first-child").css("display","none");
    });
    
    // ----------------------------- heading of the classroom--------------------------
    
    // var name;
    // $(".all>div").click(function(){
    //   var name = $(this).text();
    //   $(".h1").text(name);
    // });
    
});  


