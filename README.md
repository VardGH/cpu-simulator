# CPU simulator
The CPUSimulator represents a CPU simulator that executes instructions stored in memory.

## Arithmetic and Logic Operations:
- mov(): Performs a move operation between a register and memory.
- add(): Adds two registers and stores the result in a destination register.
- sub(): Subtracts two registers and stores the result in a destination register.
- mul(): Multiplies two registers and stores the result in a destination register.
- div(): Divides two registers and stores the result in a destination register.
- and(): Performs a bitwise AND operation between two registers and stores the result in a destination register.
- or(): Performs a bitwise OR operation between two registers and stores the result in a destination register.
- not(): Performs a bitwise NOT operation on a register.
- cmp(): Compares two registers and stores the result in the da register.
- jmp(): Jumps to a specific memory address.
- jg(): Jumps to a specific memory address if da > 0.
- jl(): Jumps to a specific memory address if da < 0.
- je(): Jumps to a specific memory address if da == 0.

## Memory Class
The Memory class represents a block of memory where data and instructions are stored.
It provides methods for reading and writing data to specific memory addresses.

## RegisterFile Class
The RegisterFile class represents a collection of registers used by the CPU. 
It provides methods for accessing and modifying register values.

## Main Class
The Main class contains the main method and is used to run the CPU simulator. 
It loads a program from a file, executes the program, and displays the contents of memory and the register values.

## Usage
- Compile the Java source files by running the following command: javac Main.java
- Once the compilation is successful, you can run the code by executing the following command: java Main
