#to build sample use commands

#create docker network

docker network create -d bridge spring_cloud

# run package
mvn clean package

# create docker images
mvn docker:build -pl account-service/,customer-service/,admin-dashboard/,discovery-service/,gateway-service/

# run containers
docker-compose up

on 8787 port allowed admin dashboard
on 8765 port allowed gateway service
on 5601 port kibana present
on 9411 port zipkin service
search index for kibana micro-*
