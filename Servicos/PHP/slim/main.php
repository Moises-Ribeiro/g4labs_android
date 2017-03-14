<?php
    
    require 'vendor/autoload.php';
    
    // Instantiate the app
    $settings = require 'settings.php';
    $app = new \Slim\App($settings);
    
    // Register routes
    require 'route/tasks.php';
    
    // Run!
    $app->run();