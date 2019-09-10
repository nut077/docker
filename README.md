in application.yml change spring.datasource.url from jdbc:mysql://localhost:3306/school to jdbc:mysql://schooldb:3306/school<br>
mvn clean install -DskipTests<br>
docker build -t docker-school .<br>
docker network create school-network<br>
docker pull mysql:8.0.17<br>
docker run --name schooldb --network school-network -e MYSQL_USER=freedom -e MYSQL_PASSWORD=123 -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=school -p 3306:3306 -d --restart=always mysql:8.0.17<br><br>
docker run --name school --network school-network -p 8080:8080 -d --restart=always docker-school<br>
docker pull phpmyadmin/phpmyadmin:4.8<br>
docker run --name schooldb-admin -d --link schooldb:db -p 8081:80 phpmyadmin/phpmyadmin:4.8<br><br>
docker ps ดู containerid ของ container name = schooldb<br>
docker exec -it #containerid# bash<br>
mysql -uroot -p123<br>
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';<br>
set jasypt<br>
cmd C:\Users\%user%\.m2\repository\org\jasypt\jasypt\1.9.2<br>
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="123" password=secretkey algorithm=PBEWithMD5AndDES

