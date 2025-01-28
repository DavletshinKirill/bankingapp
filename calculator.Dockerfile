FROM maven:3.8.5-openjdk-17 AS build
COPY /calculator/src /src
COPY /calculator/pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM eclipse-temurin:17.0.13_11-jre-noble
COPY --from=build /target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]