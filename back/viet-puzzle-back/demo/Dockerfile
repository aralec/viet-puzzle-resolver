FROM maven:latest AS buildstage

WORKDIR /app

COPY . .

RUN mvn clean install
RUN mvn clean package

FROM openjdk:17 AS runstage

COPY --from=buildstage /app/target/demo-0.0.1-SNAPSHOT.jar .

EXPOSE 9966

ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
