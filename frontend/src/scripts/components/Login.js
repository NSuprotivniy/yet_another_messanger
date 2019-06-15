var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');
var jsonSender = require('../modules/JsonSender');

var LOGIN_PAGE_SELECTOR = '.js-login-page';
var EMAIL_INPUT_SELECTOR = '.js-credentials-email';
var PASSWORD_INPUT_SELECTOR = '.js-credentials-password';
var SUBMIT_INPUT_SELECTOR = '.js-login-action-button';

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function LoginConstructor() {
    this._loginPage = document.querySelector(LOGIN_PAGE_SELECTOR);
    this._emailInput = this._loginPage.querySelector(EMAIL_INPUT_SELECTOR);
    this._passwordInput = this._loginPage.querySelector(PASSWORD_INPUT_SELECTOR);
    this._submitButton = this._loginPage.querySelector(SUBMIT_INPUT_SELECTOR);

    this._submitButton.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(LoginConstructor, Eventable);

var loginConstructorPrototype = LoginConstructor.prototype;

loginConstructorPrototype.login = function() {
    var email = this._emailInput.value.trim();
    var password = this._passwordInput.value.trim();
    if (this._emailInput.length !== 0) {
        this._emailInput.value = '';
    }
    if (this._passwordInput.length !== 0) {
        this._passwordInput.value = '';
    }

    var response = jsonSender.login(email, password);
    if (response.status === 200) {
        localStorage.setItem("userUUID", response.uuid);
        this.trigger('login');
    };
};

loginConstructorPrototype.resetSession = function() {
    localStorage.removeItem("userUUID");
    jsonSender.logout();
}

loginConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            this.login();
            break;
    }
};


module.exports = LoginConstructor;
