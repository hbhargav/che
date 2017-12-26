#!/bin/bash
set -eu

ERROR_MESSAGE="Wrong parameter, use --single or --multi"

if [ $# = 0 ]; then
    echo $ERROR_MESSAGE
    exit 1
fi

case $1 in
    --single )
        DATA_MOUNT=$HOME/.che6-single/sample/data
        DOCKER_RUN_OPTIONS=""
        ;;
    --multi )
        DATA_MOUNT=$HOME/.che6-multi/sample/data
        DOCKER_RUN_OPTIONS="-e CHE_MULTIUSER=true"
        ;;
    * )
        echo $ERROR_MESSAGE
        exit 1
esac

docker run --dns 8.8.8.8 -it --rm ${DOCKER_RUN_OPTIONS} \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v "$DATA_MOUNT:/data" \
    -v "$PWD/assembly/assembly-main/target/eclipse-che-6.0.0-M4-SNAPSHOT/eclipse-che-6.0.0-M4-SNAPSHOT" \
    eclipse/che-cli:nightly stop

docker stop $(docker ps -aq) || true
docker rm $(docker ps -aq) || true