FROM openjdk:8
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

COPY ./city-traffic-lights-orchestration-1.0-SNAPSHOT-jar-with-dependencies.jar ./prs-city-tl-orchestration.jar

VOLUME /host

ENTRYPOINT ["java", "-jar", "./prs-city-tl-orchestration.jar"]

CMD ["localhost", "8080"]
