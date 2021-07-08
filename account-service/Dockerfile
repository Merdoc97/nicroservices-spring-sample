FROM localhost:5000/13.0.2-slim-telemetry:latest
VOLUME /properties
VOLUME /logdir
ARG JAR_FILE
ENV APP_NAME=${JAR_FILE}
ADD target/${JAR_FILE} ${APP_NAME}
ENV JAVA_OPTS="-Dotel.traces.exporter=jaeger -Dotel.exporter.jaeger.endpoint=http://jaeger-collector.infra:14250 -Dotel.resource.attributes=service.name=account-service"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -javaagent:/app/bin/opentelemetry-javaagent-all.jar -jar ${APP_NAME}" ]