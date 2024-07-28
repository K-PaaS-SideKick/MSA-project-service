# 1단계: Gradle을 사용하여 애플리케이션 빌드
FROM gradle:8.0-jdk17 AS build

# Gradle wrapper와 종속성 파일 복사
COPY gradlew /home/gradle/project/gradlew
COPY gradle /home/gradle/project/gradle
COPY build.gradle /home/gradle/project/build.gradle
COPY settings.gradle /home/gradle/project/settings.gradle

# 프로젝트 소스 복사
COPY src /home/gradle/project/src

WORKDIR /home/gradle/project

# Gradle wrapper를 사용하여 종속성 다운로드 및 빌드
RUN chmod +x gradlew
RUN ./gradlew clean build --no-daemon

# 2단계: 빌드된 애플리케이션을 실행할 런타임 이미지 생성
FROM bellsoft/liberica-openjdk-alpine:17

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 애플리케이션이 사용하는 포트 설정
EXPOSE 8081
