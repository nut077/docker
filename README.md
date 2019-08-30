docker pull mysql:8.0.17<br>
docker run --name schooldb -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=school -p 3306:3306 -d mysql:8.0.17<br><br>cok
docker pull phpmyadmin/phpmyadmin:4.8<br>
docker run --name schooldb-admin -d --link schooldb:db -p 8081:80 phpmyadmin/phpmyadmin:4.8<br><br>
docker ps ดู containerid ของ container name = schooldb<br>
docker exec -it #containerid# bash<br>
mysql -uroot -p123<br>
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';

