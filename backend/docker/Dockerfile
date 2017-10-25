FROM tomee:8-jdk-7.0.1-plus
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

WORKDIR /usr/local/tomee/

COPY ./priority-rules-backend.war ./webapps/.
COPY ./resources/tomcat-user.xml ./conf/tomcat-users.xml
COPY ./resources/manager-context.xml ./webapps/manager/META-INF/context.xml

COPY ./resources/start-prs.sh .
RUN ["chmod", "u+x", "./start-prs.sh"]

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080

ENTRYPOINT ["./start-prs.sh"]