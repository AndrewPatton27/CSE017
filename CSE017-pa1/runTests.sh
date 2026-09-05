#!/bin/bash
rm -f output 2>&1 >/dev/null
javac *.java
java BankManager > output
javac Testing.java
java Testing 14 output.reference output