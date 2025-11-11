# Utiliser une image Java
FROM eclipse-temurin:17-jdk-alpine

# Créer un répertoire de travail
WORKDIR /app

# Copier le fichier JAR (à adapter selon le nom de ton JAR)
COPY target/*.jar app.jar

# Exposer le port du microservice (adapter selon chaque service)
EXPOSE 8222

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]