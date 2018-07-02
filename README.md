
## notiboy

HTTP API service for the notifications.

notiboy can notify to users through email or slack. 

---

### How to build

#### Packaging

```bash
mvn clean package
```

#### Docker image

```bash
mvn dockerfile:build
```

### How to run

```bash
java -jar notiboy-0.1.jar
```

### How to use APIs

#### 1. Email notification
http://localhost:8080/mail

Method : POST

Request header : Content-Type: application/json

JSON Sample :

```json
{
    "from": "info<info@blurblah.net>",
    "recipients": [
        "blurblah@blurblah.net"
    ],
    "subject": "Hello, notiboy",	
    "content": "Hi"
}
```
#### 2. Slack notification
http://localhost:8080/slack

Method : POST

Request header : Content-Type: application/json

JSON samples #1 :

```json
{
    "channel": "#general",
    "message": "Hello"
}
```

JSON samples #2 (direct message) :

```json
{
    "channel": "@friend1",
    "message": "Hi"
}
```
