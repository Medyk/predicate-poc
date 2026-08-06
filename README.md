# Custom models

```bash
# 
# custom model in JSON
curl -vk -H "Accept: application/vnd.book.v1+json" http://localhost:8080/

# custom model in XML
curl -vk -H "Accept: application/vnd.book.v1+xml" http://localhost:8080/
```

# Client model negotiation

```bash
# negotiate version
curl -vk -H "Accept: application/vnd.book.v2+json,application/vnd.book.v1+json;q=0.9" http://localhost:8080/

> Accept: application/vnd.book.v2+json,application/vnd.book.v1+json;q=0.9
< HTTP/1.1 200 
< Content-Type: application/vnd.book.v1+json
```