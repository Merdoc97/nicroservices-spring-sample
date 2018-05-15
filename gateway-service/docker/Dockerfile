FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD gateway-service.jar gateway-service.jar
RUN sh -c 'touch /gateway-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /gateway-service.jar" ]