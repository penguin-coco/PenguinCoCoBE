FROM adoptopenjdk/openjdk11:latest
ADD penguin-coco-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]