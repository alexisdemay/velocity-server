# Velocity

![Contributors](https://img.shields.io/badge/contributors-Alexis%20DEMAY-blue.svg)

## About The Project

TODO

### Build With

TODO

## Getting Started

### Prerequisites

TODO

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

```bash
tar -xzvf velocity-<VERSION>.tar.gz
cd velocity
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