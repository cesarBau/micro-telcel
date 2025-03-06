FROM maven:3.9.9-amazoncorretto-23 AS build

COPY src /app/src

COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -U -Dmaven.test.skip=true

FROM openjdk:23
COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 5000

CMD ["java", "-jar", "app.jar"]