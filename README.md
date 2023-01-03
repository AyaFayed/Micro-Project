# Micro-Project

![image](https://user-images.githubusercontent.com/79098945/206870116-6da4320c-9a16-4c06-9841-cfbd75411af6.png)

# Introduction

This is a simulation of Tomasulo's Architecture that apply the concept of dynamic scheduling algorithm. The simulation is done through covering a subset of MIPS assembly format associated with this architecture which include loads and stores and basic ALU operations, all will be discussed in this document.

# Approach Overview

The project is implemented using Java as programming language and Java swing for the Graphical User Interface (GUI). The project is well structured following OOP principles and proper Design patterns.

The approach of coding was to divide every element in the system into separate object and include the behaviou of this element into this object. We applied Single Responsibility for each element by extracting common fearures in higher (super) Objects (classes). In the following a detailed explaination for each element/component of the system.

## Cells

As known about Tomasulo's architecture that it have several temp storages for instructions that keep track of an instruction throughout its cycles (execution); e.g. (reservation areas, instruction queue, Buffers,....etc), and based on that every piece of hardware has its own fields regarding each instruction. So, the concept of cells and although it is obvious that every type will need different type of cell with different fields, all of them have common features. The hierarchy for cells is as following:

- Cell
  - ReservationStationCell
  - BufferCell
    - LoadBufferCell
    - StoreBufferCell

**note**: every nested class inherits all its parents up to the root; e.g. (StoreBufferCell &rarr; BufferCell &rarr; Cell)

### Cell

It descripes the basic behaviour of a cell in the system and contains the common attributes of all cells.

- Attributes
  - busy : int
  - executedCycles : int
  - index : int
  - latency : int
    ..
- Functions:
  - display()
  - execute()
  - finishedExecution()
  - free()
  - getBusy()
    ..

### ReservationStationCell

Extends `Cell` class and adds to it the additional attributes and behaviours of reservation cells.

- Attributes
  - op : Operation
  - qj : String
  - qk : String
  - vj : Double
  - vk : Double
    ..
- Functions:
  - checkValueOnBus(String, double)
  - display()
  - execute()
  - isReady()
    ..

### BufferCell

Extends `Cell` class and adds to it additional attributes and behaviours of Buffers Cell like the address of the store and load.

- Attributes
  - address : String
- Functions:
  - checkValueOnBus(String, double)
  - getAddress()
  - occupy(int, int, String)
    ..

#### LoadBufferCell

Extends `BufferCell` class and adds to it additional attributes and behaviours of Buffers Cell like modifying the behaviour of execute.

- Function:
  - execute()

#### StoreBufferCell

Extends `BufferCell` class and adds to it additional attributes and behaviours of Buffers Cell like modifying the behaviour of execute.

- Attributes:
  - q : String
  - v : Double

## Buffers

Tomasulo has to different Buffers; one for `store` operations and one for `load` operations. Buffers consist of cells. They simply holds one or more load/store instruction(s) at a time throughout the execution. The classes hierarchy as the following:

- Buffer
  - LoadBuffer
  - StoreBuffer

### Buffer

Holds information about the available cells and the size of the buffer and it represnt the super class for all buffers.

- Attributes
  - availableBuffers : int
  - buffer : BufferCell[]
  - size : int
    ..
- Functions:
  - add(int, int, String)
  - decAvailableBuffers()
  - display(String)
  - freeCell(String)
    ..

### LoadBuffer & StoreBuffer

Both Extend `Buffer` class and specify the size of it in addtion to Overriding the different functions.

## Reservation Stations

Tomasulo's architecture has to different blocks of resrvation stations one concerned with the add/sub and for mul/div. Reservation stations hold the instruction from fetching till writing the result back.

### ReservationStation

The Super class for all reservation stations classes that contains the common attributes and functions of them.

- Attributes:
  - availableReservationStations : int
  - reservationStation : ReservationStationCell[]
  - size : int
- Functions:
  - add(int, int, Operation, String, String)
  - checkValueOnBus(String, double)
  - display(String)
  - freeCell(String)
  - hasAvailableReservationStations()
  - isEmpty()

### AddReservationStations & MulReservationStations

Both Extend `ReservationStation` class and specify the size of it in addtion to Overriding the different functions.

## GUI

The simulation is done through GUI where you can set the latency and write and run your code. In the follwoing the main two frames. The source code for the GUI is written in GUI package where MainGUI is the controller of the flow.

### Initialization

![Initialization](https://i.postimg.cc/qgftfWsH/initialization.png)

### Execution Panel

![execution panel](https://i.postimg.cc/sxGBN3ZJ/executionpanel.png)

# Files Structure

![file structure](https://i.postimg.cc/fLnTRY3Y/work-space-Micro-src-gui-Execution-Panel-java-Eclipse-IDE-1-3-2023-6-16-55-PM.png)

# RUN 
To run the project all you have to do is run ```src/gui/MainGUI.java```.

## Demo

### Example 1
This demo is from lecture 11-12
- latencies
    - Add / Sub latency 2 cycles
    - Mul latency 10 cycles
    - Div latency 40 cycles
```assembly
L.D F6, 1
L.D F2, 2
Mul.D F0, F2, F4
Sub.D F8, F6, F2
Div.D F10, F0, F6
Add.D F6, F8, F2
```

#### Final output
![final output](https://i.postimg.cc/13xSk2tM/Micro-Project-1-3-2023-8-09-12-PM.png)

### Example 2

- latencies: all 2 cycles
```assembly
Add.D F0, F0, F1
Mul.D F1, F1, F0
```
Cycle 1
![Cycle1](https://i.postimg.cc/hGTkDSCh/cycle1.png)
Cycle 2
![Cycle2](https://i.postimg.cc/W1FRymgG/cycle2.png)
Cycle 3
![Cycle3](https://i.postimg.cc/g2C9bwHf/cycle3.png)
Cycle 4
![Cycle4](https://i.postimg.cc/9FN51gRX/cycle4.png)
Cycle 5
![Cycle5](https://i.postimg.cc/GtX0WqXQ/cycle5.png)
Cycle 6
![Cycle6](https://i.postimg.cc/V6BPRYM2/cycle6.png)
Cycle 7 (final)
![Cycle7](https://i.postimg.cc/85VVMFFh/cycle7.png)