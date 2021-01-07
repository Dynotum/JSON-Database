# JSON-Database

## Stage 2/6: Network connection

## Description

Usually, online databases are accessed through the Internet. In this project, the database will be on your computer, but it will still be run as a separate program. The client who wants to get, create, or delete some information is a separate program, too.

We will be using a socket to connect to the database. A socket is an interface to send and receive data between different processes. These processes can be on the same computer or different computers connected through the Internet.

In this stage, you will implement the simplest connection between one server and one client. The client should send the server a message: something along the lines of Give me a record # N, where N is an integer number. The server should reply A record # N was sent! to the client.
Both the client and the server should print the received messages to the console. Note that they exchange only these texts, not actual database files.

Before a client connects to the server, the server output should be 'Server started!'.

To connect to the server, the client must know its address, which consists of two parts: IP-address and port. The address of your computer is always "127.0.0.1". The port can be any number between 0 and 65535, but preferably greater than 1024.

Let's take a look at this client-side code:

```java
String address = "127.0.0.1";
int port = 23456;
Socket socket = new Socket(InetAddress.getByName(address), port);
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output = new DataOutputStream(socket.getOutputStream());
```

The client created a new socket, which means that the client tried to connect to the server. Successful creation of a socket means that the client found the server and managed to connect to it.

After that, you can see the creation of DataInputStream and DataOutputStream objects. These are the input and output streams to the server. If you expect data from the server, you need to write input.readUTF(). This returns the String object that the server sent to the client. If you want to send data to the server, you need to write output.writeUTF(stringText), and this message will be sent to the server.

Now let's look at the server-side code:
```java
String address = "127.0.0.1";
int port = 23456;
ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
Socket socket = server.accept();
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
```
The server created a ServerSocket object that waits for client connections. When a client connects, the method server.accept() returns the Socket connection to this client. After that, you can see the creation of DataInputStream and DataOutputStream objects: these are the input and output streams to this client, now from the server side. To receive data from the client, write input.readUTF(). To send data to the client, write output.writeUTF(stringText). The server should stop after responding to the client.

Note: the server and the client are different programs that run separately. Your server should run from the main method of the /server/Main class, and the client should run from the main method of the /client/Main class. To test your program you should run the server first so a client can connect to the server.
## Output example

The server should output something like this:

```Server started!
Received: Give me a record # 12
Sent: A record # 12 was sent!
```

The client should output something like this:

```Client started!
Sent: Give me a record # 12
Received: A record # 12 was sent!
```

## Stage 1/6: 100-cell database

## Description

JSON database is a single file database that stores information in the form of JSON. It is a remote database, so it's usually accessed through the Internet.

In this stage, you need to simulate a database that can store text information in an array of the size 100. From the start of the database, every cell contains an empty string. Users can save strings in the cells, read the information from these cells, and delete that information if needed. After a string has been deleted, that cell should contain an empty string.

The user can use the commands set, get, or, delete commands.

After set, the user should specify a number (1-100) and the text to be saved in the cell. If the index is wrong, the program should output ERROR, otherwise, output OK. If the specified cell already contains information, it should be overwritten.

After get, the user should specify the number of the cell from which they want to get information. If the cell is empty or the index is wrong, the program should output ERROR; otherwise, the program should output the content of the cell.

After delete, the user should specify the number of the cell. If the index is wrong, the program should output ERROR; otherwise, output OK. If the string is empty, you don't have to do anything.

To exit the program, the user should enter exit.

Your program should run from the main method of the server/Main class.
# Output example

The greater-than symbol followed by a space > represents the user input.
```
> get 1
ERROR
> set 1 Hello world!
OK
> set 1 HelloWorld!
OK
> get 1
HelloWorld!
> delete 1
OK
> delete 1
OK
> get 1
ERROR
> set 55 Some text here
OK
> get 55
Some text here
> get 56
ERROR
> delete 55
OK
> delete 56
OK
> delete 100
OK
> delete 101
ERROR
> exit
```