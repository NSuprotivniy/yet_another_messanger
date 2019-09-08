from random import randint
from random import choices
import signup_page_actions

def login_page_action(browser, user_number):
    browser.find_element_by_css_selector('.sidebar-login').click()

    user_email = browser.find_element_by_css_selector('.login-page > .credentials-field-w > .js-credentials-email')
    user_password = browser.find_element_by_css_selector('.login-page > .credentials-field-w > .js-credentials-password')
    login_button = browser.find_element_by_css_selector('.login-page > .js-login-action-button')

    name = 'user{}'.format(user_number)
    email = name + '@' + name + '.com'
    password = 'useruser'

    # Вошли
    user_email.send_keys(email)
    user_password.send_keys(password)
    login_button.click()

    if (browser.wait_for_element('.sidebar-list_chats') == None):
        signup_page_actions.signup_page_action(browser, user_number)
