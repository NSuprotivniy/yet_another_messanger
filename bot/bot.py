from random import choices
from random import randint
import os
from browsers_driver import browser
import lorem

import chat_page_actions
import contact_page_actions
import login_page_actions
import files_page_actions

from info import *

google_chrome = 0

def send_file(google_chrome):
    name = "{}.jpg".format(randint(1, 6))
    path = os.getcwd()+"/files/" + name
    if not files_page_actions.check_file_exists(google_chrome, path):
        files_page_actions.open_files(google_chrome)
        files_page_actions.add_file(google_chrome, path)
    return name


def do_actions():

    global google_chrome

    sent = False

    file_name = None
    if choices([0, 1], weights=[0.9, 0.1]).pop() == 1:
        file_name = send_file(google_chrome)


    for i in range(randint(0, 2)):

        user_number = randint(0, 50)
        name = 'user{}'.format(user_number)
        email = name + '@' + name + '.com'
        # name = "nikita"
        # email = "n@mail.ru"

        if not contact_page_actions.check_contact_exists(google_chrome, name):
            contact_page_actions.open_contacts(google_chrome)
            contact_page_actions.add_contact(google_chrome, name, email)

        if choices([0, 1], weights=[0.1, 0.9]).pop() == 0:
            return

        if not sent and choices([0, 1], weights=[0.3, 0.7]).pop() == 0:
            chat_page_actions.open_chats_page(google_chrome)
            chat_page_actions.add_chat(google_chrome, name)
            if file_name == None:
                chat_page_actions.send_message(google_chrome, lorem.sentence())
            else:
                chat_page_actions.send_message_with_file(google_chrome, lorem.sentence(), file_name)
            sent = True



def bot_entry_point():

    global google_chrome

    from stem import Signal
    from stem.control import Controller

    utm_labels = [localhost + '?utm_source=google&utm_medium=cpc&utm_campaign=video',
                  localhost + '?utm_source=yandex&utm_medium=cpc&utm_campaign=video',
                  localhost + '?utm_source=vk&utm_medium=banner&utm_campaign=video',
                  localhost + '?utm_source=ok&utm_medium=banner&utm_campaign=video',
                  localhost + '?utm_source=facebook&utm_medium=banner&utm_campaign=video']

    labels_probability = [35, 25, 15, 10, 15]

    # Определяется откуда пришел бот
    url = choices(utm_labels, weights=labels_probability).pop()

    print("[*] Bot start working...")

    proxy_ports = [9051, 9061, 9063, 9065]
    proxy_port = choices(proxy_ports).pop()

    if choices([0, 1], weights=[0.9, 0.1]).pop() == 1:
        with Controller.from_port(port = 9061) as controller:
          controller.authenticate()
          controller.signal(Signal.NEWNYM)

    # Запуск браузера
    google_chrome = browser("127.0.0.1:{}".format(proxy_port - 1))
    google_chrome.get(url)

    user_number = randint(0, 50)
    # Авторизация
    login_page_actions.login_page_action(google_chrome, user_number)

    # do_actions()
    try:
        do_actions()
    except Exception as detail:
        print(detail)

    google_chrome.quit()


# Закомментить при потоках
# bot_entry_point()
