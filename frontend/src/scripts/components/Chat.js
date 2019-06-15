var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var Message = require('../components/Message');
var templatesEngine = require('../modules/templatesEngine');
var FileBuffer = require('../components/FilesBuffer');
var jsonSender = require('../modules/JsonSender');
var websocketInstance = require('../modules/WebsocketInstance');

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

    var response = jsonSender.getChat(model.uuid);
    this.model = Object.assign(model, response.params);

    this._messages = [];
    this._messagesCount = 0;

    this._messageInput.addEventListener('keypress', this);
    this._chatPage = document.querySelector(CHAT_PAGE_SELECTOR);
    this._filesToChoose = this._root.querySelector(FILES_TO_CHOOSE_SELECTOR);

    this._fileBuffer = new FileBuffer();
    this._filesToAdd = [];
    
    for (let i = 0; i < model.messagesUUIDs.length; i++) {
        var m = model.messagesUUIDs[i];
        var response = jsonSender.getMessage(m);
        this._addMessage(response.params);
    };

    websocketInstance.on("websocketMessage", this._addMessage, this);

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
    var response = jsonSender.create_msg(this.model.uuid, text);
    var model = {
        id: this._messagesCount++,
        uuid: response.uuid,
        text: text,
        chatUUUID: this.model.uuid,
        creatorUUID: localStorage.getItem("userUUID")
    };
    return this;
};

chatConstructorPrototype._addMessage = function (model) {
    if (model.chatUUID === this.model.uuid) {
        var message = new Message(model);
        message.render(this._messageList);
        this._messageList.scrollTop = this._messageList.scrollHeight;
    }
    return this;
}

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
        case 'websocketMessage':

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