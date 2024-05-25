FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ./out/artifacts/MedicineService_jar/MedicineService.jar /myproj/app.jar
ENTRYPOINT ["java","-jar","/myproj/app.jar"]