FROM eclipse-temurin:27-jre

WORKDIR /app
COPY target/paiement-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
