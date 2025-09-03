# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build and Package
```bash
# Compile Clojure source to .class files
lein compile

# Package the project into a JAR
lein jar

# Package with all dependencies
lein uberjar

# Deploy to Clojars repository
lein deploy
```

### Testing and Linting
```bash
# Run tests
lein test

# Run specific test namespace
lein test namespace.name

# Lint with Eastwood
lein eastwood

# Lint with clj-kondo
lein clj-kondo

# Check for outdated dependencies
lein ancient
```

### Development
```bash
# Start a REPL session
lein repl

# Clean build artifacts
lein clean

# Check syntax and warn on reflection
lein check
```

## Architecture

This is a Clojure library providing common dependencies, functions, and schema definitions for CyVerse services using metosin/compojure-api for Swagger-documented REST endpoints.

### Core Components

1. **Schema Definitions** (`src/common_swagger_api/schema/`)
   - Provides Schema-based type definitions for API endpoints
   - Organized by domain: apps, data, metadata, tools, analyses, etc.
   - Each domain has sub-modules for specific functionality

2. **Malli Integration** (`src/common_swagger_api/malli.clj`)
   - New Malli-based schema definitions (currently being introduced)
   - Provides `StatusResponse` schema and basic validators like `NonBlankString`

3. **Route Utilities** (`src/common_swagger_api/routes.clj`)
   - Custom Compojure API metadata handlers
   - Provides `:description-file` and `:deprecated` parameter restructuring
   - Helper for generating delegation blocks in API documentation

4. **Coercion** (`src/common_swagger_api/coerce.clj`)
   - Custom coercion utilities for API parameters

### Schema Organization

The schema definitions follow a hierarchical structure:
- Top-level schemas in `schema.clj` provide base functionality and imports from compojure-api
- Domain-specific schemas are organized in subdirectories
- Admin-specific schemas are nested under their respective domains (e.g., `apps/admin/`)

### Important Notes

- This is a library project with no application entry point
- Currently uses both Schema (primary) and Malli (being introduced) for validation
- Deployed to Clojars for use by other CyVerse services
- Jenkins CI/CD pipeline handles testing and deployment via Docker