FROM openjdk:8-alpine3.8

ENV SLEEP 10

ADD build/libs/*.jar /user-ms.jar

RUN sh -c 'touch /user-ms.jar'

VOLUME /tmp

EXPOSE 8080

CMD echo "The application will start in ${SLEEP}s..." && \
    sleep ${SLEEP} && \
    java -Djava.security.egd=file:/dev/./urandom -jar /user-ms.jar
