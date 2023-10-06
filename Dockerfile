FROM openjdk:11-jre-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

ARG mongodb_uri
ARG mongodb_database
ARG mail_host
ARG mail_port
ARG mail_username
ARG mail_password
ARG mail_smtp_auth
ARG mail_smtp_timeout
ARG api_key

ENV mongodb_uri=${mongodb_uri} \
    mongodb_database=${mongodb_database} \
    mail_host=${mail_host} \
    mail_port=${mail_port} \
    mail_username=${mail_username} \
    mail_password=${mail_password} \
    mail_smtp_auth=${mail_smtp_auth} \
    mail_smtp_timeout=${mail_smtp_timeout} \
    api_key=${api_key}

ENTRYPOINT [ "java", "-jar", "app.jar" ]
