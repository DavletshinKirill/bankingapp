FROM maven:3.8.5-openjdk-17 AS build
COPY /gateway/src /src
COPY /gateway/pom.xml /
RUN mvn -f /pom.xml clean package

FROM eclipse-temurin:17.0.13_11-jre-noble
COPY --from=build /target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]