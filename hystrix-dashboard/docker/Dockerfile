FROM java:8-jre-alpine
VOLUME /properties
VOLUME /logdir
ADD hystrix-dashboard.jar hystrix-dashboard.jar
RUN sh -c 'touch /turbine-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /hystrix-dashboard.jar" ]