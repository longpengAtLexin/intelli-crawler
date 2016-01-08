#!/bin/bash

cd "$(dirname "$0")"

#user settings
PROCESS_NAME="intelli.crawler.worker.CrawlerWorkerMain"
PROCESS_MAIN_CLASS="intelli.crawler.worker.CrawlerWorkerMain"
PROCESS_LISTENING_SOCKET_PORT="10096"
JAVA_COMMAND="java"
JAVA_ARGS="-server -Dfile.encoding=UTF8 -Duser.region=CN -Duser.language=zh -Dname=${PROCESS_NAME} "

#common informations


CLASSPATH=.
filelist=`ls lib`
for file in $filelist
do
	CLASSPATH=$CLASSPATH:lib/$file
done

getprocessid()
{
    local processid=`ps -ef|grep ${PROCESS_NAME} | grep -v grep|awk '{print $2}'`
    echo ${processid} 
}


checkprocessrunning()
{
    process_id=$(getprocessid)
    if test -z "${process_id}"
    then
        echo "process has not been started"
    else
        echo "process started:${process_id}"
        if [ "$1". = "kill". ] ; then
            echo ${process_id} | xargs -t kill -9 
        fi
    fi
}

processtop()
{
    echo "checking and kill the process"
    checkprocessrunning "kill"
}

processtart()
{
    echo "starting process"
    processtop
    if [ "${command}". = "debug". ]; then
        ${JAVA_COMMAND} ${JAVA_ARGS} -classpath "$CLASSPATH" ${PROCESS_MAIN_CLASS}
    else
        ${JAVA_COMMAND} ${JAVA_ARGS} -classpath "$CLASSPATH" ${PROCESS_MAIN_CLASS} 1>/dev/null 2>&1 &
    fi
    checkprocessrunning
}

#check the command input 
if [ $# = 1 ]; then
    command=$1
else
    echo "please input command, usage ~ [start|stop]"
fi

case ${command} in
start)
    JAVA_COMMAND=java
    processtart
    ;;
stop)
    processtop
    ;;
status)
    checkprocessrunning
    ;;
print)
    ps -ef|grep ${PROCESS_NAME} | grep -v grep
    ;;
*)
    echo "Invalid command:${command}"
    ;;
esac


