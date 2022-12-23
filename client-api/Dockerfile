FROM openjdk:11.0.11-jre-slim

ARG GOOGLE_APPLICATION_CREDENTIALS
COPY ${GOOGLE_APPLICATION_CREDENTIALS} ${GOOGLE_APPLICATION_CREDENTIALS}

COPY ./client-api/build/libs/client-api.jar /app.jar

EXPOSE 5002

CMD ["java", "-Xmx2048m", "-jar", "/client-api.jar", "--server.port=5002"]
