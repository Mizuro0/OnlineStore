FROM maven:3.8.5-eclipse-temurin-17 as build

COPY src /opt/app/src
COPY pom.xml /opt/app/pom.xml

WORKDIR /opt/app

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

RUN addgroup --system spring-boot && adduser --system --group spring-boot
USER spring-boot:spring-boot

WORKDIR /opt/app
EXPOSE 8080
COPY docker-compose.yml /opt/app/docker-compose.yml
COPY --from=build /opt/app/target/AviaTickets-0.0.1-SNAPSHOT.jar /opt/app/application.jar

ENTRYPOINT ["java", "-jar", "/opt/app/application.jar"]
