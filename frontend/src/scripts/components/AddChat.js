var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

var ENTER_KEY_CODE = 13;

var CHATS_CHAT_INPUT_SELECTOR = '.js-chats-chat-input';
var CHATS_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';
var HIDDEN_MODIFICATOR = "__hidden";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function AddChatConstructor() {
    this._input = document.querySelector(CHATS_CHAT_INPUT_SELECTOR);
    this._input.addEventListener('keypress', this);
    this._input.addEventListener('click', this);
    this._checkedContacts = [];

    this._chatsContactList = document.querySelector(CHATS_CONTACT_LIST_SELECTOR);

    this._initEventable();
}

extendConstructor(AddChatConstructor, Eventable);

var addChatConstructorPrototype = AddChatConstructor.prototype;

addChatConstructorPrototype._addItem = function () {
    var chatInputValue = this._input.value.trim();

    if (chatInputValue.length !== 0) {
        this._input.value = '';
    }

    contactsModels = [];
    this._checkedContacts.forEach(function(contact) {
        contactsModels.push(contact.model);
    });

    return this.trigger('newChat', {
        name: chatInputValue,
        participants: contactsModels
    });
};

addChatConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'keypress':
            if (e.keyCode === ENTER_KEY_CODE) {
                this._addItem();
                this._checkedContacts.forEach(function(contact) {
                    contact.uncheck();
                });
                this._checkedContacts = [];
                this._chatsContactList.classList.add(HIDDEN_MODIFICATOR);
            }
            break;
        case 'click':
            this._chatsContactList.classList.remove(HIDDEN_MODIFICATOR);
            break;
    }
};

addChatConstructorPrototype._onContactItemChecked = function (contact) {
    this._checkedContacts.push(contact);
}

addChatConstructorPrototype._onContactItemUnchecked = function (contact) {
    var contactItemComponentIndex = this._checkedContacts.indexOf(contact);
    if (contactItemComponentIndex != -1) {
        this._checkedContacts.splice(contactItemComponentIndex, 1);
    }
}

module.exports = AddChatConstructor;
