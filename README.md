# Babel Pages 
- A Spring Boot Back-end REST API for an imaginary bookstore project

## **Details**
- Manually written SQL queries ONLY, no JPA at all.
- Database migrations with Flyway

## **Config and Running the project**
- Clone Repository
- Configure database and port in ```application.yml``` under ```src/main/resources```
- Create ```security.properties``` under ```src/main/resources```
  - Set the properties listed below:
  - `security.dev-mode` enables creation of a user with `ADMIN` role for testing
```
security.jwt-secret=YOUR_SECRET_HERE
security.jwt-issuer=babelpages
security.jwt-expiration=86400000
security.jwt-header-prefix=Bearer

security.dev-mode=true
security.dev-admin-username=administrator
security.dev-admin-password=10203040
```
- Create ```aws.properties``` under ```src/main/resources```
  - Set the properties listed below according to your AWS S3 configuration:
```
aws.bucket-name=babelpages
aws.profile-pictures-prefix=profilePictures/
aws.region=us-east-2
```


- There are additional development SQL scripts under ```src/main/resources/db/scripts```
  - SQL sripts that start with ```insert_dummy...(.sql)``` can be executed after running the server once (so flyway can setup the tables)
    - These will populate tables with dummy data for testing
- Refer to "Book Data Generation" section below
- **AWS Configuration**
  - This project also uses AWS S3
  - Configure the AWS credentials and region by using the AWS CLI command `aws configure`


## **Book Data Generation**
- I have made another project in python (https://github.com/joaovdonaton/book-data-collector) that uses different apis to collect and
data on actual books from ISBNs
- It is configured to use the same database as this project to insert books and tags
- It also uses OpenAI's GPT API to generate description and properly tag books by genre and descriptors

## **Important**
- Changing column names has a *HIGH* chance of breaking the queries in the DAOs
  - Doing so would probably require refactors and attention