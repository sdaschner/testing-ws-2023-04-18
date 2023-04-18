#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  #  docker stop coffee-shop coffee-shop-db barista &> /dev/null || true

  echo
}


cleanup

docker run -d --rm \
  --name coffee-shop-db \
  --network dkrnet \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15.2

docker run -d --rm \
  --name barista \
  --network dkrnet \
  -p 8002:8080 \
  wiremock/wiremock:2.33.2

echo building coffee-shop for remote-dev
mvn clean package -Dquarkus.package.type=mutable-jar
docker build -t coffee-shop:tmp .

docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -e QUARKUS_LAUNCH_DEVMODE=true \
  -e QUARKUS_LIVE_RELOAD_PASSWORD=123 \
  -e QUARKUS_LIVE_RELOAD_URL=http://localhost:8001 \
  -p 8001:8080 \
  coffee-shop:tmp

# waiting for coffee-shop
until [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8001/q/health)" == "200" ]]; do
  sleep 0.5
done;

mvn quarkus:remote-dev \
  -Dquarkus.live-reload.url=http://localhost:8001 \
  -Dquarkus.live-reload.password=123 \
  -Dquarkus.package.type=mutable-jar
