<?php
    require 'controller/task.php';

    use Slim\Http\Request;
    use Slim\Http\Response;


    $app->get('/api/tasks[/]', function ($request, $response, $args) {
        $controller = new Task();
        $tasks = $controller->tasks();
        return $response->withJson($tasks);
        //return $response->withStatus(302)->withHeader('Location', 'hello/home');
    });

    // Define app routes
    $app->get('/api/tasks/{id:[0-9]+}', function (Request $request, Response $response, $args) {
        $controller = new Task();
        $task = $controller->task($args['id']);
        return $response->withJson($task);
    });
    

    //controller
    //$app->group('/hello[/]', function () {
    //    $this->get   ('',             HelloWorld::class.':get');
    //    $this->get   ('/{id:[0-9]+}', HelloWorld::class.':get');
    //    $this->post  ('',             HelloWorld::class.':post');
    //    $this->put   ('/{id:[0-9]+}', HelloWorld::class.':put');
    //    $this->delete('/{id:[0-9]+}', HelloWorld::class.':delete');
    //});