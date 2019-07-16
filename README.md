# Introduction
This project consists of three application: the Twitter App, the Java Grep App, and the JDBC App.

- [1 Twitter CLI App](#1-twitter-cli-app)
  * [1.1 Introduction](#11-introduction)
  * [1.2 Usage](#12-usage)
  * [1.3 Design and Implementation](#13-design-and-implementation)
- [2 Java Grep App](#2-java-grep-app)
  * [2.1 Introduction](#21-introduction)
  * [2.2 Usage](#22-usage)
  * [2.3 Design and Implementation](#23-design-and-implementation)
  * [2.4 Enhancements and Issues](#24-enhancements-and-issues)
- [3 Java JDBC App](#3-java-jdbc-app)
  * [3.1 Introduction](#31-introduction)
  * [3.2 Design and Implementation](#32-design-and-implementation)
    + [3.2.1 Database tables](#321-database-tables)
    + [3.2.2 Code](#322-code)

# 1 Twitter CLI App
## 1.1 Introduction
The Twitter CLI app uses the Twitter Rest API to post, delete or search for a Tweet on Twitter from the command line.

## 1.2 Usage 
**Post a Tweet**
```
USAGE: post "text" latitude:longitude
```
This command will post a Tweet with the specified text and a geotag.
For example `post "Hello #Toronto" 43.653225:-79.383186` will post the Tweet "Hello #Toronto" with Toronto as its location.

**Delete a Tweet**
```
USAGE: delete "ids"
```
This command will delete the Tweet with the given ids.
The parameter ids is a comma-separated list of Tweet ids.
For example `delete "123, 234` will delete the two Tweets with id 123 and 234.

**Show a Tweet**
```
USAGE: show id "fields"
```
This command show the Tweet with the given id.
The parameter fields is a comma-separated list specifying which attributes of the Tweet will be displayed.
The possible attributes are: created at, text, id, hashtags, user mentions, coordinates, retweet count, favorited count, retweeted, favorited.
For example `show 3445 "hashtags, text"` will show the complete text as well as all hastags of the Tweet with id 3445.

## 1.3 Design and Implementation

![Twitter](https://github.com/MiriamEA/java_apps/blob/master/TwitterCLIApp.jpg)

The implementation is structured into several layers: access, service, interface, service and app.

**Access layer** 
The access layer handles the access to the Twitter REST API.
The HttpHelper interface has methods that take an URI, make a get or post HTTP request and return the response of that request.
The class ApacheHttpHelper implements this interface using the Apache HTTP client.
It expects the Twitter authentication details in system environment variables. 
The second interface in this layer is the CrdDao interface.
This interface contains methods to create an entity, find and entity by id, and delete an entity by id.
The class TwitterRestDao implements the CrdDao interface and depends on the HttpHelper.
This class is responsible for creating the URIs to post, show, and delete a tweet and passing them to the HttpHelper.
It is also responsible for creating tweet objects from the HTTP responses.

**Service layer**
The service layer consists of the TwitterService interface and the class TwitterServiceImp which implements TwitterService and depends on CrdDao.
All business logic is done here.
TwitterServiceImp is responsible for validating the user input (tweet text is not too long, latitude and longitude are actual coordinates, ids only consist of digits).
If the input is valid it will pass them to the corresponding method in CrdDao.
It is also responsible for selecting the correct fields when showing a tweet.

**Interface layer**
The interface layer consists of the class TwitterCLIService which depends on TwitterService.
In this class the user input is parsed and then passed on to the correct method in TwitterService.

**App layer** 
The app layer consists of the class TwitterCLIApp.
This class contains the main method.
There, all dependencies are created and the user input is passed to the interface layer.

# 2 Java Grep App
## 2.1 Introduction
The Java Grep App searches recursively for all lines in all files under a given directory that match a given regular expression.
The lines that match the pattern are written into a file. 

## 2.2 Usage
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
 
## 2.3 Design and Implementation
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

## 2.4 Enhancements and Issues
With the current implementation the app might run into an OutOfMemoryError.
All lines of a file are stored in a list, no matter if the match the expression or not.
If the directory contains very big files, allocating memory for the list can become a problem.
Another problem could be the list containing all matching lines.
This list grows until all files are processed.
Only then are the matching lines written into the file. 
These problems could be avoided if we combine all three methods into one: 
After a line is read we immediately if it matches the pattern or not.
If it matches we write it to the output file. If it does not match we can just ignore it.
In case we prefer to keep the methods separate returning streams instead of list is a better choice regarding memory issues. 

# 3 Java JDBC App
## 3.1 Introduction
The JDBC app uses the Java Database Connectivity API to connect and send SQL queries to a Postgresql database.
The database models a sales business.
The app can create a new customer, delete a customer, update information of a customer, find customer information, and get order information

## 3.2 Design and Implementation
### 3.2.1 Database tables
The database hplussport consists of five tables: customer, salesperson, product, orders, and order_item.
The table customer stores first name, last name, email address, phone number, address, city, zipcode, and an unique id for each customer.
The table salesperson stores the same information as the customer table for each salesperson.
The product table stores a code, a name, a size, a variety, a price, and a status. Each product is identified by a unique id.
The table orders contains the following information for an order: a unique id, a creation date, a total price, a status, a customer id, and a salesperson id.
The table order_item stores the product information for an order.
More specifically it stores order_id, product_it, quantity, and a unique id.

### 3.2.2 Code
![JDBC](https://github.com/MiriamEA/java_apps/blob/master/JDBC.png)

The implementation of this app uses the Data Access Object (DAO) pattern to send SQL queries and process their results.
The DAO pattern is used to separate low-level database access operations from high-level business operations.
The DataTransferObject interface ensures that a data transfer object like customer and order have a method that returns their id.
The abstract class DataAccessObject is a parameterized class where the parameter has to implement the DataTransferObject interface.
It provides method signatures for create, read, update, and delete (CRUD) operations.
The classes CustomerDAO and OrderDAO extend the DataAccessObject class.
They contain the specific SQL queries for the CRUD operations for customer and order data.
