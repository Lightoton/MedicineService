FROM openjdk:21
COPY appRunner/target/appRunner-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
