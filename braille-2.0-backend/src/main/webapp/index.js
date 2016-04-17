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