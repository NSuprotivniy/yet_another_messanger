var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function ContactListItemConstructor(itemData) {
    this._initEventable();

    var templateResult = templatesEngine.contactItem({
        name: itemData.name
    });

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

extendConstructor(ContactListItemConstructor, Eventable);

var contactListItemConstructorPrototype = ContactListItemConstructor.prototype;

/**
 * @param {HTMLElement} parent
 * @return {ContactListItemConstructor}
 */
contactListItemConstructorPrototype.render = function (parent) {
    parent.appendChild(this._root);
    return this;
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
    }
};

/**
 * @param {String} name
 * @return {ContactListItemConstructor}
 */
contactListItemConstructorPrototype.setText = function (name) {
    if (this.model.name !== name) {
        this._name.innerHTML = name;
        this.model.name = name;
    }
    return this;
};


/**
 * @return {ContactListItemConstructor}
 */
contactListItemConstructorPrototype.remove = function () {
    this._root.parentNode.removeChild(this._root);
    this.trigger('remove', this.model.id);
    return this;
};

module.exports = ContactListItemConstructor;