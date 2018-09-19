from socket import *
servreName = '192.168.1.75' # check it
serverPort = 12000

clientSocket = socket(socket.AF_INET, socket.SOCK_DGRAM)

message = raw_input('hello world')

clientSocket.sendto(message, (servreName, serverPort))

modifiedMessage, serverAdderss = clientSocket.recvfrom(2048)

print(modifiedMessage)

clientSocket.close()