var mysql      = require('mysql');
var index = 0;

function test () {

	var connection = mysql.createConnection({
	  host     : '192.168.1.90',
	  user     : 'root',
	  password : 'ddddddd',
	  database : 'connectapp'
	});

	connection.connect();

	connection.query('SELECT * FROM post', function(err, rows, fields) {
	  if (err) throw err;

	  console.log(index++);
	  console.log('The solution is: ', rows[0].title);
	});

	//connection.end();
}

for (var i = 0; i < 30; i++) {

	test();
}