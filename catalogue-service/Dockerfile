FROM eclipse-temurin:27-jre

WORKDIR /app
COPY target/catalogue-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
