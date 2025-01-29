FROM maven:3.8.5-openjdk-17 AS build
COPY ../dossier/src /src
COPY ../dossier/pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM eclipse-temurin:17.0.13_11-jre-noble
COPY --from=build /target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]