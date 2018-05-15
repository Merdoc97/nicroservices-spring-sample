#to build sample use commands
#create docker network
docker network create -d bridge spring_cloud
# run package
mvn clean package
# create docker images
mvn docker:build -pl account-service/,customer-service/,admin-dashboard/,discovery-service/,gateway-service/
# run containers
docker-compose up
