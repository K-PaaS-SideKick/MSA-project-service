# 1단계: Gradle을 사용하여 애플리케이션 빌드
FROM gradle:8.0-jdk17 AS build

# 프로젝트 소스와 Gradle 캐시를 복사
WORKDIR /home/gradle/project

# Gradle wrapper 캐시를 활용하기 위해 필요한 파일만 먼저 복사
COPY gradle /home/gradle/project/gradle
COPY gradlew /home/gradle/project/
COPY build.gradle /home/gradle/project/
COPY settings.gradle /home/gradle/project/

# Gradle wrapper와 종속성 다운로드
RUN ./gradlew dependencies --no-daemon

# 소스 파일 전체를 복사
COPY . /home/gradle/project

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew clean build --no-daemon

# 2단계: 빌드된 애플리케이션을 실행할 런타임 이미지 생성
FROM bellsoft/liberica-openjdk-alpine:17

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 애플리케이션이 사용하는 포트 설정
EXPOSE 8081
