FROM maven:3.6.3-openjdk-11-slim as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/

RUN mvn clean package
COPY target/backend-${VERSION}.jar target/app.jar

FROM openjdk:23.0.1-jre-slim
WORKDIR /app/

COPY --from=BUILDER /build/target/app.jar /app/
CMD java -jar /app/app.jar