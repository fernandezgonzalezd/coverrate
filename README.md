
## Instructions to run.

```
 mvn clean package
```

```
 docker build -t coverrate-docker .
```

```
  docker run -p 8080:8080 coverrate-docker 
```

## How to use it

* Point to
```
  http://localhost:8080/uploadMultipleFiles
```

* Example
```
curl -X POST http://localhost:8080/uploadMultipleFiles -F 'files=@filePath_1' -F 'files=@filePath_2' -H 'content-type: multipart/form-data' >> out.zip
```


