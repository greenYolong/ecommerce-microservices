FROM eclipse-temurin:27-jre

# curl est utilisé par le healthcheck Docker Compose (voir docker-compose.yml)
RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
