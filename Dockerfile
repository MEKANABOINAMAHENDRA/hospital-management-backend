FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# 🔥 THIS LINE FIXES YOUR ERROR
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/hospitalmanagement-0.0.1-SNAPSHOT.jar"]
