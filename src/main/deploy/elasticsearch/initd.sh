#!/bin/bash

echo -n $'\E[32m'
echo ''
echo ' ______ _           _   _                              _.    '
echo '|  ____| |         | | (_)                            | |    '
echo '| |__  | | __ _ ___| |_ _  ___ ___  ___  __ _ _ __ ___| |__  '
echo '|  __| | |/ _` / __| __| |/ __/ __|/ _ \/ _` | |__/ __| |_ \ '
echo '| |____| | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |'
echo '|______|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|'
echo -n $'\e[0m'
echo ''

cParamFlag=false

options=':c:h'
while getopts $options option
do
    case "$option" in
        c) ELASTIC_CONFIG_FILE=${OPTARG}; cParamFlag=true ;;
        h) usage; exit $?;;
        \?) echo "Unknown option: -${OPTARG}" >&2; exit 1;;
        :) echo "Missing option argument for -${OPTARG}" >&2; exit 1;;
        *) echo "Unimplemented option: -${OPTARG}" >&2; exit 1;;
    esac
done

if [ ! $cParamFlag ]; then
	echo "The config file param is mandatory, for more information: ./initd.sh -h"
fi

if [ ! -f $ELASTIC_CONFIG_FILE ]; then
	echo "The config file does not exist"
	exit 1
fi

. $ELASTIC_CONFIG_FILE
shift $(($OPTIND - 1))

echo "Utility script for ${APP_NAME} v${APP_VERSION}"
echo ""

command="$1"

EXIT_VAL=0

usage() {
	echo "Usage: ./initd.sh -c /path/to/elastic_config start|stop|restart"
	echo " -c: path to the file containing environment variables"
	echo " -h: show help"
	return 0
}

isRunning() {
	ps -p "$1" &> /dev/null
}

start() {
	echo -n $'\e[32m'
	echo "Starting..."
	echo -n $'\e[0m'
	$APP_FILE -p ${PID_FILE} -d
	EXIT_VAL=$?
	if [ $EXIT_VAL = 0 ]; then
		touch ${LOCK_FILE}
		echo "$APP_NAME is now started"
	fi
	return $EXIT_VAL
}

stop() {
	echo -n $'\e[91m'
	echo "Stopping..."
	echo -n $'\e[0m'
	if [ -f $PID_FILE ]; then
		pid=$( cat $PID_FILE )
	else
		pid=$( pgrep -f -d, "/${APP_NAME}" )
	fi
	if [ ! -z $pid ]; then
		isRunning "$pid" || { echo "The ${APP_NAME} process ${pid} is not running, the pid file will be removed"; rm -f $lock_file $pid_file; return 0; }
		kill "$pid"
		EXIT_VAL=$?
		if [ $EXIT_VAL = 0 ]; then
			rm -f ${LOCK_FILE} ${PID_FILE}
			echo "$APP_NAME is now stopped"
		fi
		return $EXIT_VAL
	else
		echo "No $APP_NAME process to stop"
		return 0
	fi
}

restart() {
	stop && start
}

case "$command" in
	start) start "$@"; exit $?;;
	stop) stop "$@"; exit $?;;
	restart) restart "$@"; exit $?;;
	*) usage; exit $?;;
esac
