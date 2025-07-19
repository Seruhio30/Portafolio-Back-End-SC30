FROM bellsoft/liberica-openjdk-debian:21

WORKDIR /app

# Instalar Maven y dependencias
RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/emails-0.0.1-SNAPSHOT.jar"]