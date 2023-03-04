# Identity
## Description
Identity is a lightweight reusable service that houses capability to register users and generate access tokens that may be reused for authentication and authorization.

## Technologies And Libraries Used
<a href='https://www.java.com/'>Java</a><br/>
<a href='https://spring.io/projects/spring-boot'>Spring Boot</a><br/>
<a href='https://projectreactor.io/'>Spring Reactor</a><br/>
<a href='https://docs.spring.io/spring-security/reference/'>Spring Security</a><br/>
<a href='https://r2dbc.io/'>R2DBC</a><br/>
<a href='https://flywaydb.org/'>Flyway</a><br/>
<a href='http://www.jooq.org/'>JOOQ</a><br/>
<a href='https://www.postgresql.org/'>PostgreSQL</a><br/>

## Setting Up Identity In Your Local Machine
- Create a local postgres database named identity
- Clone the forked repository locally
```
git clone https://github.com/<your-user-name>/identity
```
- Update the password of your postgres database in your <a href='https://github.com/Solixious/identity/blob/main/src/main/resources/application.properties'>application.properties</a> file
- Build the project
```
mvn clean install
```
## Instructions For Contribution
- Fork this repository
- Clone your forked repository
- Add your changes
- Commit and push
- Create a pull request
- Wait for the pull request to merge
