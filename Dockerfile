#FROM bellsoft/liberica-openjdk-alpine:17
## or
## FROM openjdk:8-jdk-alpine
## FROM openjdk:11-jdk-alpine
#
#CMD ["./gradlew", "clean", "build"]
## or Maven
## CMD ["./mvnw", "clean", "package"]
#
#VOLUME /tmp
#
#ARG JAR_FILE=build/libs/*.jar
## or Maven
## ARG JAR_FILE_PATH=target/*.jar
#
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","/app.jar"]

# 1단계: Gradle을 사용하여 애플리케이션 빌드
FROM gradle:8.0-jdk17 AS build

# 프로젝트 소스와 Gradle 캐시를 복사
COPY . /home/gradle/project
WORKDIR /home/gradle/project

# Gradle을 사용하여 애플리케이션 빌드
RUN gradle build --no-daemon

# 2단계: 빌드된 애플리케이션을 실행할 런타임 이미지 생성
FROM bellsoft/liberica-openjdk-alpine:17

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 애플리케이션이 사용하는 포트 설정
EXPOSE 8081