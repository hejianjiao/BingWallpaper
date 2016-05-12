'use strict';

const http = require('http');
const fs = require('fs');
const path = require('path');

/**
 * 请求图片数据
 */
const requestImageData = (url, callback) => {
    const req = http.request(url, res => {
        let buffers = [];
        
        res.on('data', chunk => {
            buffers.push(chunk);
        });
        
        res.on('end', () => {        
            // 获取返回数据并解析
            const buffer = Buffer.concat(buffers);
            const data = JSON.parse(buffer.toString());
            
            callback(null, data);
        });
        
        res.on('error', err => {
            callback(err);
        });
    });

    req.end();
}

/**
 * 下载图片
 */
const downloadIamge = (url, imageName) => {
    const req = http.request(url, res => {
        const dir = path.join(__dirname, 'wallpaper');
        
        fs.access(dir, err => {
            if (err) {
                fs.mkdir(dir, () => {
                    const writable = fs.createWriteStream(path.join(__dirname, 'wallpaper', imageName));
                    res.pipe(writable);
                })
                
            } else {
                const writable = fs.createWriteStream(path.join(__dirname, 'wallpaper', imageName));
                    res.pipe(writable);
            }
        });
    });

    req.end();
};


function main() {
    for (let i = 0; i <= 20; i++) {
        // 获取壁纸数据的地址
        const URL_REQUST_BING_WALLPAPER = 'http://www.bing.com/HPImageArchive.aspx?format=js&idx=' + i + '&n=1&mkt=en-US';
        
        requestImageData(URL_REQUST_BING_WALLPAPER, (err, data) => {
            if (err) {
                console.error(err);
            } else {
                console.log('Received image data from ' + URL_REQUST_BING_WALLPAPER);
                console.log(data);
                console.log();
                
                if (data == null || data.images == null) return;
                
                for (let image of data.images) {
                    console.log('Start download image from ' + image.url);
                    downloadIamge(image.url, image.startdate + '.jpg');            
                }
            }
        });
    }
    
}

// 执行
main();