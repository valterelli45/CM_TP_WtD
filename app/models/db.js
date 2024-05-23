const mysql = require('mysql');
const DBConfig = require('../config/db.config.js');

// Create a connection to the database
const connection = mysql.createConnection({
  host: DBConfig.DBSERVER,
  user: DBConfig.DBUSER,
  password: DBConfig.DBPASS,
  database: DBConfig.DBNAME
});

// Open the MySQL connection
connection.connect(error => {
  if (error) throw error;
  console.log('Successfully connected to the database.');
});

module.exports = connection;
