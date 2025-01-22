# Spring Boilerplate

## 환경설정
- JDK 17
- Gradle 7.5.1

### 로컬 DB 설정
db > Dockerfile로 이미지를 빌드하고 컨테이너를 실행한다.

포트 : 5432\
도커 네트워크 : spring-network
```shell
docker build -t postgres-image ./db

docker run --name postgres-container -p 5432:5432 -e {DB 설정} --network spring-network -d postgres-image
````

혹은 build.gradle에서 build, run task를 실행한다.

```shell
./gradlew buildPostgresImage

./gradlew runPostgresDockerContainer
```


## 빌드
```shell
./gradlew build
```

## 도커 이미지 생성 및 컨테이너 실행
```shell
docker build -t spring-boilerplate .

docker run --name spring-boilerplate-container -p 8080:8080 --network spring-network -e SPRING_PROFILES_ACTIVE=docker -d spring-boilerplate
```

혹은 build.gradle에서 build, run task를 실행한다.

```shell
./gradlew buildDockerImage

./gradlew runDockerContainer
```
