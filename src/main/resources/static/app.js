const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8086/lobby'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/lobbies/' + $("#userId").val(), (lobby) => {
        checkMessage(JSON.parse(lobby.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#lobbyTraffic").show();
        $("#simCoords").show();
    }
    else {
        $("#lobbyTraffic").hide();
        $("#simCoords").hide();
    }
    $("#messages").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendUserId() {
    stompClient.publish({
        destination: "/lobby",
        body: JSON.stringify({'userId': $("#userId").val()})
    });
}

function checkMessage(body) {
    if (body.message != undefined && body.message != "") { //If lobby traffic
        console.log("Message:" + body);
        showMessage(body.message);
    }
    else { //Else junk
        console.log("ERROR!!!")
    }
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendUserId());
});