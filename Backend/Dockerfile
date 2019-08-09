# FROM java:8
# VOLUME /tmp
# EXPOSE 8080
# ADD /build/libs/ideaLab.jar ideaLab.jar
# ENTRYPOINT ["java","-jar","ideaLab.jar"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
ADD /build/libs/idealab.jar /idealab.jar
ENTRYPOINT ["java","-jar","/idealab.jar"]