# import pyHook
from sys import platform
import uuid
import qrcode, pynput

STATUS = 'locked'
QRCODE = ''

SERVER_HOST = '207.232.21.254' # My own VPS that the server will run in for tests
SERVER_PORT = '8090'


def main():
    print '-----------------'
    print 'AugSecurity 2017'
    print '-----------------'
    initialize()


def initialize():
    generate_barcode()


def generate_barcode():
    qr = qrcode.QRCode(version=1, error_correction=qrcode.constants.ERROR_CORRECT_L, box_size=100, border=5)
    qr.add_data(get_mac())
    qr.make(fit=True)

    img = qr.make_image()
    img.save('qrcode.png')


def get_mac():
  return ':'.join(['{:02x}'.format((uuid.getnode() >> i) & 0xff) for i in range(0,8*6,8)][::-1])


# When i will be able to control mouse nad keyboard i will use this initializion function
def initialize_controllers():
    if platform == 'linux' or platform == 'linux2':
        initialize_linux()
    elif platform == 'win32' or platform == 'win64':
        initialize_windows()
    else:
        print platform, 'is not supported'


def initialize_linux():
    print 'Initializing for linux computer'
    with pynput.mouse.Listener(on_move=on_move,	on_click=on_click, on_scroll=on_scroll) as mouse_listener:
        mouse_listener.join()
    with pynput.keyboard.Listener(on_press=on_press,on_release=on_release) as keyboard_listener:
        keyboard_listener.join()
    

def initilize_windows():
    print 'Initializing for windows computer'
    # < For Windows > If will be added  
    # hm = pyHook.HookManager()
    # hm.MouseAll = mouse_keyboard_event
    # hm.KeyAll = mouse_keyboard_event
    # hm.HookMouse()
    # hm.HookKeyboard()


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


if __name__ == '__main__':
    main()
