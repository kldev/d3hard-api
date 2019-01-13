FROM openjdk:8-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY config.yml config.yml
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","server","config.yml"]