FROM openjdk:15
ADD target/auth-0.0.1-SNAPSHOT.jar docker-auth-app.jar
ENTRYPOINT ["java", "-jar", "docker-auth-app.jar"]