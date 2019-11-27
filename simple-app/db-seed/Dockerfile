FROM openjdk:8-alpine3.8

COPY . ./
RUN ./gradlew build
CMD ./gradlew migrateDatabase
