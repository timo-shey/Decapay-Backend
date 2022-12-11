# latest maven version
# The backend file/folder need to be moved in a separate module.
# this dockerfile is for backend module of the app
# it should be inside the backend module
FROM maven:3.8.6 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

# specidied jdk 11 as agreed upon by the team
FROM openjdk:11
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]