FROM eclipse-temurin:11.0.20.1_1-jre

COPY build/libs/fintechorg-0.0.1-SNAPSHOT.jar service.jar

ENTRYPOINT ["java", "-jar", "service.jar"]