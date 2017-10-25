FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./driver-client-1.0-SNAPSHOT-jar-with-dependencies.jar ./driver-client.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "./driver-client.jar"]

CMD ["localhost", "8080"]
