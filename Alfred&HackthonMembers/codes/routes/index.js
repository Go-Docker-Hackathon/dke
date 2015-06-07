var express = require('express');
var router = express.Router();

/* GET home page. */
router.all('/', function (req, res, next) {
    return res.json({
        type: 'list',
        location: '北京市东城区东直门南大街3号国华投资大厦1105室',
        list: [
            {
                name: "3GGS",
                leader: "杨武明",
                cnt: "3",
                wechat: "13811229996"
            },
            {
                name: "JetLab",
                leader: "庄X",
                cnt: "1",
                wechat: "13906917736"
            },
            {
                name: "DKE",
                leader: "林惠文",
                cnt: "1",
                wechat: "15600692844"
            },
            {
                name: "ZJU-FT",
                "leader": "王哲",
                cnt: "3",
                wechat: "15356463496"
            },
            {
                name: "MF",
                leader: "赵一方",
                cnt: "3",
                wechat: "13602192543"
            },
            {
                name: "SCUT",
                leader: "陆迪豪",
                cnt: 2,
                wechat: "15889936796"
            },
            {
                name: "iDareX",
                leader: "朗爽",
                cnt: 3,
                wechat: "18701291933"
            },
            {
                name: "CodeVS",
                leader: "王鹏翰",
                cnt: 2,
                wechat: '18768168189'
            },
            {
                name: "LHL",
                leader: "李小康",
                cnt: 3,
                wechat: "17600803107"
            },
            {
                name: "3DDU",
                leader: "沈冠璞",
                cnt: 3,
                wechat: "13811437730"
            },
            {
                name: "DAO",
                leader: "孙宏亮",
                cnt: 1,
                wechat: "15869045568"
            }
        ]
    })
});

module.exports = router;
