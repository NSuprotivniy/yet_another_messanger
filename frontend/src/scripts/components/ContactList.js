var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');

var ContactListItem = require('../components/ContactListItem');

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
    var item = new ContactListItem(Object.assign(
        {
            id: itemsIdIterator++,
        },
        contactItemData
    ));

    this._items.push(item);

    item.on('remove', this._onItemRemove, this)
        .render(this._contactList);


    item.render(this._chatAddContactList);

    return this;
};


/**
 * @param {Number} itemId
 * @return {ChatsListItem|null}
 * @private
 */
contactListConstructorPrototype._getItemById = function (itemId) {
    var items = this._items;

    for (var i = items.length; i-- ;) {
        if (items[i].model.id === itemId) {
            return items[i];
        }
    }

    return null;
};

contactListConstructorPrototype._onItemRemove = function (itemId) {
    var contactItemComponent = this._getItemById(itemId);

    if (contactItemComponent) {
        contactItemComponent.off('remove', this._onItemRemove, this);
        var contactItemComponentIndex = this._items.indexOf(contactItemComponent);
        this._items.splice(contactItemComponentIndex, 1);
    }

    return this;
};


module.exports = ContactListConstructor;