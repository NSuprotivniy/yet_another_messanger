var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');

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
};

fileBufferConstructorPrototype.getFiles = function() {
    return files;
};

fileBufferConstructorPrototype.removeFile = function(file) {
    files.push(file);
};

module.exports = FileBufferConstructor;