#!/bin/bash

echo Executing \"$0 $@\"
echo `date`: \"$0 $@\" >> run_exec.txt

export LOG_FILE_PATH=${1:-"amazon-epay-poc.log"}
export GRPCPORT=${3:-"8090"}

# nohup java -Dserver.port=$HEALTHPORT -jar build/libs/pg-connector-svc-v2.jar >/dev/null 2>&1 &
java -Dserver.port=$GRPCPORT -jar build/libs/amazon-epay-poc-0.0.1-SNAPSHOT.jar