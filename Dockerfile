FROM registry.access.redhat.com/ubi9/openjdk-21:latest
LABEL maintainer="Sebastian Mutuku App" version="v1.0.0"
ENV TZ=Africa/Nairobi
EXPOSE 8081
USER root
RUN microdnf update -y
RUN microdnf clean all -y
COPY target/*.jar /app/application.jar
WORKDIR /app
USER root
USER jboss
ENTRYPOINT exec java $JAVA_OPTS -jar application.jar