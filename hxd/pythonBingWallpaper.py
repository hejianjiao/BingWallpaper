#!/usr/bin/python
#encoding:utf-8
import urllib,json,os,uuid,urllib2,cookielib

#保存的文件夹
localPath='./img/'

#获取地址存放到list中
def getUrlList():
	list=[]
	for num in range(1,21):
		url="http://www.bing.com/HPImageArchive.aspx?format=js&idx="+str(num)+"&n=1&mkt=en-US"
		wp=urllib.urlopen(url)
		hjson=json.loads(wp.read())
		list.append("http://www.bing.com/"+hjson['images'][0]['url'])
	return list

#随机数名字
def generateFileName():
	return str(uuid.uuid1())

#创建文件夹
def createFileWithFileName(localPathParam,fileName):
	totalPath=localPathParam+fileName
	if not os.path.exists(localPathParam):
		os.makedirs(localPathParam)	
	return totalPath

#保存图片
def getAndSaveImg(imgUrl):
	if(len(imgUrl)!=0):
		fileName=generateFileName()+'.jpg'	
		urllib.urlretrieve(imgUrl,createFileWithFileName(localPath,fileName))
#下载
def downloadImg():
	urlList=getUrlList()
	for urlString in urlList:
		getAndSaveImg(urlString)



print "please wait。。。"
print "you will download 20 pics in ./img"
downloadImg()
print "OK"

#print "http://www.bing.com/"+hjson['images'][0]['url']
#save_img("./","123.jpg","http://www.bing.com/"+hjson['images'][0]['url'])
#print "OK!";
