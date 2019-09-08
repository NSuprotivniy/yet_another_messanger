from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import NoSuchElementException
from os import getcwd
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.by import By

class browser(object):

    def __init__(self, proxy):
        chrome_options = webdriver.ChromeOptions()
        chrome_options.add_argument('headless')
        chrome_options.add_argument('window-size=1200x600')
        service_args = ['--proxy-server=%s' % proxy, '--proxy-type=socks5']
        self.window = webdriver.Chrome("/usr/lib/chromium-browser/chromedriver", service_args=service_args, chrome_options=chrome_options)
        print("[*] Start browser...")

    '''
    def __del__(self):
        quit()
        print('[*] Quit browser...')
    '''

    # Переходит по ссылке
    def get(self, link):
        self.window.get(link)

    # Выключает браузер
    def quit(self):
        self.window.quit()

    # Возвращает html
    def get_html(self, link):
        self.window.get(link)
        return self.window.page_source

    # Возвращает url текущей вкладки
    def get_url(self):
        return self.window.current_url

    # Возвращает элемент с текущей ссылки по классу
    def get_elem_by_class_name(self, class_name):
        return self.window.find_element_by_class_name(class_name)
    def get_elements_by_class_name(self, class_name):
        return self.window.find_elements_by_class_name(class_name)

    # Возвращает элемент с текущей ссылки по имени
    def get_elem_by_name(self, name):
        return self.window.find_element_by_name(name)
    def get_elements_by_name(self, name):
        return self.window.find_elements_by_name(name)

    # Возвращает элемент с текущей ссылки по классу
    def find_element_by_css_selector(self, selector):
        try:
            return self.window.find_element_by_css_selector(selector)
        except NoSuchElementException:
            return None

    # Возвращает элемент с текущей ссылки по классу
    def find_elements_by_css_selector(self, selector):
        try:
            return self.window.find_elements_by_css_selector(selector)
        except NoSuchElementException:
            return None

    def wait_for_element(self, selector):
        try:
            wait = WebDriverWait(self.window, 1)
            chat_list_btn = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, selector)))
            return chat_list_btn
        except TimeoutException:
            return None

    def wait_for_element_with_text(self, selector, text):
        try:
            wait = WebDriverWait(self.window, 2)
            chat_list_btn = wait.until(EC.text_to_be_present_in_element((By.CSS_SELECTOR, selector), text))
            return chat_list_btn
        except TimeoutException:
            return None
