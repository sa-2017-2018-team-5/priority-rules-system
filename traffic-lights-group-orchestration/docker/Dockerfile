FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./traffic-lights-group-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar ./traffic-lights-group-orchestration.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "./traffic-lights-group-orchestration.jar"]

CMD ["localhost", "8080"]
