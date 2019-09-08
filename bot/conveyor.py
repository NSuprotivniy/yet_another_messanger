from multiprocessing import Process
from multiprocessing import Queue
from sys import argv
from bot import bot_entry_point


# Запускает бота number раз, если передали 'inf', то будет делать это бесконечно
def start(number):
    if number == 'inf':
        n = 0
        while True:
            print("Loop number: {}".format(n))
            bot_entry_point()
            n += 1
    else:
        n = number
        while True:
            n -= 1
            if n == 0 : break
            print("Loop number: {}".format(n))
            bot_entry_point()

    print("Один из потоков отработал {} раз".format(number))


# Запускает потоки с ботами
if __name__ == '__main__':
    '''
    times = 'inf'

    if len(argv) > 1:
        times = int(argv[1])

    print('Количество циклов: {}'.format(times))
    start(times)
    '''

    if len(argv) > 1:
        threads_number = int(argv[1])    # Сколько потоков запустить
        times = int(argv[2])             # Сколько раз поток будет запускать бота
    else:
        threads_number = 3
        times = 'inf'


    print('Количество потоков: {}'.format(threads_number))
    print('Количество циклов на поток: {}'.format(times))

    bots = []

    for i in range(threads_number):
        bots.append(Process(target=start, args = (times, )))

    for bot in bots:
        bot.daemon = True
        bot.start()

    for bot in bots:
        bot.join()
