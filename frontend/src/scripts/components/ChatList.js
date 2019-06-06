var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var jsonSender = require('../modules/JsonSender');
var ChatListItem = require('../components/ChatListItem');

var CHATS_LIST_SELECTOR = '.js-chats-list';
var itemsIdIterator = 0;

/**
 * @extends {Eventable}
 * @constructor
 */
function ChatListConstructor() {
    /**
     * @type {Array.<ChatsConstructor>}
     * @private
     */
    this._initEventable();

    this._items = [];
    this._chatsList = document.querySelector(CHATS_LIST_SELECTOR);
    var response = jsonSender.getChats();
    console.log(response);
    if (response.status === 200) {
        for (let i = 0; i < response.uuids.length; i++) {
            this.createItem({uuid: response.uuids[i], name: response.names[i]});
        }
    }
}

extendConstructor(ChatListConstructor, Eventable);

var chatListConstructorPrototype = ChatListConstructor.prototype;

/**
 * @return {Number}
 */
chatListConstructorPrototype.getItemsCount = function () {
    return this._items.length;
};

/**
 * @param {Object} chatsItemData
 * @return {ChatListConstructor}
 */
chatListConstructorPrototype.createItem = function (chatsItemData) {
    var item = new ChatListItem(Object.assign(
        {
            id: itemsIdIterator++,
        },
        chatsItemData
    ));

    this._items.push(item);

    item
        .on('remove', this._onItemRemove, this)
        .on('openChat', this._openChat, this)
        .render(this._chatsList)
        .renderChatPage();

    this.trigger('itemAdd', item);

    return this;
};


/**
 * @param {Number} itemId
 * @return {ChatListItem|null}
 * @private
 */
chatListConstructorPrototype._getItemById = function (itemId) {
    var items = this._items;

    for (var i = items.length; i-- ;) {
        if (items[i].model.id === itemId) {
            return items[i];
        }
    }

    return null;
};

chatListConstructorPrototype._onItemRemove = function (itemId) {
    var chatsItemComponent = this._getItemById(itemId);

    if (chatsItemComponent) {
        chatsItemComponent.off('remove', this._onItemRemove, this);
        var chatsItemComponentIndex = this._items.indexOf(chatsItemComponent);
        this._items.splice(chatsItemComponentIndex, 1);
    }

    return this;
};

chatListConstructorPrototype._openChat = function(modelId) {
    this.trigger('openChat', modelId);
}


module.exports = ChatListConstructor;