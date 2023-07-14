FROM bellsoft/liberica-openjdk-alpine:11 AS build

WORKDIR /app

COPY server/build/libs/server-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir /logs

ENTRYPOINT ["java", "-jar", "app.jar", "-spring.profiles.active=server"]