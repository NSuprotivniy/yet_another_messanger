var ChatsList = require('./components/ChatsList');
var AddChat = require('./components/AddChat');
var Sidebar = require('./components/Sidebar');
var AddContact = require('./components/AddContact')
var ContactList = require('./components/ContactList')


function init() {
    var sidebar = new Sidebar();

    var addChat = new AddChat();
    var chatsList = new ChatsList();
    addChat.on('newChat', function (chatData) { chatsList.createItem(chatData); });

    var addContact = new AddContact();
    var contactList = new ContactList();

    addContact.on('newContact',function (contactData) { contactList.createItem(contactData); });

}

document.addEventListener('DOMContentLoaded', init);