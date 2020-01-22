in application.yml change spring.datasource.url from jdbc:mysql://localhost:3306/school to jdbc:mysql://schooldb:3306/school<br>
mvn clean install -DskipTests<br>
docker build -t docker-school .<br>
docker network create school-network<br>
docker pull mysql:8.0<br>
docker run --name schooldb --network school-network -e MYSQL_USER=freedom -e MYSQL_PASSWORD=123 -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=school -e TZ=Asia/Bangkok -p 3308:3306 -d --restart=always mysql:8.0<br><br>
docker run --name school --network school-network -p 8080:8080 -d --restart=always docker-school<br>
docker run --name school --network school-network -p 8080:8080 -d --restart=always -e jasypt.encryptor.password=secretkey docker-school<br>
docker pull phpmyadmin/phpmyadmin:4.8<br>
docker run --name schooldb-admin -d --network school-network -p 8081:80 phpmyadmin/phpmyadmin:4.8<br><br>
docker ps ดู containerid ของ container name = schooldb<br>
docker exec -it #containerid# bash<br>
mysql -uroot -p123<br>
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';<br>
set jasypt<br>
add in pom<br>
```xml
<dependency>
    <groupId>org.jasypt</groupId>
    <artifactId>jasypt</artifactId>
    <version>1.9.2</version>
</dependency>
```
cmd C:\Users\%user%\.m2\repository\org\jasypt\jasypt\1.9.2<br>
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="123" password=secretkey algorithm=PBEWithMD5AndDES<br>
remove org.jasypt in pom<br>
run : -Djasypt.encryptor.password=secretkey ใส่ไว้ใน VM Options
<br><br><br>
other<br>
Environment variables : SPRING_PROFILES_ACTIVE=local<br>
คำสั่งเช็ค docker <br>
docker -H 10.33.44.55:2376 --tls ps<br>
killprocess by port<br>
netstat -ano | findstr :8080<br>
taskkill /PID 3192 /F<br>
<br>run spring boot file bat<br>
@echo off<br>
set prj.name=bpmfacade-service<br>
title %prj.name% build<br>
rem cd ..<br>
rem cd %prj.name%<br>
call mvn clean spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=local"<br>
pause<br>


