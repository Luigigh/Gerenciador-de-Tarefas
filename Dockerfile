# Estágio 1: Build da aplicação com Maven e Java 21
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro (otimiza cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e gera o pacote .jar sem rodar testes
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Imagem final enxuta para rodar o .jar
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o .jar gerado no primeiro estágio
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 padrão do Spring Boot
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]