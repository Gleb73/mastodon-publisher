FROM openjdk:23-jdk-slim

ENV MASTODON_TOKEN=${MASTODON_TOKEN} \
    MAX_ATTEMPTS=${MAX_ATTEMPTS} \
    DELAY_MS=${DELAY_MS} \
    RESPONSE_TIMEOUT_MS=${RESPONSE_TIMEOUT_MS} \
    CONNECTION_TIMEOUT_MS=${CONNECTION_TIMEOUT_MS} \
    ENABLE_LOG=${ENABLE_LOG} \
    DB_HOST=${DB_HOST} \
    DB_PORT=${DB_PORT} \
    DB_NAME=${DB_NAME} \
    DB_USER=${DB_USER} \
    DB_PASSWORD=${DB_PASSWORD} \
    KAFKA_SERVER=${KAFKA_SERVER}

WORKDIR /app

COPY build/libs/mastodon-connection-0.0.1-SNAPSHOT.jar /app/mastodon-connection.jar

RUN if [ ! -f "/app/mastodon-connection.jar" ]; then echo "JAR file not found!" && exit 1; fi

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/mastodon-connection.jar"]