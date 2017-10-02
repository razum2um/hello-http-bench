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

> java 1.8.0_141, clojure 1.8.0, undertow-1.4.20

```
lein uberjar
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar tutorial undertow
```


```
wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.08ms    3.55ms  46.01ms   93.14%
    Req/Sec    70.88k     6.13k   84.35k    69.00%
  704280 requests in 10.00s, 83.96MB read
Requests/sec:  70425.71
Transfer/sec:      8.40MB
```