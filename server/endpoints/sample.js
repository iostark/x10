const routes = require('express').Router();

// API: foobar/baz/v1
// 
routes.get('/foobar/baz/v1', function (req, res) {
  res.status(200).json({ message: 'Yes' });
})

module.exports = routes;
