FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/OntoCite-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8080

LABEL authors="danil"

ENTRYPOINT ["java", "-jar", "app.jar"]