# TimeForge – Backend

Plateforme backend pour **TimeForge**, organisée en microservices Spring Boot avec *Spring Cloud* (Gateway, Eureka, Config Server).

## 🚀 Objectifs

- Gérer des entités de productivité (ex. objectifs, projets).
- Offrir une architecture modulaire extensible.
- Faciliter le déploiement local via Docker (MongoDB, Zipkin) et l’exécution service par service.

## 🧱 Architecture (vue d’ensemble)

```
[ Client ]  →  [ API Gateway ]  →  routes  →  [ goal-service ]
                    │                          [ project-service ]
                    └──→ [ Discovery (Eureka) ]
                             ↑
                   [ Config Server ]  ←  repo de config (git/local)

[Données] MongoDB (pour les services métier)
[Tracing] Zipkin (optionnel)
```

### Modules du dépôt

- **api-gateway** : passerelle d’entrée (Spring Cloud Gateway), centralise l’authentification/ratelimiting/routage.
- **discovery-service** : service d’enregistrement/découverte (Eureka Server).
- **configserver-service** : distribution de la configuration externe (Spring Cloud Config Server).
- **goal-service** : microservice métier pour la gestion des objectifs.
- **project-service** : microservice métier pour la gestion des projets.

## 🛠️ Stack technique

- **Java 17+**, **Spring Boot**
- **Spring Cloud** : Gateway, Netflix Eureka, Config Server
- **MongoDB** (persistance)
- **Zipkin** (traces distribuées, optionnel)
- **Maven**, **Docker / Docker Compose**

## 📦 Prérequis

- Java 17+  
- Maven 3.8+  
- Docker & Docker Compose (facultatif mais recommandé pour MongoDB/Zipkin)

## ▶️ Démarrage (local, sans Docker Compose)

1. **Cloner** le repo :
   ```bash
   git clone https://github.com/walaghrairi24-afk/TimeForge-Backend.git
   cd TimeForge-Backend
   ```

2. **Build** général :
   ```bash
   mvn -q -DskipTests clean install
   ```

3. **Lancer les services dans cet ordre** :
   - Config Server (par défaut sur `:8888`)
   - Discovery / Eureka (par défaut sur `:8761`)
   - Services métier (`goal-service`, `project-service`)
   - API Gateway (souvent `:8080`)

4. **MongoDB** : utiliser l’instance locale ou Docker.

## 🐳 Démarrage (avec Docker)

```yaml
version: "3.8"

services:
  mongodb:
    image: mongo:7
    container_name: mongodb
    restart: unless-stopped
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

  zipkin:
    image: openzipkin/zipkin:latest
    container_name: zipkin
    restart: unless-stopped
    ports:
      - "9411:9411"

volumes:
  mongo_data:
```

```bash
docker compose up -d
```

## ⚙️ Configuration

- **Config Server** : repo Git ou local
- **Discovery** : `eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka`
- **Gateway** : routes vers `lb://NOM-DU-SERVICE`

## 🔌 Points d’accès

- **Eureka Dashboard** : `http://localhost:8761`
- **Gateway** : `http://localhost:8080`
- **Zipkin** : `http://localhost:9411`

## ✅ Tests

```bash
mvn test
```

## 📁 Structure du repo

```
TimeForge-Backend/
├─ api-gateway/
├─ configserver-service/
├─ discovery-service/
├─ goal-service/
└─ project-service/
```

## 📜 Licence

Projet éducatif - MIT Licence (à confirmer)

## 👥 Auteurs

- [wala-ghrairi](https://github.com/walaghrairi24-afk)
- [hadhemibelgacem](https://github.com/hadhemibelgacem)
