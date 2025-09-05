# Bygg jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean bootJar -x test

# Kör jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENV PORT=10000
EXPOSE 10000
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT} -jar /app/app.jar"]