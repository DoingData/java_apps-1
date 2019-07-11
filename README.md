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
If the directory contains very big files, allocating memory for the list can become a problem.
Another problem could be the list containing all matching lines.
This list grows until all files are processed.
Only then are the matching lines written into the file. 
These problems could be avoided if we combine all three methods into one: 
After a line is read we immediately if it matches the pattern or not.
If it matches we write it to the output file. If it does not match we can just ignore it.
In case we prefer to keep the methods separate returning streams instead of list is a better choice regarding memory issues. 


# Java JDBC App
## Introduction
The JDBC app uses the Java Database Connectivity API to connect and send SQL queries to a Postgresql database.

## Design and Implementation
### Database tables
The database hplussport consists of five tables: customer, salesperson, product, orders, and order_item.
The table customer stores first name, last name, email address, phone number, address, city, zipcode, and an unique id for each customer.
The table salesperson stores the same information as the customer table for each salesperson.
The product table stores a code, a name, a size, a variety, a price, and a status. Each product is identified by a unique id.
The table orders contains the following information for an order: a unique id, a creation date, a total price, a status, a customer id, and a salesperson id.
The table order_item stores the product information for an order.
More specifically it stores order_id, product_it, quantity, and a unique id.

### Code
The implementation of this app uses the Data Access Object pattern to send and process SQL queries.


# Twitter CLI App
## Introduction
The Twitter CLI app uses the Twitter Rest API to post, delete or search for a Tweet on Twitter from the command line.

## Usage 
**Post a Tweet**
```
USAGE: post "text" latitude:longitude
```
This command will post a Tweet with the specified text and a geotag. 

**Delete a Tweet**
```
USAGE: delete "ids"
```
This command will delete the Tweet with the given ids.
The parameter ids is a comma-separated list of Tweet ids.

**Show a Tweet**
```
USAGE: show id "fields"
```
This command show the Tweet with the given id.
The parameter fields is a comma-separated list specifying which attributes of the Tweet will be displayed.
The possible attributes are: created at, text, id, hashtags, user mentions, coordinates, retweet count, favorited count, retweeted, favorited.

## Design and Implementation

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