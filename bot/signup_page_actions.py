from random import randint
from random import choices

def signup_page_action(browser, user_number):
    browser.find_element_by_css_selector('.sidebar-registration').click()

    user_name = browser.find_element_by_css_selector('.registration-page > .credentials-field-w > .js-credentials-name')
    user_email = browser.find_element_by_css_selector('.registration-page > .credentials-field-w > .js-credentials-email')
    user_password = browser.find_element_by_css_selector('.registration-page > .credentials-field-w > .js-credentials-password')
    user_password_confirmation = browser.find_element_by_css_selector('.registration-page > .credentials-field-w > .js-credentials-password-confirmation')
    button = browser.find_element_by_css_selector('.registration-page > .js-registration-action-button')

    #username = 'deliftUser' + str(user_number) + '@yopgmail.com'
    name = 'user{}'.format(user_number)
    email = name + '@' + name + '.com'
    username = name
    password = 'useruser'

    # Вошли
    user_name.send_keys(name)
    user_email.send_keys(email)
    user_password.send_keys(password)
    user_password_confirmation.send_keys(password)
    button.click()

    if (browser.wait_for_element('.sidebar-list_chats') == None):
        browser.quit()
