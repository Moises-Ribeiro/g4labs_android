<?php

    class Task {
        private $db;

        public function __construct() {
            $db = new PDO('sqlite:tasks.db') or die('Could not open database');
            $query = 'CREATE TABLE IF NOT EXISTS task (
                        id  INTEGER PRIMARY KEY AUTOINCREMENT,
                        title VARCHAR(50),
                        completed INTEGER,
                        description VARCHAR(150)
                    )';
            $db->exec($query);
        }

        public function tasks() {
            $response = array();
            $db = new PDO('sqlite:tasks.db') or die('Could not open database');
            $rows = $db->query('SELECT * FROM task') or die('Query failed');
            foreach($rows as $row) {
                $filter = array_filter(array_keys($row),  function ($key) {
                    return !is_numeric($key);
                });
                array_push($response, array_intersect_key($row, array_flip($filter)));
            }
            return $response;
        }

        public function task($id) {
            $response = array();
            $db = new PDO('sqlite:tasks.db') or die('Could not open database');
            $rows = $db->query('SELECT * FROM task WHERE id = '. $id) or die('Query failed');
            foreach($rows as $row) {
                 $filter = array_filter(array_keys($row),  function ($key) {
                    return !is_numeric($key);
                });
                array_push($response, array_intersect_key($row, array_flip($filter)));
            }
            return $response[0];
        }

        public function save($task) {
            $rows = array();
            $values = array();

            foreach($task as $key=>$value) {
                array_push($rows, $key);
                array_push($values, '"' . $value . '"');
            }

            $db = new PDO('sqlite:tasks.db') or die('Could not open database');
            $response = $db->query('INSERT INTO (' . join(',', $rows) . ') task VALUES ('. join(',', $values) . ')') or die('Query failed');
            return $response > 0;
        }

        public function delete($id) {
            $db = new PDO('sqlite:tasks.db') or die('Could not open database');
            $response = $db->query('DELETE FROM task id = '. $id) or die('Query failed');
            return $response > 0;
        }

    }
        
