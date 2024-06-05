
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
- Intégration de ANTLR4
  https://codevomit.wordpress.com/2015/03/15/antlr4-project-with-maven-tutorial-episode-1/
- https://www.youtube.com/watch?v=6uF1Nxo2xjk&list=PL5dxAmCmjv_4FGYtGzcvBeoS-BobRTJLq&index=5&ab_channel=JackieWang