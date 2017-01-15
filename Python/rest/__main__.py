# coding: utf-8

import tornado.ioloop
import tornado.web

from rest.pessoa import PessoaView


def map_urls():

    return tornado.web.Application([
        (r'/pessoas/?', PessoaView)
    ])


if __name__ == '__main__':
    app = map_urls()
    app.listen(8080)
    tornado.ioloop.IOLoop.current().start()
