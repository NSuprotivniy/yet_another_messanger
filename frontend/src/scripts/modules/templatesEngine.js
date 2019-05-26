
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
        var name = root.querySelector('.js-chat-add-contact-list-item_name');

        if (data.name) {
            name.innerText = data.name;
        }

        return {
            root: root,
            name: name,
            removeAction: removeAction
        };
    }
};

module.exports = templatesEngine;