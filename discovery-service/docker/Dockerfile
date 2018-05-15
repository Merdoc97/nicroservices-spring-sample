FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD discovery-service.jar discovery-service.jar
ENV JAVA_OPTS=""
RUN sh -c 'touch /discovery-service.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /discovery-service.jar" ]