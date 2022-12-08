#!/bin/bash
echo $0    # 当前脚本的文件名（间接运行时还包括绝对路径）。
echo $n    # 传递给脚本或函数的参数。n 是一个数字，表示第几个参数。例如，第一个参数是 $1 。
echo $#    # 传递给脚本或函数的参数个数。
echo $*    # 传递给脚本或函数的所有参数。
echo $@    # 传递给脚本或函数的所有参数。被双引号 (" ") 包含时，与 $* 不同，下面将会讲到。
echo $?    # 上个命令的退出状态，或函数的返回值。
echo $$    # 当前 Shell 进程 ID。对于 Shell 脚本，就是这些脚本所在的进程 ID。
echo $_    # 上一个命令的最后一个参数
echo $!    # 后台运行的最后一个进程的 ID 号

# sh deploy.sh test 8080 hs yzt_web_hs-1.0-SNAPSHOT.jar yzt_web_hs start

# 修改APP_NAME为云效上的应用名
#PACKAGE_PATH=$1 #上传的压缩包路径

BASE_FILE=$1 #后端项目运行目录

APP_NAME=$2 #项目名称（项目jar包所在目录名）

JAR_NAME=$3 #jar包全称

ENV=$4 #运行环境

APP_PORT=$5 # 应用端口

ACTION=$6 #执行动作

PROG_NAME=$0


APP_START_TIMEOUT=20    # 等待应用启动的时间
HEALTH_CHECK_URL=http://127.0.0.1:${APP_PORT}  # 应用健康检查URL

APP_HOME=${BASE_FILE}${APP_NAME} # 服务目录 （从package.tgz中解压出来的jar包放到这个目录下）
JAR_NAME=${APP_HOME}/${JAR_NAME} #完整jar包路径
JAVA_OUT=${APP_HOME}/start.log  #日志路径 （应用的启动日志）



echo "所有参数：" $*
echo "执行命令：" sh ${BASE_FILE}


# 若相关目录不存在则创建出相关目录
mkdir -p ${APP_HOME}
#mkdir -p ${APP_HOME}logs

# 参数不匹配情况
usage() {
    echo "Usage: $PROG_NAME {start|stop|restart}"
    exit 2
}

#心跳检查
health_check() {
    exptime=0
    echo "checking ${HEALTH_CHECK_URL}"
    while true
        do
            status_code=`/usr/bin/curl -L -o /dev/null --connect-timeout 5 -s -w %{http_code}  ${HEALTH_CHECK_URL}`
            if [ "$?" != "0" ]; then
               echo -n -e "\rapplication not started"
            else
                echo "code is $status_code"
                if [ "$status_code" == "404" ];then
                    break
                fi
            fi
            sleep 1
            ((exptime++))

            echo -e "\rWait app to pass health check: $exptime..."

            if [ $exptime -gt ${APP_START_TIMEOUT} ]; then
                echo 'app start failed'
               exit 1
            fi
        done
    echo "check ${HEALTH_CHECK_URL} success"
}

#启动项目
start_application() {
    echo "starting java process"
    echo "日志输出路径：" $JAVA_OUT

    nohup java -jar $JAR_NAME  --spring.profiles.active=${ENV} --server.port=${APP_PORT} > ${JAVA_OUT} 2>&1 &

    tail -f $JAVA_OUT|sed '/application start.../q'

    echo "started java process"
  
}

#停止项目
stop_application() {
   checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v 'deploy.sh'| awk '{print$2}'`
   
   if [[ ! $checkjavapid ]];then
      echo -e "\rno java process"
      return
   fi

   echo "stop java process"
   times=60
   for e in $(seq 60)
   do
        sleep 1
        COSTTIME=$(($times - $e ))
        checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v 'deploy.sh'| awk '{print$2}'`
        if [[ $checkjavapid ]];then
            kill -9 $checkjavapid
            echo -e  "\r        -- stopping java lasts `expr $COSTTIME` seconds."
        else
            echo -e "\rjava process has exited"
            break;
        fi
   done
   echo ""
}
#启动命令
start() {
    start_application
    health_check
}
#停止命令
stop() {
    stop_application
}

#接收命令
case "$ACTION" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        stop
        start
    ;;
    *)
        usage
    ;;
esac
