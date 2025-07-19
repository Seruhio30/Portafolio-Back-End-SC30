FROM eclipse-temurin:21-jdk

WORKDIR /app

# Instala Maven manualmente
RUN apt-get update && \
    apt-get install -y maven && \
    mvn -version

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/emails-0.0.1-SNAPSHOT.jar"]