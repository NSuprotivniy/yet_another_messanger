var IP = ''
var xhr;

var send_json = function (main_json, cookies, fingerprint, uuid, flag)
{
    if (main_json != null) {
        xhr.setRequestHeader("cookies", cookies)
        xhr.setRequestHeader("fingerprint", fingerprint)
        xhr.send(JSON.stringify(main_json, cookies, fingerprint));
    } else {
        if (uuid != null) {
            hr.setRequestHeader("uuid", uuid)
        }
        xhr.send();
    }

    if (xhr.status != 200) {
        // обработать ошибку
        console.log(xhr.status + ': ' + xhr.statusText); // пример вывода: 404: Not Found
        return null;
    } else {
        // вывести результат
        //var myHeader = xhr.getResponseHeader('token');

        var event = JSON.parse(xhr.responseText);
        /* var response = {
           token: myHeader,
           uuid: event.params.uuid,
           name: event.params.name
         };*/
        if (flag == 1) {
            var token = xhr.getResponseHeader("Set-Cookie");
            var result = {
                result: event,
                cookie: token
            };
            return result;
        }
        console.log(event); // responseText -- текст ответа.
        return event;
    }
}

var jsonSender = {
    login: function (user_email, user_password) {
        console.log(user_email);
        xhr = new XMLHttpRequest();
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

        console.log(JSON.stringify(main_json));
        var result = send_json(main_json, null, null, null, 1);
        console.log(result);
        //localStorage.setItem('token',token);
        return {name: result.result.params.name, uuid: result.result.params.uuid, cookie: result.token};
    },

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

    getChats: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/chats', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    deleteToken: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', IP + '/auth', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
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
        return true;
    },

    createFriend: function (cookies, fingerprint, name, email) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/contact', false);

        var participantsUUIDs = JSON.stringify(uuids);

        var params_json = {
            email: email,
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        var result = send_json(main_json, cookies, fingerprint, null, 0);
        return true;
    }
}

