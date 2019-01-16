# Inspiration

https://www.diabloprogress.com/

### Diablo API

- https://develop.battle.net/documentation/guides/getting-started
- https://develop.battle.net/documentation/api-reference/diablo-3-community-api
- https://develop.battle.net/documentation/api-reference/diablo-3-game-data-api


### Require

- docker
- docker-compose

### Develpoment

```
 cd compose-dev
 run.sh
 
```

## setup run


- Main class com.kldev.d3.Main
- Program agruments: server ./src/main/config.yml


## app command - import old forum
java -jar app.jar forum --jdbc="jdbc:mysql://mysql-service:3306/d3hard?useSSL=false&user=d3hard&password=d3hard" --url="http://localhost:9000"

## app command - update all
java -jar app.jar update --jdbc="jdbc:postgresql://postgres-service:5432/d3hard?useSSL=false&user=d3hard&password=d3hard" --url="http://localhost:9000"


## hosts files

vim /etc/hosts
```
127.0.0.1   mysql-service
127.0.0.1   postgres-service
```

## dump db (from runing docker iamge)

```
docker exec CONTAINER_ID_OR_NAME pg_dump -d d3hard -U d3hard -Fc  > d3hard.sql
```