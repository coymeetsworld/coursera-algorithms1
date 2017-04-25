#!/bin/bash
wget http://algs4.cs.princeton.edu/linux/findbugs.zip
wget http://algs4.cs.princeton.edu/linux/findbugs.xml
wget http://algs4.cs.princeton.edu/linux/findbugs-algs4
unzip findbugs.zip
chmod 755 findbugs-algs4
mv findbugs-algs4 /usr/local/bin
