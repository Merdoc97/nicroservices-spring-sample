#to build sample use commands

#create docker network

docker network create -d bridge spring_cloud

# run package
mvn clean package

# create docker images
mvn dockerfile:build -pl account-service/,customer-service/,admin-dashboard/,discovery-service/,gateway-service/,turbine/

# run containers
docker-compose up

on 8787 port allowed admin dashboard
on 8765 port allowed gateway service
on 5601 port kibana present
on 9411 port zipkin service
search index for kibana micro-*
after images succesfully run go to admin dashboard and go to turbine dashboard (to see loading activity)
run command

ab -c 10 -n 20000 http://localhost:8765/api/customer/customers
ab -c 10 -n 20000 http://localhost:8765/api/account/accounts
ab -c 10 -n 20000 http://localhost:8765/api/customer/customers/2
and see how turbine works via rabbitmq