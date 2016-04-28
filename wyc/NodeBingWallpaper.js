/**
 * Created by wyc on 16/4/21.
 *
 * 简单的弄了一个,有很多不足
 */

var http = require('http');
var request = require('request');
var fs = require('fs');
var path = require('path');


var imgsPath  = path.resolve(__dirname, 'imgs');
try {
    var stat = fs.statSync(imgsPath);
    if(!stat.isDirectory()){
        fs.mkdirSync(imgsPath)
    }
}catch (e){
    fs.mkdirSync(imgsPath)
}

for(var i=1;i<21;i++){
    (function(i){
        setTimeout(function () {
            var url = 'http://www.bing.com/HPImageArchive.aspx?format=js&idx='+ i +'&n=1&mkt=en-US';
            console.log(url);
            request.get(url, function (err, res, body) {
                if(body!=null){
                    try {
                        var bodyJSON = JSON.parse(body);
                        if(bodyJSON) {
                            var images = bodyJSON.images;
                            if (images && images[0]) {
                                var imgUrl = images[0].url;
                                var date = images[0].startdate;
                                var ext = path.extname(imgUrl);
                                request.get(imgUrl).pipe(fs.createWriteStream('imgs/' + date + ext));
                            }
                        }
                    }catch (e){
                        console.log(i,e.stack);
                        return;
                    }
                }
            })
        },Math.floor(Math.random() * (10 - 3 + 1) + 3)*1000)
    })(i);
}
