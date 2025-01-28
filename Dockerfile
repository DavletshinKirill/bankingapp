FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY deal/pom.xml deal/


RUN mvn dependency:go-offline

COPY deal/src deal/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/deal/target/*.jar application.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "application.jar"]