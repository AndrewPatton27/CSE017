#!/bin/bash
cd "$(dirname "$0")"
rm -f output.txt 2>&1 >/dev/null
javac -d . ../*.java *.java
java -cp . Test > output.txt
java -cp . Testing 5 output.reference output.txt
