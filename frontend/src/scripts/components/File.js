var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

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