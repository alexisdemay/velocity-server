# Velocity

![Contributors](https://img.shields.io/badge/contributors-Alexis%20DEMAY-blue.svg)

## About The Project

Spring Boot server that uses JCDecaux API to perform statistics and vizualize them in Kibana.

This project has also a learning purpose to test and build skills on different technologies like Spring or Elasticsearch.

### Build With

* Spring Boot
* Spring Actuator
* Spring AOP
* Spring Webflux
* Elasticsearch
* Kibana
* ...

## Getting Started

### Prerequisites

To work on this project, the lombok plugin is required. You can find more informations on <https://projectlombok.org/setup/>

### Installation

* Get a free API key at <https://developer.jcdecaux.com>
* Add the following environment variables:
```bash
APP_VERSION=<CURRENT_VERSION>
APP_NAME=<CURRENT_APP_NAME>
JCDECAUX_API_KEY=<YOUR_JCDECAUX_API_KEY>
LOGS_PATH=<YOUR_LOG_PATH>
ELASTIC_USERNAME=<YOUR_ELASTIC_USERNAME>
ELASTIC_PASSWORD=<YOUR_ELASTIC_PASSWORD>
ELASTIC_HOST=<YOUR_ELASTIC_HOST>
ELASTIC_PORT=<YOUR_ELASTIC_PORT>
```
* Build the application with the following command:
```bash
mvn clean install -Pdev 
```

## Usage

### Docker

* Unzip the following archive file:

```bash
tar -xzvf velocity-<VERSION>.tar.gz
```

* In docker folder, create a __.env__ file and add the following environment variables:
```bash
APP_VERSION=<CURRENT_VERSION>
APP_NAME=<CURRENT_APP_NAME>
JCDECAUX_API_KEY=<YOUR_JCDECAUX_API_KEY>
LOGS_PATH=<YOUR_LOG_PATH>
ELASTIC_USERNAME=<YOUR_ELASTIC_USERNAME>
ELASTIC_PASSWORD=<YOUR_ELASTIC_PASSWORD>
```

* Start all docker services:
```bash
docker-compose up -d
```

## Help

### Docker

* Start all services from the docker-compose file:
```bash
docker-compose up -d
```

* Start a service from the docker-compose file:
```bash
docker-compose up -d <SERVICE_NAME>
```

* Stop a service from the docker-compose file:
```bash
docker-compose stop <CONTAINER_NAME>
```

* View the console logs of a docker container:
```bash
docker logs -f --tail 100 <CONTAINER_NAME>
```

* List all docker services starting:
```bash
docker ps
```

### Elasticsearch

* To start, stop or restart Elasticsearch, execute the following script:
```bash
./initd.sh -c <PATH_TO_ELASTIC_CONFIG> start|stop|restart
./ -c /Users/ademay/Desktop/Projets/velocity/velocity-server/src/main/deploy/elasticsearch/elastic_dev_config start
```

### Kibana

* To start, stop or restart Kibana, execute the following script:
```bash
./initd.sh -c <PATH_TO_KIBANA_CONFIG> start|stop|restart
./initd.sh -c /Users/ademay/Desktop/Projets/velocity/velocity-server/src/main/deploy/kibana/kibana_dev_config start
```

### Velocity

* To start, stop or restart Velocity, execute the following script:
```bash
./initd.sh -c <PATH_TO_VELOCITY_CONFIG> start|stop|restart
./initd.sh -c /Users/ademay/Desktop/Projets/velocity/velocity-server/src/main/deploy/elasticsearch/velocity_dev_config start
```