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

![webpage_whole](/images/webpage_whole.png)

### Test some functions
__1. Register account__

  Before registering, the account record of server background.

  ![before register](/images/before_register.png)
  
  Register _account: __tangmoon___ and _password: __888888___
  
  Then it will jump to another page and notice whether successful.
  
  ![whether_successful](/images/whether_successful.png)
  
  If successd, the server background will add an account record.
  
  ![after register](/images/after_register.png)
  
__2. Play music__

  Press the botton of _play_, normally the music will load successfully and play.
  
  ![play_music](/images/play_music.png)
  
__3. Download something__

  We can download some resource such the music above. Click the _download_ botton.
  
  ![download_music](/images/download_music.png)
  

---

The next step is evaluating the throughput level. I used ___WSL (a Linux sub-system based on Windows)___ and ___Siege (a high-performance http stress testing tool)___ to test this __HttpServer__. Here are my testing results.

The configuration of machine which used for testing.

![machine configuration](/images/machine_configuration.png)

__1. 200 concurrency, 100 requests per sec, test 5 minutes__ 

  _HttpServer configuration: FixedThreadPool, 8 threads_

  ![200con, 100req/s, 5min](/images/200con_100req.png)
   
__2. 400 concurrency, 100 requests per sec, test 10 seconds__

  _HttpServer configuration: FixedThreadPool, 8 threads_

  ![400con, 100req/s, 10sec](/images/400con_100req.png)

__3. 1000 concurrency, 100 requests per sec, test 10 seconds__

  _HttpServer configuration: FixedThreadPool, 32 threads_
  
  ![1000con, 100req/s, 10sec](/images/1000con_100req.png)
  
























	
