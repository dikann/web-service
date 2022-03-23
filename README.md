# Dikann's web service

Dikann's web service is the backbone of Dikann. It provides you with a secure, fast, and powerful API to create, read, update, and delete Users,
Products, Discounts, Category, etc.

# Installation

There are two ways to install Dikann's web service on your machine. The easiest way is by using docker.

## Docker

Download the latest version of `web-service-docker.zip` from [here](https://github.com/dikann/web-service/releases).

### Unzip the file:

```shell
unzip web-service-docker.zip
```

### Change directory:

```shell
cd web-service-docker
```

### Edit the `.env` file:

`.env` file contains all the configurations of Dikann's web service.
> Make sure to change `DIKANN_ADMIN_EMAIL`, `DIKANN_ADMIN_PASSWORD`, `DIKANN_JWT_SECRET_KEY`, `POSTGRESQL_DATABASE_password` variables.

```shell
vi .env
```

### All set! Simply run Dikann's web service:

```shell
docker-compose up
```

## Jar file

**Make sure you have openjdk >= 8 installed on your machine.**\
\
**Dikann uses PostgreSQL as its database.**
> Download PostgreSQL from [here](https://www.postgresql.org/download/).

> Install PostgreSQL on your machine and create a database and a user with  privileges to that database. [Docs](https://www.postgresql.org/docs/)

> You can also use Docker to install PostgreSQL on your machine. [more information](https://hub.docker.com/_/postgres)

Download the latest version of `web-service.zip` from [here](https://github.com/dikann/web-service/releases).

### Unzip the file:

```shell
unzip web-service.zip
```

### Change directory:

```shell
cd web-service-docker
```

### Edit the `.env` file:

`.env` file contains all the configurations of Dikann's web service.
> Make sure to change `DIKANN_ADMIN_EMAIL`, `DIKANN_ADMIN_PASSWORD`, `DIKANN_JWT_SECRET_KEY`, ``POSTGRESQL_DATABASE_NAME`` ,`POSTGRESQL_DATABASE_USER` ,`POSTGRESQL_DATABASE_password` variables.

```shell
vi .env
```

### Run Dikann's web service:

```shell
java -jar dikann-web-service.jar --spring.config.location=application.properties
```

## Documentation
All the documentations can be found [here](https://dikann.github.io/documentation-web-service/).