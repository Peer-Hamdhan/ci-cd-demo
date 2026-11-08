# Stage 1: build
FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle installDist --no-daemon

# Stage 2: run (smaller image, no build tools)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/install/ci-cd/ /app/
EXPOSE 8080
CMD ["/app/bin/ci-cd"]