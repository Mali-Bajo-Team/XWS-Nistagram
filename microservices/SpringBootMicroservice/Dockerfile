FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
ADD target/XWS-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/application.jar"]