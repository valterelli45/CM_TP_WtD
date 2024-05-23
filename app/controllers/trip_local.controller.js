const TripLocal = require("../models/trip_local.model.js");

// Create and Save a new TripLocal
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a TripLocal
  const tripLocal = new TripLocal({
    trip_id: req.body.trip_id,
    local_id: req.body.local_id,
    nr_order: req.body.nr_order,
    date_finish: req.body.date_finish,
    classification: req.body.classification
  });

  // Save TripLocal in the database
  TripLocal.create(tripLocal, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the TripLocal."
      });
    else res.send(data);
  });
};

// Retrieve all TripLocals from the database.
exports.findAll = (req, res) => {
  TripLocal.getAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving trip locals."
      });
    else res.send(data);
  });
};

// Find a single TripLocal with a tripLocalId
exports.findOne = (req, res) => {
  TripLocal.findById(req.params.tripLocalId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found TripLocal with id ${req.params.tripLocalId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving TripLocal with id " + req.params.tripLocalId
        });
      }
    } else res.send(data);
  });
};

// Update a TripLocal identified by the tripLocalId in the request
exports.update = (req, res) => {
  // Validate Request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  TripLocal.updateById(
    req.params.tripLocalId,
    new TripLocal(req.body),
    (err, data) => {
      if (err) {
        if (err.kind === "not_found") {
          res.status(404).send({
            message: `Not found TripLocal with id ${req.params.tripLocalId}.`
          });
        } else {
          res.status(500).send({
            message: "Error updating TripLocal with id " + req.params.tripLocalId
          });
        }
      } else res.send(data);
    }
  );
};

// Delete a TripLocal with the specified tripLocalId in the request
exports.delete = (req, res) => {
  TripLocal.remove(req.params.tripLocalId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found TripLocal with id ${req.params.tripLocalId}.`
        });
      } else {
        res.status(500).send({
          message: "Could not delete TripLocal with id " + req.params.tripLocalId
        });
      }
    } else res.send({ message: `TripLocal was deleted successfully!` });
  });
};

// Delete all TripLocals from the database.
exports.deleteAll = (req, res) => {
  TripLocal.removeAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all trip locals."
      });
    else res.send({ message: `All TripLocals were deleted successfully!` });
  });
};
