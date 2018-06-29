FROM openjdk:8-jdk-alpine
LABEL maintainer="blurblah@blurblah.net"

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
