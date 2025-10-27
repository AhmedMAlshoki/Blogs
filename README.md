# Blog Platform

A modern, full-featured blog platform built with Spring Boot and GraphQL, featuring real-time interactions, advanced search capabilities, and robust security.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security](#security)
- [Performance](#performance)

## 🎯 Overview

This blog platform is a GraphQL API-based social blogging system that allows users to create,  and interact with blog posts. It includes features like user authentication, post creation, commenting, liking, following other users, and advanced search functionality.

**Category:** Backend API, Social Media Platform, Blog Platform

## ✨ Features

### Core Functionality
- **User Management**: Registration, authentication, profile management
- **Blog Posts**: Create, read, update, and delete posts
- **Interactions**: Like and comment on posts
- **Social Network**: Follow/unfollow users
- **User Feed**: View posts from users you follow
- **Advanced Search**: Full-text search with PostgreSQL's GIN indexes and search vectors
- **Geolocation Tracking**: Track user locations with GeoIP2 integration
- **Rate Limiting**: Protect against abuse with configurable rate limits

### Technical Features
- **GraphQL API**: Flexible query language for efficient data fetching
- **JWT Authentication**: Secure token-based authentication
- **Redis Caching**: High-performance caching layer
- **Database Migrations**: Version-controlled schema with Liquibase
- **Aspect-Oriented Programming**: Cross-cutting concerns with Spring AOP
- **Scheduled Jobs**: Automated background tasks
- **Custom Exception Handling**: Comprehensive error management

## 🛠️ Tech Stack

### Core Framework
- **Java 21**: Modern Java features
- **Spring Boot 3.4.5**: Rapid application development framework
- **Maven**: Dependency management and build tool

### Backend Technologies
| Technology | Purpose | Version |
|------------|---------|---------|
| Spring GraphQL | API layer | Latest |
| Spring Security | Authentication & Authorization | Latest |
| Spring Data JDBC | Database access | Latest |
| Spring AOP | Cross-cutting concerns | Latest |
| PostgreSQL | Primary database | Latest |
| Redis | Caching and session management | Latest |
| Liquibase | Database migrations | Latest |
| JWT (jjwt) | Token-based authentication | 0.12.6 |

### Supporting Libraries
- **Lombok**: Boilerplate code reduction
- **MapStruct**: Entity-DTO mapping
- **Jackson**: JSON serialization/deserialization
- **Hibernate Validator**: Input validation (8.0.2.Final)
- **AspectJ**: AOP implementation
- **GraphQL-Java DataLoader**: N+1 query prevention
- **MaxMind GeoIP2**: Geolocation tracking

### Development Tools
- **GraphQL SPQR**: Annotation-based GraphQL schema
- **GraphiQL**: Interactive GraphQL playground
- **Docker Compose**: Container orchestration

## 🏗️ Architecture

### Architectural Pattern
- **Layered Architecture**: Separation of concerns with clear boundaries
- **GraphQL-First**: Schema-driven development
- **Repository Pattern**: Abstraction layer for data access

### Project Structure
```
src/main/java/com/example/Blogs/
├── Aspects/                    # AOP concerns (Rate limiting)
├── AuthenticationObject/       # Custom authentication tokens
├── AuthenticationProviders/    # Custom authentication logic
├── Config/                     # Spring configuration classes
│   ├── SecurityConfiguration
│   ├── RedisConfiguration
│   ├── GraphQLConfiguration
│   └── DataLoaderConfig
├── CustomResponses/            # GraphQL response DTOs
├── DAOs/                       # Data Access Objects
├── DataLoaders/                # GraphQL DataLoader implementations
├── DTOs/                       # Data Transfer Objects
├── Enums/                      # Enumeration types
├── ExceptionHandler/           # Global exception handling
├── Exceptions/                 # Custom exception classes
├── Filters/                    # HTTP request filters
├── Mappers/                    # Entity-DTO mappers (MapStruct)
├── Models/                     # Domain entities
├── Resolvers/                  # GraphQL resolvers
├── ScheduleJobs/               # Scheduled background tasks
├── Serializers/                # Custom JSON serializers
├── Services/                   # Business logic layer
└── Utils/                      # Utility classes
```

### Key Components

#### 1. **GraphQL Layer** (`Resolvers/`)
- Query resolvers for data fetching
- Mutation resolvers for data modifications
- Field resolvers for nested data resolution

#### 2. **Security Layer** (`Filters/`, `AuthenticationProviders/`)
- JWT-based authentication
- Custom authentication provider
- Role-based access control
- Rate limiting via AOP

#### 3. **Data Access Layer** (`DAOs/`)
- Database abstraction using Spring Data JDBC
- Manual SQL queries for complex operations
- Optimized for performance with batch operations

#### 4. **Business Logic Layer** (`Services/`)
- User management
- Post and comment operations
- Geolocation services
- Security services

#### 5. **Caching Layer** (`Config/RedisConfiguration`)
- Redis for session management
- Cached queries for frequently accessed data
- Connection pooling with Lettuce

#### 6. **DataLoader Pattern**
Prevents N+1 query problem in GraphQL:
- `UserDataLoader`: Efficient user fetching
- `PostDataLoader`: Batch post loading
- `CommentDataLoader`: Bulk comment retrieval

### Database Architecture

**Primary Database:** PostgreSQL
- Full-text search with `tsvector` and `tsquery`
- GIN indexes for fast text search
- Trigger functions for automatic score calculation
- Custom search function with ranking and highlighting

**Cache:** Redis
- Session storage
- Frequently accessed data
- Connection pooling with Apache Commons Pool2

### Search Capabilities

The platform implements advanced search using PostgreSQL's full-text search:

```sql
-- Key features:
- PostgreSQL search_vector with weight distribution
- Fuzzy matching with pg_trgm extension
- International content support with unaccent
- Ranked results with time-based boosting
- Highlighted search terms in results
- Filtering by author and date range
- Pagination support
```

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- PostgreSQL (or use Docker Compose)

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd Blogs
```

2. **Start infrastructure with Docker Compose**
```bash
docker compose up -d
```
This will start:
- PostgreSQL on port `5432`
- Redis on port `6379`

3. **Build the project**
```bash
./mvnw clean install
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

The application will be available at:
- **GraphQL Endpoint**: `http://localhost:8080/graphql`
- **GraphiQL UI**: `http://localhost:8080/graphiql`

### Docker Compose Services

The `compose.yaml` file defines:
- **PostgreSQL**: Main database with persistent volume
- **Redis**: Cache and session store with persistent volume

## ⚙️ Configuration

### Application Properties

Key configuration in `src/main/resources/application.properties`:

#### Database
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogs
spring.datasource.username=postgres
spring.datasource.password=postgres
```

#### Redis
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

#### JWT
```properties
app.jwtSecret=<your-secret-key>
app.jwtExpirationMs=86400000  # 24 hours
```

#### Rate Limiting
```properties
fixedRateLimiter.limit=10
fixedRateLimiter.windowSize=60000  # 1 minute
```

## 📚 API Documentation

### GraphQL Endpoint

Access the interactive GraphiQL interface at `/graphiql` to explore the API.

### Key Queries

```graphql
# Get user profile
query {
  userProfile(userId: "1") {
    id
    username
    displayName
    createdAt
    posts {
      id
      title
      body
    }
  }
}

# Search posts
query {
  searchPosts(query: "spring boot", size: 20, offset: 1) {
    total
    SearchQueryPosts {
      id
      title
      highlight
      rank
    }
  }
}

# Get top posts
query {
  getTopPosts(offset: 0) {
    id
    title
    likes {
      userId
    }
  }
}
```

### Key Mutations

```graphql
# Register
mutation {
  register(
    username: "john_doe"
    password: "securepassword"
    email: "john@example.com"
    displayName: "John Doe"
  )
}

# Login
mutation {
  login(email: "john@example.com", password: "securepassword") {
    token
    user {
      id
      username
    }
  }
}

# Create post
mutation {
  saveNewPost(title: "My First Post", body: "Content here")
}

# Follow user
mutation {
  followUser(userId: "2")
}
```

## 🗄️ Database Schema

### Core Tables

1. **users**: User accounts and profiles
2. **posts**: Blog posts with search vectors
3. **comments**: Post comments
4. **likes**: Post likes
5. **relationships**: User follow/unfollow relationships

### Key Features
- **Foreign Key Constraints**: Cascade deletion for data integrity
- **Indexes**: Optimized for common queries
- **Triggers**: Automatic score updates and timestamp management
- **Full-Text Search**: PostgreSQL search vectors with weighted ranking
- **Constraints**: Unique indexes to prevent duplicate actions

### Search Implementation

The database includes a custom `search_articles()` function with:
- Full-text search with relevance ranking
- Time-based boosting for recent content
- Highlighted search terms in results
- Author and date range filtering
- Pagination support

## 🔒 Security

### Authentication & Authorization
- **JWT Tokens**: Secure token-based authentication
- **BCrypt Password Hashing**: Secure password storage
- **Custom Authentication Provider**: Advanced email/password authentication
- **Spring Security Filter Chain**: Layered security approach

### Rate Limiting
- **AspectJ-Based**: Applied to all resolvers
- **Fixed Window Algorithm**: Prevents API abuse
- **Configurable Limits**: Adjustable per environment

### Security Filters
1. `JwtAuthenticationFilter`: Validates JWT tokens
2. `EmailPasswordAuthenticationFilter`: Handles login requests
3. `RequestCachingFilter`: Caches request body for repeated reading
4. `ClientApiExtractionFilter`: Extracts client information
5. `ExceptionHandlerFilter`: Global exception handling

### Geolocation Tracking
- **GeoIP2 Integration**: Tracks user locations
- **Privacy-Conscious**: Respects user privacy

## ⚡ Performance

### Optimization Strategies

1. **GraphQL DataLoader Pattern**: Batch queries to prevent N+1 problems
2. **Redis Caching**: Frequently accessed data cached
3. **Database Indexes**: Optimized query performance
   - User posts index
   - Post likes/comments indexes
   - Full-text search GIN indexes
   - Unique constraints on relationships
4. **Connection Pooling**: Redis connection pooling with Lettuce
5. **Lazy Loading**: Efficient data fetching strategy

### Database Triggers
- Automatic post score calculation on likes/comments
- Timestamp management for created_at and updated_at fields

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request


