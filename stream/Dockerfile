FROM openjdk:11-jre-slim

ARG GOOGLE_APPLICATION_CREDENTIALS
COPY ${GOOGLE_APPLICATION_CREDENTIALS} ${GOOGLE_APPLICATION_CREDENTIALS}


COPY ./build/libs/stream.jar /app.jar

EXPOSE 5003

CMD ["java", "-Xmx2048m", "-jar","/app.jar", "--server.port=5003"]