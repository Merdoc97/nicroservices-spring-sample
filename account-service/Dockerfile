FROM openjdk:17.0.1-slim as JAR_EXTRACT
VOLUME /properties
VOLUME /logdir
ARG JAR_FILE
WORKDIR /application
COPY target/${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17.0.1-slim
WORKDIR /application
COPY --from=JAR_EXTRACT /application/dependencies ./
COPY --from=JAR_EXTRACT /application/spring-boot-loader ./
COPY --from=JAR_EXTRACT /application/snapshot-dependencies ./
COPY --from=JAR_EXTRACT /application/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]