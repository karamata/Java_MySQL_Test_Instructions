docker run --name wallethub-dev -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=wallethubdb -p 3306:3306 -d mysql:5.7