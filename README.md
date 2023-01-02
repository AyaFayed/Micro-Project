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
Extends ```Cell``` class and adds to it the additional attributes and behaviours of reservation cells.

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
Extends ```Cell``` class and adds to it additional attributes and behaviours of Buffers Cell like the address of the store and load.
 
- Attributes
    - address : String
- Functions:
    - checkValueOnBus(String, double)
    - getAddress()
    - occupy(int, int, String)
    ..

#### LoadBufferCell
Extends ```BufferCell``` class and adds to it additional attributes and behaviours of Buffers Cell like modifying the behaviour of execute.

- Function:
    - execute()

#### StoreBufferCell
Extends ```BufferCell``` class and adds to it additional attributes and behaviours of Buffers Cell like modifying the behaviour of execute.

- Attributes:
    - q : String
    - v : Double

## Buffers
Tomasulo has to different Buffers; one for ```store``` operations and one for ```load``` operations. Buffers consist of cells. They simply holds one or more load/store instruction(s) at a time throughout the execution. The classes hierarchy as the following:

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