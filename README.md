# Portfolio Backend API

A robust Spring Boot REST API backend for managing portfolio content with JWT authentication, PostgreSQL database, and comprehensive CRUD operations.

## 🚀 Features

- **RESTful API** - Complete CRUD operations for portfolio management
- **JWT Authentication** - Secure admin authentication with token-based authorization
- **PostgreSQL Database** - Reliable data persistence
- **Environment-based Configuration** - Separate dev and prod configurations
- **Spring Security** - Protected admin endpoints with public API access
- **Cross-Origin Support** - CORS configuration for frontend integration
- **Profile Management** - Personal information, photo, and favicon
- **Project Showcase** - Manage projects with tech stacks and links
- **Experience Timeline** - Work history with detailed descriptions
- **Skills Management** - Technical skills with display ordering
- **Education Records** - Academic background information
- **Social Links** - GitHub, LinkedIn, Instagram, CodeChef, and more
- **Current Job** - Highlight current position

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Security** - JWT authentication
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Primary database
- **Maven** - Dependency management
- **Lombok** - Reduced boilerplate code
- **Spring Dotenv** - Environment variable management

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## ⚙️ Installation

### 1. Clone the Repository

```bash
git clone https://github.com/RagingScout95/Portfolio-backend.git
cd Portfolio-backend
```

### 2. Database Setup

Create PostgreSQL databases:

```sql
CREATE DATABASE portfolio_dev;
CREATE DATABASE portfolio_prod;
```

### 3. Environment Configuration

Create a `.env` file in the project root:

```env
# Development Database
DB_HOST_DEV=localhost
DB_PORT_DEV=5432
DB_NAME_DEV=portfolio_dev
DB_USERNAME_DEV=postgres
DB_PASSWORD_DEV=your_password

# Production Database
DB_HOST_PROD=your_prod_host
DB_PORT_PROD=5432
DB_NAME_PROD=portfolio_prod
DB_USERNAME_PROD=your_prod_username
DB_PASSWORD_PROD=your_prod_password

# JWT Configuration
JWT_SECRET=your-super-secret-jwt-key-min-256-bits
JWT_EXPIRATION=86400000

# CORS Origins
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://localhost:4201
```

> **Note:** Never commit the `.env` file to version control. It's already in `.gitignore`.

### 4. Build the Project

```bash
mvn clean install
```

## 🚀 Running the Application

### Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

### Production Mode

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
```

The API will start on `http://localhost:8080`

## 📚 API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Admin login |

### Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/public/portfolio` | Get all portfolio data |

### Admin Endpoints (Requires JWT)

#### Profile
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/profile` | Get profile |
| PUT | `/api/admin/profile` | Update profile |

#### Projects
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/projects` | Get all projects |
| POST | `/api/admin/projects` | Create project |
| PUT | `/api/admin/projects/{id}` | Update project |
| DELETE | `/api/admin/projects/{id}` | Delete project |

#### Experience
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/experiences` | Get all experiences |
| POST | `/api/admin/experiences` | Create experience |
| PUT | `/api/admin/experiences/{id}` | Update experience |
| DELETE | `/api/admin/experiences/{id}` | Delete experience |

#### Skills
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/skills` | Get all skills |
| POST | `/api/admin/skills` | Create skill |
| PUT | `/api/admin/skills/{id}` | Update skill |
| DELETE | `/api/admin/skills/{id}` | Delete skill |

#### Education
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/educations` | Get all educations |
| POST | `/api/admin/educations` | Create education |
| PUT | `/api/admin/educations/{id}` | Update education |
| DELETE | `/api/admin/educations/{id}` | Delete education |

#### Social Links
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/social-links` | Get all social links |
| POST | `/api/admin/social-links` | Create social link |
| PUT | `/api/admin/social-links/{id}` | Update social link |
| DELETE | `/api/admin/social-links/{id}` | Delete social link |

#### Current Job
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/current-job` | Get current job |
| PUT | `/api/admin/current-job` | Update current job |

## 🔒 Security

- **JWT Authentication** - All admin endpoints require valid JWT tokens
- **Password Encryption** - BCrypt password hashing
- **CORS Protection** - Configured allowed origins
- **Environment Variables** - Sensitive data stored securely

### Default Admin Credentials

On first run, a default admin account is created:
- Username: `admin`
- Password: `admin123`

> **⚠️ IMPORTANT:** Change the default password immediately after first login!

## 📁 Project Structure

```
src/main/java/com/ragingscout/portfolio/
├── config/              # Configuration classes
├── controller/          # REST controllers
├── dto/                 # Data transfer objects
├── entity/              # JPA entities
├── repository/          # Data repositories
├── security/            # Security configurations
└── service/             # Business logic
```

## 🧪 Testing

```bash
mvn test
```

## 📦 Building for Production

```bash
mvn clean package -DskipTests
```

The JAR file will be created in the `target/` directory.

## 🚢 Deployment

### Using JAR

```bash
java -jar target/portfolio-backend-1.0.0.jar --spring.profiles.active=prod
```

### Using Docker (Optional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/portfolio-backend-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🔧 Configuration Files

- `application-dev.properties` - Development environment
- `application-prod.properties` - Production environment
- `.env` - Environment variables (not in repo)

## 📝 Database Schema

The application uses JPA to automatically create/update the database schema. Tables include:
- `profiles` - User profile information
- `projects` - Project showcase
- `experiences` - Work history
- `skills` - Technical skills
- `educations` - Academic background
- `social_links` - Social media links
- `current_job` - Current position
- `admins` - Admin users

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 🐛 Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check database credentials in `.env`
- Ensure databases are created

### JWT Token Errors
- Verify JWT_SECRET is set correctly
- Check token expiration time
- Ensure Authorization header format: `Bearer <token>`

### Port Already in Use
```bash
# Find process using port 8080
netstat -ano | findstr :8080
# Kill the process
taskkill /PID <pid> /F
```

## 📞 Support

For issues and questions, please open an issue on GitHub.

---

Built with ❤️ using Spring Boot
