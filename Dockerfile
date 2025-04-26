FROM maven:3.9-amazoncorretto-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:21-alpine3.18

WORKDIR /app

RUN addgroup -S javauser && adduser -S -G javauser javauser

COPY --from=builder /app/target/*.jar app.jar

RUN chown -R javauser:javauser /app
USER javauser

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]