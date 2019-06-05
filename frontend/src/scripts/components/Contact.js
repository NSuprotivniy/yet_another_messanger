var Eventable = require('../modules/Eventable');
var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');

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