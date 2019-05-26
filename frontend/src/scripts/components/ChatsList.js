var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');

var ChatsListItem = require('../components/ChatsListItem');

var CHATS_LIST_SELECTOR = '.js-chats-list';
var itemsIdIterator = 0;

/**
 * @extends {Eventable}
 * @constructor
 */
function ChatsListConstructor() {
    /**
     * @type {Array.<ChatsConstructor>}
     * @private
     */
    this._items = [];
    this._chatsList = document.querySelector(CHATS_LIST_SELECTOR);

    this._initEventable();
}

extendConstructor(ChatsListConstructor, Eventable);

var chatsListConstructorPrototype = ChatsListConstructor.prototype;

/**
 * @return {Number}
 */
chatsListConstructorPrototype.getItemsCount =function () {
    return this._items.length;
};

/**
 * @param {Object} chatsItemData
 * @return {ChatsListConstructor}
 */
chatsListConstructorPrototype.createItem = function (chatsItemData) {
    var item = new ChatsListItem(Object.assign(
        {
            id: itemsIdIterator++,
        },
        chatsItemData
    ));

    this._items.push(item);

    item.on('remove', this._onItemRemove, this)
        .render(this._chatsList);

    this.trigger('itemAdd', item);

    return this;
};


/**
 * @param {Number} itemId
 * @return {ChatsListItem|null}
 * @private
 */
chatsListConstructorPrototype._getItemById = function (itemId) {
    var items = this._items;

    for (var i = items.length; i-- ;) {
        if (items[i].model.id === itemId) {
            return items[i];
        }
    }

    return null;
};

chatsListConstructorPrototype._onItemRemove = function (itemId) {
    var chatsItemComponent = this._getItemById(itemId);

    if (chatsItemComponent) {
        chatsItemComponent.off('remove', this._onItemRemove, this);
        var chatsItemComponentIndex = this._items.indexOf(chatsItemComponent);
        this._items.splice(chatsItemComponentIndex, 1);
    }

    return this;
};


module.exports = ChatsListConstructor;