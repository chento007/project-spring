FROM openjdk:17-alpine

RUN mkdir -p workspace

COPY ./build/libs/photostad-1.0.0.jar /workspace

EXPOSE 8082

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","./workspace/photostad-1.0.0.jar"]

