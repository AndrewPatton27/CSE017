#!/bin/bash
rm -f output 2>&1 >/dev/null
javac *.java
rm accounts.txt
cp original.txt accounts.txt
java BankManager > output
javac Testing.java
java Testing 40 output.reference output