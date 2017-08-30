# camel-socket
A project about camel orquestrator.

This project is receiving a JSON data like:
{"id": 8, "name": "ABC", "score": 1, "account": "0689"}

Where depending the score value, you will have a different value in the field credit;

# Response Json Example
{"score":1,"name":"ABC","id":8,"credit":20000,"account":"0689"}

# Using
To use this project you need to have this project running and send a hhtp request to localhost:9090/socket or if you preffer, you can change this address in the application.properties.

Until now, this project is working just with a Spring boot API.

#TODO
- In the future, it will receive different kind of APIs
