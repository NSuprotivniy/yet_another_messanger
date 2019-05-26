/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/scripts/main.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/scripts/components/AddChat.js":
/*!*******************************************!*\
  !*** ./src/scripts/components/AddChat.js ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\nvar Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\n\nvar ENTER_KEY_CODE = 13;\n\nvar CHATS_CHAT_INPUT_SELECTOR = '.js-chats-chat-input';\nvar CHATS_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';\nvar HIDDEN_MODIFICATOR = \".__hidden\";\n\n/**\n * @implements {EventListener}\n * @extends {Eventable}\n * @constructor\n */\nfunction AddChatConstructor() {\n    this._contactInput = document.querySelector(CHATS_CHAT_INPUT_SELECTOR);\n    this._contactInput.addEventListener('keypress', this);\n    this._contactInput.addEventListener('click', this);\n\n    this._chatsContactList = document.querySelector(CHATS_CONTACT_LIST_SELECTOR);\n\n    this._initEventable();\n}\n\nextendConstructor(AddChatConstructor, Eventable);\n\nvar addChatConstructorPrototype = AddChatConstructor.prototype;\n\naddChatConstructorPrototype._addItem = function () {\n    var chatInputValue = this._contactInput.value.trim();\n\n    if (chatInputValue.length !== 0) {\n        this._contactInput.value = '';\n    }\n\n    return this.trigger('newChat', {\n        name: chatInputValue\n    });\n};\n\naddChatConstructorPrototype.handleEvent = function (e) {\n    switch (e.type) {\n        case 'keypress':\n            if (e.keyCode === ENTER_KEY_CODE) {\n                this._addItem();\n                this._chatsContactList.classList.add(HIDDEN_MODIFICATOR);\n            }\n            break;\n        case 'click':\n            this._chatsContactList.classList.remove(HIDDEN_MODIFICATOR);\n            break;\n    }\n};\n\nmodule.exports = AddChatConstructor;\n\n\n//# sourceURL=webpack:///./src/scripts/components/AddChat.js?");

/***/ }),

/***/ "./src/scripts/components/AddContact.js":
/*!**********************************************!*\
  !*** ./src/scripts/components/AddContact.js ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\nvar Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\n\nvar ENTER_KEY_CODE = 13;\n\nvar CONTACT_ADD_INPUT_SELECTOR = '.js-contact-add-input';\nvar HIDDEN_MODIFICATOR = \".__hidden\";\n\n/**\n * @implements {EventListener}\n * @extends {Eventable}\n * @constructor\n */\nfunction AddContactConstructor() {\n    this._contactInput = document.querySelector(CONTACT_ADD_INPUT_SELECTOR);\n    this._contactInput.addEventListener('keypress', this);\n    this._contactInput.addEventListener('click', this);\n\n    this._initEventable();\n}\n\nextendConstructor(AddContactConstructor, Eventable);\n\nvar addContactConstructorPrototype = AddContactConstructor.prototype;\n\naddContactConstructorPrototype._addItem = function () {\n    var contactInputValue = this._contactInput.value.trim();\n\n    if (contactInputValue.length !== 0) {\n        this._contactInput.value = '';\n    }\n\n    return this.trigger('newContact', {\n        name: contactInputValue\n    });\n};\n\naddContactConstructorPrototype.handleEvent = function (e) {\n    switch (e.type) {\n        case 'keypress':\n            if (e.keyCode === ENTER_KEY_CODE) {\n                this._addItem();\n            }\n            break;\n    }\n};\n\nmodule.exports = AddContactConstructor;\n\n\n//# sourceURL=webpack:///./src/scripts/components/AddContact.js?");

/***/ }),

/***/ "./src/scripts/components/ChatsList.js":
/*!*********************************************!*\
  !*** ./src/scripts/components/ChatsList.js ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\nvar extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\n\nvar ChatsListItem = __webpack_require__(/*! ../components/ChatsListItem */ \"./src/scripts/components/ChatsListItem.js\");\n\nvar CHATS_LIST_SELECTOR = '.js-chats-list';\nvar itemsIdIterator = 0;\n\n/**\n * @extends {Eventable}\n * @constructor\n */\nfunction ChatsListConstructor() {\n    /**\n     * @type {Array.<ChatsConstructor>}\n     * @private\n     */\n    this._items = [];\n    this._chatsList = document.querySelector(CHATS_LIST_SELECTOR);\n\n    this._initEventable();\n}\n\nextendConstructor(ChatsListConstructor, Eventable);\n\nvar chatsListConstructorPrototype = ChatsListConstructor.prototype;\n\n/**\n * @return {Number}\n */\nchatsListConstructorPrototype.getItemsCount =function () {\n    return this._items.length;\n};\n\n/**\n * @param {Object} chatsItemData\n * @return {ChatsListConstructor}\n */\nchatsListConstructorPrototype.createItem = function (chatsItemData) {\n    var item = new ChatsListItem(Object.assign(\n        {\n            id: itemsIdIterator++,\n        },\n        chatsItemData\n    ));\n\n    this._items.push(item);\n\n    item.on('remove', this._onItemRemove, this)\n        .render(this._chatsList);\n\n    this.trigger('itemAdd', item);\n\n    return this;\n};\n\n\n/**\n * @param {Number} itemId\n * @return {ChatsListItem|null}\n * @private\n */\nchatsListConstructorPrototype._getItemById = function (itemId) {\n    var items = this._items;\n\n    for (var i = items.length; i-- ;) {\n        if (items[i].model.id === itemId) {\n            return items[i];\n        }\n    }\n\n    return null;\n};\n\nchatsListConstructorPrototype._onItemRemove = function (itemId) {\n    var chatsItemComponent = this._getItemById(itemId);\n\n    if (chatsItemComponent) {\n        chatsItemComponent.off('remove', this._onItemRemove, this);\n        var chatsItemComponentIndex = this._items.indexOf(chatsItemComponent);\n        this._items.splice(chatsItemComponentIndex, 1);\n    }\n\n    return this;\n};\n\n\nmodule.exports = ChatsListConstructor;\n\n//# sourceURL=webpack:///./src/scripts/components/ChatsList.js?");

/***/ }),

/***/ "./src/scripts/components/ChatsListItem.js":
/*!*************************************************!*\
  !*** ./src/scripts/components/ChatsListItem.js ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\nvar extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\nvar templatesEngine = __webpack_require__(/*! ../modules/templatesEngine */ \"./src/scripts/modules/templatesEngine.js\");\n\n/**\n * @param itemData\n * @implements {EventListener}\n * @constructor\n */\nfunction ChatsListItemConstructor(itemData) {\n    this._initEventable();\n\n    var templateResult = templatesEngine.chatItem({\n        name: itemData.name\n    });\n\n    this._root = templateResult.root;\n    this._removeAction = templateResult.removeAction;\n    this._name = templateResult.name;\n\n    this.model = {\n        id: itemData.id,\n        name: itemData.name\n    };\n\n    if (itemData.isReady) {\n        this._setReadyModificator(true);\n    }\n\n    this._removeAction.addEventListener('click', this);\n    this._name.addEventListener('input', this);\n}\n\nextendConstructor(ChatsListItemConstructor, Eventable);\n\nvar chatsListItemConstructorPrototype = ChatsListItemConstructor.prototype;\n\n/**\n * @param {HTMLElement} parent\n * @return {ChatsListItemConstructor}\n */\nchatsListItemConstructorPrototype.render = function (parent) {\n    parent.appendChild(this._root);\n    return this;\n};\n\n/**\n * @param {Event} e\n */\nchatsListItemConstructorPrototype.handleEvent = function (e) {\n    switch (e.type) {\n        case 'click':\n            if (e.target === this._removeAction) {\n                this.remove();\n            }\n            break;\n        case 'input':\n            this.setText(this._name.innerText);\n            break;\n    }\n};\n\n/**\n * @param {String} name\n * @return {ChatsListItemConstructor}\n */\nchatsListItemConstructorPrototype.setText = function (name) {\n    if (this.model.name !== name) {\n        this._name.innerHTML = name;\n        this.model.name = name;\n    }\n    return this;\n};\n\n\n/**\n * @return {ChatsListItemConstructor}\n */\nchatsListItemConstructorPrototype.remove = function () {\n    this._root.parentNode.removeChild(this._root);\n    this.trigger('remove', this.model.id);\n    return this;\n};\n\nmodule.exports = ChatsListItemConstructor;\n\n//# sourceURL=webpack:///./src/scripts/components/ChatsListItem.js?");

/***/ }),

/***/ "./src/scripts/components/ContactList.js":
/*!***********************************************!*\
  !*** ./src/scripts/components/ContactList.js ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\nvar extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\n\nvar ContactListItem = __webpack_require__(/*! ../components/ContactListItem */ \"./src/scripts/components/ContactListItem.js\");\n\nvar CONTACT_LIST_SELECTOR = '.js-contact-list';\nvar CHAT_ADD_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';\nvar itemsIdIterator = 0;\n\n/**\n * @extends {Eventable}\n * @constructor\n */\nfunction ContactListConstructor() {\n    /**\n     * @type {Array.<ChatsConstructor>}\n     * @private\n     */\n    this._items = [];\n    this._contactList = document.querySelector(CONTACT_LIST_SELECTOR);\n    this._chatAddContactList = document.querySelector(CHAT_ADD_CONTACT_LIST_SELECTOR);\n\n    this._initEventable();\n}\n\nextendConstructor(ContactListConstructor, Eventable);\n\nvar contactListConstructorPrototype = ContactListConstructor.prototype;\n\n/**\n * @return {Number}\n */\ncontactListConstructorPrototype.getItemsCount = function () {\n    return this._items.length;\n};\n\n/**\n * @param {Object} contactItemData\n * @return {ContactListConstructor}\n */\ncontactListConstructorPrototype.createItem = function (contactItemData) {\n    var item = new ContactListItem(Object.assign(\n        {\n            id: itemsIdIterator++,\n        },\n        contactItemData\n    ));\n\n    this._items.push(item);\n\n    item.on('remove', this._onItemRemove, this)\n        .render(this._contactList);\n\n\n    item.render(this._chatAddContactList);\n\n    return this;\n};\n\n\n/**\n * @param {Number} itemId\n * @return {ChatsListItem|null}\n * @private\n */\ncontactListConstructorPrototype._getItemById = function (itemId) {\n    var items = this._items;\n\n    for (var i = items.length; i-- ;) {\n        if (items[i].model.id === itemId) {\n            return items[i];\n        }\n    }\n\n    return null;\n};\n\ncontactListConstructorPrototype._onItemRemove = function (itemId) {\n    var contactItemComponent = this._getItemById(itemId);\n\n    if (contactItemComponent) {\n        contactItemComponent.off('remove', this._onItemRemove, this);\n        var contactItemComponentIndex = this._items.indexOf(contactItemComponent);\n        this._items.splice(contactItemComponentIndex, 1);\n    }\n\n    return this;\n};\n\n\nmodule.exports = ContactListConstructor;\n\n//# sourceURL=webpack:///./src/scripts/components/ContactList.js?");

/***/ }),

/***/ "./src/scripts/components/ContactListItem.js":
/*!***************************************************!*\
  !*** ./src/scripts/components/ContactListItem.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\nvar extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\nvar templatesEngine = __webpack_require__(/*! ../modules/templatesEngine */ \"./src/scripts/modules/templatesEngine.js\");\n\n/**\n * @param itemData\n * @implements {EventListener}\n * @constructor\n */\nfunction ContactListItemConstructor(itemData) {\n    this._initEventable();\n\n    var templateResult = templatesEngine.contactItem({\n        name: itemData.name\n    });\n\n    this._root = templateResult.root;\n    this._removeAction = templateResult.removeAction;\n    this._name = templateResult.name;\n\n    this.model = {\n        id: itemData.id,\n        name: itemData.name\n    };\n\n    if (itemData.isReady) {\n        this._setReadyModificator(true);\n    }\n\n    this._removeAction.addEventListener('click', this);\n}\n\nextendConstructor(ContactListItemConstructor, Eventable);\n\nvar contactListItemConstructorPrototype = ContactListItemConstructor.prototype;\n\n/**\n * @param {HTMLElement} parent\n * @return {ContactListItemConstructor}\n */\ncontactListItemConstructorPrototype.render = function (parent) {\n    parent.appendChild(this._root);\n    return this;\n};\n\n/**\n * @param {Event} e\n */\ncontactListItemConstructorPrototype.handleEvent = function (e) {\n    switch (e.type) {\n        case 'click':\n            if (e.target === this._removeAction) {\n                this.remove();\n            }\n            break;\n    }\n};\n\n/**\n * @param {String} name\n * @return {ContactListItemConstructor}\n */\ncontactListItemConstructorPrototype.setText = function (name) {\n    if (this.model.name !== name) {\n        this._name.innerHTML = name;\n        this.model.name = name;\n    }\n    return this;\n};\n\n\n/**\n * @return {ContactListItemConstructor}\n */\ncontactListItemConstructorPrototype.remove = function () {\n    this._root.parentNode.removeChild(this._root);\n    this.trigger('remove', this.model.id);\n    return this;\n};\n\nmodule.exports = ContactListItemConstructor;\n\n//# sourceURL=webpack:///./src/scripts/components/ContactListItem.js?");

/***/ }),

/***/ "./src/scripts/components/Sidebar.js":
/*!*******************************************!*\
  !*** ./src/scripts/components/Sidebar.js ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var extendConstructor = __webpack_require__(/*! ../utils/extendConstructor */ \"./src/scripts/utils/extendConstructor.js\");\nvar Eventable = __webpack_require__(/*! ../modules/Eventable */ \"./src/scripts/modules/Eventable.js\");\n\nvar SIDEBAR_SELECTOR = \".js-sidebar\";\n\nvar SIDEBAR_CHATS_BUTTON_SELECTOR = \".js-sidebar-list_chats\";\nvar SIDEBAR_CONTACTS_BUTTON_SELECTOR = \".js-sidebar-list_contacts\";\nvar SIDEBAR_FILES_BUTTON_SELECTOR = \".js-sidebar-list_files\";\n\nvar CHATS_PAGE_SELECTOR = \".js-chats-list-page\";\nvar FILES_PAGE_SELECTOR = \".js-files-page\";\nvar CONTACTS_PAGE_SELECTOR = \".js-contacts-page\";\n\nvar HIDDEN_MODIFICATOR = \"__hidden\";\nvar ACTIVE_MODIFICATOR = \"__active\";\n\n/**\n * @implements {EventListener}\n * @extends {Eventable}\n * @constructor\n */\nfunction SidebarConstructor() {\n    this._sidebar = document.querySelector(SIDEBAR_SELECTOR);\n\n    this._sidebar_chats_button = this._sidebar.querySelector(SIDEBAR_CHATS_BUTTON_SELECTOR);\n    this._sidebar_contacts_button = this._sidebar.querySelector(SIDEBAR_CONTACTS_BUTTON_SELECTOR);\n    this._sidebar_files_button = this._sidebar.querySelector(SIDEBAR_FILES_BUTTON_SELECTOR);\n\n    this._chats_page = document.querySelector(CHATS_PAGE_SELECTOR);\n    this._contacts_page = document.querySelector(CONTACTS_PAGE_SELECTOR);\n    this._files_page = document.querySelector(FILES_PAGE_SELECTOR);\n\n    this._sidebar_chats_button.addEventListener('click', this);\n    this._sidebar_contacts_button.addEventListener('click', this);\n    this._sidebar_files_button.addEventListener('click', this);\n\n    this._initEventable();\n}\n\nextendConstructor(SidebarConstructor, Eventable);\n\nvar sidebarConstructorPrototype = SidebarConstructor.prototype;\n\nsidebarConstructorPrototype.handleEvent = function (e) {\n    switch (e.type) {\n        case 'click':\n            this._sidebar_chats_button.classList.remove(ACTIVE_MODIFICATOR);\n            this._sidebar_contacts_button.classList.remove(ACTIVE_MODIFICATOR);\n            this._sidebar_files_button.classList.remove(ACTIVE_MODIFICATOR);\n\n            this._chats_page.classList.add(HIDDEN_MODIFICATOR);\n            this._contacts_page.classList.add(HIDDEN_MODIFICATOR);\n            this._files_page.classList.add(HIDDEN_MODIFICATOR);\n\n            switch (e.srcElement) {\n                case this._sidebar_chats_button:\n                    this._sidebar_chats_button.classList.add(ACTIVE_MODIFICATOR);\n                    this._chats_page.classList.remove(HIDDEN_MODIFICATOR);\n                    break;\n                case this._sidebar_contacts_button:\n                    this._sidebar_contacts_button.classList.add(ACTIVE_MODIFICATOR);\n                    this._contacts_page.classList.remove(HIDDEN_MODIFICATOR);\n                    break;\n                case this._sidebar_files_button:\n                    this._sidebar_files_button.classList.add(ACTIVE_MODIFICATOR);\n                    this._files_page.classList.remove(HIDDEN_MODIFICATOR);\n                    break;\n            }\n            break;\n    }\n};\n\nmodule.exports = SidebarConstructor;\n\n\n//# sourceURL=webpack:///./src/scripts/components/Sidebar.js?");

/***/ }),

/***/ "./src/scripts/main.js":
/*!*****************************!*\
  !*** ./src/scripts/main.js ***!
  \*****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("var ChatsList = __webpack_require__(/*! ./components/ChatsList */ \"./src/scripts/components/ChatsList.js\");\nvar AddChat = __webpack_require__(/*! ./components/AddChat */ \"./src/scripts/components/AddChat.js\");\nvar Sidebar = __webpack_require__(/*! ./components/Sidebar */ \"./src/scripts/components/Sidebar.js\");\nvar AddContact = __webpack_require__(/*! ./components/AddContact */ \"./src/scripts/components/AddContact.js\")\nvar ContactList = __webpack_require__(/*! ./components/ContactList */ \"./src/scripts/components/ContactList.js\")\n\n\nfunction init() {\n    var sidebar = new Sidebar();\n\n    var addChat = new AddChat();\n    var chatsList = new ChatsList();\n    addChat.on('newChat', function (chatData) { chatsList.createItem(chatData); });\n\n    var addContact = new AddContact();\n    var contactList = new ContactList();\n\n    addContact.on('newContact',function (contactData) { contactList.createItem(contactData); });\n\n}\n\ndocument.addEventListener('DOMContentLoaded', init);\n\n//# sourceURL=webpack:///./src/scripts/main.js?");

/***/ }),

/***/ "./src/scripts/modules/Eventable.js":
/*!******************************************!*\
  !*** ./src/scripts/modules/Eventable.js ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("function Eventable() {}\n\nvar eventablePrototype = Eventable.prototype;\n\neventablePrototype._initEventable = function () {\n    this._eventable_registry = {};\n};\n\nfunction getEventSubscribers(eventable, eventName, needCreate) {\n    var registry = eventable._eventable_registry;\n\n    if (eventName in registry) {\n        return registry[eventName];\n\n    } else if (needCreate) {\n        return registry[eventName] = [];\n    }\n\n    return null;\n}\n\neventablePrototype.on = function (eventName, handler, ctx) {\n    var subscribers = getEventSubscribers(this, eventName, true);\n\n    subscribers.push({\n        handler: handler,\n        ctx: ctx\n    });\n\n    return this;\n};\n\neventablePrototype.off = function (eventName, handler, ctx) {\n    var subscribers = getEventSubscribers(this, eventName);\n\n    if (subscribers) {\n        for (var i = subscribers.length; i-- ;) {\n            if ((subscribers[i].handler === handler)\n                && (subscribers[i].ctx === ctx)\n            ) {\n                subscribers.splice(i, 1);\n                return this;\n            }\n        }\n    }\n\n    return this;\n};\n\neventablePrototype.trigger = function (eventName, data) {\n    var subscribers = getEventSubscribers(this, eventName);\n\n    if (subscribers) {\n        var subscribersCopy = subscribers.slice();\n        for (var i = 0, l = subscribersCopy.length; i !== l; i += 1) {\n            subscribersCopy[i].handler.call(subscribersCopy[i].ctx, data);\n        }\n    }\n\n    return this;\n};\n\nmodule.exports = Eventable;\n\n//# sourceURL=webpack:///./src/scripts/modules/Eventable.js?");

/***/ }),

/***/ "./src/scripts/modules/templatesEngine.js":
/*!************************************************!*\
  !*** ./src/scripts/modules/templatesEngine.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("\nvar div = document.createElement('div');\n\nfunction getTemplateRootNode(scriptId) {\n    var scriptTag = document.getElementById(scriptId);\n    div.innerHTML = scriptTag.innerHTML;\n    var result = div.children[0];\n    div.removeChild(result);\n    return result;\n}\n\nvar templatesEngine = {\n    chatItem: function (data) {\n        var root = getTemplateRootNode('chatsListItem');\n\n        var removeAction = root.querySelector('.js-chats-list-item_remove-action');\n        var name = root.querySelector('.js-chats-list-item_name');\n\n        if (data.name) {\n            name.innerText = data.name;\n        }\n\n        return {\n            root: root,\n            name: name,\n            removeAction: removeAction\n        };\n    },\n\n    contactItem: function (data) {\n        var root = getTemplateRootNode('contactListItem');\n\n        var removeAction = root.querySelector('.js-contact-list-item_remove-action');\n        var name = root.querySelector('.js-contact-list-item_name');\n\n        if (data.name) {\n            name.innerText = data.name;\n        }\n\n        return {\n            root: root,\n            name: name,\n            removeAction: removeAction\n        };\n    },\n\n    chatAddContactItem: function (data) {\n        var root = getTemplateRootNode('chatAddContactListItem');\n\n        var removeAction = root.querySelector('.js-chat-add-contact-list-item_remove-action');\n        var name = root.querySelector('.js-chat-add-contact-list-item_name');\n\n        if (data.name) {\n            name.innerText = data.name;\n        }\n\n        return {\n            root: root,\n            name: name,\n            removeAction: removeAction\n        };\n    }\n};\n\nmodule.exports = templatesEngine;\n\n//# sourceURL=webpack:///./src/scripts/modules/templatesEngine.js?");

/***/ }),

/***/ "./src/scripts/utils/extendConstructor.js":
/*!************************************************!*\
  !*** ./src/scripts/utils/extendConstructor.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("/**\n * @param {Function} Extendable\n * @param {Function} Extension\n * @return {Function} Extendable\n */\nfunction extendConstructor(Extendable, Extension) {\n    var extendablePrototype = Extendable.prototype;\n    var extensionPrototype = Extension.prototype;\n\n    for (var p in extensionPrototype) {\n        extendablePrototype[p] = extensionPrototype[p];\n    }\n\n    return Extendable;\n}\n\nmodule.exports = extendConstructor;\n\n//# sourceURL=webpack:///./src/scripts/utils/extendConstructor.js?");

/***/ })

/******/ });