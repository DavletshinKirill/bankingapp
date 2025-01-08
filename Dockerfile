FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY calculator/pom.xml calculator
COPY suppressions.xml .
RUN mvn dependency:go-offline

RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=build calculator/target/calculator-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]