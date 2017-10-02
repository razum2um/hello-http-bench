# Hello World via HTTP

All servers configured to have 1 thread handling requests

Tested with `wrk -t 1 http://localhost:.../`

Hardware: i7-6700k@4Ghz, DDR4-2400Mhz

## Clojure

> java 1.8.0_141, clojure 1.8.0, ring-jetty-1.6.2

```
lein uberjar
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar
```

```
$ wrk -t 1 http://localhost:8288/                                                                                                                                                                                     [ruby-2.3.4]
Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   507.13us    3.86ms  80.25ms   99.01%
    Req/Sec    55.71k    14.89k   65.27k    83.17%
  559264 requests in 10.10s, 84.27MB read
Requests/sec:  55373.97
Transfer/sec:      8.34MB
```

## Ruby

> Ruby 2.3.4, puma 3.10.0

```
gem install puma
puma -t 1:1
```

```
$ wrk -t 1 http://localhost:9292/                                                                                                                                                                                     [ruby-2.3.4]
Running 10s test @ http://localhost:9292/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    42.38us   15.67us   1.37ms   89.26%
    Req/Sec    22.92k   551.38    23.66k    89.11%
  230278 requests in 10.10s, 19.11MB read
Requests/sec:  22800.95
Transfer/sec:      1.89MB
```

## Rust

> rust 1.22.0-nightly (f1b5225e8 2017-10-01), iron 0.4.0

```
cd hello-http-rust
cargo run
```

```
$ wrk -t 1 http://localhost:8286/                                                                                                                                                                                     [ruby-2.3.4]
Running 10s test @ http://localhost:8286/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.53ms    5.69ms 122.01ms   96.35%
    Req/Sec    16.51k   417.92    16.93k    95.05%
  165805 requests in 10.10s, 19.61MB read
Requests/sec:  16417.27
Transfer/sec:      1.94MB
```

## Python (aiohttp)

> python 3.6.1, aiohttp 2.2.5

```
cd hello-http-python
pip3 install aiohttp uvloop
python3 hello-python-http.py
```

```
$ wrk -t 1 http://localhost:8287/                                                                                                                                                                                     [ruby-2.3.4]
Running 10s test @ http://localhost:8287/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.51ms   82.95us   2.88ms   89.72%
    Req/Sec     6.57k    87.94     6.69k    82.00%
  65421 requests in 10.00s, 10.04MB read
Requests/sec:   6540.52
Transfer/sec:      1.00MB
```
