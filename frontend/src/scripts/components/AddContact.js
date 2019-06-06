var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

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
