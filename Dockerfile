FROM maven:3.9.3-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/emails-0.0.1-SNAPSHOT.jar"]