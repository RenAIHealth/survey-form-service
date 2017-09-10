#!/usr/bin/env bash

CMD=$1

if [ ! -n "$CMD" ] ;then
    CMD=./entrypoint.sh
fi

docker rm -f survey-form-service || echo "No started service found"

docker run -t -p 9190:9190 --rm --name=survey-form-service \
         -e APP_ENV=${APP_ENV} \
         -e DB_SERVER=${DB_SERVER} \
         -e DB_USER=${DB_USER} \
         -e DB_PASSWORD=${DB_PASSWORD} \
         registry.cn-beijing.aliyuncs.com/stardust/survey-form-service \
         $CMD &