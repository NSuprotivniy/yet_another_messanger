var socket = new WebSocket("ws://javascript.ru/ws");

socket.onopen = function() {};
  
socket.onclose = function(event) {};

socket.onmessage = function(event) {
var event = JSON.parse(event.data);
if (event.method == "message_broadcast_create" || event.method == "file_broadcast_create")
{
    var myDate = new Date(event.params.time);
    var date = (myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds());
    //new msg
    var msg_info = {
        text: event.params.text,
        uuid: event.params.uuid,
        chatUUID: event.params.chatUUID,
        chatName: event.params.chatName,
        createAt: myDate,
        creatorUUID: event.params.creatorUUID,
        creatorName: event.params.creatorName
    };
    //notify or add
}
else{
    //new chat
}
};

socket.onerror = function(error) {

};