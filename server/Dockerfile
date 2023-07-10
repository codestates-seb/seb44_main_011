FROM adoptopenjdk:11-jre-hotspot AS build

WORKDIR /app

COPY server/build/libs/server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]