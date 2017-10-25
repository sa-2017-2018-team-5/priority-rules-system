FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./administration-client-1.0-SNAPSHOT-jar-with-dependencies.jar ./administration-client.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "./administration-client.jar"]

CMD ["localhost", "8080"]
