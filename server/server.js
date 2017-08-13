// Logger Setup
require('./lib/logger.js')

// Modules
const os = require('os')
const cluster = require('cluster')
const express = require('express')
const settings = require('./config/settings.js')
const endpoints = require('./endpoints/endpoints.js')
const logger = require('winston')

// App
const app = express()

// Setup app's endpoints
endpoints.all.forEach(function (item, index, array) {
  app.use('/', item)
})

// App ready to serve
app.listen(settings.PORT, function () {
  console.log('Ready to serve!')
})

function startServer (app, migrate, callback) {
  var httpServer = require('http').createServer(app)
  httpServer.listen(settings.port, function () {
    callback && callback()
  })
}

const isProductionOrStagingEnvironment = process.env.NODE_ENV && process.env.NODE_ENV !== settings.ENV_TEST

if (isProductionOrStagingEnvironment && cluster.isMaster) {
  // Create as many nodejs processes as cpu cores
  const numCPUs = os.cpus().length
  for (var i = 0; i < numCPUs; i++) {
    cluster.fork()
  }
  cluster.on('exit', function (worker, code) {
    logger.log('warning', 'worker %d died (%d). Restarting ...', worker.process.pid, code)
    cluster.fork()
  })
} else {
  startServer(app, true, function () {
    logger.log('info', '[ %d ] server running on port %d', process.pid, settings.port)
  })
}
