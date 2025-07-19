FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . /app

RUN ls -la /app && mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/emails-0.0.1-SNAPSHOT.jar"]