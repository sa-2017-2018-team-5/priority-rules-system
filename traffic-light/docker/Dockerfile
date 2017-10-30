FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./traffic-light-1.0-SNAPSHOT-jar-with-dependencies.jar ./traffic-light.jar
COPY ./resources/wait-for-it.sh .

VOLUME /host

CMD ["java", "-jar", "./traffic-light.jar"]