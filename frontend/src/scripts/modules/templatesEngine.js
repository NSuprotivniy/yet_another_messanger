
var div = document.createElement('div');

function getTemplateRootNode(scriptId) {
    var scriptTag = document.getElementById(scriptId);
    div.innerHTML = scriptTag.innerHTML;
    var result = div.children[0];
    div.removeChild(result);
    return result;
}

var templatesEngine = {
    chatItem: function (data) {
        var root = getTemplateRootNode('chatsListItem');

        var removeAction = root.querySelector('.js-chats-list-item_remove-action');
        var name = root.querySelector('.js-chats-list-item_name');

        if (data.name) {
            name.innerText = data.name;
        }

        return {
            root: root,
            name: name,
            removeAction: removeAction
        };
    },

    contactItem: function (data) {
        var root = getTemplateRootNode('contactListItem');

        var removeAction = root.querySelector('.js-contact-list-item_remove-action');
        var name = root.querySelector('.js-contact-list-item_name');

        if (data.name) {
            name.innerText = data.name;
        }

        return {
            root: root,
            name: name,
            removeAction: removeAction
        };
    },

    chatAddContactItem: function (data) {
        var root = getTemplateRootNode('chatAddContactListItem');

        var removeAction = root.querySelector('.js-chat-add-contact-list-item_remove-action');
        var checkAction = root.querySelector(".js-chat-add-contact-list-item_check");
        var name = root.querySelector('.js-chat-add-contact-list-item_name');

        if (data.name) {
            name.innerText = data.name;
        }

        return {
            root: root,
            name: name,
            removeAction: removeAction,
            checkAction: checkAction
        };
    },

    chat: function (data) {
        var root = getTemplateRootNode('chat');
        var name = root.querySelector('.js-chat_name');
        var messageList = root.querySelector('.js-chat_message-list');
        var messageInput = root.querySelector('.js-chat_message-text');
        if (data.name) {
            name.innerText = data.name;
        }
        return {
            root: root,
            name: name,
            messageList: messageList,
            messageInput: messageInput
        };
    },

    message: function (data) {
        var root = getTemplateRootNode('message');
        var text = root.querySelector('.js-message_text');
        var creatorName = root.querySelector('.js-message_creator-name');
        var createdAt = root.querySelector('.js-message_created-at');
        var avatar = root.querySelector('.js-message_creator-avatar');

        if (data.text) {
            text.innerText = data.text;
        }
        if (data.creatorName) {
            creatorName.innerText = data.creatorName;
        }
        if (data.createdAt) {
            var myDate = new Date( data.createdAt);
            var date = (myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds());
            createdAt.innerText = date;
        }
        if (data.avatar) {
            avatar.innerText = data.avatar;
        }

        return {
            root: root
        };
    },

    file: function (data) {
        var root = getTemplateRootNode('file');
        var name = root.querySelector('.js-file_name');
        var creatorName = root.querySelector('.js-file_creator-name');
        var createdAt = root.querySelector('.file_created-at');
        var removeAction = root.querySelector('.js-file_remove-action');

        if (data.name) {
            name.innerText = data.name;
        }
        if (data.creatorName) {
            creatorName.innerText = data.creatorName;
        }
        if (data.createdAt) {
            var myDate = new Date( data.createdAt);
            var date = (myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds());
            createdAt.innerText = date;
        }

        return {
            root: root,
            removeAction: removeAction
        };
    },

    fileToChoose: function(data) {
        var root = getTemplateRootNode('fileToChoose');
        var name = root.querySelector('.js-file-to-choose_name');
        var id = root.querySelector('.js-file-to-choose_id');
        if (data.name) {
            name.innerText = data.name;
        }
        if (data.id) {
            id.innerText = data.id;
        }
        return {
            root: root,
            id: id
        };
    }
};

module.exports = templatesEngine;