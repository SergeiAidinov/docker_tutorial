FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY src/ ./src/
RUN mkdir out
RUN javac -d out $(find src -name "*.java")
RUN jar --create --file clock.jar --main-class com.aydinov.sergey.Main -C out .
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/clock.jar ./clock.jar
ENTRYPOINT ["java", "--module-path", "/app/clock.jar", "--module", "docker_tutorial/com.aydinov.sergey.Main"]