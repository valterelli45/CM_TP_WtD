module.exports = app => {
  const trips = require("../controllers/trip.controller.js");

  // Create a new Trip
  app.post("/trips", trips.create);

  // Retrieve all Trips
  app.get("/trips", trips.findAll);

  // Retrieve a single Trip with tripId
  app.get("/trips/:tripId", trips.findOne);

  // Update a Trip with tripId
  app.put("/trips/:tripId", trips.update);

  // Delete a Trip with tripId
  app.delete("/trips/:tripId", trips.delete);

  // Delete all Trips
  app.delete("/trips", trips.deleteAll);
};
