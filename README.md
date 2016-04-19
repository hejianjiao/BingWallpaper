# BingWallpaper
hejianjiao爬虫技术交流活动
### 需求
（url）http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US  
通过如上url获取到一个json对象  
（json）
```javascript
{
    "images": [
        {
            "startdate": "20160329", 
            "fullstartdate": "201603291600", 
            "enddate": "20160330", 
            "url": "http://s.cn.bing.net/az/hprichbg/rb/WestBow_ZH-CN11767628474_1920x1080.jpg", 
            "urlbase": "/az/hprichbg/rb/WestBow_ZH-CN11767628474", 
            "copyright": "苏格兰，爱丁堡，西弓街 (© Rory McDonald/Getty Images)", 
            "copyrightlink": "http://www.bing.com/search?q=%E7%88%B1%E4%B8%81%E5%A0%A1&form=hpcapt&mkt=zh-cn", 
            "wp": true, 
            "hsh": "e60edeea9c4614012b0ad920eeb2cdef", 
            "drk": 1, 
            "top": 1, 
            "bot": 1, 
            "hs": [ ], 
            "msg": [
                {
                    "title": "今日图片故事", 
                    "link": "http://www.bing.com/search?q=%E7%88%B1%E4%B8%81%E5%A0%A1&form=pgbar1&mkt=zh-cn", 
                    "text": "爱丁堡"
                }
            ]
        }
    ], 
    "tooltips": {
        "loading": "正在加载...", 
        "previous": "上一页", 
        "next": "下一页", 
        "walle": "此图片不能下载用作壁纸。", 
        "walls": "下载今日美图。仅限用作桌面壁纸。"
    }
}
```  
获取json中的images下的url参数的值  
（url2）http://s.cn.bing.net/az/hprichbg/rb/WestBow_ZH-CN11767628474_1920x1080.jpg  
从url2处下载图片，保存图片，将文件重命名为Bing网站上展示这张图片的日期。  
将url中的idx后面的参数由0进行++操作，下载下一张图片  
到获取到所有能获取到的url2为止。  

### 参与
（实现需求）使用某一编程语言，实现需求，以自己的名字或昵称命名文件夹，存放自己的代码，向本项目提交该文件夹的pull request  
（沟通与问答）任何问题于本项目创建新issues  
（时间与周期）本项目周期为一个月，项目开始时间3月30日，将于4月30日晚8点结束。

### 代码说明
 commons-beanutils-1.8.0.jar,commons-collections-3.2.1.jar,commons-lang-2.6.jar,commons-logging-1.1.1.jar,ezmorph-1.0.6.jar,json-lib-2.4-jdk15.jar是解析json时需要用到的jar

