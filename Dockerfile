# 1st Docker build stage: build the project with Maven
FROM maven:3.9.5-eclipse-temurin-21 as builder
WORKDIR /project
COPY pom.xml /project/pom.xml
RUN mvn dependency:resolve
COPY . /project/
RUN mvn clean package -DskipTests -B

# 2nd Docker build stage: copy builder output and configure entry point
FROM eclipse-temurin:21-jdk-alpine
ENV APP_DIR /application
ENV APP_FILE app.jar

WORKDIR $APP_DIR
COPY --from=builder /project/target/*-0.0.1-SNAPSHOT.jar $APP_DIR/$APP_FILE

ENTRYPOINT java $CONFIG -jar $APP_FILE