FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./traffic-lights-group-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar ./traffic-lights-group-orchestration.jar
COPY ./resources/wait-for-it.sh .

VOLUME /host

CMD ["java", "-jar", "./traffic-lights-group-orchestration.jar"]