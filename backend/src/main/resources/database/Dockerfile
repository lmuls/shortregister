FROM postgres
ENV POSTGRES_DB shortdb
ENV POSTGRES_USER shortuser
ENV POSTGRES_PASSWORD shortPASS123

# To be used when instantiating db
COPY my_files/custom_init.sql /docker-entrypoint-initdb.d/