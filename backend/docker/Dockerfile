FROM tomee:8-jdk-7.0.1-plus
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

WORKDIR /usr/local/tomee/

COPY ./priority-rules-backend.war ./webapps/.
COPY ./resources/tomcat-user.xml ./conf/tomcat-users.xml
COPY ./resources/manager-context.xml ./webapps/manager/META-INF/context.xml

# Copy the startup script
COPY ./resources/start-prs.sh .
RUN ["chmod", "u+x", "./start-prs.sh"]

# Creating an health check verification to check TomEE status
HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

# exposing the 8080 port to support external connections
EXPOSE 8080

# Starting the service
ENTRYPOINT ["./start-prs.sh"]