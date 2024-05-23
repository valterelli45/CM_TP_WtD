const UserTrip = require("../models/user_trip.model.js");

// Create and Save a new UserTrip
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  // Create a UserTrip
  const userTrip = new UserTrip({
    user_id: req.body.user_id,
    trip_id: req.body.trip_id
  });

  // Save UserTrip in the database
  UserTrip.create(userTrip, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the UserTrip."
      });
    else res.send(data);
  });
};

// Retrieve all UserTrips from the database.
exports.findAll = (req, res) => {
  UserTrip.getAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving user trips."
      });
    else res.send(data);
  });
};

// Find a single UserTrip with a userTripId
exports.findOne = (req, res) => {
  UserTrip.findById(req.params.userTripId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found UserTrip with id ${req.params.userTripId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving UserTrip with id " + req.params.userTripId
        });
      }
    } else res.send(data);
  });
};

// Update a UserTrip identified by the userTripId in the request
exports.update = (req, res) => {
  // Validate Request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  UserTrip.updateById(
    req.params.userTripId,
    new UserTrip(req.body),
    (err, data) => {
      if (err) {
        if (err.kind === "not_found") {
          res.status(404).send({
            message: `Not found UserTrip with id ${req.params.userTripId}.`
          });
        } else {
          res.status(500).send({
            message: "Error updating UserTrip with id " + req.params.userTripId
          });
        }
      } else res.send(data);
    }
  );
};

// Delete a UserTrip with the specified userTripId in the request
exports.delete = (req, res) => {
  UserTrip.remove(req.params.userTripId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found UserTrip with id ${req.params.userTripId}.`
        });
      } else {
        res.status(500).send({
          message: "Could not delete UserTrip with id " + req.params.userTripId
        });
      }
    } else res.send({ message: `UserTrip was deleted successfully!` });
  });
};


// Delete all UserTrips from the database.
exports.deleteAll = (req, res) => {
  UserTrip.removeAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all user trips."
      });
    else res.send({ message: `All UserTrips were deleted successfully!` });
  });
};
