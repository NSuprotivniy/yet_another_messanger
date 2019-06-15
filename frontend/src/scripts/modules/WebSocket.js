var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');



/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function WebsocketConstructor() {
    var socket = new WebSocket("ws://188.243.95.184:9091/");
    var ctx = this;
    socket.onmessage = function(event) {
        var event = JSON.parse(event.data);
        switch (event.method) {
            case "message_broadcast_create":
                ctx.trigger('websocketMessage', event.params);
                break;
            case "chat_broadcast_create":
                ctx.trigger('websocketMessage', event.params);
                break;
            case "logon_error":
                ctx.trigger('logonError', event.params);
                break;
        }
    };
    this._initEventable();
}

extendConstructor(WebsocketConstructor, Eventable);

var websocketConstructorPrototype = WebsocketConstructor.prototype;



module.exports = WebsocketConstructor;
