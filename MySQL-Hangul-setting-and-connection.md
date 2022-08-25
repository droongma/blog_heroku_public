# 1. MySQL Hangul Setting
Add the following lines to each section(client, mysql, mysqld) and restart MySQL:
```
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
init_connect='SET collation_connection = utf8_general_ci'
character-set-server=utf8
```

# 2. Check Hangul Setting
```
show variables like 'c%';
```

# 3. Create User, Grant Permissions, and Create DB
```
-- 유저이름@아이피주소
create user 'cos'@'%' identified by 'cos1234';
-- ON DB이름.테이블명
-- TO 유저이름@아이피주소
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
CREATE DATABASE blog CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
use blog;
```

# 4. Connect MySQL to SpringBoot
In **src/main/resources/application.yml** file, add following lines:
FYI, format of yml file looks like JSON. This file can specify all settings of Spring.
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul
    username: cos
    password: cos1234
```
