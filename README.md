# Clothing Store

Modern full-stack clothing store application built with Spring Boot 3.x and React.

## 📋 Overview

This project consists of two backend applications and a React frontend:
- **Clothing Store** (port 8080) - Main store API with JWT authentication
- **Clothing Store Supplier** (port 8081) - Supplier management API
- **Frontend** (port 5173) - React SPA with Mantine UI

## 🚀 Quick Start

### Prerequisites

- **Java 17+** (required) - Download from [oracle.com](https://www.oracle.com/java/technologies/downloads/)
- **Node.js 18+** (for frontend) - Download from [nodejs.org](https://nodejs.org/)
- **Maven 3.6+** (or use included Maven wrapper)

### Backend Setup

1. **Navigate to project directory**
```bash
cd clothing_store_java
```

2. **Create environment files** (optional, defaults are provided for dev)
```bash
# For main store
cp clothing-store/.env.example clothing-store/.env

# For supplier
cp clothing-store-supplier/.env.example clothing-store-supplier/.env
```

3. **Run the backend applications**

Open two terminals:

```bash
# Terminal 1 - Main Store (port 8080)
cd clothing-store
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 2 - Supplier (port 8081)
cd clothing-store-supplier
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Frontend Setup

Open a third terminal:

```bash
# Navigate to frontend directory
cd clothing_store_java/frontend

# Install dependencies (first time only)
npm install

# Run development server
npm run dev
```

The application will be available at:
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080
- **Supplier API**: http://localhost:8081
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console** (dev): http://localhost:8080/h2-console

## 💻 Windows Commands

If you're on Windows, use `mvnw.cmd` instead of `./mvnw`:

```cmd
REM Terminal 1 - Main Store
cd clothing-store
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

REM Terminal 2 - Supplier
cd clothing-store-supplier
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

REM Terminal 3 - Frontend
cd frontend
npm run dev
```

## 📚 API Documentation

Once the backend is running, access the Swagger UI:
- **Main Store**: http://localhost:8080/swagger-ui.html
- **Supplier**: http://localhost:8081/swagger-ui.html

## 🔍 Search API

The application includes a full-text search feature:

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/search?query=nike` | Search clothes by query |
| GET | `/api/search?query=nike&sex=MALE&type=TSHIRT` | Search with filters |
| POST | `/api/search` | Search with JSON body |
| GET | `/api/search/all` | Get all searchable items |
| GET | `/api/search/suggestions?query=ni` | Get search suggestions |

### Search Filters

- `query` - Search text (searches in title, description, type, brand)
- `sex` - Filter by sex (MALE, FEMALE, UNISEX)
- `type` - Filter by type (TSHIRT, HOODIE, JEANS, etc.)
- `minPrice` - Minimum price filter
- `maxPrice` - Maximum price filter
- `page` - Page number (default: 0)
- `size` - Page size (default: 20)

### Example Request

```bash
# Search for "nike" t-shirts
curl "http://localhost:8080/api/search?query=nike&sex=MALE&type=TSHIRT"

# Search with price range
curl "http://localhost:8080/api/search?query=shirt&minPrice=20&maxPrice=100"
```

### Elasticsearch (Optional)

For production use with Elasticsearch:

```bash
# Start Elasticsearch with Docker
docker run -d --name elasticsearch \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  elasticsearch:8.12.0

# Run with elasticsearch profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,elasticsearch
```

## 🔐 Authentication

The API uses JWT-based authentication:

1. **Register**: `POST /api/auth/register`
2. **Login**: `POST /api/auth/login`
3. **Refresh Token**: `POST /api/auth/refresh`
4. **Logout**: `POST /api/auth/logout`

Include the JWT token in the `Authorization` header:
```
Authorization: Bearer <your-token>
```

## 🗄️ Database

The application uses **H2 embedded database** in file mode:
- **Dev**: Data stored in `./data/clothing_store_dev`
- **Test**: In-memory H2
- **Prod**: Configurable via environment variables

**H2 Console** (dev only): http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/clothing_store_dev`
- Username: `sa`
- Password: (empty)

## 📁 Project Structure

```
clothing_store_java/
├── clothing-store/                   # Main store backend
│   ├── src/main/java/com/clothingstore/
│   │   ├── config/                   # Security, OpenAPI config
│   │   ├── controller/               # REST controllers
│   │   ├── service/                  # Business logic
│   │   ├── repository/               # Data access
│   │   ├── entity/                   # JPA entities
│   │   ├── dto/                      # Request/Response DTOs
│   │   ├── security/                 # JWT auth
│   │   └── exception/                # Error handling
│   ├── src/main/resources/
│   │   ├── db/migration/             # Flyway migrations
│   │   └── application.yml           # Config
│   └── pom.xml
│
├── clothing-store-supplier/          # Supplier backend
│   └── src/main/java/com/clothingstore/supplier/
│
├── frontend/                         # React SPA
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── hooks/
│   └── package.json
│
├── README.md
└── .gitignore
```

## 🔧 Configuration

### Profiles

- `dev` - Development with H2 console, debug logging
- `test` - In-memory database for tests
- `prod` - Production settings

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `JWT_SECRET` | JWT signing secret | dev-secret-key... |
| `DB_USERNAME` | Database username | sa |
| `DB_PASSWORD` | Database password | (empty) |
| `CORS_ALLOWED_ORIGINS` | Allowed CORS origins | http://localhost:5173 |
| `SUPPLIER_API_KEY` | Supplier API key | supplier-dev-key |

## 🧪 Testing

```bash
# Run all tests for main store
cd clothing-store
./mvnw test

# Run specific test class
./mvnw test -Dtest=AuthenticationIntegrationTest

# Run with coverage
./mvnw test jacoco:report
```

## 🔐 Default Credentials

After first run with `dev` profile, admin account is created:
- **Email**: admin@store.com
- **Password**: admin123

## 🛠️ Tech Stack

### Backend
- Spring Boot 3.2.x
- Spring Security 6 (JWT)
- Spring Data JPA
- Flyway (migrations)
- H2 Database
- MapStruct (DTO mapping)
- Lombok
- OpenAPI/Swagger

### Frontend
- React 18
- TypeScript
- Vite
- Mantine UI
- React Query
- React Router

## 📄 License

MIT
