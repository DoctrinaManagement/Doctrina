var id ;
var time;
var currentId;
     
  function PlayerStart (video_id, time, current_id){
      var tag = document.createElement('script');
    
      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName('script')[0];
      
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
      id = video_id;//'3AtDnEC4zak';
      time = time;
      currentId = current_id;
      
       $(".video_div").css({
            "padding": "0"
        });
        $(".video_div+section").css({
            "display": "block"
        });
      document.getElementById("player").style.display="block";
    }
  
  var player;
  function onYouTubeIframeAPIReady() {alert(id);
    player = new YT.Player('player', {
      height: '390',
      width: '640',
      videoId: id,
      playerVars: {'controls':1, 'rel':0, 'showinfo':0, start:time},
      events: {
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
      }
    });
  }

  function onPlayerReady(event) {
    event.target.playVideo();
  }

  function onPlayerStateChange(event) {
      var duration = Math.floor(event.target.getDuration());
      var current = Math.floor(event.target.getCurrentTime());
        $.post("/videostatus", {"total_duration":duration+"", "current_duration":current+"","id":currentId}, function(data, status) {
        })
  }
  function stopVideo() {
    player.stopVideo();
  }