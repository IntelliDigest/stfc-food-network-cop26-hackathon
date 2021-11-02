FROM postgres:9.4
COPY sql/create_tables.sql /docker-entrypoint-initdb.d/
COPY ../../data/agronomic/*.csv /docker-entrypoint-initdb.d
