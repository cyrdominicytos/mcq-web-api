


services:
api:
build: .
restart: always
ports:
- 8080:8080
container_name: mcq_api
mysql:
image: 'mysql:latest'
environment:
- 'MYSQL_DATABASE=mcq_api_db'
- 'MYSQL_PASSWORD=secret'
- 'MYSQL_ROOT_PASSWORD=verysecret'
- 'MYSQL_USER=root'
ports:
- '3306'
        
