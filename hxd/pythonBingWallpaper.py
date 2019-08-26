#!/usr/bin/python
#encoding:utf-8
import urllib,json,os,urllib2,cookielib

#保存的文件夹
localPath='./img/'

#获取图片的Url,中文说明,哈希等信息
def getImgSet():
	imgSet={}
	for num in range(1,21):
		url="http://www.bing.com/HPImageArchive.aspx?format=js&idx="+str(num)+"&n=1&mkt=en-US"
		wp=urllib.urlopen(url)
		hjson=json.loads(wp.read())
		imgSet["http://www.bing.com/"+hjson['images'][0]['url']] = {
            "copyright": hjson['images'][0]['copyright'],
            "hsh": hjson['images'][0]['hsh'],
        }
	return imgSet

#创建文件夹
def createFileWithFileName(localPathParam,fileName):
	totalPath=localPathParam+fileName
	if not os.path.exists(localPathParam):
		os.makedirs(localPathParam)	
	return totalPath

#保存图片
def getAndSaveImg(imgUrl, hsh):
	if(len(imgUrl)!=0):
		fileName=hsh+'.jpg'
		urllib.urlretrieve(imgUrl,createFileWithFileName(localPath,fileName))

#下载
def downloadImg():
	imgSet=getImgSet()
	print "you will download %d pics in ./img" % len(imgSet)
	for urlString in imgSet:
		copyright = imgSet[urlString]['copyright']
		hsh = imgSet[urlString]['hsh']
		print u"downloading: [%s](%s)" % (copyright, urlString)
		getAndSaveImg(urlString, hsh)

if __name__ == "__main__":
	print "please wait..."
	downloadImg()
	print "OK"

	#print "http://www.bing.com/"+hjson['images'][0]['url']
	#save_img("./","123.jpg","http://www.bing.com/"+hjson['images'][0]['url'])
	#print "OK!";
