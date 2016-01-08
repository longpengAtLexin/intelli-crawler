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


function stopWeb()
{
	#1)先关闭
	pid=`ps -ef|grep tomcat|grep intelligent-crawler-web|awk '{print $2}'`
	if [ "$pid"x != ""x ]; then
		kill -9 $pid
	fi
	
	
}

function stopWorker()
{
	#1) 停服务
	if [ -f $CRAWLER_HOME/worker/worker.sh ]; then
		$CRAWLER_HOME/worker/worker.sh stop
	fi	
	
	
}

function stopAll()
{
	
	stopWeb
	stopWorker
	return 0
}


if [ $module = $MODULE_WEB ]; then

	stopWeb
	

elif [ $module = $MODULE_WORKER ]; then

	stopWorker
	
elif [ $module = $MODULE_ALL ]; then
	
	stopAll
	
else
	echo "[Error] parameter error. parameter: worker | web | all"
	exit 1
fi

exit 0