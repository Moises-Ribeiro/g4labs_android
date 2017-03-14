# coding: utf-8

import sqlite3


class Task:
    def __init__(self, *args, **kwargs):
        self.__db = sqlite3.connect('tasks.db')
        self.__db.execute('''
            CREATE TABLE IF NOT EXISTS task (
                id  INTEGER PRIMARY KEY AUTOINCREMENT,
                title VARCHAR(50),
                completed INTEGER,
                description VARCHAR(150)
            );
        ''')
        self.__db.commit()

    def tasks(self):
        cursor = self.__db.cursor()
        rows = cursor.execute('SELECT * FROM task')
        return [row for row in self.__dict_gen(rows)]

    def task(self, id):
        cursor = self.__db.cursor()
        rows = cursor.execute('SELECT * FROM task WHERE id = {}'.format(id))
        return [row for row in self.__dict_gen(rows)][0]

    def save(self, task):
        cursor = self.__db.cursor()

        rows = []
        values = []

        for key in task:
            rows.append(key)
            values.append('"{}"'.format(task[key]))

        cursor.execute('INSERT INTO ({}) task VALUES ({})'.format(','.join(rows), ','.join(values)))
        self.__db.commit()

    def delete(self, id):
        cursor = self.__db.cursor()
        cursor.execute('DELETE FROM task WHERE id = {}'.format(id))

    def __dict_gen(self, cursor):
        field_names = [desc[0].lower() for desc in cursor.description]
        while True:
            rows = cursor.fetchmany()
            if not rows: return
            for row in rows:
                yield dict(zip(field_names, row))
