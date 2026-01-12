# 1. Base image sifatida OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Ishchi direktoriyani yaratish va unga o'tish
WORKDIR /app

# 3. Maven loyihani build qilish uchun jar faylni konteynerga ko'chirish
COPY target/testWEbb-0.0.1-SNAPSHOT.jar app.jar

# 4. Portni ochish (Spring Boot default 8080)
EXPOSE 8080

# 5. Ilovani ishga tushirish
ENTRYPOINT ["java","-jar","/app/app.jar"]
