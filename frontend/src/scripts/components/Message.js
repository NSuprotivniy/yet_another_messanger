var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

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