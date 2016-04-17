// index.js

// request message on server
//Calls SimpleServlet to get the "Hello World" message
xhrGet("SimpleServlet", function(responseText){
	// add to document
	var mytitle = document.getElementById('message');
	mytitle.innerHTML = responseText;

}, function(err){
	console.log(err);
});

function getAudioResponse(){
    
	var url = "audio/response";
    
    $.ajax({
          url: url,
          type: 'POST',
          success: function(data){
              if (data == "FAIL") {
                  alert("File not found!");
              } else {
                  data; // Audio file
              }
          },
          error: function (request, status, error) {
              alert("The request failed: " + request.responseText);
          }
    });
}

function getAudioCategory(){
    
	//Change categoryName to the category name received from QR CODE
	var url = "audio/category?category=categoryName";
    
    $.ajax({
          url: url,
          type: 'GET',
          success: function(data){
              if (data == "FAIL") {
                  alert("File not found!");
              } else {
                  data; // Audio file
              }
          },
          error: function (request, status, error) {
              alert("The request failed: " + request.responseText);
          }
    });
}

//utilities
function createXHR(){
	if(typeof XMLHttpRequest != 'undefined'){
		return new XMLHttpRequest();
	}else{
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){}
		}
	}
	return null;
}
function xhrGet(url, callback, errback){
	var xhr = new createXHR();
	xhr.open("GET", url, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.timeout = 3000;
	xhr.ontimeout = errback;
	xhr.send();
}
function parseJson(str){
	return window.JSON ? JSON.parse(str) : eval('(' + str + ')');
}
function prettyJson(str){
	// If browser does not have JSON utilities, just print the raw string value.
	return window.JSON ? JSON.stringify(JSON.parse(str), null, '  ') : str;
}

