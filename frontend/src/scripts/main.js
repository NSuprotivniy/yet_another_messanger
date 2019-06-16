var ChatList = require('./components/ChatList');
var AddChat = require('./components/AddChat');
var Sidebar = require('./components/Sidebar');
var AddContact = require('./components/AddContact');
var ContactList = require('./components/ContactList');
var FileList = require('./components/FileList');
var Login = require('./components/Login');
var Registration = require('./components/Registration');
var websocketInstance = require('./modules/WebsocketInstance');

function init() {
    try {
        var sidebar = new Sidebar();
        var login = new Login();
        var registration = new Registration();

        websocketInstance.on("logonError", function () {
            sidebar.setPage("login");
        });

        var fileList = new FileList();

        var addChat = new AddChat();
        var chatList = new ChatList();
        addChat.on('newChat', function (chatData) {
            chatList._createItem(chatData);
            sidebar.setPage("chat");
        });

        chatList.on('openChat', function (modelId) {
            sidebar.setPage("chat");
        });

        var addContact = new AddContact();
        var contactList = new ContactList();

        addContact.on('newContact', function (contactData) {
            contactList._createItem(contactData);
        });
        contactList
            .on('itemChecked', function (contact) {
                addChat._onContactItemChecked(contact);
            })
            .on('itemUnchecked', function (contact) {
                addChat._onContactItemUnchecked(contact);
            });


        login.on('login', function () {
            sidebar.setLoggedOn();
            chatList.loadChats();
            contactList.loadContacts();
            fileList.load();
        });
        registration.on('registration', function () {
            sidebar.setLoggedOn();
            chatList.loadChats();
            contactList.loadContacts();
            fileList.load();
        });

        sidebar.on("logoutButton", function () {
           login.resetSession();
           chatList.clear();
           contactList.clear();
        });

    } catch (e) {
        if (e.name === "LogonError") {
            login.resetSession();
            sidebar.setPage("login");
        } else {
            console.log(e);
        }
    }

}

document.addEventListener('DOMContentLoaded', init);