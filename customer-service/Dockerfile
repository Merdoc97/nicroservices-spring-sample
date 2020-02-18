FROM azul/zulu-openjdk-alpine:11-jre
VOLUME /properties
VOLUME /logdir
ARG JAR_FILE
COPY ${JAR_FILE} target/${JAR_FILE}
ENV APP_NAME=target/${JAR_FILE}
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar ${APP_NAME}" ]