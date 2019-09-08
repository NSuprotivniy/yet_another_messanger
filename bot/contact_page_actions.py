from bs4 import BeautifulSoup
from random import choices
from random import randint
from selenium.webdriver.common.keys import Keys

def open_contacts(browser):
    browser.find_element_by_css_selector('.sidebar-list_contacts').click()

def check_contact_exists(browser, name):
    if (browser.wait_for_element_with_text('.js-contacts-page > .js-contact-list > .contact-list-item > .contact-list-item_name-w > .js-contact-list-item_name', name) == None):
        return False
    else:
        return True

def add_contact(browser, name, email):
    if (browser.wait_for_element('.js-contacts-page > .contact-add > .contact-add_input-w > .js-contact-add-input') == None):
        raise Exception("No contact input")

    input = browser.find_element_by_css_selector('.js-contacts-page > .contact-add > .contact-add_input-w > .js-contact-add-input')
    input.send_keys(email)
    input.send_keys(Keys.RETURN)
    # 
    # if (browser.wait_for_element_with_text('.js-contacts-page > .js-contact-list > .contact-list-item > .contact-list-item_name-w > .js-contact-list-item_name', name) == None):
    #     raise Exception("No contact added")
