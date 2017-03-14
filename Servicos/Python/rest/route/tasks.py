# coding: utf-8

import json

from rest.controller.task import Task as TaskController
from http import client
from tornado.web import RequestHandler

class BaseRequestHandler(RequestHandler):
    def __init__(self, *args, **kwargs):
        super(BaseRequestHandler, self).__init__(*args, **kwargs)

    def data_received(self, chunk):
        pass

    @property
    def content(self):
        if not self.request.body:
            return None

        content_type = self.request.headers.get('content-type') or ''
        content_type = content_type.lower()

        if 'application/json' in content_type:
            return json.loads(self.request.body.decode())
        else:
            return self.request.body

    def response_success(self, content: dict or str or None = None, default_status_code: int or None = None):
        if default_status_code:
            self.set_status(default_status_code)
        else:
            self.set_status(client.CREATED if self.request.method.upper() == 'POST' else client.OK)

        if content is not None:
            self.set_header('Content-Type', 'application/json')
            data = (
                (json.dumps(content) if not isinstance(content, list) else json.dumps(
                    content)) if not isinstance(
                    content, str) else content)
            self.write(data)

        self.finish()


class TasksView(BaseRequestHandler):
    def __init__(self, *args, **kwargs):
        super(TasksView, self).__init__(*args, **kwargs)
        self.__controller = TaskController()

    def get(self, id,  *args, **kwargs):
        if id:
            tasks = self.__controller.task(id)
        else:
            tasks = self.__controller.tasks()
        self.response_success(tasks)

    def post(self, *args, **kwargs):
        task = self.content
        self.__controller.save(task)
        self.response_success(task)

    def delete(self, id,*args, **kwargs):
        self.__controller.delete(id)