#!/bin/bash
rm -f output.txt 2>&1 >/dev/null
javac *.java
java Test > output.txt
javac Testing.java
java Testing 5 output.reference output.txt