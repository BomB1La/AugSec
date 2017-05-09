import socket, sys, thread, time

# Communication Protocol
class Protocol:
    OK = '200'
    LOCKSCREEN_CONNECTION = '100'
    ASK_FOR_ID = '201'
    GET_INFO = '101'


logged_in = {}
computers = {}
clients_computers = {}


def main():
    print '-----------------'
    print 'AugSecurity 2017 Server'
    print '-----------------'
    initialize()


def initialize():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('localhost', 8090)

    print 'starting up on %s port %s' % server_address
    print '-----------------'

    server_socket.bind(server_address)
    listen(server_socket)
    server_socket.close()


def listen(sock):
    print 'Listening for connections'
    print '-----------------'
    sock.listen(9999999)
    while True:
        c, csock = sock.accept()
        print ''
        print 'Got connection from', csock
        print '-----------------'
        thread.start_new_thread(on_new_client, (c, csock, sock))


def on_new_client(conn, clientsocket, server_socket):
    addr, cport = clientsocket
    while True:
        if clientsocket not in logged_in:
            send(conn, Protocol().ASK_FOR_ID)
            msg = recv(conn, 1024)
            if Protocol().LOCKSCREEN_CONNECTION in msg:
                print clientsocket, '>>', 'Lockscreen connected'
                send(conn, Protocol().OK)
                msg = recv(conn, 1024)  # Reciving the MAC ADDRESS
                print clientsocket, '>>', msg
                computers[addr] = msg
            else:
                print clientsocket, '>>', msg
            logged_in[clientsocket] = True
        elif computers[addr] is not None:
            if computers[addr] not in clients_computers:  # That's pointing to the barcode
                return
            send(conn, Protocol().ASK_FOR_ID)  # Asking for the Identification
            msg = recv(conn, 1024)
            client = clients_computers[clientsocket]

            send(client, msg)
            clients_computers[computers[clientsocket]] = None
        else:
            msg = recv(conn, 1024)
            if Protocol().GET_INFO in msg:
                print addr, '>>', 'Wants to get information for computer'
                server_socket.sendto(Protocol().OK, addr)
                msg = recv(conn, 1024)  # Getting the computer MAC_ADDRESS <- QRCODE
                print addr, '>>', msg
                clients_computers[msg] = clientsocket
                print addr, '>>', 'Waiting for information from:', msg
            else:
                print addr, '>>', msg

        msg = recv(conn, 1024)
        if 'DISCONNECT' in msg:
            break

    logged_in[addr] = None
    computers[addr] = None
    print clientsocket, '>>', 'Disconnected'
    conn.close()


def send(conn, msg):
    conn.send(msg + '\r')


def recv(conn, bytes):
    return str(conn.recv(bytes)).replace('\n', '').replace('\r', '')

if __name__ == '__main__':
    main()
