# Velocity (Work in Progress)

Serveur d'application sous Spring Boot qui expose une API REST ayant pour objectif de fournir des services aux usagers de vélos en libre service.

Ce projet utilise les données vélos fournis par les APIs exposées par JCDecaux : https://developer.jcdecaux.com/

## Developed using

Le but de cette application est également de découvrir et de tester l'utilisation des composants suivants :

* Spring Boot
* Spring Webflux
* Spring Data JPA
* Spring Actuator
* Spring AOP
* Swagger
* Prometheus
* Grafana
* Docker

## Setup

1) Ajouter le plugin Lombok : http://mapstruct.org/documentation/ide-support/
2) Récupérer une clef pour utiliser l'API fournit JCDecaux : https://developer.jcdecaux.com/#/signup
3) Pour lancer l'application Spring Boot via un IDE, ajouter les variables d'environnements suivantes :
    * APP_VERSION=XXXXXX
    * APP_NAME=XXXXXX
    * POSTGRES_USER=XXXXXX
    * POSTGRES_PASSWORD=XXXXXX
    * POSTGRES_DB=XXXXXX
    * JCDECAUX_API_KEY=XXXXXX

## Build

1) Cloner le repository depuis GitHub
2) Importer le projet dans un IDE en tant que Maven Project
3) Se déplacer dans le répertoire suivant : ```/src/main/docker/```
4) Créer un fichier ```.env``` puis ajouter les paramètres suivants :
```
# Application
APP_VERSION=0.0.1-SNAPSHOT
APP_NAME=velocity

# PostegreSQL
POSTGRES_USER=XXXXXXXX
POSTGRES_PASSWORD=XXXXXXXX
POSTGRES_DB=velocity_db

# JCDecaux
JCDECAUX_API_KEY=XXXXXXXX
```

```bash
mvn clean install -P docker
````

## Deploy

1. Récupérer l'archive velocity-<VERSION>.tar.gz générée et la déposer sur le serveur
2. Lancer les commandes suivantes

```bash
tar -xzvf velocity-<VERSION>.tar.gz
cd velocity
docker-compose up -d
```

## Utils

```bash
# Lancer les conteneurs
docker-compose up -d

# Lister les processus
docker ps

# Afficher les logs d'un container
docker logs -f --tail 100 <CONTAINER_NAME>

# Arrêter les conteneurs
docker-compose stop <CONTAINER_NAME>

# /!\ Supprimer tous les conteneurs
docker-compose rm -f

# Supprimer toutes les images 
docker rmi $(docker images -aq)
```