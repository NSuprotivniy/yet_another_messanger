var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');
var jsonSender = require('../modules/JsonSender');

var REGISTRATION_PAGE_SELECTOR = '.js-registration-page';
var EMAIL_INPUT_SELECTOR = '.js-credentials-email';
var NAME_INPUT_SELECTOR = '.js-credentials-name';
var PASSWORD_INPUT_SELECTOR = '.js-credentials-password-confirmation';
var PASSWORD_CONFIRMATION_INPUT_SELECTOR = '.js-credentials-password';
var SUBMIT_INPUT_SELECTOR = '.js-registration-action-button';

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function LoginConstructor() {
    this._loginPage = document.querySelector(REGISTRATION_PAGE_SELECTOR);
    this._emailInput = this._loginPage.querySelector(EMAIL_INPUT_SELECTOR);
    this._nameInput = this._loginPage.querySelector(NAME_INPUT_SELECTOR);
    this._passwordInput = this._loginPage.querySelector(PASSWORD_INPUT_SELECTOR);
    this._passwordConfirmationInput = this._loginPage.querySelector(PASSWORD_CONFIRMATION_INPUT_SELECTOR);
    this._submitButton = this._loginPage.querySelector(SUBMIT_INPUT_SELECTOR);

    this._submitButton.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(LoginConstructor, Eventable);

var loginConstructorPrototype = LoginConstructor.prototype;

loginConstructorPrototype.registration = function() {
    var email = this._emailInput.value.trim();
    var name = this._nameInput.value.trim();
    var password = this._passwordInput.value.trim();
    var passwordConfirmation = this._passwordConfirmationInput.value.trim();
    if (this._emailInput.length !== 0) {
        this._emailInput.value = '';
    }
    if (this._nameInput.length !== 0) {
        this._nameInput.value = '';
    }
    if (this._passwordInput.length !== 0) {
        this._passwordInput.value = '';
    }
    if (this._passwordConfirmationInput.length !== 0) {
        this._passwordConfirmationInput.value = '';
    }

    var response = jsonSender.registration(name, email, password, passwordConfirmation);
    localStorage.setItem("userUUID", response.uuid);
    this.trigger('registration');
};

loginConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            this.registration();
            break;
    }
};


module.exports = LoginConstructor;
