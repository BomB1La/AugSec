from sys import platform
import socket, uuid, qrcode

STATUS = 'locked'
QRCODE = ''

SERVER_HOST = 'localhost'
SERVER_PORT = 8090


def main():
    print '-----------------'
    print 'AugSecurity 2017 Lockscreen'
    print '-----------------'
    initialize()


def initialize():
    generate_barcode()
    initialize_connection()


def initialize_connection():
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect((SERVER_HOST, SERVER_PORT))
    client.close()


# When i will be able to control mouse nad keyboard i will use this initializion function
def initialize_controllers():
    if platform == 'linux' or platform == 'linux2':
        initialize_linux()
    elif platform == 'win32' or platform == 'win64':
        initialize_windows()
    else:
        print platform, 'is not supported'


def initialize_linux():
    import pynput

    print 'Initializing for linux computer'
    with pynput.mouse.Listener(on_move=on_move,	on_click=on_click, on_scroll=on_scroll) as mouse_listener:
        mouse_listener.join()
    with pynput.keyboard.Listener(on_press=on_press,on_release=on_release) as keyboard_listener:
        keyboard_listener.join()
    

# < For Windows > If will be added
def initilize_windows():
    import pyHook

    print 'Initializing for windows computer'
    hm = pyHook.HookManager()
    hm.MouseAll = mouse_keyboard_event
    hm.KeyAll = mouse_keyboard_event
    hm.HookMouse()
    hm.HookKeyboard()


# Mouse listener functions:
def on_move(x, y):
    print('Pointer moved to {0}'.format((x, y)))


def on_click(x, y, button, pressed):
    print('{0} at {1}'.format('Pressed' if pressed else 'Released', (x, y)))


def on_scroll(x, y, dx, dy):
    print('Scrolled {0}'.format((x, y)))



# Keyboard listener functions:
def on_press(key):
    print('{0} pressed'.format(key))


def on_release(key):
    print('{0} release'.format(key))
    if key == Key.esc:
        # Stop listener
        return False


def generate_barcode():
    qr = qrcode.QRCode(version=1, error_correction=qrcode.constants.ERROR_CORRECT_L, box_size=100, border=5)
    qr.add_data(get_mac())
    qr.make(fit=True)
    img = qr.make_image()

    img.save('qrcode.png') # That line is used to save the qrcode direclty to the computer (we are not going to do it on the project) we are going to display it on our gui


def get_mac():
  return ':'.join(['{:02x}'.format((uuid.getnode() >> i) & 0xff) for i in range(0,8*6,8)][::-1])


if __name__ == '__main__':
    main()
