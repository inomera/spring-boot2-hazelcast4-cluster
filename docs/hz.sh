#!/usr/bin/env bash
#################################################
# Edit .bashrc and add following line:          #
# alias hz="sh ${MY_SCRIPTS}/hz.sh"             #
#                                               #
# Usage: hz start|stop|log|pid|kill             #
#                                               #
# Code conventions:                             #
# https://google.github.io/styleguide/shell.xml #
#################################################

# Constants
readonly E_DID_NOTHING=1

readonly APP="hzcluster.jar"

readonly JAVA="/app/jdk/bin/java"
readonly JARS="/app/hzcluster"
readonly LOGS="/app/hzcluster/logs"
readonly RAM="-Xms1g -Xmx3g"


readonly CFG_LOCATION="-Dspring.config.location=${JARS}/application.yml"
readonly PROFILES="-Djava.net.preferIPv4Stack=true"


readonly LOG_FILE="${LOGS}/hzcluster.log"

readonly PID=$(ps -ef | grep java | grep hzcluster | grep -v grep | awk {'print $2'})
readonly COMMAND="$1"

start_hz() {
    if [[ ! -z "${PID}" ]]; then
        printf "${APP} already running\n"
        exit "${E_DID_NOTHING}"
    fi

    printf "Starting ${APP}...\n"

    nohup ${JAVA} ${RAM} -jar ${PROFILES} ${CFG_LOCATION} ${JARS}/${APP} > ${LOG_FILE} 2>&1&
    tail_hz_log
}

stop_hz() {
    if [[ -z "${PID}" ]]; then
        printf "PID not found. ${APP} is not running\n"
    else
        printf "Stopping ${APP} with PID ${PID}\n"
        kill -s TERM "${PID}"
    fi
}

kill_hz() {
    if [[ -z "${PID}" ]]; then
        printf "PID not found. ${APP} is not running\n"
    else
        printf "Killing ${APP} with PID ${PID}\n"
        kill -9 "${PID}"
    fi
}

show_hz_pid() {
        printf "${APP} PID = ${PID}\n"
}

tail_hz_log() {
    tail -f ${LOG_FILE}
}

if [[ -z "${COMMAND}" ]] ; then
        printf "Usage: hz start|stop|log|pid|kill\n"
        exit "${E_DID_NOTHING}"
elif [[ "${COMMAND}" != "start" ]] &&
     [[ "${COMMAND}" != "stop" ]] &&
     [[ "${COMMAND}" != "log" ]] &&
     [[ "${COMMAND}" != "pid" ]] &&
     [[ "${COMMAND}" != "kill" ]]; then
        printf "Invalid command: ${COMMAND}\n"
        printf "Available commands: start, stop, log, pid, kill\n"
        exit "${E_DID_NOTHING}"
else
        printf "Running command ${COMMAND}\n"
fi

if [[ "${COMMAND}" = "start" ]]; then
        start_hz
elif [[ "${COMMAND}" = "stop" ]]; then
        stop_hz
elif [[ "${COMMAND}" = "log" ]]; then
        tail_hz_log
elif [[ "${COMMAND}" = "pid" ]]; then
        show_hz_pid
elif [[ "${COMMAND}" = "kill" ]]; then
        kill_hz
fi
