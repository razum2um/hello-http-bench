import time

def application(env, start_response):
    start_response('200 OK', [('Content-Type', 'text/plain')])
    return [("Hello world %d" % round(time.time() * 1000)).encode()]
