import asyncio

from aiohttp import web

def create_app(argv=None):
    app = web.Application()
    app.router.add_get('/', hello_world)
    return app

async def hello_world(request: web.Request) -> web.Response:
	return web.Response(body=b'Hello world', content_type='text/plain')

web.run_app(create_app(), port=8287)