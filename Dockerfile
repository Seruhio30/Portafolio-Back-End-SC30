FROM bellsoft/liberica-openjdk-debian:21

WORKDIR /app

# Instala Maven manualmente
RUN apt-get update && apt-get install -y maven

# Copia solo el proyecto Java desde la carpeta 'emails'
COPY emails/ /app

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/emails-0.0.1-SNAPSHOT.jar"]