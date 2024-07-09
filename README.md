# Babel Pages 
- A Spring Boot Back-end REST API for an imaginary bookstore project

## **Details**
- Manually written SQL queries ONLY, no JPA at all.
- Database migrations with Flyway

## **Config and Running the project**
- Clone Repository
- Configure database and port in ```application.yml``` under ```src/main/resources```
- Create ```security.properties``` under ```src/main/resources```
  - Set the following properties:
```
security.jwt-secret=YOUR_SECRET_HERE
security.jwt-issuer=babelpages
security.jwt-expiration=86400000
security.jwt-header-prefix=Bearer
```
- There are additional development SQL scripts under ```src/main/resources/db/scripts```
  - SQL sripts that start with ```insert_dummy...(.sql)```should be executed after running the server once (so flyway can setup the tables)
    - These will populate tables with dummy data for testing

## **Important**
- MySQL enables `NO_ZERO_IN_DATE` by default. We want to disable that in our connection sessions to allow insertion
of books with publication dates where month or days are optional (set to 00)
  - This is done in the `application.yml` data source URL property.