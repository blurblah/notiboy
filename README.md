
## notiboy

HTTP API service for the notifications.

notiboy can notify to users through email or slack. 

---

### How to build

#### Packaging

mvn clean package

#### Docker image

mvn dockerfile:build

---

### How to run

java -jar notiboy-0.1.jar

---

### How to use APIs

#### 1. Email notification
http://localhost:8080/mail

Method : POST

JSON Sample :

```json
{
    "from":"info<info@blurblah.net>",
    "to":"blurblah@blurblah.net",
    "subject":"Hello, notiboy",	
    "content":"Hi"
}
```
#### 2. Slack notification
http://localhost:8080/slack

Method : POST

Request header : Content-Type: application/json

JSON sample :

```json
{
    "channel": "#general",
    "message": "Hello"
}
```
