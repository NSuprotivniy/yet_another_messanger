var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

var ENTER_KEY_CODE = 13;

var CHATS_CHAT_INPUT_SELECTOR = '.js-chats-chat-input';
var CHATS_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';
var HIDDEN_MODIFICATOR = ".__hidden";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function AddChatConstructor() {
    this._contactInput = document.querySelector(CHATS_CHAT_INPUT_SELECTOR);
    this._contactInput.addEventListener('keypress', this);
    this._contactInput.addEventListener('click', this);

    this._chatsContactList = document.querySelector(CHATS_CONTACT_LIST_SELECTOR);

    this._initEventable();
}

extendConstructor(AddChatConstructor, Eventable);

var addChatConstructorPrototype = AddChatConstructor.prototype;

addChatConstructorPrototype._addItem = function () {
    var chatInputValue = this._contactInput.value.trim();

    if (chatInputValue.length !== 0) {
        this._contactInput.value = '';
    }

    return this.trigger('newChat', {
        name: chatInputValue
    });
};

addChatConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'keypress':
            if (e.keyCode === ENTER_KEY_CODE) {
                this._addItem();
                this._chatsContactList.classList.add(HIDDEN_MODIFICATOR);
            }
            break;
        case 'click':
            this._chatsContactList.classList.remove(HIDDEN_MODIFICATOR);
            break;
    }
};

module.exports = AddChatConstructor;
