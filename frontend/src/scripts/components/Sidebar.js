var extendConstructor = require('../utils/extendConstructor');
var Eventable = require('../modules/Eventable');

var SIDEBAR_SELECTOR = ".js-sidebar";

var SIDEBAR_CHATS_BUTTON_SELECTOR = ".js-sidebar-list_chats";
var SIDEBAR_CONTACTS_BUTTON_SELECTOR = ".js-sidebar-list_contacts";
var SIDEBAR_FILES_BUTTON_SELECTOR = ".js-sidebar-list_files";

var CHATS_PAGE_SELECTOR = ".js-chats-list-page";
var FILES_PAGE_SELECTOR = ".js-files-page";
var CONTACTS_PAGE_SELECTOR = ".js-contacts-page";

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

    this._chats_page = document.querySelector(CHATS_PAGE_SELECTOR);
    this._contacts_page = document.querySelector(CONTACTS_PAGE_SELECTOR);
    this._files_page = document.querySelector(FILES_PAGE_SELECTOR);

    this._sidebar_chats_button.addEventListener('click', this);
    this._sidebar_contacts_button.addEventListener('click', this);
    this._sidebar_files_button.addEventListener('click', this);

    this._initEventable();
}

extendConstructor(SidebarConstructor, Eventable);

var sidebarConstructorPrototype = SidebarConstructor.prototype;

sidebarConstructorPrototype.handleEvent = function (e) {
    switch (e.type) {
        case 'click':
            this._sidebar_chats_button.classList.remove(ACTIVE_MODIFICATOR);
            this._sidebar_contacts_button.classList.remove(ACTIVE_MODIFICATOR);
            this._sidebar_files_button.classList.remove(ACTIVE_MODIFICATOR);

            this._chats_page.classList.add(HIDDEN_MODIFICATOR);
            this._contacts_page.classList.add(HIDDEN_MODIFICATOR);
            this._files_page.classList.add(HIDDEN_MODIFICATOR);

            switch (e.srcElement) {
                case this._sidebar_chats_button:
                    this._sidebar_chats_button.classList.add(ACTIVE_MODIFICATOR);
                    this._chats_page.classList.remove(HIDDEN_MODIFICATOR);
                    break;
                case this._sidebar_contacts_button:
                    this._sidebar_contacts_button.classList.add(ACTIVE_MODIFICATOR);
                    this._contacts_page.classList.remove(HIDDEN_MODIFICATOR);
                    break;
                case this._sidebar_files_button:
                    this._sidebar_files_button.classList.add(ACTIVE_MODIFICATOR);
                    this._files_page.classList.remove(HIDDEN_MODIFICATOR);
                    break;
            }
            break;
    }
};

module.exports = SidebarConstructor;
