FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD customer-service.jar customer-service.jar
RUN sh -c 'touch /customer-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /customer-service.jar" ]