FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./traffic-light-1.0-SNAPSHOT-jar-with-dependencies.jar ./traffic-light.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "./traffic-light.jar"]

CMD ["localhost", "8080"]
