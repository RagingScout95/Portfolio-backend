# Portfolio Admin Dashboard Setup Guide

This guide will help you set up the complete admin dashboard system for your portfolio.

## Architecture

- **Backend**: Spring Boot REST API (Port: 8080)
- **Admin Dashboard**: Angular application (Port: 4200)
- **Database**: PostgreSQL
- **Domains**:
  - Portfolio: ragingscout97.in
  - API: api.ragingscout97.in
  - Admin: admin.ragingscout97.in

## Prerequisites

1. Java 17 or higher
2. Maven 3.6+
3. Node.js 18+ and npm
4. PostgreSQL 12+
5. Angular CLI 19+

## Database Setup

1. Ensure PostgreSQL is running
2. Create database (if needed):
```sql
CREATE DATABASE postgres;
```

3. Database credentials (as specified):
   - Host: localhost
   - Port: 5432
   - Database: postgres
   - Username: postgres
   - Password: root

## Backend Setup

**⚠️ Important: All commands below must be run from the `portfolio-backend` directory.**

1. Navigate to backend directory:
```bash
cd portfolio-backend
```

2. Create `.env` file:
   - The `.env` file should already exist in the `portfolio-backend` directory
   - Edit it with your actual values
   - **Important**: Never commit the `.env` file to version control

3. Load environment variables:
   - Spring Boot reads environment variables automatically
   - You can export variables from `.env` manually or use IDE plugins
   - Most IDEs (IntelliJ, VS Code) can load `.env` files automatically

4. Build the project:
```bash
# Make sure you're in portfolio-backend directory
cd portfolio-backend
mvn clean install
```

5. Run the application:

**Development Mode:**
```bash
# From portfolio-backend directory
# For Windows PowerShell, use quotes:
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=dev"

# For Linux/Mac/bash:
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

**Production Mode:**
```bash
# From portfolio-backend directory
# For Windows PowerShell, use quotes:
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=prod"

# For Linux/Mac/bash:
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
```

**Alternative: Run from JAR file (after building):**
```bash
# Make sure you're in portfolio-backend directory
cd portfolio-backend

# Build JAR first
mvn clean package

# Run JAR in development
java -jar target/portfolio-backend-1.0.0.jar --spring.profiles.active=dev

# Run JAR in production
java -jar target/portfolio-backend-1.0.0.jar --spring.profiles.active=prod
```

The backend will start on `http://localhost:8080` (or the port specified in `SERVER_PORT` environment variable).

### Default Admin Credentials

On first run, a default admin user is created:
- **Username**: admin
- **Password**: admin123

**⚠️ IMPORTANT: Change this password immediately after first login!**

## Environment Variables

The `.env` file should contain the following variables:

### Database Configuration
- `DB_HOST` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 5432)
- `DB_NAME` - Database name (default: postgres)
- `DB_USERNAME` - Database username (default: postgres)
- `DB_PASSWORD` - Database password (default: root)

### Server Configuration
- `SERVER_PORT` - Server port (default: 8080)

### JWT Configuration
- `JWT_SECRET` - JWT secret key (REQUIRED in production, minimum 256 bits)
- `JWT_EXPIRATION` - Token expiration in milliseconds (default: 86400000)

### CORS Configuration
- `CORS_ALLOWED_ORIGINS` - Comma-separated list of allowed origins

### GitHub API
- `GITHUB_API_URL` - GitHub API URL (default: https://api.github.com)

**Note**: Spring Boot automatically reads environment variables. Properties files (`application-dev.properties`, `application-prod.properties`) have default values if environment variables are not set.

## Admin Dashboard Setup

1. Navigate to admin dashboard directory:
```bash
cd portfolio-admin
```

2. Install dependencies:
```bash
npm install
```

3. Update API URL in services (if needed):
   - Edit `src/app/services/auth.service.ts`
   - Edit `src/app/services/portfolio.service.ts`
   - Edit `src/app/services/github.service.ts`
   - Change `http://localhost:8080/api` to `https://api.ragingscout97.in/api` for production

4. Run development server:
```bash
npm start
```

5. Build for production:
```bash
npm run build
```

## Production Deployment

### Backend

1. Update `.env` file (or set environment variables):
   - Set proper database connection (DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD)
   - Set `JWT_SECRET` environment variable (minimum 256 bits)
   - Update CORS allowed origins (CORS_ALLOWED_ORIGINS)
   
   The application will automatically read from environment variables or `.env` file.

2. Build JAR:
```bash
mvn clean package
```

3. Run:
```bash
java -jar target/portfolio-backend-1.0.0.jar --spring.profiles.active=prod
```

### Admin Dashboard

1. Update API URLs in services to use `https://api.ragingscout97.in`
2. Build:
```bash
npm run build
```
3. Deploy `dist/portfolio-admin` to `admin.ragingscout97.in`

## Features

### Admin Dashboard Features

1. **Profile Management**
   - Edit name, role, tagline
   - Update photo URL
   - Edit about me section

2. **Projects Management**
   - Add projects manually
   - Import projects from GitHub
   - Edit project details (title, description, tech stack, links, images)
   - Drag and drop to reorder projects
   - Delete projects

3. **Education Management**
   - Add/Edit/Delete education entries
   - Manage degree, institute, year

4. **Skills Management**
   - Add/Delete skills
   - Skills are displayed as tags

5. **Experience Management**
   - Add/Edit/Delete work experiences
   - Manage role, company, dates, descriptions

6. **Social Links Management**
   - Add/Edit/Delete social media links
   - Manage name, URL, icon

7. **Current Job Management**
   - Edit current job title, company, since date, description

## API Endpoints

### Public Endpoints
- `GET /api/public/portfolio` - Get all portfolio data (for frontend)

### Authentication
- `POST /api/auth/login` - Login with username/password

### Admin Endpoints (Requires JWT Token)
- Profile: `GET /api/admin/profile`, `PUT /api/admin/profile`
- Projects: `GET /api/admin/projects`, `POST /api/admin/projects`, `PUT /api/admin/projects/{id}`, `DELETE /api/admin/projects/{id}`, `POST /api/admin/projects/reorder`
- GitHub: `GET /api/admin/github/repos/{username}`, `POST /api/admin/github/import/{username}`, `POST /api/admin/github/import-single`
- Similar endpoints for educations, skills, experiences, social-links, and current-job

## Security Notes

1. **JWT Secret**: Use a strong secret (minimum 256 bits) in production
2. **Password**: Change default admin password immediately
3. **HTTPS**: Use HTTPS in production
4. **CORS**: Configure CORS properly for production domains

## Troubleshooting

### Backend won't start
- Check PostgreSQL is running
- Verify database credentials in `.env` file or `application-dev.properties` / `application-prod.properties`
- Check port 8080 is available

### Admin dashboard can't connect to API
- Verify backend is running
- Check API URL in services
- Check CORS configuration in backend
- Verify JWT token is being sent in requests

### GitHub import not working
- Check internet connection
- Verify GitHub username is correct
- Check GitHub API rate limits

## Next Steps

1. Update your portfolio frontend to fetch data from `/api/public/portfolio`
2. Configure production domains
3. Set up SSL certificates
4. Configure reverse proxy (nginx/Apache) for domains
5. Set up CI/CD pipeline if needed

