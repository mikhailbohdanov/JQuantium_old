<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, max-age=0">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="http://stat.vo.org.ua:81/sock/scripts/bootstrap/css/bootstrap.min.css">
<title>Learn WebSockets 1</title>
<script src="http://stat.vo.org.ua:81/sock/scripts/sockjs-0.3.4.min.js"></script>
<script src="http://stat.vo.org.ua:81/sock/scripts/stomp.js"></script>
</head>
<body>
  <noscript>
    <h2 style="color: #ff0000">Seems your browser doesn't support Javascript! WebSocket relies on Javascript being enabled. Please enable Javascript and reload this page!</h2>
  </noscript>
  <c:url var="imageUrl" value="http://stat.vo.org.ua:81/sock/images/user01.png" />
  <div class="container">
    <div class="row">
      <div class="col-sm-10">
        <div id="heading" class="masthead">
          <div class="pull-right">
            Logged In: <strong>${username}</strong> | ${time } | <a href="${pageContext.request.contextPath}/logout">Logout&nbsp;<span class="glyphicon glyphicon-remove"></span></a>
          </div>
          <h3 class="muted">
            <img src="${imageUrl}" />Welcome to Learning WebSockets 1
          </h3>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6">
        <p>&nbsp;</p>
        <div class="panel">
          <button id="connect" class="btn btn-success btn-sm">Connect</button>
          <button id="disconnect" class="btn btn-danger btn-sm">Disconnect</button>
        </div>
        <p />
        <div class="panel panel-default">
          <div class="panel-heading">Send Messages To WebSocket Server <select id="to">
              <option value="me">Only me</option>
              <option value="all">To all</option>
          </select></div>
          <div class="panel-body" id="conversationDiv">
            <div class="input-groupId">
              <input type="text" class="form-control" id="txtSendMessage" placeholder="Enter message"> <span class="input-groupId-btn">
                <button id="sendMessage" class="btn btn-primary">
                  <span class="glyphicon glyphicon-share-alt"></span>&nbsp;Send
                </button>
              </span>
            </div>
            <div class="alert alert-danger alert-dismissable" id="formAlert">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              <strong>Error!</strong> Message cannot be blank.
            </div>
            <div class="alert alert-info alert-dismissable" id="formInfoAlert">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              <strong>Message Sent!</strong> <br />Your message has been sent to the staticNode. You can continue to do other actions. Server response will be shown when it arrives.
            </div>
          </div>
          <div class="panel-body" id="response"></div>
        </div>
      </div>
    </div>
  </div>
  <script src="http://stat.vo.org.ua:81/sock/scripts/jquery-1.10.2.min.js"></script>
  <script src="http://stat.vo.org.ua:81/sock/scripts/bootstrap/js/bootstrap.min.js"></script>
  <script src="http://stat.vo.org.ua:81/sock/scripts/knockout-3.0.0.js"></script>
  <c:url value="/simplemessages" var="socketDest" />
  <script type="text/javascript">
            var stompClient = null;
            $(document).ready(function() {
                $("#disconnect").prop('disabled', true);
                $("#txtSendMessage").prop('disabled', true);
                $("#sendMessage").prop('disabled', true);
                $("#txtSendMessage").val("");
                $("#response").empty();
                $(".alert").hide();
                $("#connect").on("click", function(e) {
                    $("#formAlert").slideUp(400);
                    connect();
                });
                $("#disconnect").on("click", function(e) {
                    $("#formAlert").slideUp(400);
                    disconnect();
                });
                $(".alert").find(".close").on("click", function(e) {
                    e.stopPropagation();
                    e.preventDefault();
                    $(this).closest(".alert").slideUp(400);
                    $("#txtSendMessage").select();
                    $("#txtSendMessage").focus();
                });
                $("#sendMessage").on("click", function(e) {
                    var messageForServer = $("#txtSendMessage").val();

                    if (messageForServer === "") {
                        e.preventDefault();
                        $("#formAlert").slideDown(400);

                    } else {
                        $("#formAlert").slideUp(400);
                        $("#formInfoAlert").slideDown(400);
                        sendMessageToServer(messageForServer);
                    }
                });
            });
            function setConnected(connected) {
                $("#connect").prop('disabled', connected);
                $("#disconnect").prop('disabled', !connected);
                $("#sendMessage").prop('disabled', !connected);
                $("#txtSendMessage").prop('disabled', !connected)
            }
            function connect() {
                $("#response").empty();
                $("#txtSendMessage").val("");
                $("#txtSendMessage").focus();
                $("#txtSendMessage").select();
                var socket = new SockJS('${socketDest}');
                stompClient = Stomp.over(socket);
                stompClient.connect('', '', function(frame) {
                    setConnected(true);
                    console.log("Connected: " + frame);
                    showServerBroadcast("Connection established: " + frame, false);
                    stompClient.subscribe("/topic/simplemessagesresponse", function(servermessage) {
                        showServerBroadcast(JSON.parse(servermessage.body).messageContent, false);
                        $("#formInfoAlert").slideUp(400);
                        $("#txtSendMessage").val("");
                        $("#txtSendMessage").focus();
                        $("#txtSendMessage").select();
                    });
                    stompClient.subscribe("/test/answer", function(servermessage) {
                        showServerBroadcast(JSON.parse(servermessage.body).messageContent, false);
                        $("#formInfoAlert").slideUp(400);
                        $("#txtSendMessage").val("");
                        $("#txtSendMessage").focus();
                        $("#txtSendMessage").select();
                    });
                });
            }
            function disconnect() {
                $("#formAlert").slideUp(400);
                $("#formInfoAlert").slideUp(400);
                stompClient.disconnect();
                setConnected(false);
                console.log("Disconnected");
                showServerBroadcast("WebSocket connection is now terminated!", true);
            }
            function sendMessageToServer(messageForServer) {
                showServerBroadcast("Your message '" + messageForServer + "' is being sent to the staticNode.", true);
                var toMe = ($('#to').val() == 'me' ? true : false);
                stompClient.send((toMe ? '/test' : "/app/simplemessages"), {}, JSON.stringify({
                    'message' : messageForServer
                }));
            }
            function showServerBroadcast(servermessage, localMessage) {
                var decoded = $("<div/>").html(servermessage).text();

                var tmp = "";
                var serverResponse = document.getElementById("response");
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';

                if (localMessage) {
                    p.style.color = '#006600';
                    tmp = "<span class='glyphicon glyphicon-dashboard'></span> " + decoded + " (Browser time:" + getCurrentDateTime() + ")";
                } else {
                    p.style.color = '#8A0808';
                    tmp = "<span class='glyphicon glyphicon-arrow-right'></span> " + decoded;
                }
                p.innerHTML = tmp;
                serverResponse.appendChild(p);
            }
            function getCurrentDateTime() {
                var date = new Date();
                var n = date.toDateString();
                var time = date.toLocaleTimeString();
                return n + " @ " + time;
            }
        </script>
</body>
</html>