FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD turbine-service.jar turbine-service.jar
RUN sh -c 'touch /turbine-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /turbine-service.jar" ]