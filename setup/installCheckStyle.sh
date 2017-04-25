#!/bin/bash
wget http://algs4.cs.princeton.edu/linux/checkstyle.zip
wget http://algs4.cs.princeton.edu/linux/checkstyle-algs4.xml
wget http://algs4.cs.princeton.edu/linux/checkstyle-suppressions.xml
wget http://algs4.cs.princeton.edu/linux/checkstyle-algs4
unzip checkstyle.zip
chmod 755 checkstyle-algs4
mv checkstyle-algs4 /usr/local/bin
