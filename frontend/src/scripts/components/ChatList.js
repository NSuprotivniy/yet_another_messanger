var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var jsonSender = require('../modules/JsonSender');
var ChatListItem = require('../components/ChatListItem');
var websocketInstance = require('../modules/WebsocketInstance');

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
    if (localStorage.getItem("userUUID") !== null) {
        try { this.loadChats(); } catch (e) {}
    }
    websocketInstance.on("websocketChat", this._addChat, this);
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
chatListConstructorPrototype.createItem = function (model) {
    var response = jsonSender.createChat(model.name, model.participantsUUIDs.map(function (p) {p.uuid}));
    this.addItem(response).renderChatPage();
    return this;
};

chatListConstructorPrototype.addItem = function (model) {
    var item = new ChatListItem(Object.assign(
        {
            id: itemsIdIterator++,
        },
        model
    ));

    this._items.push(item);

    item
        .on('remove', this._onItemRemove, this)
        .on('openChat', this._openChat, this)
        .render(this._chatsList);

    this.trigger('itemAdd', item);

    return item;
};

chatListConstructorPrototype.loadChats = function() {
    var response = jsonSender.getChats();
    if (response.status === 200) {
        for (let i = 0; i < response.uuids.length; i++) {
            this.addItem({uuid: response.uuids[i], name: response.names[i]});
        }
    }
};

chatListConstructorPrototype.clear = function() {
    var items = this._items.slice();
    items.forEach(function (i) {
       i.remove();
    });
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