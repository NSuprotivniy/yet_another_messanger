var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

var SIDEBAR_SELECTOR = ".js-sidebar";

var SIDEBAR_CHATS_BUTTON_SELECTOR = ".js-sidebar-list_chats";
var SIDEBAR_CONTACTS_BUTTON_SELECTOR = ".js-sidebar-list_contacts";
var SIDEBAR_FILES_BUTTON_SELECTOR = ".js-sidebar-list_files";
var SIDEBAR_LOGIN_BUTTON_SELECTOR = ".js-sidebar-login";
var SIDEBAR_REGISTRATION_BUTTON_SELECTOR = ".js-sidebar-registration";
var SIDEBAR_LOGOUT_BUTTON_SELECTOR = ".js-sidebar-logout";

var CHATS_PAGE_SELECTOR = ".js-chats-list-page";
var FILES_PAGE_SELECTOR = ".js-files-page";
var CONTACTS_PAGE_SELECTOR = ".js-contacts-page";
var CHAT_PAGE_SELECTOR = ".js-chat-page";
var LOGIN_PAGE_SELECTOR = ".js-login-page";
var REGISTRATION_PAGE_SELECTOR = ".js-registration-page";

var HIDDEN_MODIFICATOR = "__hidden";
var ACTIVE_MODIFICATOR = "__active";

/**
 * @implements {EventListener}
 * @extends {Eventable}
 * @constructor
 */
function SidebarConstructor() {
    this._sidebar = document.querySelector(SIDEBAR_SELECTOR);

    this._sidebar_chats_button = this._sidebar.querySelector(SIDEBAR_CHATS_BUTTON_SELECTOR);
    this._sidebar_contacts_button = this._sidebar.querySelector(SIDEBAR_CONTACTS_BUTTON_SELECTOR);
    this._sidebar_files_button = this._sidebar.querySelector(SIDEBAR_FILES_BUTTON_SELECTOR);
    this._sidebar_login_button = this._sidebar.querySelector(SIDEBAR_LOGIN_BUTTON_SELECTOR);
    this._sidebar_registraion_button = this._sidebar.querySelector(SIDEBAR_REGISTRATION_BUTTON_SELECTOR);
    this._sidebar_logout_button = this._sidebar.querySelector(SIDEBAR_LOGOUT_BUTTON_SELECTOR);

    this._chats_page = document.querySelector(CHATS_PAGE_SELECTOR);
    this._contacts_page = document.querySelector(CONTACTS_PAGE_SELECTOR);
    this._files_page = document.querySelector(FILES_PAGE_SELECTOR);
    this._chat_page = document.querySelector(CHAT_PAGE_SELECTOR);
    this._login_page = document.querySelector(LOGIN_PAGE_SELECTOR);
    this._registration_page = document.querySelector(REGISTRATION_PAGE_SELECTOR);

    this._sidebar_chats_button.addEventListener('click', this);
    this._sidebar_contacts_button.addEventListener('click', this);
    this._sidebar_files_button.addEventListener('click', this);
    this._sidebar_login_button.addEventListener('click', this);
    this._sidebar_registraion_button.addEventListener('click', this);
    this._sidebar_logout_button.addEventListener('click', this);

    if (localStorage.getItem("userUUID") === null) {
        this.setLoggedOut();
    } else {
        this.setLoggedOn();
    }

    this._initEventable();
}

extendConstructor(SidebarConstructor, Eventable);

var sidebarConstructorPrototype = SidebarConstructor.prototype;

sidebarConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            switch (e.target) {
                case this._sidebar_chats_button:
                    this.setPage("chats");
                    break;
                case this._sidebar_contacts_button:
                    this.setPage("contacts");
                    break;
                case this._sidebar_files_button:
                    this.setPage("files");
                    break;
                case this._sidebar_login_button:
                    this.setPage("login");
                    break;
                case this._sidebar_registraion_button:
                    this.setPage("registration");
                    break;
                case this._sidebar_logout_button:
                    this._setLoggedOutButtonVisibility();
                    this.setLoggedOut();
                    this.trigger("logoutButton");
                    break;
            }
    }
};

sidebarConstructorPrototype.setPage = function (name) {
    if (localStorage.getItem("userUUID") === null) {
        this._setLoggedOutButtonVisibility();
        if (name !== "login" && name !== "registration") {
            name = "login";
        };
    }

    this._sidebar_chats_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_contacts_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_files_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_login_button.classList.remove(ACTIVE_MODIFICATOR);
    this._sidebar_registraion_button.classList.remove(ACTIVE_MODIFICATOR);

    this._chats_page.classList.add(HIDDEN_MODIFICATOR);
    this._contacts_page.classList.add(HIDDEN_MODIFICATOR);
    this._files_page.classList.add(HIDDEN_MODIFICATOR);
    this._chat_page.classList.add(HIDDEN_MODIFICATOR);
    this._login_page.classList.add(HIDDEN_MODIFICATOR);
    this._registration_page.classList.add(HIDDEN_MODIFICATOR);

    switch (name) {
        case "chats":
            this._sidebar_chats_button.classList.add(ACTIVE_MODIFICATOR);
            this._chats_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "contacts":
            this._sidebar_contacts_button.classList.add(ACTIVE_MODIFICATOR);
            this._contacts_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "files":
            this._sidebar_files_button.classList.add(ACTIVE_MODIFICATOR);
            this._files_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "chat":
            this._chat_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "login":
            this._sidebar_login_button.classList.add(ACTIVE_MODIFICATOR);
            this._login_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
        case "registration":
            this._sidebar_registraion_button.classList.add(ACTIVE_MODIFICATOR);
            this._registration_page.classList.remove(HIDDEN_MODIFICATOR);
            break;
    }
};

sidebarConstructorPrototype.setLoggedOn = function () {
    this._sidebar_login_button.classList.add(HIDDEN_MODIFICATOR);
    this._sidebar_registraion_button.classList.add(HIDDEN_MODIFICATOR);
    this._sidebar_chats_button.classList.remove(HIDDEN_MODIFICATOR);
    this._sidebar_contacts_button.classList.remove(HIDDEN_MODIFICATOR);
    this._sidebar_files_button.classList.remove(HIDDEN_MODIFICATOR);
    this._sidebar_logout_button.classList.remove(HIDDEN_MODIFICATOR);
    this.setPage("chats");
};

sidebarConstructorPrototype._setLoggedOutButtonVisibility = function () {
    this._sidebar_login_button.classList.remove(HIDDEN_MODIFICATOR);
    this._sidebar_registraion_button.classList.remove(HIDDEN_MODIFICATOR);
    this._sidebar_chats_button.classList.add(HIDDEN_MODIFICATOR);
    this._sidebar_contacts_button.classList.add(HIDDEN_MODIFICATOR);
    this._sidebar_files_button.classList.add(HIDDEN_MODIFICATOR);
    this._sidebar_logout_button.classList.add(HIDDEN_MODIFICATOR);
};

sidebarConstructorPrototype.setLoggedOut = function () {
    this.setPage("login");
}

module.exports = SidebarConstructor;
