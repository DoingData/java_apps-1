# Java Grep App
## Introduction
The Java Grep App searches recursively for all lines in all files under a given directory that match a given regular expression.
The lines that match the pattern are written into a file. 

## Usage
```
USAGE: regex directory outFile
```
* regex - a regular expression describing the pattern that we are looking for
* directory  - the full path of the directory under which the app is supposed to search
* outFile - the full path of the file where the matched lines will be written
 
Running the app with the following three arguments
`.*data.* /home/user /tmp/grep.out`
searches all files in the directory /home/user and all subdirectories.
It writes any line containing the word 'data' to the file /tmp/grep.out.
 
## Design and Implementation
The implementation of the process() method is straightforward with two nested for-loops.
The out loop iterates over all files and the inner loop iterates over all lines in one file.
```
matchingLines = []
for file in listFiles(directory)
    for line in readLines(file)
        if containesPattern(line)
            matchingLines.add(line)
writeToFile(matchingLines)
```

## Enhancements and Issues
With the current implementation the app might run into an OutOfMemoryError.
All lines of a file are stored in a list, no matter if the match the expression or not.
If the directory containes very big files, allocating memory for the list can become a problem.
Another problem could be the list containing all matching lines.
This list grows until all files are processed.
Only then are the matching lines written into the file. 
These problems could be avoided if we combine all three methods into one: 
After a line is read we immediately if it matches the pattern or not.
If it matches we write it to the output file. If it does not match we can just ignore it.
In case we prefer to keep the methods separate returning streams instead of list is a better choice regarding memory issues. 


# Java JDBC App


# Twitter CLI App