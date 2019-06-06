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
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
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
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 17);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

function Eventable() {}

var eventablePrototype = Eventable.prototype;

eventablePrototype._initEventable = function () {
    this._eventable_registry = {};
};

function getEventSubscribers(eventable, eventName, needCreate) {
    var registry = eventable._eventable_registry;

    if (eventName in registry) {
        return registry[eventName];

    } else if (needCreate) {
        return registry[eventName] = [];
    }

    return null;
}

eventablePrototype.on = function (eventName, handler, ctx) {
    var subscribers = getEventSubscribers(this, eventName, true);

    subscribers.push({
        handler: handler,
        ctx: ctx
    });

    return this;
};

eventablePrototype.off = function (eventName, handler, ctx) {
    var subscribers = getEventSubscribers(this, eventName);

    if (subscribers) {
        for (var i = subscribers.length; i-- ;) {
            if ((subscribers[i].handler === handler)
                && (subscribers[i].ctx === ctx)
            ) {
                subscribers.splice(i, 1);
                return this;
            }
        }
    }

    return this;
};

eventablePrototype.trigger = function (eventName, data) {
    var subscribers = getEventSubscribers(this, eventName);

    if (subscribers) {
        var subscribersCopy = subscribers.slice();
        for (var i = 0, l = subscribersCopy.length; i !== l; i += 1) {
            subscribersCopy[i].handler.call(subscribersCopy[i].ctx, data);
        }
    }

    return this;
};

module.exports = Eventable;

/***/ }),
/* 1 */
/***/ (function(module, exports) {

/**
 * @param {Function} Extendable
 * @param {Function} Extension
 * @return {Function} Extendable
 */
function extendConstructor(Extendable, Extension) {
    var extendablePrototype = Extendable.prototype;
    var extensionPrototype = Extension.prototype;

    for (var p in extensionPrototype) {
        extendablePrototype[p] = extensionPrototype[p];
    }

    return Extendable;
}

module.exports = extendConstructor;

/***/ }),
/* 2 */
/***/ (function(module, exports) {


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
        var checkAction = root.querySelector(".js-chat-add-contact-list-item_check");
        var name = root.querySelector('.js-chat-add-contact-list-item_name');

        if (data.name) {
            name.innerText = data.name;
        }

        return {
            root: root,
            name: name,
            removeAction: removeAction,
            checkAction: checkAction
        };
    },

    chat: function (data) {
        var root = getTemplateRootNode('chat');
        var name = root.querySelector('.js-chat_name');
        var messageList = root.querySelector('.js-chat_message-list');
        var messageInput = root.querySelector('.js-chat_message-text');
        if (data.name) {
            name.innerText = data.name;
        }
        return {
            root: root,
            name: name,
            messageList: messageList,
            messageInput: messageInput
        };
    },

    message: function (data) {
        var root = getTemplateRootNode('message');
        var text = root.querySelector('.js-message_text');
        var creatorName = root.querySelector('.js-message_creator-name');
        var createdAt = root.querySelector('.s-message_created-at');
        var avatar = root.querySelector('.js-message_creator-avatar');

        if (data.text) {
            text.innerText = data.text;
        }
        if (data.creatorName) {
            creatorName.innerText = data.creatorName;
        }
        if (data.createdAt) {
            createdAt.innerText = data.createdAt;
        }
        if (data.avatar) {
            avatar.innerText = data.avatar;
        }

        return {
            root: root
        };
    },

    file: function (data) {
        var root = getTemplateRootNode('file');
        var name = root.querySelector('.js-file_name');
        var creatorName = root.querySelector('.js-file_creator-name');
        var createdAt = root.querySelector('.file_created-at');
        var removeAction = root.querySelector('.js-file_remove-action');

        if (data.name) {
            name.innerText = data.name;
        }
        if (data.creatorName) {
            creatorName.innerText = data.creatorName;
        }
        if (data.createdAt) {
            createdAt.innerText = data.createdAt;
        }

        return {
            root: root,
            removeAction: removeAction
        };
    },

    fileToChoose: function(data) {
        var root = getTemplateRootNode('fileToChoose');
        var name = root.querySelector('.js-file-to-choose_name');
        var id = root.querySelector('.js-file-to-choose_id');
        if (data.name) {
            name.innerText = data.name;
        }
        if (data.id) {
            id.innerText = data.id;
        }
        return {
            root: root,
            id: id
        };
    }
};

module.exports = templatesEngine;

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);

var files = [];

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function FileBufferConstructor() {

    this._initEventable();
}

extendConstructor(FileBufferConstructor, Eventable);

var fileBufferConstructorPrototype = FileBufferConstructor.prototype;

fileBufferConstructorPrototype.addFile = function(file) {
    files.push(file);
}

fileBufferConstructorPrototype.getFiles = function() {
    return files;
}

fileBufferConstructorPrototype.removeFile = function(file) {
    files.push(file);
}

module.exports = FileBufferConstructor;

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

var extendConstructor = __webpack_require__(1);
var Eventable = __webpack_require__(0);

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


/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

var extendConstructor = __webpack_require__(1);
var Eventable = __webpack_require__(0);

var ENTER_KEY_CODE = 13;

var CONTACT_ADD_INPUT_SELECTOR = '.js-contact-add-input';
var HIDDEN_MODIFICATOR = ".__hidden";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function AddContactConstructor() {
    this._contactInput = document.querySelector(CONTACT_ADD_INPUT_SELECTOR);
    this._contactInput.addEventListener('keypress', this);
    this._contactInput.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(AddContactConstructor, Eventable);

var addContactConstructorPrototype = AddContactConstructor.prototype;

addContactConstructorPrototype._addItem = function () {
    var contactInputValue = this._contactInput.value.trim();

    if (contactInputValue.length !== 0) {
        this._contactInput.value = '';
    }

    return this.trigger('newContact', {
        email: contactInputValue
    });
};

addContactConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'keypress':
            if (e.keyCode === ENTER_KEY_CODE) {
                this._addItem();
            }
            break;
    }
};

module.exports = AddContactConstructor;


/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);

var ChatListItem = __webpack_require__(13);

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
    this._items = [];
    this._chatsList = document.querySelector(CHATS_LIST_SELECTOR);
    this._initEventable();
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

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var jsonSender = __webpack_require__(18);
var Contact = __webpack_require__(14);

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

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var FileBuffer = __webpack_require__(3);
var jsonSender = __webpack_require__(18);
var File = __webpack_require__(15);

var FILE_PAGE_SELECTOR = '.js-files-page';
var FILE_LIST_SELECTOR = '.js-file-list';
var FILE_INPUT_SELECTOR = '.js-file-input';

var itemsIdIterator = 0;

/**
 * @extends {Eventable}
 * @constructor
 */
function FileListConstructor() {
    /**
     * @type {Array.<ChatsConstructor>}
     * @private
     */
    this._items = [];
    this._filePage = document.querySelector(FILE_PAGE_SELECTOR);
    this._fileList = this._filePage.querySelector(FILE_LIST_SELECTOR);
    this._fileInput = this._filePage.querySelector(FILE_INPUT_SELECTOR);

    this._fileInput.addEventListener('change', this);

    this._fileBuffer = new FileBuffer();

    this._initEventable();
}

extendConstructor(FileListConstructor, Eventable);

var fileListConstructorPrototype = FileListConstructor.prototype;

/**
 * @return {Number}
 */
fileListConstructorPrototype.getItemsCount = function () {
    return this._items.length;
};

/**
 * @param {Object} fileItemData
 * @return {FileListConstructor}
 */
fileListConstructorPrototype.createItem = function () {
    var files = this._fileInput.files;
    for (let i = 0; i < files.length; i++) {
        var file = files[i];
        var reader = new FileReader();
        reader.readAsDataURL(file);
        var result;
        reader.addEventListener("load", function () {
            result = reader.result;
        }, false);
        var name = file.name;
        var base64 = btoa(result);
        
        var uuid = jsonSender.upload_file(base64, name, fingerprint);

        var fileItem = new File(Object.assign(
            {
                id: uuid,
                name: name,
            },
        ));
        fileItem
            .on('remove', this._onItemRemove, this)
            .render(this._fileList);
        this._items.push(fileItem);
        this._fileBuffer.addFile(fileItem.model);
        this.trigger('fileAdd', fileItem.model);
    };
    this._fileInput.value = "";
    return this;
};

fileListConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'change':
            this.createItem();
            break;
    }
};


/**
 * @param {Number} itemId
 * @return {ChatsListItem|null}
 * @private
 */
fileListConstructorPrototype._getItemById = function (items, itemId) {
    for (var i = items.length; i-- ;) {
        if (items[i].model.id === itemId) {
            return items[i];
        }
    }

    return null;
};

fileListConstructorPrototype._onItemRemove = function (itemId) {
    var item = this._getItemById(itemId);

    if (item) {
        var itemIndex = this._items.indexOf(item);
        this._items.splice(itemIndex, 1);
    }

    return this;
};

module.exports = FileListConstructor;

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

var extendConstructor = __webpack_require__(1);
var Eventable = __webpack_require__(0);

var LOGIN_PAGE_SELECTOR = '.js-login-page';
var EMAIL_INPUT_SELECTOR = '.js-credentials-email';
var PASSWORD_INPUT_SELECTOR = '.js-credentials-password';
var SUBMIT_INPUT_SELECTOR = '.js-login-action-button';

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function LoginConstructor() {
    this._loginPage = document.querySelector(LOGIN_PAGE_SELECTOR);
    this._emailInput = this._loginPage.querySelector(EMAIL_INPUT_SELECTOR);
    this._passwordInput = this._loginPage.querySelector(PASSWORD_INPUT_SELECTOR);
    this._submitButton = this._loginPage.querySelector(SUBMIT_INPUT_SELECTOR);

    this._submitButton.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(LoginConstructor, Eventable);

var loginConstructorPrototype = LoginConstructor.prototype;

loginConstructorPrototype.login = function() {
    var email = this._emailInput.value.trim();
    var password = this._passwordInput.value.trim();
    if (this._emailInput.length !== 0) {
        this._emailInput.value = '';
    }
    if (this._passwordInput.length !== 0) {
        this._passwordInput.value = '';
    }

    this.trigger('login');
};

loginConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            this.login();
            break;
    }
};


module.exports = LoginConstructor;


/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

var extendConstructor = __webpack_require__(1);
var Eventable = __webpack_require__(0);

var REGISTRATION_PAGE_SELECTOR = '.js-registration-page';
var EMAIL_INPUT_SELECTOR = '.js-credentials-email';
var NAME_INPUT_SELECTOR = '.js-credentials-name';
var PASSWORD_INPUT_SELECTOR = '.js-credentials-password-confirmation';
var PASSWORD_CONFIRMATION_INPUT_SELECTOR = '.js-credentials-password';
var SUBMIT_INPUT_SELECTOR = '.js-registration-action-button';

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function LoginConstructor() {
    this._loginPage = document.querySelector(REGISTRATION_PAGE_SELECTOR);
    this._emailInput = this._loginPage.querySelector(EMAIL_INPUT_SELECTOR);
    this._nameInput = this._loginPage.querySelector(NAME_INPUT_SELECTOR);
    this._passwordInput = this._loginPage.querySelector(PASSWORD_INPUT_SELECTOR);
    this._passwordConfirmationInput = this._loginPage.querySelector(PASSWORD_CONFIRMATION_INPUT_SELECTOR);
    this._submitButton = this._loginPage.querySelector(SUBMIT_INPUT_SELECTOR);

    this._submitButton.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(LoginConstructor, Eventable);

var loginConstructorPrototype = LoginConstructor.prototype;

loginConstructorPrototype.registration = function() {
    var email = this._emailInput.value.trim();
    var name = this._nameInput.value.trim();
    var password = this._passwordInput.value.trim();
    var passwordConfirmation = this._passwordConfirmationInput.value.trim();
    if (this._emailInput.length !== 0) {
        this._emailInput.value = '';
    }
    if (this._nameInput.length !== 0) {
        this._nameInput.value = '';
    }
    if (this._passwordInput.length !== 0) {
        this._passwordInput.value = '';
    }
    if (this._passwordConfirmationInput.length !== 0) {
        this._passwordConfirmationInput.value = '';
    }

    this.trigger('registration');
};

loginConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            this.registration();
            break;
    }
};


module.exports = LoginConstructor;


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

var extendConstructor = __webpack_require__(1);
var Eventable = __webpack_require__(0);

var SIDEBAR_SELECTOR = ".js-sidebar";

var SIDEBAR_CHATS_BUTTON_SELECTOR = ".js-sidebar-list_chats";
var SIDEBAR_CONTACTS_BUTTON_SELECTOR = ".js-sidebar-list_contacts";
var SIDEBAR_FILES_BUTTON_SELECTOR = ".js-sidebar-list_files";
var SIDEBAR_LOGIN_BUTTON_SELECTOR = ".js-sidebar-login";
var SIDEBAR_REGISTRATION_BUTTON_SELECTOR = ".js-sidebar-registration";

var CHATS_PAGE_SELECTOR = ".js-chats-list-page";
var FILES_PAGE_SELECTOR = ".js-files-page";
var CONTACTS_PAGE_SELECTOR = ".js-contacts-page";
var CHAT_PAGE_SELECTOR = ".js-chat-page";
var LOGIN_PAGE_SELECTOR = ".js-login-page";
var REGISTRATION_PAGE_SELECTOR = ".js-registration-page";

var HIDDEN_MODIFICATOR = "__hidden";
var ACTIVE_MODIFICATOR = "__active";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function SidebarConstructor() {
    this._sidebar = document.querySelector(SIDEBAR_SELECTOR);

    this._sidebar_chats_button = this._sidebar.querySelector(SIDEBAR_CHATS_BUTTON_SELECTOR);
    this._sidebar_contacts_button = this._sidebar.querySelector(SIDEBAR_CONTACTS_BUTTON_SELECTOR);
    this._sidebar_files_button = this._sidebar.querySelector(SIDEBAR_FILES_BUTTON_SELECTOR);
    this._sidebar_login_button = this._sidebar.querySelector(SIDEBAR_LOGIN_BUTTON_SELECTOR);
    this._sidebar_registraion_button = this._sidebar.querySelector(SIDEBAR_REGISTRATION_BUTTON_SELECTOR);

    this._chats_page = document.querySelector(CHATS_PAGE_SELECTOR);
    this._contacts_page = document.querySelector(CONTACTS_PAGE_SELECTOR);
    this._files_page = document.querySelector(FILES_PAGE_SELECTOR);
    this._chat_page = document.querySelector(CHAT_PAGE_SELECTOR);
    this._login_page = document.querySelector(LOGIN_PAGE_SELECTOR);
    this._registration_page = document.querySelector(REGISTRATION_PAGE_SELECTOR);

    this._sidebar_chats_button.addEventListener('click', this);
    this._sidebar_contacts_button.addEventListener('click', this);
    this._sidebar_files_button.addEventListener('click', this);
    this._sidebar_login_button.addEventListener('click', this);
    this._sidebar_registraion_button.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(SidebarConstructor, Eventable);

var sidebarConstructorPrototype = SidebarConstructor.prototype;

sidebarConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            switch (e.target) {
                case this._sidebar_chats_button:
                    this.setPage("chats");
                    break;
                case this._sidebar_contacts_button:
                    this.setPage("contacts");
                    break;
                case this._sidebar_files_button:
                    this.setPage("files");
                    break;
                case this._sidebar_login_button:
                    this.setPage("login");
                    break;
                case this._sidebar_registraion_button:
                    this.setPage("registration");
                    break;
            }
    }
};

sidebarConstructorPrototype.setPage = function (name) {
    this._sidebar_chats_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_contacts_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_files_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_login_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_registraion_button.classList.remove(ACTIVE_MODIFICATOR);

    this._chats_page.classList.add(HIDDEN_MODIFICATOR);
    this._contacts_page.classList.add(HIDDEN_MODIFICATOR);
    this._files_page.classList.add(HIDDEN_MODIFICATOR);
    this._chat_page.classList.add(HIDDEN_MODIFICATOR);
    this._login_page.classList.add(HIDDEN_MODIFICATOR);
    this._registration_page.classList.add(HIDDEN_MODIFICATOR);

    switch (name) {
        case "chats":
            this._sidebar_chats_button.classList.add(ACTIVE_MODIFICATOR);
            this._chats_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "contacts":
            this._sidebar_contacts_button.classList.add(ACTIVE_MODIFICATOR);
            this._contacts_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "files":
            this._sidebar_files_button.classList.add(ACTIVE_MODIFICATOR);
            this._files_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "chat":
            this._chat_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "login":
            this._sidebar_login_button.classList.add(ACTIVE_MODIFICATOR);
            this._login_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "registration":
            this._sidebar_registraion_button.classList.add(ACTIVE_MODIFICATOR);
            this._registration_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
    }
}

module.exports = SidebarConstructor;


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var Message = __webpack_require__(16);
var templatesEngine = __webpack_require__(2);
var FileBuffer = __webpack_require__(3);

var ENTER_KEY_CODE = 13;

var CHAT_PAGE_SELECTOR = ".js-chat-page";
var FILES_TO_CHOOSE_SELECTOR = ".js-chat_files-to-choose";
var FILES_TO_CHOOSE_ID_SELECTOR = ".js-file-to-choose_id";

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function ChatConstructor(model) {
    this._initEventable();

    var templateResult = templatesEngine.chat({
        name: model.name
    });

    this._root = templateResult.root;
    this._name = templateResult.name;
    this._messageInput = templateResult.messageInput;
    this._messageList = templateResult.messageList;

    this.model = model;

    this._messages = [];
    this._messagesCount = 0;

    this._messageInput.addEventListener('keypress', this);
    this._chatPage = document.querySelector(CHAT_PAGE_SELECTOR);
    this._filesToChoose = this._root.querySelector(FILES_TO_CHOOSE_SELECTOR);

    this._fileBuffer = new FileBuffer();
    this._filesToAdd = [];

    this._initEventable();
}

extendConstructor(ChatConstructor, Eventable);

var chatConstructorPrototype = ChatConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {ChatConstructor}
 */
chatConstructorPrototype.render = function (parent) {

    this._chatPage.childNodes.forEach(function (c) {c.remove();});
    this._chatPage.appendChild(this._root);
    this.trigger('openChat', this.model.id);
    return this;
};

chatConstructorPrototype._createMessage = function () {
    var text = this._messageInput.value.trim();
    if (text.length !== 0) {
        this._messageInput.value = '';
    }

    

    var message = new Message({
        id: this._messagesCount++,
        text: text
    });
    message.render(this._messageList);
    this._messageList.scrollTop = this._messageList.scrollHeight;
};

chatConstructorPrototype._parseFiles = function () {
    var text = this._messageInput.value.trim();
    var fileModels = [];
    var myRegexp = /\[(.*?)\]/g;
    var match = myRegexp.exec(text);
    while (match != null) {
        var fileName = match[1];
        this._fileBuffer.getFiles().forEach(function (file) {
            if (file.name.startsWith(fileName)) {
                fileModels.push(file);
            }
        });
        match = myRegexp.exec(text);
    }
    this._filesToChoose.innerHTML = '';
    for (var i = fileModels.length; i-- ;) {
        var fileModel = fileModels[i];
        var fileToChoose = templatesEngine.fileToChoose({
            name: fileModel.name,
            id: fileModel.id});
        this._filesToChoose.appendChild(fileToChoose.root);
        fileToChoose.root.addEventListener('click', this);
    }
};

chatConstructorPrototype._addFileToSend = function(target) {
    var id = target.querySelector(FILES_TO_CHOOSE_ID_SELECTOR);
    var fileModel;
    this._fileBuffer.getFiles().forEach(function (file) {
        if (file.id == id.innerHTML) {
            fileModel = file;
        }
    });
    this._filesToAdd.push(fileModel);
};


/**
 * @param {Event} e
 */
chatConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'keypress':
            switch (e.target) {
                case this._messageInput:
                    if (e.keyCode === ENTER_KEY_CODE) {
                        this._filesToChoose.innerHTML = '';
                        this._createMessage();
                    } else {
                        this._parseFiles();
                    }
                    break;
            }
            break;
        case 'click':
            switch (e.target.parentNode) {
                case this._filesToChoose:
                    this._addFileToSend(e.target);
                    this._filesToChoose.innerHTML = '';
                    break;
            }
    }
};


/**
 * @return {ChatConstructor}
 */
chatConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = ChatConstructor;

/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var Chat = __webpack_require__(12);
var extendConstructor = __webpack_require__(1);
var templatesEngine = __webpack_require__(2);

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

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var templatesEngine = __webpack_require__(2);

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function ContactConstructor(itemData, type) {
    this._initEventable();
    switch (type) {
        case "main":
            var templateResult = templatesEngine.contactItem({name: itemData.name });
            break;
        case "chat":
            var templateResult = templatesEngine.chatAddContactItem({name: itemData.name });
            this._checkAction = templateResult.checkAction;
            this._checkAction.addEventListener('change', this);
            break;
    }
    this._root = templateResult.root;
    this._removeAction = templateResult.removeAction;
    this._name = templateResult.name;
    
    this.model = {
        id: itemData.id,
        name: itemData.name
    };

    if (itemData.isReady) {
        this._setReadyModificator(true);
    }

    this._removeAction.addEventListener('click', this);
}

extendConstructor(ContactConstructor, Eventable);

var contactListItemConstructorPrototype = ContactConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {ContactConstructor}
 */
contactListItemConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
};

contactListItemConstructorPrototype.uncheck = function() {
    this._checkAction.checked = false;
};

/**
 * @param {Event} e
 */
contactListItemConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            if (e.target === this._removeAction) {
                this.remove();
            }
            break;
        case 'change':
            if (e.target === this._checkAction) {
                if (this._checkAction.checked) {
                    this.trigger('checked', this);
                } else {
                    this.trigger('unchecked', this);
                }
            }
            break;
    }
};

/**
 * @param {String} name
 * @return {ContactConstructor}
 */
contactListItemConstructorPrototype.setText = function (name) {
    if (this.model.name !== name) {
        this._name.innerHTML = name;
        this.model.name = name;
    }
    return this;
};


/**
 * @return {ContactConstructor}
 */
contactListItemConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = ContactConstructor;

/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var templatesEngine = __webpack_require__(2);

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function FileConstructor(model) {
    this._initEventable();

    var templateResult = templatesEngine.file(model);

    this._root = templateResult.root;
    this._removeAction = templateResult.removeAction;
    this._name = templateResult.name;

    

    this.model = {
        id: model.id,
        name: model.name,
    };

    this._removeAction.addEventListener('click', this);
}

extendConstructor(FileConstructor, Eventable);

var fileConstructorPrototype = FileConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {FileConstructor}
 */
fileConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
};


/**
 * @param {Event} e
 */
fileConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            switch (e.target) {
                case this._removeAction:
                    this.remove();
                    break;
            }
            break;
    }
};

/**
 * @return {FileConstructor}
 */
fileConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = FileConstructor;

/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

var Eventable = __webpack_require__(0);
var extendConstructor = __webpack_require__(1);
var templatesEngine = __webpack_require__(2);

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function MessageConstructor(model) {
    this._initEventable();

    this.model = model;
    var templateResult = templatesEngine.message(model);
    this._root = templateResult.root;
}

extendConstructor(MessageConstructor, Eventable);

var messageConstructorPrototype = MessageConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {MessageConstructor}
 */
messageConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
};

/**
 * @param {Event} e
 */
messageConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {}
};


/**
 * @return {MessageConstructor}
 */
messageConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = MessageConstructor;

/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

var ChatList = __webpack_require__(6);
var AddChat = __webpack_require__(4);
var Sidebar = __webpack_require__(11);
var AddContact = __webpack_require__(5);
var ContactList = __webpack_require__(7);
var FileList = __webpack_require__(8);
var Login = __webpack_require__(9);
var Registration = __webpack_require__(10);



function init() {
    var sidebar = new Sidebar();

    var login = new Login();
    var registration = new Registration();

    login.on('login', function () { sidebar.setPage("chats"); });
    registration.on('registration', function () { sidebar.setPage("chats"); });

    var addChat = new AddChat();
    var chatList = new ChatList();
    addChat.on('newChat', function (chatData) {
        chatList.createItem(chatData);
        sidebar.setPage("chat");
    });

    chatList.on('openChat', function (modelId) { sidebar.setPage("chat"); });

    var addContact = new AddContact();
    var contactList = new ContactList();

    addContact.on('newContact',function (contactData) { contactList.createItem(contactData); });
    contactList
        .on('itemChecked', function(contact) {addChat._onContactItemChecked(contact); })
        .on('itemUnchecked', function(contact) {addChat._onContactItemUnchecked(contact); });

    var fileList = new FileList();

}

document.addEventListener('DOMContentLoaded', init);

/***/ }),
/* 18 */
/***/ (function(module, exports) {

var IP = 'http://188.243.95.184:9090'
var xhr;

var send_json = function (main_json, fingerprint, uuid, flag)
{
    if (main_json != null) {
        xhr.setRequestHeader("fingerprint", fingerprint)
        console.log("sending all\n");
        console.log(main_json);
        console.log(xhr.status);
        xhr.send(main_json);
    } else {
        if (uuid != null) {
            hr.setRequestHeader("uuid", uuid)
        }
        console.log("sending\n");
        xhr.send();
    }
    console.log("sent\n");
    if (xhr.status != 200) {
        console.log("sent\n");
        alert(xhr.status);
        return null;
    } else {
        console.log("sent\n");
        var event = JSON.parse(xhr.responseText);
        if (flag == 1) {
            var token = xhr.getResponseHeader("Set-Cookie");
            var result = {
                result: event,
                cookie: token
            };
            alert(result);
            return result;
        }
        alert(event);
        return event;
    }
}

var jsonSender = {

    upload_file: function (body, name, fingerprint)
    {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/file', false);
        var params_json = {
            body: body,
            name: '[' + name + ']'
        };
        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };
        var result = send_json(main_json, fingerprint, null, 0);
        return result.params.uuid;
    },

    login: function (user_email, user_password) {
        console.log(user_email);
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/auth', false);

        var params_json = {
            email: user_email,
            password: user_password
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        //console.log(JSON.stringify(main_json));
        var result = send_json(main_json, null, null, 1);
        console.log(result);
        //localStorage.setItem('token',token);
        return {name: result.result.params.name, uuid: result.result.params.uuid, cookie: result.token};
    },

    registrate: function(name, password, email) {
        console.log(user_email);
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/user', false);

        var params_json = {
            email: user_email,
            password: user_password,
            name: name
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        // console.log(JSON.stringify(main_json, null, null));
        var result = send_json(main_json, null, null, null, 0);
        //localStorage.setItem('token',token);
        return {name: result.params.name, uuid: result.params.uuid};
    },

    getChats: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/chats', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    deleteToken: function () {
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', IP + '/auth', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        return true;
    },

    getFile: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/file', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getFiles: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/files', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    getChat: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/file', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getMsg: function (uuid, cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/message', false);
        var result = send_json(null, cookies, fingerprint, uuid, 0);
        return result;
    },

    getContacts: function (cookies, fingerprint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', IP + '/contacts', false);
        var result = send_json(null, cookies, fingerprint, null, 0);
        var uuids = [], names = [];
        for (var i = 0; i < result.params.uuids.length; i++) {
            uuids.push(result.params.uuids[i])
            names.push(result.params.names[i]);
        }
        console.log({uuids, names});
        return {uuids, names};
    },

    createChat: function (cookies, fingerprint, name, uuids) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/chat', false);

        var participantsUUIDs = JSON.stringify(uuids);

        var params_json = {
            participantsUUIDs: participantsUUIDs,
            name: name
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        var result = send_json(main_json, cookies, fingerprint, null, 0);
        return result;
    },

    createFriend: function (fingerprint, email) {
        xhr = new XMLHttpRequest();
        xhr.open('POST', IP + '/contact', false);

        var params_json = {
            email: email,
        };

        var main_json = {
            id: "1234",
            jsonrpc: "2.0",
            method: "create_user",
            params: params_json
        };

        xhr.setRequestHeader("fingerprint", fingerprint)
        xhr.send(main_json);
    if (xhr.status != 200) {
        return null;
    } else {
        var event = JSON.parse(xhr.responseText);
        if (flag == 1) {
            var token = xhr.getResponseHeader("Set-Cookie");
            var result = {
                result: event,
                cookie: token
            };
            alert(result);
            return result;
        }
        return event;
    }
    },

    create_msg(chat_uuid, fingerrint)
    {
    }
}
module.exports = jsonSender;



/***/ })
/******/ ]);