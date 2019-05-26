var extendConstructor = require('../utils/extendConstructor');
var templatesEngine = require('../modules/templatesEngine');
var ContactListItem = require('../components/ContactListItem');

/**
 * @param itemData
 * @implements {EventListener}
 * @constructor
 */
function ContactListItemConstructor(itemData) {
    this._initEventable();

    var templateResult = templatesEngine.chatAddContactItem({
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

extendConstructor(ContactListItemConstructor, ContactListItem);