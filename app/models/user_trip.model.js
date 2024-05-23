const sql = require("./db.js");

// Constructor
const UserTrip = function(userTrip) {
  this.user_id = userTrip.user_id;
  this.trip_id = userTrip.trip_id;
};

UserTrip.create = (newUserTrip, result) => {
  sql.query("INSERT INTO users_trips SET ?", newUserTrip, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created user trip: ", { id: res.insertId, ...newUserTrip });
    result(null, { id: res.insertId, ...newUserTrip });
  });
};

UserTrip.findById = (id, result) => {
  sql.query(`SELECT * FROM users_trips WHERE id = ${id}`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found user trip: ", res[0]);
      result(null, res[0]);
      return;
    }

    result({ kind: "not_found" }, null);
  });
};

UserTrip.getAll = (result) => {
  sql.query("SELECT * FROM users_trips", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("user trips: ", res);
    result(null, res);
  });
};

UserTrip.updateById = (id, userTrip, result) => {
  sql.query(
    "UPDATE users_trips SET user_id = ?, trip_id = ? WHERE id = ?",
    [userTrip.user_id, userTrip.trip_id, id],
    (err, res) => {
      if (err) {
        console.log("error: ", err);
        result(null, err);
        return;
      }

      if (res.affectedRows == 0) {
        result({ kind: "not_found" }, null);
        return;
      }

      console.log("updated user trip: ", { id: id, ...userTrip });
      result(null, { id: id, ...userTrip });
    }
  );
};

UserTrip.remove = (id, result) => {
  sql.query("DELETE FROM users_trips WHERE id = ?", id, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    if (res.affectedRows == 0) {
      result({ kind: "not_found" }, null);
      return;
    }

    console.log("deleted user trip with id: ", id);
    result(null, res);
  });
};

UserTrip.removeAll = (result) => {
  sql.query("DELETE FROM users_trips", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log(`deleted ${res.affectedRows} user trips`);
    result(null, res);
  });
};

module.exports = UserTrip;
