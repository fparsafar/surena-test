FROM openjdk
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

