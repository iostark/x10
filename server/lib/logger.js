var util = require('util');
var winston = require('winston');

var logger = new winston.Logger({
  transports: [
    new winston.transports.Console({
      level: 'debug',
      timestamp: true,
      colorize: true,
      handleExceptions: true,
      humanReadableUnhandledException: true
    }),
  ]
});

process.on('unhandledRejection', function(error, promise) {
  logger.error("Detected an unhandled promise rejection!", JSON.stringify({
    error: error,
    stack: error && error.stack,
    promise: promise
  }));
});

// Override the built-in console methods with winston hooks
console.log = function(){
    logger.info.apply(logger, formatArgs(arguments));
};
console.info = function(){
    logger.info.apply(logger, formatArgs(arguments));
};
console.warn = function(){
    logger.warn.apply(logger, formatArgs(arguments));
};
console.error = function(){
    logger.error.apply(logger, formatArgs(arguments));
};
console.debug = function(){
    logger.debug.apply(logger, formatArgs(arguments));
};

function formatArgs(args){
    return [util.format.apply(util.format, Array.prototype.slice.call(args))];
}
