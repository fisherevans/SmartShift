var LOG_OFF = -1;
var LOG_ERROR = 0;
var LOG_INFO = 1;
var LOG_DEBUG = 2;

var DEBUG_LEVEL = LOG_DEBUG;

function error(msg) {
    if(DEBUG_LEVEL >= LOG_ERROR) {
        console.log("[ERROR] " + msg);
    }
}

function info(msg) {
    if(DEBUG_LEVEL >= LOG_INFO) {
        console.log("[INFO] " + msg);
    }
}

function debug(msg) {
    if(DEBUG_LEVEL >= LOG_DEBUG) {
        console.log("[DEBUG] " + msg);
    }
}