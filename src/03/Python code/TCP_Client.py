from socket import *

serverName = ''

serverPort = 12000
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))

sentence = raw_input("hello world")

clientSocket.send(sentence)

modifiedSentence = clientSocket.recv(1024)

print("From Server: " + modifiedSentence)

clientSocket.close()