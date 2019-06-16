var IP = 'http://188.243.95.184:9090';
var xhr;

var send_json = function (main_json, fingerprint, uuid, flag)
{
    if (main_json != null) {
        xhr.setRequestHeader("fingerprint", 'fingerprint')
        console.log("sending all\n");
        console.log(main_json);
        console.log(xhr.status);
        xhr.send(JSON.stringify(main_json));
    } else {
        if (uuid != null) {
            hr.setRequestHeader("uuid", uuid)
        }
        console.log("sending\n");
        console.log(xhr.status)
        xhr.send();
    }
    console.log("sent\n");
    if (xhr.status != 200) {
        console.log("sent\n");
        alert(xhr.status);
        return null;
    } else {
        console.log("sent\n");
        var event = JSON.parse(xhr.responseText);
        if (flag == 1) {
            var token = xhr.getResponseHeader("Set-Cookie");
            var result = {
                result: event,
                cookie: token
            };
            alert(result);
            return result;
        }
        alert(event);
        return event;
    }
};

function LogonError(message, cause) {
    this.message = message;
    this.cause = cause;
    this.name = 'LogonError';
    this.stack = cause.stack;
}

var handleLogonError = function(xhr) {
    var response = JSON.parse(xhr.responseText);
    if (xhr.status === 400 && response.method === "logon_error") {
        throw new LogonError();
    }
};

var jsonSender = {

    upload_file: function (body, name)
    {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/file', false);
        var params_json = {
            body: body,
            name: '[' + name + ']'
        };
        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };
        var result = send_json(main_json, "fingerprint", null, 0);
        return result.params.uuid;
    },

    login: function (user_email, user_password) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/auth', false);

        var params_json = {
            email: user_email,
            password: user_password
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send(JSON.stringify(main_json));
        return Object.assign(
            { status: xhr.status },
            JSON.parse(xhr.responseText).params
        );
    },

    logout: function() {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', IP + '/auth', false);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        return Object.assign(
            { status: xhr.status },
            JSON.parse(xhr.responseText).params
        );
    },

    registration: function(name, email, password, password_confirmation) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/user', false);

        var params_json = {
            email: email,
            name: name,
            password: password,
            passwordConfirmation: password_confirmation,
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        var result = send_json(main_json, null, null, null, 0);
        return {name: result.params.name, uuid: result.params.uuid};
    },

    getChats: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/chats', false);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        handleLogonError(xhr);
        var result = JSON.parse(xhr.responseText);
        return Object.assign({status: xhr.status}, result.params);
    },

    deleteToken: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', IP + '/auth', false);
        var result = send_json(xhr,null, cookies, fingerprint, null, 0);
        return true;
    },

    getFile: function (uuid) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/file', false);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.setRequestHeader("uuid", uuid);
        xhr.send();
        handleLogonError(xhr);
        var result = JSON.parse(xhr.responseText);
        return Object.assign({status: xhr.status}, result.params);
    },

    getFiles: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/files', false);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        handleLogonError(xhr);
        var result = JSON.parse(xhr.responseText);
        return Object.assign({status: xhr.status}, result.params);
    },

    getChat: function (uuid) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/chat', false);
        xhr.setRequestHeader("uuid", uuid);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        return Object.assign(
            {status: xhr.status},
            JSON.parse(xhr.responseText)
        );
        return result;
    },

    getMessage: function (uuid) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/message', false);
        xhr.setRequestHeader("uuid", uuid);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        return Object.assign(
            {status: xhr.status},
            JSON.parse(xhr.responseText)
        );
        return result;
    },

    getContacts: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/contacts', false);
        xhr.setRequestHeader("fingerprint", 'fingerprint')
        xhr.send();
        var result = JSON.parse(xhr.responseText);
        console.log(result);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return Object.assign({status: xhr.status}, result.params);
    },

    createChat: function (name, participantsUUIDs) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/chat', false);

        var params_json = {
            participantsUUIDs: participantsUUIDs,
            name: name
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send(JSON.stringify(main_json));
        return Object.assign(
            {status: xhr.status},
            JSON.parse(xhr.responseText)
        );
        return result;
    },

    createFriend: function (fingerprint, email) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/contact', false);
        console.log(email);

        var params_json = {
            email: email,
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        xhr.setRequestHeader("fingerprint", fingerprint)
        xhr.send(JSON.stringify(main_json));
        if (xhr.status !== 200) {
            return null;
        } else {
            var event = JSON.parse(xhr.responseText);
            return event;
        }
    },

    create_msg(chat_uuid, text)
    {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/message', false);

        var params_json = {
            chatUUID: chat_uuid,
            text: text
        };
        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };
        xhr.setRequestHeader("fingerprint", 'fingerprint')
        xhr.send(JSON.stringify(main_json));
        if (xhr.status != 200) {
            return null;
        } else {
            var event = JSON.parse(xhr.responseText);
            return event.params;
        }
    }
}
module.exports = jsonSender;

