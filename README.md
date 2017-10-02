# Hello World via HTTP

tested with `wrk http://localhost:.../`
hardware: i7-6700k@4Ghz, DDR4-2400Mhz

## Clojure

> java 1.8.0_141, clojure 1.8.0, ring-jetty-1.6.2

```
lein uberjar
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar
```

```
$ wrk http://localhost:8288/                                                                                                                                                                         [ruby-2.3.4]
Running 10s test @ http://localhost:8288/
  2 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.82ms   21.43ms 157.11ms   94.90%
    Req/Sec    41.35k     6.58k   62.10k    90.21%
  808215 requests in 10.02s, 110.99MB read
Requests/sec:  80667.94
Transfer/sec:     11.08MB
```

## Rust

> rust 1.22.0-nightly (f1b5225e8 2017-10-01), iron 0.4.0

```
cd hello-http-rust
cargo run
```

```
$ wrk http://localhost:8286/                                                                                                                                                                         [ruby-2.3.4]
Running 10s test @ http://localhost:8286/
  2 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.64ms    6.58ms 124.94ms   96.70%
    Req/Sec     8.26k     0.94k   10.76k    66.34%
  165884 requests in 10.10s, 17.88MB read
Requests/sec:  16425.12
Transfer/sec:      1.77MB
```

## Python (aiohttp)

> python 3.6.1, aiohttp 2.2.5

```
cd hello-http-python
pip3 install aiohttp
python3 hello-python-http.py
```

```
$ wrk http://localhost:8287/                                                                                                                                                                         [ruby-2.3.4]
Running 10s test @ http://localhost:8287/
  2 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.44ms  100.52us   6.60ms   94.20%
    Req/Sec     3.49k    42.91     3.58k    77.72%
  70152 requests in 10.10s, 9.83MB read
Requests/sec:   6945.43
Transfer/sec:      0.97MB
```
