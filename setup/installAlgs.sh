#!/bin/bash
# Along with other scripts, important /usr/local/bin/ is in your PATH environment variable, need to use sudo to run scripts also.
wget http://algs4.cs.princeton.edu/code/algs4.jar
wget http://algs4.cs.princeton.edu/linux/javac-algs4
wget http://algs4.cs.princeton.edu/linux/java-algs4
chmod 755 javac-algs4 java-algs4
mv javac-algs4 /usr/local/bin
mv java-algs4 /usr/local/bin
