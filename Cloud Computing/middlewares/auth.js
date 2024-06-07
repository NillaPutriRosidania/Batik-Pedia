const passport = require("passport");

const authenticate = (req, res, next) => {
  passport.authenticate("jwt", { session: false }, (err, user) => {
    if (err || !user) {
      return res
        .status(401)
        .json({ status: "error", code: 401, message: "Unauthorized" });
    }

    req.user = user;
    next();
  })(req, res, next);
};

module.exports = authenticate;
