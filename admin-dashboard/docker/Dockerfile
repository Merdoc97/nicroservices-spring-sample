FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD admin.jar admin.jar
RUN sh -c 'touch /admin.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /admin.jar" ]