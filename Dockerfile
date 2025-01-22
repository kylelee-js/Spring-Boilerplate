# 빌드 단계: OpenJDK 17 기반의 이미지 사용
FROM openjdk:17-jdk-slim AS build

# 애플리케이션 소스를 복사
WORKDIR /app/boilerplate
COPY . .

# Gradle을 사용하여 빌드
RUN ./gradlew build --no-daemon -x test

# 실행 단계: 빌드한 .jar 파일을 실행
FROM openjdk:17-jdk-slim

# 빌드 단계에서 생성한 jar 파일을 복사
COPY --from=build /app/boilerplate/build/libs/boilerplate-0.0.1-SNAPSHOT.jar /app/boilerplate.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/boilerplate.jar"]