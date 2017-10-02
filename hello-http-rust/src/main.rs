extern crate iron;

use std::time::{SystemTime, UNIX_EPOCH};
use iron::prelude::*;
use iron::status;

fn hello_world(_: &mut Request) -> IronResult<Response> {
	let start = SystemTime::now();
	let since_the_epoch = start.duration_since(UNIX_EPOCH).expect("Time went backwards");
	Ok(Response::with((status::Ok, format!("Hello world {:?}", since_the_epoch.as_secs()))))
}
fn main() {
    let _server = Iron::new(hello_world).http("0.0.0.0:8286").unwrap();
    println!("Listening on http:://0.0.0.0:8286/")
}
