FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install curl for HEALTHCHECK
RUN apk add --no-cache curl

COPY target/*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=3s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]
