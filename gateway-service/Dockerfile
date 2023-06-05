FROM flyway/flyway:8.4.4-alpine as FLYWAY

FROM openjdk:17.0.1-slim as JAR_EXTRACT
VOLUME /properties
VOLUME /logdir
ARG JAR_FILE
WORKDIR /application
COPY target/${JAR_FILE} app.jar
COPY *.sh ./application/
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17.0.1-slim
WORKDIR /application
COPY --from=JAR_EXTRACT /application/dependencies ./
COPY --from=JAR_EXTRACT /application/spring-boot-loader ./
COPY --from=JAR_EXTRACT /application/snapshot-dependencies ./
COPY --from=JAR_EXTRACT /application/application ./
COPY --from=FLYWAY /flyway ./flyway
ENTRYPOINT ["sh", "docker_entrypoint.sh"]