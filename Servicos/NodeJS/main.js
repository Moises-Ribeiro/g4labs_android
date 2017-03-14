var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var router = express.Router();
// Definir a route principal
app.get('/', function(req, res) {
  res.send('Welcome to API');
});



router.route('/tasks/:id')

    .post(function(req, res) {
        
        var bear = new Bear();      // create a new instance of the Bear model
        bear.name = req.body.name;  // set the bears name (comes from the request)

        // save the bear and check for errors
        bear.save(function(err) {
            if (err)
                res.send(err);

            res.json({ message: 'Bear created!' });
        });
        
});




app.use('/api', router);

// Lista de Utilizadores
var users = [
  { id: 1, username: 'Manuel', email: 'manuel@examplo.com' },
  { id: 2, username: 'Maria', email: 'maria@examplo.com' }
];

// Definir um endpoint da API
app.get('/api/tasks', function(req, res, next) {
  res.send(users);
})

// Aplicação disponível em http://127.0.0.1:9000/
app.listen(8080);