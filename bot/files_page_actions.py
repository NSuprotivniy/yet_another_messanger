from bs4 import BeautifulSoup
from random import choices
from random import randint
from selenium.webdriver.common.keys import Keys

def open_files(browser):
    browser.find_element_by_css_selector('.sidebar-list_files').click()

def check_file_exists(browser, path):
    file_name = path.split('/')[-1]
    if (browser.wait_for_element_with_text('.files-page > .js-file-list > .file > .js-file_name', file_name) == None):
        return False
    else:
        return True

def add_file(browser, path):
    input = browser.find_element_by_css_selector('.files-page > .file-add > .js-file-input')
    input.send_keys(path)

    file_name = path.split('/')[-1]
    if (browser.wait_for_element_with_text('.files-page > .js-file-list > .file > .js-file_name', file_name) == None):
        raise Exception("No file added")
