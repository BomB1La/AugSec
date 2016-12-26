import socket, sys, thread

logged_in = []
computers = {}
clients_computers = {}

def main():
    print '-----------------'
    print 'AugSecurity 2017 Server'
    print '-----------------'
    initialize()


def initialize():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('localhost', 8090)
    print >> sys.stderr, 'starting up on %s port %s' % server_address
    sock.bind(server_address)
    listen(sock)
    socket.close()


def listen(sock):
    sock.listen(9999999)
    while True:
       c, addr = sock.accept()
       thread.start_new_thread(on_new_client,(addr,))


def on_new_client(clientsocket):
    print 'Got connection from', clientsocket
    while True:
        if (loggend_in[clientsocket] is None):
            msg = clientsocket.recv(1024)
            if '100' is in msg:
                print addr, '>>', 'Lockscreen connected'
                clientsocket.send('200')
                msg = clientsocket.recv(1024)
                print addr, '>>', msg
                computers[clientsocket] = msg
            else:
                print addr, '>>', msg
            logged_in[client_socket] = client_socket
        elif (computers[clientsocket] is not None) and (clients_computers[computers[clientsocket]] is not None): # clients_computers[computers[clientsocket]] < That's pointing to the barcode
            clientsocket.send('201') # Asking for the Identification
            msg = clientsocket.recv(1024)
            client = clients_computers[clientsocket]
            client.send(msg)
            clients_computers[computers[clientsocket]] = None
        else:
            msg = clientsocket.recv(1024)
            print addr, '>>', msg
    logged_in[client_socket] = None
    clientsocket.close()


if __name__ == '__main__':
    main()
