var IP = 'http://188.243.95.184:9090'
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
}

var jsonSender = {

    upload_file: function (body, name, fingerprint)
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
        var result = send_json(main_json, fingerprint, null, 0);
        return result.params.uuid;
    },

    login: function (user_email, user_password) {
        console.log(user_email);
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
            {status: xhr.status},
            JSON.parse(xhr.responseText)
        );
    }
    ,

    registrate: function(name, password, email) {
        console.log(user_email);
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/user', false);

        var params_json = {
            email: user_email,
            password: user_password,
            name: name
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        // console.log(JSON.stringify(main_json, null, null));
        var result = send_json(main_json, null, null, null, 0);
        //localStorage.setItem('token',token);
        return {name: result.params.name, uuid: result.params.uuid};
    },

    getChats: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/chats', false);
        xhr.setRequestHeader("fingerprint", "fingerprint");
        xhr.send();
        var result = JSON.parse(xhr.responseText)
        return Object.assign({status: xhr.status}, result.params);
    },

    deleteToken: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', IP + '/auth', false);
        var result = send_json(xhr,null, cookies, fingerprint, null, 0);
        return true;
    },

    getFile: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/file', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getFiles: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/files', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    getChat: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/file', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getMsg: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/message', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getContacts: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/contacts', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    createChat: function (cookies, fingerprint, name, uuids) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/chat', false);

        var participantsUUIDs = JSON.stringify(uuids);

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

        var result = send_json(main_json, cookies, fingerprint, null, 0);
        return result;
    },

    createFriend: function (fingerprint, email) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/contact', false);

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
        xhr.send(main_json);
    if (xhr.status != 200) {
        return null;
    } else {
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
        return event;
    }
    },

    create_msg(chat_uuid, fingerrint)
    {
    }
}
module.exports = jsonSender;

