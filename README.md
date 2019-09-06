docker network create school-mysql<br>
docker pull mysql:8.0.17<br>
docker run --name schooldb -e MYSQL_USER=freedom -e MYSQL_PASSWORD=123 -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=school -p 3306:3306 -d mysql:8.0.17<br><br>
docker pull phpmyadmin/phpmyadmin:4.8<br>
docker run --name schooldb-admin -d --link schooldb:db -p 8081:80 phpmyadmin/phpmyadmin:4.8<br><br>
docker ps ดู containerid ของ container name = schooldb<br>
docker exec -it #containerid# bash<br>
mysql -uroot -p123<br>
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';<br>
set jasypt<br>
cmd C:\Users\%user%\.m2\repository\org\jasypt\jasypt\1.9.2<br>
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="123" password=secretkey algorithm=PBEWithMD5AndDES

