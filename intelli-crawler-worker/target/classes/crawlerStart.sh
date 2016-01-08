# 属性定义
CRAWLER_HOME="/app/intellicrawler"
CRAWLER_INSTALL="$CRAWLER_HOME/install"

MODULE_WEB="web"
MODULE_WORKER="worker"
MODULE_ALL="all"


if [ $# != 1 ]; then
	echo "[Error] parameter error. parameter: worker | web | all"
	exit 1
fi
module=$1


function startWeb()
{
	#1)先关闭
	pid=`ps -ef|grep tomcat|grep intelligent-crawler-web|awk '{print $2}'`
	if [ "$pid"x != ""x ]; then
		kill -9 $pid
	fi
	
	#2) 再启动
	sh $CRAWLER_HOME/web/apache-tomcat-7.0.65/bin/startup.sh
	return 0
}

function startWorker()
{
	#1) 停服务
	if [ -f $CRAWLER_HOME/worker/worker.sh ]; then
		$CRAWLER_HOME/worker/worker.sh stop
	fi	
	
	
	#3) 再启动
	sh $CRAWLER_HOME/worker/worker.sh start
	return 0
}

function startAll()
{
	
	startWorker
	startWeb
	return 0
}


if [ $module = $MODULE_WEB ]; then

	startWeb
	

elif [ $module = $MODULE_WORKER ]; then

	startWorker
	
elif [ $module = $MODULE_ALL ]; then
	
	startAll
	
else
	echo "[Error] parameter error. parameter: worker | web | all"
	exit 1
fi

exit 0