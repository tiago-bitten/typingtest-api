# TypingTest API
![Java](https://img.shields.io/badge/Java-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-brightgreen?style=flat-square)
![Spring Security](https://img.shields.io/badge/Spring%20Security-brightgreen?style=flat-square)
![JWT Auth0](https://img.shields.io/badge/JWT%20Auth0-yellow?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-red?style=flat-square)
![Railway](https://img.shields.io/badge/Railway-purple?style=flat-square)
![Swagger](https://img.shields.io/badge/Swagger-brightgreen?style=flat-square)


# About the Project

This project represents a Java Spring API designed to serve as a platform for typing tests. The API generates entirely random typing tests, enabling users to practice and enhance their typing skills.

### Random Test Resources

The API incorporates a collection of short words (3 to 4 letters), medium words (5 to 6 letters), and long words (7 to 8 letters) stored in three files located in resources/typingtest-words. These words are utilized to create random tests, guaranteeing a diverse and challenging learning experience.

### Security with Spring Security and JWT Auth0

To ensure the security of the API's endpoints, the Spring Security Framework has been implemented. This ensures that only authorized users can access the API's resources. Authentication is carried out through Auth0's JWT (JSON Web Token), providing secure and efficient authentication.

### PostgreSQL Database

For the purpose of storing essential data and user information, the PostgreSQL database is employed. This allows the maintenance of an organized record of test scores and other pertinent information.

### Hosted on Railway

Presently, this project is hosted on the Railway platform, which offers a reliable and scalable hosting environment for our API.

- Check out the documentation: [TypingTest API Swagger](https://typingtest-api-production.up.railway.app/swagger-ui/index.html)

# Testing Locally

To run this project on your local machine, follow these steps:

### Clone the Repository

1. Open your terminal.
2. Navigate to the directory where you want to clone the repository.
3. Run the following command to clone the repository:

```sh
git clone https://github.com/tiago-bitten/typingtest-api.git
```

### Set Up the Database

1. Open your PostgreSQL client and create a new database for the project.
2. Modify the variable `spring.profiles.active` to `dev` in `application.properties`.
3. Configure the database. The settings can be found in the `application-dev.properties` file.
   
```sh
spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
spring.datasource.username=your-username
spring.datasource.password=your-password
```
### Build and Run

1. Open the project in your Java IDE.
2. Build the project.
3. Run the application.

### Access the API Locally

Once the application is up and running, you can access the API locally by navigating to:
```sh
http://localhost:8080/api/home
```
If everything is the API will return this:
```sh
{
    "words": [
        "Hello",
        "and",
        "welcome",
        "to",
        "our",
        "typing",
        "test",
        "platform!",
        "Dive",
        "in",
        "and",
        "create",
        "an",
        "account",
        "to",
        "start",
        "having",
        "fun!"
    ]
}
```

## Author

<a href="https://github.com/tiago-bitten">
  <img src="https://avatars.githubusercontent.com/tiago-bitten" width="50" height="50">  
</a>

[tiago-bitten](https://github.com/tiago-bitten)

## License

This project is licensed under the [![License: MIT](https://img.shields.io/badge/MIT_License-brightgreen?style=flat-square)](https://github.com/tiago-bitten/typingtest-api/blob/main/LICENSE)
