# Voting-system

### Installation requirements:
1. JDK 14
2. Maven

### How to start:
1. mvn clean package
2. mvn cargo:run

### Example usage:

#### Get all restaurants
`curl localhost:8080/voting-system/rest/admin/restaurants`

#### Get restaurant 100000
`curl localhost:8080/voting-system/rest/admin/restaurants/100000`

#### Create restaurant
`curl -X POST -d '{"name" : "NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting-system/rest/admin/restaurants`

#### Update restaurant
`curl -X PUT -d '{"name" : "NewName"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting-system/rest/admin/restaurants/100000`

#### Delete restaurant 100000
`curl -X DELETE localhost:8080/voting-system/rest/admin/restaurants/100000`

#### Get all lunches
`curl localhost:8080/voting-system/rest/admin/restaurants/lunches`

#### Create lunch for today for restaurant 100000
`curl -X POST localhost:8080/voting-system/rest/admin/restaurants/100000/lunches`

#### Add dish 100010 to lunch (created in previous step)
`curl -X PUT localhost:8080/voting-system/rest/admin/restaurants/100000/lunches/100020/dish/100010`

#### Get all restaurants that offer lunch for today
`curl localhost:8080/voting-system/rest/voting/restaurants`

#### Get history of votes
`curl localhost:8080/voting-system/rest/voting/votes`

#### Vote for restaurant 100000
`curl localhost:8080/voting-system/rest/voting/votes/100000`
