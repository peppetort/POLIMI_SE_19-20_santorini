# Santorini

A light and fast implementation of Santorini board game by Cranio Creations written in Java.

## Features

### Socket

The game was developed as a distributed application. So both the server and the client have been implemented using Socket.

### Complete rules

The game can be played in 2 or 3, with or without the use of cards.

Implemented gods:

- Apollo
- Artemis
- Athena
- Atlas
- Demeter
- Hephaestus
- Minotaur
- Pan
- Prometheus

### CLI

User interface developed via command line.

Support for:

- Bash
- cmd
- Window Power Shell

### GUI

User interface written in JavaFX

### Additional functionality: Multiple Games

The server supports multiple game sessions simultaneously

### Additional functionality: UNDO

At any moment of the turn, starting from the choice of the pawn, it is possible to return to the initial state.
If all the mandatory moves have taken place, you have 5 seconds to apply the UNDO, otherwise the turn will be automatically passed.

## Requirements

### Main

The requirements to run the program are:

- Java JRE 8 

(you can download the right version for your OS at this URL: https://www.oracle.com/java/technologies/javase-jre8-downloads.html)

If your OS is Window, once downloaded java, remember to set the global java path

### CLI

If you run on Windows you'll need Windows terminal (you can download it on the Microsoft Store free)

If you don't want to download the new terminal you have to open the **cmd** and insert:

```cmd
REG ADD HKCU\CONSOLE /f /v VirtualTerminalLevel /t REG_DWORD /d 1
```
and press enter to enable colors on your windows terminal

## Start the game

### Server

##### Linux / OSX / Window

Open a new terminal window and type:

```bash
cd /path_to_Santorini_folder/deliverables
java -jar Server.jar
```

### Client

#### GUI

JavaFX dependencies are already included in the JAR.

##### Linux / OSX / Window

Go to *deliverables* folder located into this repo folder and double click on

- *SantoriniGUI.jar* if you started the server on *localhost*
- *SantoriniGUI_remote.jar* if you want to use a remote server created specifically for testing

#### CLI

##### Linux / OSX / Window

Open a new terminal window and type:

```bash
cd /path_to_Santorini_folder/deliverables
```

If you started the server on *localhost* type:

```bash
java -jar SantoriniCLI.jar
```

If you want to use a remote server created specifically for testing type:

```bash
java -jar SantoriniCLI_remote.jar
```

## Coverage

All the code was tested using *Junit*. 

Test cases have been written to maximize the number of lines covered:


**Package name** | **Class coverage** | **Lines Coverage**
------------ | ------------- | -------------
CLI | 0% | 0%
Client | 40% | 12%
Controller | 100% | 92%
Exceptions | 50% | 50%
GUI | 0% | 0%
Messages | 76% | 51%
Model | 100% | 96%
Observer | 100% | 37%
Server | 0% | 0%
View | 0% | 0%

## Documentation

To open the JavaDoc webpage open the following link: https://github.com/peppetort/ing-sw-2020-Tome-Tortorelli-Verze/blob/master/deliverables/JavaDoc/index.html.
The folder used to contains the generated javadoc in the master repo is ./deliverables/JavaDoc.

