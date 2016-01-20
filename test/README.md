## Bug Test

- Too many connection at MySQL, as Play + Slick didn't close connection!!

PHP, node-mysql, Java (mysql jdbc), HikariCP, Play!Slick 

PHP, node-mysql, Java (JDBC) all fine with connection (conn.close required in Java)

Slick do have conn.close in BaseSession, but I don't know how it used..

HiKariCP also very important..
