**redis+lua 实现分布式令牌桶，高并发限流**
![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTgwMzI2MTYzNTIyMTE4?x-oss-process=image/format,png)


- 令牌桶算法(Token Bucket)和 Leaky Bucket
   效果一样但方向相反的算法,更加容易理解.随着时间流逝,系统会按恒定1/QPS时间间隔(如果QPS=100,则间隔是10ms)往桶里加入Token(想象和漏洞漏水相反,有个水龙头在不断的加水),如果桶已经满了就不再加了.新请求来临时,会各自拿走一个Token,如果没有Token可拿了就阻塞或者拒绝服务.
- 令牌桶的另外一个好处是可以方便的改变速度. 一旦需要提高速率,则按需提高放入桶中的令牌的速率.一般会定时(比如100毫秒)往桶中增加一定数量的令牌, 有些变种算法则实时的计算应该增加的令牌的数量.

![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTgwMzI2MTYyOTAzODIx?x-oss-process=image/format,png)
- last_mill_second  最后时间毫秒
- curr_permits 当前可用的令牌
- max_burst    令牌桶最大值
- rate		     每秒生成几个令牌
- app		     应用
- 令牌桶内令牌生成借鉴Guava-RateLimiter类的设计
- 每次getToken根据时间戳生成token，不超过最大值