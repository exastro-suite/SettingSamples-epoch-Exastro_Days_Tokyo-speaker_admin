FROM openjdk:17-alpine as builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

ARG MAVEN_OPTS
RUN MVNW_VERBOSE=true ./mvnw dependency:go-offline ${MAVEN_OPTS:""}

COPY src ./src

RUN ./mvnw package


FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/target/speaker_user.war .


CMD ["java", "-jar", "speaker_user.war"]

