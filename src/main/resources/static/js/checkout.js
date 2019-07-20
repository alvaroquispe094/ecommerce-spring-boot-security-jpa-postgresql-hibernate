function onReady(callback) {
	$('html, body').css({
	    'overflow': 'hidden',
	    'height': '100%'
	});
//	$("body").addClass("no_scroll");
	
  var intervalId = window.setInterval(function() {
    if (document.getElementsByTagName('body')[0] !== undefined) {
      window.clearInterval(intervalId);
      callback.call(this);
    }
  }, 1000);
  
  
}

function setVisible(selector, visible) {
  document.querySelector(selector).style.display = visible ? 'block' : 'none';
}

onReady(function() {
  setVisible('.page', true);
  setVisible('#loading', false);
  $('html, body').css({
	    'overflow': 'auto',
	   'height': 'auto'
	});
//  $("body").removeClass("no_scroll");
});