#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

docker stop coffee-shop coffee-shop-db barista &> /dev/null || true

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
  sdaschner/barista:quarkus-testing-2


# waiting for db startup
until docker exec coffee-shop-db pg_isready -h localhost > /dev/null; do
  sleep 0.5
done;

# waiting for barista
until [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8002/q/health)" == "200" ]]; do
  sleep 0.5
done;


docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 8001:8080 \
  coffee-shop


# waiting for coffee-shop
until [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8001/q/health)" == "200" ]]; do
  sleep 0.5
done;