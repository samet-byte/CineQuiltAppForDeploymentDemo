# CineQuiltApp-v2

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
git clone
```

2. Install dependencies

```bash
gradle build
```

3. Create a PostgreSQL database

```bash
create database cine_demo_db2
```

4. Create a `get_by_column_and_value_sort_by_column_and_order` function in `cine_demo_db2` and add the following lines

```sql
CREATE OR REPLACE FUNCTION public.get_by_column_and_value_sort_by_column_and_order(
    _COLUMN varchar,
    _VALUE  varchar,
    _BY varchar DEFAULT 'title',
    _ORDER  varchar DEFAULT 'ASC' -- Default order is ASC
)
RETURNS TABLE (
    id               integer,
    create_date      timestamp(6),
    created_by       integer,
    director         varchar(255),
    duration         integer,
    last_modified    timestamp(6),
    last_modified_by integer,
    poster_url       varchar(255),
    release_year     integer,
    soundtrack_url   varchar(255),
    title            varchar(255),
    trailer_url      varchar(255),
    video_url        varchar(255),
    description      varchar(1000),
    genre            varchar(255),
    type             varchar(255),
    episode_number   integer,
    season_number    integer
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY EXECUTE FORMAT(
        'SELECT * FROM metadatas %s ORDER BY %I %s',
        CASE WHEN
            _COLUMN IS NOT NULL AND
            _VALUE IS NOT NULL AND
            _VALUE::text IN ('MOVIE', 'TV_SHOW')
        THEN
            FORMAT('WHERE %I = $1', quote_ident(_COLUMN))   
        ELSE
            ''
        END,
        quote_ident(_BY),
        _ORDER
    )
    USING _VALUE;
END;
$$;
```


5. Create a `application-key.yml` file in `src/main/resources` and add the following lines

```yml
# application-key.yml
key:
  mail-username: example@gmail.com
  mail-password: pass word 1234 5678 
```

6. Run the application

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