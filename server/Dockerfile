#FROM adoptopenjdk:11-jre-hotspot AS build
#
#RUN apt-get update && apt-get install -y less
#
#WORKDIR /app
#
#COPY server/build/libs/server-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM bellsoft/liberica-openjdk-alpine:11 AS build

WORKDIR /app

COPY server/build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# 로그 디렉토리 생성
RUN mkdir /logs

ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=server"]