
# Launch this project by executing the following cmd (avoir l'engine de docker demarrer)

- Pour un premier lancement : docker-compose up --build (sinon : docker-compose up)
- Une fois lancer acceder à la doc via l'URL :  http://localhost:8080/swagger-ui/index.html


# Useful resources
- Swagger integration
  https://medium.com/@berktorun.dev/swagger-like-a-pro-with-spring-boot-3-and-java-17-49eed0ce1d2f
- docker config with spring boot and mysql
  https://www.youtube.com/watch?v=-ekBqIvAGY4
- Remettre tous vos conteneur a plat (Attention, les données sont perdues, et tous vos contenurs hors projet sont supprimés)
  docker rmi -f $(docker images -q)
- 