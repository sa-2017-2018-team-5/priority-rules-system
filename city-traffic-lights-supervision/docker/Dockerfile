FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./city-traffic-lights-supervision-1.0-SNAPSHOT-jar-with-dependencies.jar ./prs-city-tl-supervision.jar
COPY ./resources/wait-for-it.sh .

VOLUME /host

CMD ["java", "-jar", "./prs-city-tl-supervision.jar"]