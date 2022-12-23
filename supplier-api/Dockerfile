FROM openjdk:11.0.11-jre-slim

ARG GOOGLE_APPLICATION_CREDENTIALS
COPY ${GOOGLE_APPLICATION_CREDENTIALS} ${GOOGLE_APPLICATION_CREDENTIALS}

COPY ./supplier-api/build/libs/supplier-api.jar /app.jar

EXPOSE 5001

CMD ["java", "-Xmx2048m", "-jar", "/supplier-api.jar", "--server.port=5001"]