# coding: utf-8

import json
from http import client
from tornado.web import RequestHandler
from datetime import datetime


def singleton(class_):
  instances = {}

  def getinstance(*args, **kwargs):
    if class_ not in instances:
        instances[class_] = class_(*args, **kwargs)
    return instances[class_]
  return getinstance


class BaseRequestHandler(RequestHandler):
    def __init__(self, *args, **kwargs):
        super(BaseRequestHandler, self).__init__(*args, **kwargs)

    def data_received(self, chunk):
        pass

    @property
    def content(self):
        """
        Retorna o corpo do request com os tratamentos necessários de acordo com o content-type.
        """
        if not self.request.body:
            return None

        content_type = self.request.headers.get('content-type') or ''
        content_type = content_type.lower()

        if 'application/json' in content_type:
            return json.loads(self.request.body.decode())
        else:
            return self.request.body

    def response_json_success(self, content: dict or str or None = None, default_status_code: int or None = None):
        """
        Resposta padronizada para o caso de sucesso com conteúdo JSON

        :param content: Dicionário a ser serializado como JSON
        :param default_status_code: Status HTTP
        """

        if default_status_code:
            self.set_status(default_status_code)
        else:
            self.set_status(client.CREATED if self.request.method.upper() == 'POST' else client.OK)

        if content is not None:
            self.set_header('Content-Type', 'application/json')
            data = (
                (json.dumps(content) if not isinstance(content, list) else json.dumps(
                    {'lista': content})) if not isinstance(
                    content, str) else content)
            self.write(data)

        self.finish()


class PessoaView(BaseRequestHandler):
    def __init__(self, *args, **kwargs):
        super(PessoaView, self).__init__(*args, **kwargs)
        self.__session = Session()

    def get(self, *args, **kwargs):
        self.response_json_success(self.__session.pessoas())

    def post(self, *args, **kwargs):
        pessoa = self.content

        pessoa['id'] = int('{}{}'.format(datetime.now().hour, datetime.now().minute))
        self.__session.add(pessoa)

        self.response_json_success(pessoa)

    def put(self, *args, **kwargs):
        pessoa = self.content
        self.__session.update(pessoa)
        self.response_json_success(pessoa)


@singleton
class Session(object):

    def __init__(self):
        self.__pessoas = [{'id': i, 'nome': 'PESSOA{}'.format(i), 'idade': 10 + i} for i in range(0, 10)]

    def pessoas(self):
        return self.__pessoas

    def add(self, pessoa):
        self.__pessoas.append(pessoa)

    def update(self, pessoa):
        for pes in self.__pessoas:
            if pes['id'] == pessoa['id']:
                pes.update(pessoa)
                break