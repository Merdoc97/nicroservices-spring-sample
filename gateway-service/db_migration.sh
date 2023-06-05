echo 'Run migration'
./flyway/flyway -user=$SPRING_DATASOURCE_USERNAME -password=$SPRING_DATASOURCE_PASSWORD -url=$SPRING_DATASOURCE_URL -locations=BOOT-INF/classes/db migrate
