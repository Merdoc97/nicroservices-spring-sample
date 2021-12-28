FROM openjdk:17.0.1-slim
VOLUME /logdir
ARG JAR_FILE
ENV APP_NAME=${JAR_FILE}
ADD target/${JAR_FILE} ${APP_NAME}
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar ${APP_NAME}" ]