# station3 dabang 면접 과제
### h2 db 사용
```
docker run -d -p 1521:1521 -p 8090:81 -v /path/to/local/data_dir:/opt/h2-data -e H2_OPTIONS='-ifNotExists' --name=dabang oscarfonts/h2:1.4.199
```

### api 명세(Swagger3)
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)