var needle = require('needle'),
    async = require('async'),
    color = require('colors');

/**
 * 他是阿福
 * @param name 我们给他的代号
 * @param command 他需要处理的指令
 * @param url 他处理指令需要的资源
 * @constructor
 */
function Alfred(name, command, url) {
    this.name = name;
    this.command = command;
    this.url = url;
}

/**
 * 唤醒一个沉睡的阿福。
 * @param callback
 */
Alfred.prototype.wakeUp = function (callback) {
    /*TODO: 启动 Container */
    callback(null);
};

/**
 * 阿福要去处理事情了
 * @param body
 * @param callback
 */
Alfred.prototype.handleIt = function (content, callback) {
    var that = this;
    async.series({
        wake: function (cb) {
            that.wakeUp(cb);
        },
        doIt: function (cb) {
            needle.post(that.url, {
                content: content
            }, function (err, resp) {
                if (err || resp.statusCode != 200) {
                    console.log('Error:'.red + '请求远程服务出错');
                    cb(new Error('任务主线出错'));
                } else {
                    cb(null, JSON.parse(resp.body));
                }
            });
        },
        sleep: function (cb) {
            that.relax(cb);
        }
    }, function (err, result) {
        if (err) {
            console.log('Error:'.red + 'HandleIt error');
            return callback(err);
        }
        return callback(err, result.doIt);
    });

};


/**
 * 干的漂亮，让他去休息吧。
 * @param callback
 */
Alfred.prototype.relax = function (callback) {
    /*TODO: 关闭 Container */
    callback(null);
};

module.exports = Alfred;

