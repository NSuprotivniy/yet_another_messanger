var Eventable = require('../modules/Eventable');
var Chat = require('../components/Chat');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

var CHAT_PAGE_SELECTOR = ".js-chat-page";

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function ChatListItemConstructor(itemData) {
    this._initEventable();

    var templateResult = templatesEngine.chatItem({
        name: itemData.name
    });

    this._root = templateResult.root;
    this._removeAction = templateResult.removeAction;
    this._name = templateResult.name;

    this.model = {
        id: itemData.id,
        name: itemData.name,
        participants: itemData.participants
    };

    this._removeAction.addEventListener('click', this);
    this._name.addEventListener('click', this);

    this._chatPage = new Chat(this.model);
}

extendConstructor(ChatListItemConstructor, Eventable);

var chatListItemConstructorPrototype = ChatListItemConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {ChatListItemConstructor}
 */
chatListItemConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
};

chatListItemConstructorPrototype.renderChatPage = function () {
    this._chatPage.render();
    this.trigger('openChat', this.model.id);
    return this;
};

/**
 * @param {Event} e
 */
chatListItemConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            switch (e.target) {
                case this._removeAction:
                    this.remove();
                    break;
                case this._name:
                    this.renderChatPage();
                    break;

            }
            break;
    }
};

/**
 * @param {String} name
 * @return {ChatListItemConstructor}
 */
chatListItemConstructorPrototype.setText = function (name) {
    if (this.model.name !== name) {
        this._name.innerHTML = name;
        this.model.name = name;
    }
    return this;
};

/**
 * @return {ChatListItemConstructor}
 */
chatListItemConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = ChatListItemConstructor;