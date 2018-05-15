FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD account-service.jar account-service.jar
RUN sh -c 'touch /account-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /account-service.jar" ]