# 属性定义
CRAWLER_HOME="/app/intellicrawler"
CRAWLER_INSTALL="$CRAWLER_HOME/install"
CRAWLER_CONF="$CRAWLER_HOME/conf"

MODULE_WEB="web"
MODULE_WORKER="worker"
MODULE_ALL="all"

#获取参数
if [ $# != 1 ]; then
	echo "[Error] parameter error. parameter: web | worker | all "
	exit 1
fi
module=$1


#安装web
function installWeb()
{
	#1) 停服务
	pid=`ps -ef|grep tomcat|grep intellicrawler|awk '{print $2}'`
	if [ "$pid"x != ""x ]; then
		kill -9 $pid
	fi	

	#2) 清理原程序
	if [ -d $CRAWLER_HOME/web/apache-tomcat-7.0.65/webapps ]; then		
		rm -rf $CRAWLER_HOME/web/apache-tomcat-7.0.65/webapps/*
	else
		mkdir $CRAWLER_HOME/web/apache-tomcat-7.0.65/webapps
	fi
	

	
	#4) 拷贝war包
	cp $CRAWLER_INSTALL/intelligent-crawler-web.war $CRAWLER_HOME/web/apache-tomcat-7.0.65/webapps
	
	return 0
}

#安装worker
function installWorker()
{
	#1) 停服务
	if [ -f $CRAWLER_HOME/worker/worker.sh ]; then
		$CRAWLER_HOME/worker/worker.sh stop
	fi	

	#2) 清理原程序
	if [ -d $CRAWLER_HOME/worker ]; then		
		rm -rf $CRAWLER_HOME/worker/*
	else
		mkdir $CRAWLER_HOME/worker
	fi

	#4) 解压tar包
	tar -xf $CRAWLER_INSTALL/intelligent-crawler-worker-0.0.1-SNAPSHOT-release.tar -C $CRAWLER_HOME/worker

	#5) 修改名称
	mv $CRAWLER_HOME/worker/intelligent-crawler-worker-0.0.1-SNAPSHOT/* $CRAWLER_HOME/worker
	
	cp $CRAWLER_HOME/worker/[c-p]*.sh  $CRAWLER_INSTALL
	cp $CRAWLER_HOME/worker/conf/*  $CRAWLER_CONF
	
	#6) 修改用户
	#chown zx:zx $CRAWLER_HOME/outbound/rh
	
	#7) 修改权限
	chmod a+x $CRAWLER_HOME/worker/*.sh
	
	return 0
}

#安装所有
function installAll()
{
	installWeb;
	installWorker;
	return 0
}

#1. 安装schedule
if [ $module = $MODULE_WEB ]; then

	installWeb
#安装所有
elif [ $module = $MODULE_WORKER ]; then
	
	installWorker	
#安装所有
elif [ $module = $MODULE_ALL ]; then
	
	installAll
	
else
	echo "[Error] parameter error. parameter: web | worker |all"
	exit 1
fi

exit 0

