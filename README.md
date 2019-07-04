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

<div align=center><img src="/images/webpage_whole.png" height="410" width="700"/></div>

## Test some functions
__1. Register account__

  Before registering, the account record of server background.

  <div align=center><img src="/images/before_register.png" height="130" width="450" ></div>
  
  Register _account: __tangmoon___ and _password: __888888___
  
  Then it will jump to another page and notice whether successful.
  
  <div align=center><img src="/images/whether_successful.png" height="410" width="700" ></div>
  
  If successd, the server background will add an account record.
  
  <div align=center><img src="/images/after_register.png" height="130" width="450" ></div>
  
__2. Play music__

  Press the botton of _play_, normally the music will load successfully and play.
  
  <div align=center><img src="/images/play_music.png" height="130" width="450" ></div>
  
__3. Download something__

  We can download some resource such the music above. Click the _download_ botton.
  
  <div align=center><img src="/images/download_music.png" height="300" width="500" ></div>
  

---

The next step is evaluating the throughput level. I used ___WSL (a Linux sub-system based on Windows)___ and ___Siege (a high-performance http stress testing tool)___ to test this __HttpServer__. Here are my testing results.

The configuration of machine which used for testing.

<div align=center><img src="/images/machine_configuration.png" height="380" width="700" ></div>

__1. 200 concurrency, 100 requests per sec, test 5 minutes__ 

  &emsp;_HttpServer configuration: FixedThreadPool, 8 threads_

  <div align=center><img src="/images/200con_100req.png" height="380" width="700" ></div>
   
__2. 400 concurrency, 100 requests per sec, test 10 seconds__

  &emsp;_HttpServer configuration: FixedThreadPool, 8 threads_

  <div align=center><img src="/images/400con_100req.png" height="380" width="700" ></div>

__3. 1000 concurrency, 100 requests per sec, test 10 seconds__

  &emsp;_HttpServer configuration: FixedThreadPool, 32 threads_
  
  <div align=center><img src="/images/1000con_100req.png" height="380" width="700" ></div>
  
























	
