require('colors');
/**
 * 他是伟大的 Batman。
 * @type {{getInstance}}
 */
var Batman = (function(){
    var instance;
    Batman = function Batman(){
        if(instance){
            return instance;
        }
        instance = this;
        instance.servants = {};
        console.log('News:'.red + 'Batman 在哥潭市诞生');
    };
    return new Batman();
}());
/**
 * 雇佣一个仆人
 * @param alfred
 */
Batman.hire = function (alfred) {
    console.log('News:'.red + 'Batman 雇佣了新的仆人，代号：' + alfred.name);
    Batman.servants[alfred.name] = alfred;
};

/**
 * Fire !
 * @param alfred
 */
Batman.fire = function (alfred) {
    delete Batman.servants[alfred.name];
};

module.exports = Batman;