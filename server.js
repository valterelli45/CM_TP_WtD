const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

// Create an Express app
const app = express();

// Configure the port
const PORT = process.env.PORT || 3000;

// Middleware configuration
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Test route
app.get('/', (req, res) => {
  res.json({ message: "Welcome to the Trip Management API" });
});

// Import and use the routes
require('./app/routes/user.routes.js')(app);
require('./app/routes/trip.routes.js')(app);
require('./app/routes/local.routes.js')(app);
require('./app/routes/local_img.routes.js')(app);
require('./app/routes/user_trip.routes.js')(app);
require('./app/routes/trip_local.routes.js')(app);

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});
