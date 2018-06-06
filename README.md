<h1 align="center">
  <br>
  <img height="240" width="320" src="https://github.com/Guertz/pricetag/blob/master/client/src/main/assets/icons/pricetag_2_transparent.png?raw=true" alt="Pricetag logo" title="pricetag" />
  <br>
  Amazon price tracker
  <br>
</h1>

<p align="center">  
   <a href="https://www.npmjs.com/package/ws"><img src="https://img.shields.io/npm/v/ws.svg"></a>
   <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/license-MIT-blue.svg"></a>
   <a href="https://guertz.github.io/pricetag/"><img src="https://img.shields.io/badge/docs-100%25-green.svg"></a>
</p>
<p align="center">
  <a href="#some-facts">Some Facts</a> •
  <a href="#how-to-use">How to use</a> •
  <a href="#download">Download</a> •
  <a href="#license">License</a>
</p>

## Some Facts
Pricetag is an University project. Actually, We chose to make this because we didn't like the proposed one by the Professor 
so we came up with this challenging idea. Basically, the program works like [CamelCamelCamel](https://camelcamelcamel.com/) but the core part here is that all the prices are tracked by clients. That means when you search a product, firstly, pricetag will check 
if any of the connected client has already a more updated price timeline, if so all the data is sent to requester. Secondly it will make a request to lookup the product in Amazon. The project is made with Java for client side and Nodejs as a server side, to trace all clients and to make a peer-to-peer communication through sockets. Everything is possibile thanks to

<p align="center">
<a href="https://docs.aws.amazon.com/AWSECommerceService/latest/DG/CHAP_ApiReference.html">
    <img src="https://raw.githubusercontent.com/ISchwarz23/Amazon-Product-Advertising-API-URL-Builder/master/README/apaapi_logo.png" alt="AWS product api logo" title="AWS" height="60" />
</a>
</p>

## How to use
Download and run it!
Run
```java -jar downloaded.jar databasefile```
where databasefile is a file where pricetag will use as database to store products.

Actually you can omit the database file, that means everything will be stored in the memory and once you close the program all data will be erased:
```java -jar downloaded.jar```

Note: if you are under Java 9, it may have problems. If so try to run:
```java --add-modules java.xml.bind  -jar downloaded.jar databasefile```


### From Scratch
If you want to build your own version from scratch:

### Server
* Make sure to get npm
* Go to /server directory
* Run ```npm install```
* Run ```npm run build```
### Client
* Make sure to get Maven
* Go to /client directory
* Run ```mvn package```
* Run ```java -jar target/app-1.0-SNAPSHOT.jar```

## Download
Get directly the Jar from here: [PriceTag](https://github.com/Guertz/pricetag/releases)

## License
```
The MIT License

Copyright (c) 2018 Singh Amarjot, Matteo Guerzoni

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
```
