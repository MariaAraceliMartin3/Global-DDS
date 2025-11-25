# ETAPA 1: Construcción (Build)
# Usamos una imagen con JDK ya instalado para compilar
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

# Compilamos el proyecto
RUN ./gradlew bootJar --no-daemon

# ETAPA 2: Ejecución (Run)
# Usamos JRE (más ligero) en lugar de JDK para correr la app
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

# Copiamos el jar generado en la etapa anterior
# NOTA: Asegúrate que la ruta /app/build/libs/... coincida con donde gradle deja tu jar
COPY --from=build /app/build/libs/mutantes-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]