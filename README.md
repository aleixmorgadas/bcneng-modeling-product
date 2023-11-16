# bcneng modeling product

This is part of a conversation regarding how to model a domain with two concepts:

- Category
- Product

## Run locally

Start the spring boot application with postgres as dev mode:

```bash
./gradlew bootTestRun
```

Then, you can play with the API with [`api.http`](./api.http) file.

Run tests:

```bash
./gradlew test
```