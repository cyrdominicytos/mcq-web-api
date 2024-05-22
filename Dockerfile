#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/qcmapp/src
COPY pom.xml /home/qcmapp
RUN mvn -f /home/qcmapp/pom.xml clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-alpine
WORKDIR /qcmapp
COPY --from=build /home/qcmapp/target/*.jar /qcmapp/qcmapp.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","qcmapp.jar"]
#ENTRYPOINT ["java","-jar","/home/app/target/spring_rest_docker.jar"]