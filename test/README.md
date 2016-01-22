## Bug Test

Too many connection at MySQL


Compare it with PHP, node-mysql, Java (mysql jdbc), HikariCP, Play!Slick 

PHP, node-mysql, Java (JDBC) all fine with connection (conn.close required in Java)


The reason is, it used connection pool, the size of pool is numThreads * 5, so if the numThreads is 10, it will keep 50 connection alive, that quite a lot for a desktop dev Linux distro, also, when we run it with `activiator ~run`, it will reload the app and re-compile it for any change, this time, the connection will be accumulated as well, so sooner or later, mysql will reject the connection, there are a few way to avoid this, for dev, set numThreads to 1, is ok, and queueSize to be 100 or whatever..

For mysql server, we can add idle time in my.cnf, to force it close the connection after certain time.
