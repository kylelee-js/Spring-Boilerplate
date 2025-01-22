# Use the official PostgreSQL image
FROM postgres:latest

# Set environment variables for the database configuration
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=boilerplate

# Create a directory for the database files (this is where the volume will mount)
VOLUME /var/lib/postgresql/data

# Expose PostgreSQL port
EXPOSE 5432

# Default command to run PostgreSQL
CMD ["postgres"]