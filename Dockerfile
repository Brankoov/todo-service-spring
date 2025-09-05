# Bygg jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean bootJar -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT} -jar /app/app.jar"]
