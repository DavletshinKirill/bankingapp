FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY calculator/pom.xml calculator/
COPY deal/pom.xml deal/
COPY dossier/pom.xml dossier/
COPY gateway/pom.xml gateway
COPY statement/pom.xml statement/
COPY suppressions.xml .

RUN mvn dependency:go-offline

COPY calculator/src calculator/src
COPY deal/src deal/src
COPY gateway/src gateway/src
COPY dossier/src dossier/src
COPY statement/src statement/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/deal/target/*.jar application.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "application.jar"]