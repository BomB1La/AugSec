import socket, sys, thread

# Communication Protocol
class Protocol:
    OK = '200'
    LOCKSCREEN_CONNECTION = '100'
    ASK_FOR_ID = '201'
    GET_INFO = '101'


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
            if Protocol().LOCKSCREEN_CONNECTION in msg:
                print addr, '>>', 'Lockscreen connected'
                clientsocket.send(Protocol().OK)
                msg = clientsocket.recv(1024)
                print addr, '>>', msg
                computers[clientsocket] = msg
            else:
                print addr, '>>', msg
            logged_in[client_socket] = client_socket
        elif (computers[clientsocket] is not None):
            if (clients_computers[computers[clientsocket]] is None): # clients_computers[computers[clientsocket]] < That's pointing to the barcode
                return
            clientsocket.send(Protocol().ASK_FOR_ID) # Asking for the Identification
            msg = clientsocket.recv(1024)
            client = clients_computers[clientsocket]
            client.send(msg)
            clients_computers[computers[clientsocket]] = None
        else:
            msg = clientsocket.recv(1024)
            if Protocol().GET_INFO in msg:
                print addr, '>>', 'Wants to get information for computer'
                clientsocket.send(Protocol().OK)
                msg = clientsocket.recv(1024) # Getting the computer MAC_ADDRESS <- QRCODE
                print addr, '>>', msg
                clients_computers[msg] = clientsocket
                print addr, '>>', 'Waiting for information from:', msg
            else:
                print addr, '>>', msg
    logged_in[client_socket] = None
    clientsocket.close()    


if __name__ == '__main__':
    main()
