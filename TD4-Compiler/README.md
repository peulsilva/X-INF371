# Coding a Java compiler

## :white_check_mark: Goals

The goal of this assignment is to code a Java compiler and understand the design of compilers and abstract classes.

This new language will be compiled in ``` .wil``` files and can ran through [XVM](https://www.enseignement.polytechnique.fr/informatique/INF371/xvm/) (Polytechnique's Virtual Machine). 

# :runner: Running the code

To generate the compiled code, run ```/src/TestProgramCodeGen.java```. It will compile the code in ```/example.wil```. Feel free to modify this file.

Some observations about this new language:

* The only types allowed are ```int``` and ```boolean```.
* The accepted instructions are ```if(condition)/else, while(condition), return (value), print (expression)```