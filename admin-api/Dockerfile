FROM openjdk:11.0.11-jre-slim

ARG GOOGLE_APPLICATION_CREDENTIALS
COPY ${GOOGLE_APPLICATION_CREDENTIALS} ${GOOGLE_APPLICATION_CREDENTIALS}

COPY ./admin-api/build/libs/admin-api.jar /app.jar


EXPOSE 5000

CMD ["java", "-Xmx2048m", "-jar", "/admin-api.jar", "--server.port=5000"]
