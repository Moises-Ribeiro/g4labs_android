<?php

    require 'controller/task.php';

    $controller = new Task();
    $tasks = $controller->tasks();

    print_r(json_encode($tasks));


    /*
    $db = new PDO('sqlite:tasks.db') or die('Could not open database');
    $query = 'CREATE TABLE IF NOT EXISTS task (
                id  INTEGER PRIMARY KEY AUTOINCREMENT,
                title VARCHAR(50),
                completed INTEGER,
                description VARCHAR(150)
            )';
    $db->exec($query);

    $response = array();

    $rows = $db->query('SELECT * FROM task') or die('Query failed');
    foreach($rows as $row) {
        array_push($response, array_filter($row,  function ($key) use ($allowed) {
            return !is_numeric($key);
        }));
    }

    print_r(json_encode($response));
    */
?>