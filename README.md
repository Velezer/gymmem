Untuk curl cukup dari file curl.rest, sudah urut dari atas.

```
mvn install
```

```
docker build . -t gymmem:latest
```

don't forget to execute this sql
```
./src/main/resources/data.sql
```