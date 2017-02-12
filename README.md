# Mips CLI Simulator

Mips CLI Simulator simulates the MIPS (Microprocessor without Interlocked Pipelined Stages) architecture by taking set of assembler instruction and parsing it to MIPS-compatible instructions according to the original op-function map:

![opFuncMap](images/opFuncMap.png?raw=true "output1")

### How To Run

MipsSim uses Maven. Run:
```sh
mvn clean install compile package
```
 ... in order to generate a runnable jar-file in ./target
 
The jar file requires an input-file of instructions (separated by newlines) and an optional argument of an output-file. Test files can be found under src/resources.

To run e.g. testrun1.txt run:
```sh
java -jar target/MipsSim-1.0-SNAPSHOT.jar src/resources/testrun1.txt ./output.txt
```

which will yield the following result:

![Testrun1 output](images/testrun1.png?raw=true "output1")
