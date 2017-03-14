# coding: utf-8

import tornado.ioloop
import tornado.web

from rest.route.tasks import TasksView


def map_urls():
    return tornado.web.Application([
        (r'/api/tasks/?(?P<id>[^/]+)?/?', TasksView)
    ])


if __name__ == '__main__':
    app = map_urls()
    app.listen(8080)
    tornado.ioloop.IOLoop.current().start()
