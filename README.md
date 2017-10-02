# Hello World via HTTP

All servers configured to have 1 thread handling requests

Tested with `wrk -t 1 http://localhost:.../`

Hardware: Ubuntu Trusty, i7-3770@3.40GHz

## Rust

> rust 1.22.0-nightly (f1b5225e8 2017-10-01), iron 0.4.0

```
cd hello-http-rust
cargo build --release
./target/release/hello-http-rust
```

```
$ wrk -t 1 http://localhost:8286/

Running 10s test @ http://localhost:8286/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   217.77us    0.97ms  24.04ms   97.24%
    Req/Sec   104.30k    10.05k  119.89k    69.31%
  1047663 requests in 10.10s, 123.89MB read
Requests/sec: 103730.81
Transfer/sec:     12.27MB
```

## Ruby

> Ruby 2.4.1, puma 3.10.0

```
gem install puma
puma -t 1:1 -q
```

```
$ wrk -t 1 http://localhost:9292/

Running 10s test @ http://localhost:9292/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    52.48us   46.34us   4.49ms   99.66%
    Req/Sec    19.09k   620.84    20.07k    69.00%
  189876 requests in 10.00s, 15.75MB read
Requests/sec:  18987.04
Transfer/sec:      1.58MB
```

## Clojure

> java 1.8.0_141, clojure 1.8.0, ring-jetty-1.6.2

```
lein uberjar
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar
```

```
$ wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.37ms   15.85ms 151.76ms   87.46%
    Req/Sec    15.87k    13.22k   35.00k    64.65%
  157490 requests in 10.01s, 23.73MB read
Requests/sec:  15738.32
Transfer/sec:      2.37MB
```


## Python

> python 3.4.3, uwsgi 2.0.15

```
cd hello-http-python
pip3 install uswgi
./bin/uwsgi -L --http :8287 -p 1 --wsgi-file hello-http-python.py
```

```
$ wrk -t 1 http://localhost:8287/

Running 10s test @ http://localhost:8287/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   734.08us  531.56us  28.24ms   98.41%
    Req/Sec    10.12k     2.69k   12.49k    92.86%
  28211 requests in 10.02s, 1.88MB read
  Socket errors: connect 10, read 28211, write 0, timeout 0
Requests/sec:   2815.94
Transfer/sec:    192.50KB
```
