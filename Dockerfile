# 빌드 단계: OpenJDK 17 기반의 이미지 사용
FROM openjdk:17-jdk-slim AS build

# Gradle 빌드 도구를 설치
RUN apt-get update && apt-get install -y curl unzip
RUN curl -s https://services.gradle.org/distributions/gradle-7.5.1-bin.zip -o gradle.zip
RUN unzip gradle.zip -d /opt
RUN ln -s /opt/gradle-7.5.1/bin/gradle /usr/bin/gradle

# 애플리케이션 소스를 복사
WORKDIR /app/demoApp
COPY . .

# Gradle을 사용하여 빌드
RUN gradle build --no-daemon

# 실행 단계: 빌드한 .jar 파일을 실행
FROM openjdk:17-jdk-slim

# 빌드 단계에서 생성한 jar 파일을 복사
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]