extern crate iron;

use iron::prelude::*;
use iron::status;

fn hello_world(_: &mut Request) -> IronResult<Response> {
	Ok(Response::with((status::Ok, "Hello world")))
}
fn main() {
    let _server = Iron::new(hello_world).http("0.0.0.0:8286").unwrap();
    println!("Listening on http:://0.0.0.0:8286/")
}
