var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function PhotoConstructor(model) {
    this._initEventable();

    this.model = model;
    var templateResult = templatesEngine.photo(model);
    this._root = templateResult.root;


}

extendConstructor(PhotoConstructor, Eventable);

var photoConstructorPrototype = PhotoConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {PhotoConstructor}
 */
photoConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
};

/**
 * @param {Event} e
 */
photoConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {}
};


/**
 * @return {PhotoConstructor}
 */
photoConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = PhotoConstructor;