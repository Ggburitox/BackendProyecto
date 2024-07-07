#Spring Boot

Setup:
- Enable Environment Variables from Run/Debug configuration
- Add the following environment variables:
    - `JWT_SECRET=secret;`
    - `PG_HOST=localhost;`
    - `PG_PORT=5432;`
    - `PGPASSWORD=postgres;` (depends on your pgadmin configuration)
    - `PGUSER=postgres;` (depends on your pgadmin configuration)
    - `POSTGRES_DB=postgres`