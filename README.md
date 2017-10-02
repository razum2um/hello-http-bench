# Hello World via HTTP

Some Clojure performance tuning (up to 3x times faster than in everyone's tutorial!)

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
```

### Tutorial version

```
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar tutorial
```

```
$ wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.83ms   20.51ms 225.12ms   91.20%
    Req/Sec    17.24k    12.31k   34.91k    39.80%
  169345 requests in 10.01s, 25.52MB read
Requests/sec:  16924.17
Transfer/sec:      2.55MB
```


### Tuned tutorial version

```
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar nocontenttype
```

```
$ wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.52ms   18.44ms 241.71ms   92.64%
    Req/Sec    20.55k    12.65k   36.76k    44.00%
  204251 requests in 10.01s, 25.71MB read
Requests/sec:  20405.39
Transfer/sec:      2.57MB
```


### Balance of ugly, but high-level and performant implementation

```
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar handler
```

```
$ wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   238.79us  275.62us  11.98ms   98.76%
    Req/Sec    45.30k     4.25k   48.86k    88.12%
  454878 requests in 10.10s, 57.26MB read
Requests/sec:  45037.47
Transfer/sec:      5.67MB
```


### Same as handler above + lazy ring-request parsing (default in uberjar)

```
java -jar target/uberjar/hello-http-bench-0.1.0-SNAPSHOT-standalone.jar
```

```
$ wrk -t 1 http://localhost:8288/

Running 10s test @ http://localhost:8288/
  1 threads and 10 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   214.24us  198.50us   8.06ms   99.14%
    Req/Sec    49.36k     5.90k   53.99k    84.00%
  490842 requests in 10.00s, 61.79MB read
Requests/sec:  49083.45
Transfer/sec:      6.18MB
```