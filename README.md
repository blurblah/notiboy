
# notiboy

HTTP API service for the notifications.

notiboy can notify to users through email or slack. 

### Prerequisites

1. Mailgun api key : Sign-in to [Mailgun](https://www.mailgun.com/)
and issue a api key

2. Slack oauth access token : Create a slack app
through this [link](https://api.slack.com/apps?new_app=1)
if you don't have it and generate oauth token.

3. Slack app permissions : Should be granted some permissions, **channels:read**,
**chat:write:bot** and **groups:read**.

### How to build

#### 1. Packaging

```bash
mvn clean package
```

#### 2. Docker image

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

JSON sample#1 (basic) :

```json
{
    "channel": "general",
    "message": "Hello"
}
```

JSON sample#2 (support attachments) :
```json
{
    "channel": "test_channel",
    "message": "",
    "attachments": [
    	{
            "fallback": "Reported 2018-07-05 5:52:03 PM",
            "author_name": "Slack Tester",
            "color": "danger",
            "title": "<!date^1530813123^Reported {date_num} {time_secs}|Reported 2018-07-05 5:52:03 PM>",
            "text": "sample message",
            "fields": [
                {
                    "title": "sample_field",
                    "value": "sample_value",
                    "short": false
                }
            ],
            "footer": "Slack API",
            "footer_icon": "https://platform.slack-edge.com/img/default_application_icon.png",
            "ts": 1530813123
        }
    ]
}
```