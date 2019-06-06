var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var FileBuffer = require('../components/FilesBuffer');
var jsonSender = require('../modules/JsonSender');
var File = require('../components/File');

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
        
        var uuid = jsonSender.upload_file(base64, name, "fingerprint");

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