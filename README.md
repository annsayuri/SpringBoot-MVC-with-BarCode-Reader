# Product CRUD — Spring MVC Demo

A minimal Spring Boot app demonstrating classic **Controller → Service → Repository** MVC layering, backed by Supabase (Postgres). `Product` and `Supplier resources`, full CRUD, plus a barcode-scanner-friendly search endpoint.

```
[Browser page + USB scanner]
        |  JSON over HTTP
        v
  Controller  (@RestController - HTTP only, no business logic)
        |
     Service   (business rules)
        |
   Repository  (JpaRepository - DB access)
        |
     Supabase (Postgres)
```

## 1. Prerequisites

### Install Java 17

Spring Boot 3.x requires Java 17 or newer.

- **Windows**: download the installer from [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=17) (choose JDK 17, `.msi` for Windows), run it, and make sure "Add to PATH" and "Set JAVA_HOME" are checked during install.
- Alternatively with `winget`:
  ```powershell
  winget install EclipseAdoptium.Temurin.17.JDK
  ```

Verify it worked (open a **new** terminal so PATH changes apply):
```powershell
java -version
```
You should see something like `openjdk version "17.0.x"`.

### Install Maven

- **Windows**: download the binary zip from [Maven's download page](https://maven.apache.org/download.cgi), extract it (e.g. to `C:\Program Files\Maven`), then add `<extract-path>\bin` to your `PATH` environment variable (System Properties → Environment Variables → edit `Path`).
- Alternatively with `winget`:
  ```powershell
  winget install Apache.Maven
  ```

Verify:
```powershell
mvn -v
```
You should see the Maven version and the Java version it's using (should match step above).

### Create a Supabase project

1. Sign up / log in at [supabase.com](https://supabase.com) and create a new project.
2. Once created, click **Connect** (top of the project dashboard) → **Connection String** → **URI** tab → select **Session pooler** (this works over plain IPv4, unlike the direct connection which is IPv6-only on many networks).
3. You'll get something like:
   ```
   postgresql://postgres.<project-ref>:[YOUR-PASSWORD]@aws-0-<region>.pooler.supabase.com:5432/postgres
   ```
   Note the three pieces you'll need: host+port, username (`postgres.<project-ref>`), and your database password.

## 2. Clone and set up the project

```powershell
git clone https://github.com/annsayuri/SpringBoot-MVC-with-BarCode-Reader.git
cd SpringBoot-MVC-with-BarCode-Reader
```

The database credentials are **not** stored in the repo — they're read from environment variables at startup (see [application.properties](src/main/resources/application.properties)). Set them in your terminal session before running the app:

```powershell
$env:SUPABASE_DB_URL="jdbc:postgresql://aws-0-<region>.pooler.supabase.com:5432/postgres"
$env:SUPABASE_DB_USERNAME="postgres.<project-ref>"
$env:SUPABASE_DB_PASSWORD="<your-db-password>"
```

Replace `<region>`, `<project-ref>`, and `<your-db-password>` with the values from your Supabase Connect dialog. These $env: variables only last for the current terminal session — you'll need to re-set them if you open a new window (or set them permanently via System Properties → Environment Variables).

## 3. Run it

```powershell
mvn spring-boot:run
```

Once started, open your browser:

 - Main Web App: `http://localhost:8081`
 - Suppliers Management: `http://localhost:8081/suppliers`


You'll see a page with a barcode-scan input, an add/edit form, and a product table. A USB barcode scanner behaves like a keyboard (types the code, then presses Enter), so clicking into the scan field and scanning "just works."

## 4. API endpoints

### 📦 Product Endpoints
| Method | Path | Description |
|---|---|---|
| POST | `/api/products` | Create a product |
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/barcode/{barcode}` | Get product by barcode (used by scanner) |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### 🏬 Supplier Endpoints (UI Routes)
| Method | Path | Description |
|---|---|---|
| GET | `/suppliers` | View all suppliers list |
| GET | `/suppliers/new` | Render form to create a new supplier |
| POST | `/suppliers/save` | Save/Update supplier details |
| GET | `/suppliers/edit/{id}` | Render form to edit an existing supplier |
| GET | `/suppliers/delete/{id}` | Delete a supplier by ID |

## 5. Project structure

```
src/main/java/com/bci/productcrud/
├── controller/
│   ├── ProductController.java       -> Handles Product HTTP routes & API endpoints
│   └── SupplierController.java      -> Handles Supplier CRUD routes and views
├── exception/
│   ├── DuplicateBarcodeException.java
│   ├── GlobalExceptionHandler.java  -> Centralized error handling (@RestControllerAdvice)
│   └── ProductNotFoundException.java
├── model/
│   ├── Product.java                 -> Product JPA Entity (Relationship with Supplier)
│   └── Supplier.java                -> Supplier JPA Entity
├── repository/
│   ├── ProductRepository.java       -> JpaRepository interface for Product
│   └── SupplierRepository.java      -> JpaRepository interface for Supplier
├── service/
│   ├── ProductService.java          -> Product service interface
│   ├── ProductServiceImpl.java      -> Product business logic implementation
│   ├── SupplierService.java         -> Supplier service interface
│   └── SupplierServiceImpl.java     -> Supplier business logic implementation
└── ProductCrudApplication.java      -> Spring Boot main entry point

src/main/resources/
├── application.properties           -> Configuration (DB properties, port 8081)
├── static/                          -> Frontend assets
│   ├── app.js                       -> Barcode scanner & JS logic
│   ├── index.html                   -> Single-page web UI
│   └── style.css                    -> CSS styling
└── templates/                       -> Thymeleaf UI templates
    └── suppliers/
        ├── form.html                -> Add/Edit Supplier form
        └── list.html                -> Supplier list view          
```

Request flow for a barcode scan: `app.js` → `GET /api/products/barcode/{code}` → `ProductController` → `ProductService` → `ProductRepository` → Supabase, and the `Product` JSON flows back the same path in reverse.
