# Stage 1: build
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew installDist --no-daemon

# Stage 2: run (smaller image, no build tools)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/install/ci-cd/ /app/
EXPOSE 8080
CMD ["/app/bin/ci-cd"]