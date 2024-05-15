#!/bin/sh

DEBUG_LOG="debug.log"

# blue 컨테이너가 띄워져 있는지 확인
EXIST_BLUE=$(docker ps --filter "name=was-blue" -q | grep -E .)

if [ -n "$EXIST_BLUE" ]; then
  TARGET_COLOR="green"
  NOW_COLOR="blue"
  WEB_SERVER_TARGET_PORT=8081
  WEB_SERVER_STOP_PORT=8080
else
  TARGET_COLOR="blue"
  NOW_COLOR="green"
  WEB_SERVER_TARGET_PORT=8080
  WEB_SERVER_STOP_PORT=8081
fi

# Run docker-compose
DOCKER_COMPOSE="docker-compose-$TARGET_COLOR.yml"
DOCKER_ENV=".env"

echo "<<< Run docker-compose : $DOCKER_COMPOSE $(date +'%Y-%m-%d %H:%M:%S')" >> $DEBUG_LOG

sed -i "s/USE_PROFILE=$NOW_COLOR/USE_PROFILE=$TARGET_COLOR/g" $DOCKER_ENV
docker compose -f "$DOCKER_COMPOSE" up -d

echo ">>> Complete docker-compose up $(date +'%Y-%m-%d %H:%M:%S')" >> $DEBUG_LOG

sleep 60

# nginx 설정 및 reload
echo "<<< Reload nginx $(date +'%Y-%m-%d %H:%M:%S')" >> $DEBUG_LOG

NGINX_ID=$(docker ps --filter "name=nginx" -q)
NGINX_CONFIG="../nginx/nginx.conf"

sed -i "s/sleep-well-$NOW_COLOR:$WEB_SERVER_STOP_PORT/sleep-well-$TARGET_COLOR:$WEB_SERVER_TARGET_PORT/g" $NGINX_CONFIG
sed -i "s/sleep-well-$NOW_COLOR/sleep-well-$TARGET_COLOR/g" $NGINX_CONFIG
docker restart nginx

echo ">>> Complete reload $(date +'%Y-%m-%d %H:%M:%S')" >> $DEBUG_LOG

# 이전 버전 컨테이너 & 이미지 삭제
echo ">>> kill old version $(date +'%Y-%m-%d %H:%M:%S')" >> $DEBUG_LOG

STOP_WAS_ID=$(docker ps --filter "name=sleep-well-$NOW_COLOR" -q)

docker stop $STOP_WAS_ID
docker rm $STOP_WAS_ID

docker image prune -a -f

echo "------------------ All is done $(date +'%Y-%m-%d %H:%M:%S')------------------" >> $DEBUG_LOG
