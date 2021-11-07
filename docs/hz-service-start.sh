HZ_DIR="/app/hzcluster"

HZ_LOG="${HZ_DIR}/logs/hzcluster.out"

readonly APP="hzcluster.jar"

readonly JAVA="/app/jdk/bin/java"
readonly LOGS="/app/hzcluster/logs"
readonly RAM="-Xms1g -Xmx3g"

readonly CFG_LOCATION="-Dspring.config.location=${HZ_DIR}/application.yml"
readonly PROFILES="-Djava.net.preferIPv4Stack=true"

readonly LOG_FILE="${LOGS}/hzcluster.log"

PID=$(ps -ef | grep java | grep hzcluster | grep -v grep | awk {'print $2'})

if [ -z "$PID" ]
then
     printf "Starting ${APP}...\n"

     nohup ${JAVA} ${RAM} -jar ${PROFILES} ${CFG_LOCATION} ${JARS}/${APP} > ${LOG_FILE} 2>&1&

     echo "hzcluster is started"
else
     echo "hzcluster is up and run PID : $PID"
fi
