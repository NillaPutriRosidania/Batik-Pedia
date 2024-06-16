const { Strategy, ExtractJwt } = require("passport-jwt");
const config = require("./config");
const { getDoc, doc } = require("firebase/firestore");
const { db } = require("./firebase");

const options = {
  jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
  secretOrKey: config.jwtSecret,
};

module.exports = (passport) => {
  passport.use(
    new Strategy(options, (jwt_payload, done) => {
      getDoc(doc(db, "users", jwt_payload.id))
        .then((user) => {
          if (user.exists()) {
            return done(null, { id: user.id, ...user.data() });
          }

          return done(null, false);
        })
        .catch((err) => done(err, false));
    })
  );
};
