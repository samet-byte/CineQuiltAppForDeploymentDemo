# Cine Quilt App

Web Design and Applications Term Project Backend

- Java (pinch of Kotlin :)
- Spring Web
- Spring Security
- JPA



## Getting Started

### Prerequisites

- Java 8 or higher
- Gradle
- PostgreSQL

### Installing

1. Clone the repository

```bash
git clone https://github.com/samet-byte/CineQuiltAppBackend.git
```

2. Install dependencies

```bash
gradle build
```

3. Create a PostgreSQL database and follow the instructions in `DB_INSTRUCTIONS.md`

```bash
create database cine_demo_db2
```

4Create a `application-key.yml` file in `src/main/resources` and add the following lines

```yml
# application-key.yml
key:
  mail-username: example@gmail.com
  mail-password: pass word 1234 5678 
```

5. Run the application

```bash
gradle bootRun
```

## Built With

- [Spring](https://spring.io/) - The web framework used
- [Gradle](https://gradle.org/) - Dependency Management
- [Kotlin](https://kotlinlang.org/) - Programming Language
- [PostgreSQL](https://www.postgresql.org/) - Database
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
- [Postman](https://www.postman.com/) - API Development Environment
- [Git](https://git-scm.com/) - Version Control System
- [GitHub](https://github.com/samet-byte/cinequilt-app-v2) - Code Hosting Platform

## Authors
- [Samet Bayat](https://sametb.com/)
- [Ramazan Alper]()

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details