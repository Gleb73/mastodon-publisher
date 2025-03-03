FROM openjdk:23-jdk-slim

ENV KAFKA_SERVER=${KAFKA_SERVER}

WORKDIR /app

COPY build/libs/post-gateway-0.0.1-SNAPSHOT.jar /app/post-gateway.jar

RUN if [ ! -f "/app/post-gateway.jar" ]; then echo "JAR file not found!" && exit 1; fi

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/post-gateway.jar"]