var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var jsonSender = require('../modules/JsonSender');
var Contact = require('../components/Contact');

var CONTACT_LIST_SELECTOR = '.js-contact-list';
var CHAT_ADD_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';
var itemsIdIterator = 0;

/**
 * @extends {Eventable}
 * @constructor
 */
function ContactListConstructor() {
    /**
     * @type {Array.<ChatsConstructor>}
     * @private
     */
    this._items = [];
    this._itemsChat = [];
    this._contactList = document.querySelector(CONTACT_LIST_SELECTOR);
    this._chatAddContactList = document.querySelector(CHAT_ADD_CONTACT_LIST_SELECTOR);

    this._initEventable();
}

extendConstructor(ContactListConstructor, Eventable);

var contactListConstructorPrototype = ContactListConstructor.prototype;

/**
 * @return {Number}
 */
contactListConstructorPrototype.getItemsCount = function () {
    return this._items.length;
};

/**
 * @param {Object} contactItemData
 * @return {ContactListConstructor}
 */
contactListConstructorPrototype.createItem = function (contactItemData) {

    var mail = contactItemData.email;
    var answer = jsonSender.createFriend("fingering",mail);
    contactItemData = Object.assign(contactItemData, answer.params.uuid, answer.params.name);
    
    //contactItemData

    var itemMainContactList = new Contact(Object.assign(
        {
            id: contactItemData.uuid,
        },
        contactItemData
    ), "main");

    var itemChatContactList = new Contact(Object.assign(
        {
            id: itemsIdIterator++,
        },
        contactItemData
    ), "chat");

    this._items.push(itemMainContactList);
    this._itemsChat.push(itemChatContactList);

    itemMainContactList
        .on('remove', this._onItemMainContactListRemove, this)
        .render(this._contactList);

    itemChatContactList
        .on('remove', this._onItemChatContactListRemove, this)
        .on('checked', this._onItemChecked, this)
        .on('unchecked', this._onItemChecked, this)
        .render(this._chatAddContactList);

    return this;
};


/**
 * @param {Number} itemId
 * @return {ChatsListItem|null}
 * @private
 */
contactListConstructorPrototype._getItemById = function (items, itemId) {
    for (var i = items.length; i-- ;) {
        if (items[i].model.id === itemId) {
            return items[i];
        }
    }

    return null;
};

contactListConstructorPrototype._onItemMainContactListRemove = function (itemId) {
    var contactItemComponent = this._getItemById(this._items, itemId);
    var contactItemComponentChat = this._getItemById(this._itemsChat, itemId);

    if (contactItemComponent) {
        contactItemComponent.off('remove', this._onItemRemove, this);
        var contactItemComponentIndex = this._items.indexOf(contactItemComponent);
        this._items.splice(contactItemComponentIndex, 1);
    }
    if (contactItemComponentChat) {
        contactItemComponentChat.remove();
    }

    return this;
};

contactListConstructorPrototype._onItemChatContactListRemove = function (itemId) {
    var contactItemComponent = this._getItemById(this._items, itemId);
    var contactItemComponentChat = this._getItemById(this._itemsChat, itemId);

    if (contactItemComponentChat) {
        contactItemComponentChat.off('remove', this._onItemRemove, this);
        var contactItemComponentIndex = this._items.indexOf(contactItemComponentChat);
        this._itemsChat.splice(contactItemComponentIndex, 1);
    }
    if (contactItemComponent) {
        contactItemComponent.remove();
    }

    return this;
};

contactListConstructorPrototype._onItemChecked = function (contact) {
    this.trigger('itemChecked', contact);
}

contactListConstructorPrototype._onItemUnchecked = function (itemId) {
    this.trigger('itemUnchecked', contact);
}


module.exports = ContactListConstructor;