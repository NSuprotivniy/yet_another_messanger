var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var Message = require('../components/Message')
var templatesEngine = require('../modules/templatesEngine');
var FileBuffer = require('../components/FilesBuffer');

var ENTER_KEY_CODE = 13;

var CHAT_PAGE_SELECTOR = ".js-chat-page";
var FILES_TO_CHOOSE_SELECTOR = ".js-chat_files-to-choose";

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
    this.filesToChoose = this._root.querySelector(FILES_TO_CHOOSE_SELECTOR);

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
}

chatConstructorPrototype._parseFiles = function () {
    var text = this._messageInput.value.trim();
    var myRegexp = /\[(.*?)\]/g;
    var match = myRegexp.exec(text);
    while (match != null) {
        var fileName = match[1];
        var fileModel;
        this._fileBuffer.getFiles().forEach(function (file) {
            /// TODO
        });
        this._parsedFiles.push();
        match = myRegexp.exec(text);
    }
}


/**
 * @param {Event} e
 */
chatConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'keypress':
            switch (e.target) {
                case this._messageInput:
                    if (e.keyCode === ENTER_KEY_CODE) {
                        this._createMessage();
                    } else {
                        this._parseFiles();
                    }
                    break;
            }
            break;
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