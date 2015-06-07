var express = require('express');
var router = express.Router();

var Batman = require('../controllers/Batman'),
    Alfred = require('../models/Alfred');

/* GET home page. */
router.get('/', function (req, res, next) {
    var services = [];
    for (var i in  Batman.servants) {
        services.push(Batman.servants[i]);
    }
    return res.render('index', {
        servants: services
    })
});

router.post('/mission', function (req, res) {
    var command = req.body.command;
    var instructions = command.split(' ');
    command = instructions[0];
    var content = instructions[1];
    var killer;
    for (var i in Batman.servants) {
        var alfred = Batman.servants[i];
        if (command.indexOf(alfred.command) != -1) {
            killer = alfred;
            break;
        }
    }
    if (!killer) {
        return res.json({
            err: true,
            msg: '无人可以胜任这个任务',
            command: command
        });
    }
    killer.handleIt(content, function (err, resp) {
        if (err) {
            console.log(err);
            return res.json({
                err: true,
                msg: '任务出错',
                alfred: alfred
            });
        }
        return res.json({
            err: false,
            msg: '任务成功完成',
            data: resp
        });
    });
});

router.post('/hire', function (req, res, next) {
    var name = req.body.name,
        command = req.body.command,
        url = req.body.url;
    Batman.hire(new Alfred(name, command, url));
    return res.json({
        err: false,
        msg: '雇佣了一个新的侍从，代号：' + name
    });
});

module.exports = router;
