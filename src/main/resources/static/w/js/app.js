var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    // var socket = new SockJS('/better-websocket');
    var socket = new SockJS('http://localhost:8080/better-websocket');

    stompClient = Stomp.over(socket);
    var accessToken = $("#account").val();
    var headers = {
            login: 'mylogin',
            passcode: 'mypasscode',
            // additional header
            accessToken: accessToken,
            'client-id': 'my-client-id'
        };
    stompClient.connect(headers, function (frame) {
        setConnected(true);
//        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            console.log("better");
            showGreeting(JSON.parse(greeting.body).message);
        });
        stompClient.subscribe('/user/queue/come', function (greeting) {
            console.log("wonderful");
            showGreeting(JSON.parse(greeting.body).message);
        });
        stompClient.subscribe('/user/queue/offline', function (greeting) {
            console.log("offline");
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/user/tick', function (response) {
            console.log("heart beat");

            stompClient.send(
                "/app/tick",
                headers,
                "0"
                // JSON.stringify({'name': $("#name").val()})
            );
            showGreeting(response.body);
        });

        stompClient.send(
            "/app/hello",
            headers,
            JSON.stringify({'name': accessToken})
        );
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var message = $("#message").val();
    var accessToken = $("#account").val();
    var headers = {
        "Access-token": accessToken
    };

    stompClient.send(
        "/app/hello",
        headers,
        JSON.stringify({'message': message})
    );

    stompClient.send(
        "/app/tick",
        headers,
        "0"
        // JSON.stringify({'name': $("#name").val()})
    );
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function login(){
    var account=$("#account").val();
    if(account==""){
        alert("请输入账户名！");
        return;
    }
    
    $("#connect").prop("disabled", false);
    $("#disconnect").prop("disabled", false);
//    $("#send").prop("disabled", false);
    $("#login").prop("disabled", true);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#login" ).click(function() { login(); });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});

