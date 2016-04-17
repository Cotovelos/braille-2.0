/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
		
			

		
		ScanBarCode(function(qrText){
			tellCategory(qrText, recordAnswer)
		},
		function(){
			
		});
				
		
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();



function ScanBarCode(successCallback, errorCallback) {
	cordova.plugins.barcodeScanner.scan(
      function (result) {
          /*alert("We got a barcode\n" +
                "Result: " + result.text + "\n" +
                "Format: " + result.format + "\n" +
                "Cancelled: " + result.cancelled);*/
			console.log(result);
			successCallback.call(this, result.text);
      }, 
      function (error) {
          alert("Scanning failed: " + error);
		  errorCallback.call(this);	
      }
   );
}

function tellCategory(qrText, successCallback, errorCallback) {
	var category = getCategoryFromQr(qrText);
	var media = new Media("http://braille2.mybluemix.net/audio/category?category="+category, 
		function(){successCallback.call(this, qrText)}, 
		errorCallback);
	media.play();
}

function tellContent(qrText, successCallback, errorCallback) {
	var url = getUrlFromQr(qrText);
	var media = new Media(url, successCallback, errorCallback);
	media.play();
}

function recordAnswer(qrText) {
	// capture callback
	
		var captureSuccess = function(mediaFiles) {
		var i, path, len;
		for (i = 0, len = mediaFiles.length; i < len; i += 1) {
			path = mediaFiles[i].fullPath;
			console.log(mediaFiles[i]);
			uploadFile(mediaFiles[i]);
		}
		tellContent(qrText);
		};

		// capture error callback
		var captureError = function(error) {
		navigator.notification.alert('Error code: ' + error.code, null, 'Capture Error');
		};

		// start audio capture
		navigator.device.capture.captureAudio(captureSuccess, captureError, {limit:1});
}

function recSuc(data)
{
	
}

function uploadFile(mediaFile) {
	var ft = new FileTransfer(),
		path = mediaFile.fullPath,
		name = mediaFile.name;

	ft.upload(path,
		"http://braille2.mybluemix.net/audio/response",
		function(result) {
			console.log('Upload success: ' + result);
		},
		function(error) {
			console.log('Error uploading file ' + path + ': ' + error.code);
		},
		{ fileName: name });
}
	
function getCategoryFromQr(qrText){
	var splits = qrText.split("@#");
	if(splits.length > 1){
		return splits[1].substring(4);
	}
}

function getUrlFromQr(qrText){
	var splits = qrText.split("@#");
	if(splits.length > 2){
		console.log(splits[2]);
		console.log(splits[2].substring(6));
		return splits[2].substring(6);
	}
}