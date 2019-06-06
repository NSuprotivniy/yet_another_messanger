var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

var ENTER_KEY_CODE = 13;

var LOGIN_PAGE_SELECTOR = '.js-login-page';
var CHATS_CONTACT_LIST_SELECTOR = '.js-chat-add-contact-list';
var HIDDEN_MODIFICATOR = "__hidden";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function LoginConstructor() {
    this._loginPage = document.querySelector(LOGIN_PAGE_SELECTOR);

    this._chatsContactList = document.querySelector(CHATS_CONTACT_LIST_SELECTOR);

    this._initEventable();
}

extendConstructor(LoginConstructor, Eventable);

var loginConstructorPrototype = LoginConstructor.prototype;


module.exports = LoginConstructor;
