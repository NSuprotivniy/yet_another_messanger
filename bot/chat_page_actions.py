from bs4 import BeautifulSoup
from random import choices
from random import randint
from selenium.webdriver.common.keys import Keys

def open_chats_page(browser):
    browser.find_element_by_css_selector('.sidebar-list_chats').click()

def open_chat(browser, name):
    chat_names = browser.find_elements_by_css_selector('.js-chats-list > .chats-list-item > .js-chats-list-item_name')
    for i in range(len(chat_names)):
        if (chat_names[i].text == name):
            chat_names[i].click()
            break

def add_chat(browser, name):
    input = browser.find_element_by_css_selector('.chat-add > .chat-add_input-w > .js-chats-chat-input')
    input.click()

    contact_names = browser.find_elements_by_css_selector('.js-chat-add-contact-list > .chat-add-contact-list-item > .chat-add-contact-list-item_name-w > .js-chat-add-contact-list-item_name')
    contact_check = browser.find_elements_by_css_selector('.js-chat-add-contact-list > .chat-add-contact-list-item > .chat-add-contact-list-item_checker > .js-chat-add-contact-list-item_check')

    for i in range(len(contact_names)):
        if (contact_names[i].text == name):
            contact_check[i].click()

    input.send_keys(name)
    input.send_keys(Keys.RETURN)

    if (browser.wait_for_element_with_text('.chat > .chat_name', name) == None):
        raise Exception("No chat added")

def send_message(browser, text):
    input = browser.find_element_by_css_selector('.chat > .chat_message-input-w > .js-chat_message-text')
    input.send_keys(text)
    input.send_keys(Keys.RETURN)

def send_message_with_file(browser, text, file_name):
    text = text + " [" + file_name + "]"
    input = browser.find_element_by_css_selector('.chat > .chat_message-input-w > .js-chat_message-text')
    input.send_keys(text)
    print(text, input)
    for f_name in browser.find_elements_by_css_selector('.chat > .js-chat_files-to-choose > .file-to-choose > .js-file-to-choose_name'):
        if f_name.text == file_name:
            f_name.click()
    input.send_keys(Keys.RETURN)
