
<?php
// Connecting, selecting database

function test() {
	$link = mysql_connect('192.168.1.90', 'root', 'ddddddd')
	    or exit('Could not connect: ' . mysql_error());
	echo 'Connected successfully';

	mysql_select_db('connectapp') or exit('Could not select database');

	$query = 'SELECT * FROM post';
	$result = mysql_query($query) or exit('Query failed: ' . mysql_error());

	while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) {
	    foreach ($line as $col_value) {
	        echo "$col_value";
	    }
	}

	mysql_free_result($result);

	//mysql_close($link);
}

for ($i = 0; $i < 100; ++$i) {

	//for($j = 0; $j < 10000000; ++$j);
	test();
}

?>