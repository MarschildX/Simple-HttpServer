# Simple-HttpServer
__`Java`__
### Implemented meta-functions
* HTTP request analysis
* Respond request
* Data transmission
* Log in 
* Log up

Based on the meta-functions above, this simple HttpServer can achieve some basic _http request_ such as __transmit picture__, __transmit music file__, __support download__, __log in__, __register account__ and so on.

I made a simple webpage to test whether __HttpServer__ is available. The simple webpage illustrated follow.
![1]()

![2]()

![3]()

The images above shows several _http requests_. My test result shows that they can be responded well.

The next step is evaluating the throughput level. I used ___WSL (a Linux sub-system based on Windows)___ and ___Siege (a high-performance http stress testing tool)___ to test this __HttpServer__. Here are my testing results.

The configuration of machine which used for testing.

![machine configuration]()

__1. 200 concurrency, 100 requests per sec:__ 

   ![200con, 100 req/s]()

	
