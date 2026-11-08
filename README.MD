# Time Connector

A simple Java application demonstrating asynchronous HTTP communication between Docker containers.

Each application instance:

- starts an HTTP server;
- returns the current time in its configured time zone;
- periodically sends asynchronous HTTP requests to another container;
- handles connection errors without stopping the application.

## Technologies

- Java 21
- Java HttpClient
- CompletableFuture
- Docker

## Building the Docker image

From the project directory, run:

```bash
docker build -t time_connector .
```

## Creating a Docker network

Create a network for the containers:

```bash
docker network create clock-network
```

You only need to create the network once.

## Running two containers

This example starts two containers: one configured for New York and another for Yerevan.

### New York

Start the New York container:

```bash
docker run --rm --name new-york --network clock-network time_connector America/New_York yerevan 8080
```

The container uses the `America/New_York` time zone and sends requests to the `yerevan` container.

### Yerevan

In another terminal, start the Yerevan container:

```bash
docker run --rm --name yerevan --network clock-network time_connector Asia/Yerevan new-york 8080
```

The container uses the `Asia/Yerevan` time zone and sends requests to the `new-york` container.

## How it works

Each container starts an HTTP server on port `8080`.

The server provides the following endpoint:

```text
/currentTime
```

For example, one container can send a request to:

```text
http://yerevan:8080/currentTime
```

Docker provides internal DNS resolution, so containers connected to the same network can use container names as host names.

The application uses `HttpClient.sendAsync()` to send requests asynchronously.

Successful responses are processed using `thenAccept()`.

Connection errors and other request failures are handled using `exceptionally()`.

## Timeouts

The application uses two timeouts:

- connection timeout: 2 seconds;
- HTTP request timeout: 2 seconds.

If the remote container is unavailable, the application continues running and reports the error:

```text
HTTP request failed: java.net.ConnectException
```

When the remote container becomes available again, the application automatically resumes successful communication.

## Stopping containers

To stop a container from another terminal:

```bash
docker stop new-york
```

or:

```bash
docker stop yerevan
```

Because the containers are started with the `--rm` option, Docker automatically removes them after they stop.

## Viewing logs

To view the logs of a running container:

```bash
docker logs new-york
```

To follow logs in real time:

```bash
docker logs -f new-york
```