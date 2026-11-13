FROM eclipse-temurin:27-jre

WORKDIR /app
COPY target/commande-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
