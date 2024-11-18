###
### BUILD
###

FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN mvn clean package -Dmaven.test.skip

###
### DEPLOY
###

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/DCompiler.jar /app/DCompiler.jar

ENTRYPOINT ["java", "-jar", "DCompiler.jar"]
