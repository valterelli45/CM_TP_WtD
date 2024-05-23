const Trip = require("../models/trip.model.js");

// Create and Save a new Trip
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  // Create a Trip
  const trip = new Trip({
    name: req.body.name,
    description: req.body.description,
    date_created: req.body.date_created,
    date_of_the_trip: req.body.date_of_the_trip,
    end_of_the_trip: req.body.end_of_the_trip,
    classification: req.body.classification
  });

  // Save Trip in the database
  Trip.create(trip, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Trip."
      });
    else res.send(data);
  });
};

// Retrieve all Trips from the database.
exports.findAll = (req, res) => {
  Trip.getAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving trips."
      });
    else res.send(data);
  });
};

// Find a single Trip with a tripId
exports.findOne = (req, res) => {
  Trip.findById(req.params.tripId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found Trip with id ${req.params.tripId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving Trip with id " + req.params.tripId
        });
      }
    } else res.send(data);
  });
};

// Update a Trip identified by the tripId in the request
exports.update = (req, res) => {
  // Validate Request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  Trip.updateById(
    req.params.tripId,
    new Trip(req.body),
    (err, data) => {
      if (err) {
        if (err.kind === "not_found") {
          res.status(404).send({
            message: `Not found Trip with id ${req.params.tripId}.`
          });
        } else {
          res.status(500).send({
            message: "Error updating Trip with id " + req.params.tripId
          });
        }
      } else res.send(data);
    }
  );
};

// Delete a Trip with the specified tripId in the request
exports.delete = (req, res) => {
  Trip.remove(req.params.tripId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found Trip with id ${req.params.tripId}.`
        });
      } else {
        res.status(500).send({
          message: "Could not delete Trip with id " + req.params.tripId
        });
      }
    } else res.send({ message: `Trip was deleted successfully!` });
  });
};

// Delete all Trips from the database.
exports.deleteAll = (req, res) => {
  Trip.removeAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all trips."
      });
    else res.send({ message: `All Trips were deleted successfully!` });
  });
};
