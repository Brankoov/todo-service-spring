# Todo-service

En enkel webbtjänst där användare kan 
registrera sig och skapa 
att-göra-listor (todos) med titel och 
beskrivning. Varje todo kan markeras som 
klar/ej klar. Alla todos är kopplade till 
inloggad användare.

## Hur man startar projektet

**Förkrav:** Java 21 och Docker Desktop.

1) Starta databasen (MongoDB):

docker compose up -d mongo

2) Starta applikationen 

./gradlew bootRun 

(Alternativt kör via IntelliJ “Run” om 
spring.data.mongodb.uri redan är satt i 
din run-konfiguration.)

Hosting (Render):
Prod-basadress:
https://todo-service-spring-docker.onrender.com

## Exempel på API-Anrop

curl -s http://localhost:8080/actuator/health
# eller mot prod:
curl -s https://todo-service-spring-docker.onrender.com/actuator/health


(Jag föredrar att testa i Postman så kommande
API anrop är till Postman, men går
bra att köra via terminal också)
----------------------------------------
http://localhost:8080/auth/register

**NO AUTH**
{
"username": "branknado",
"password": "password"
}
------------------------------------
**BASIC AUTH(With the created user)**
*POST*
http://localhost:8080/todos
- Body raw
- {
  "title": "Eat cake",
  "description": "Eat some cake lol",
  "completed": false
  }
-------------------------------------
**BASIC AUTH**
*GET*
http://localhost:8080/todos
GET ALL TODOS
--------------------------------------

## Kort reflektion (Branko)

Jag har fått en större förståelse för 
webbtjänster, och verktygen runt dem – 
t.ex. Docker för databasen och Postman 
för att testa API:t – och blivit mer 
bekant med koder och klasser som brukar 
ingå i en sådan app.

Versionshanteringen har också lyft: 
jag lärde mig jobba från en develop-branch, 
brancha ut för features/fixes och alltid 
PR:a mot develop för att skydda main. 
Det kändes både logiskt och kul 
att implementera.

Samarbetet tycker jag har gått bra, vi 
har haft möten vid behov och planerat 
några dagar fram och sedan oftast jobbat 
självständigt med uppgifter vi har valt 
att göra efter dessa möten.
Vi har också hjälpt varandra vid behov 
via teams där vi har fått jobba 
tillsammans och felsöka samt lösa 
problem tillsammans. 






