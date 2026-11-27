FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/install/ci-cd/ /app/
EXPOSE 8080
CMD ["/app/bin/ci-cd"]