require("dotenv").config();

module.exports = {
  port: process.env.PORT || 3000,
  baseUrl: process.env.BASE_URL || "http://localhost:3000",

  //   Firebase
  firebaseApiKey: process.env.FIREBASE_API_KEY,
  firebaseAuthDomain: process.env.FIREBASE_AUTH_DOMAIN,
  firebaseProjectId: process.env.FIREBASE_PROJECT_ID,
  firebaseStorageBucket: process.env.FIREBASE_STORAGE_BUCKET,
  firebaseMessagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
  firebaseAppId: process.env.FIREBASE_APP_ID,

  // JWT
  jwtSecret: process.env.JWT_SECRET,

  // Nodemailer
  nodemailerEmailUser: process.env.NODEMAILER_EMAIL_USER,
  nodemailerEmailPass: process.env.NODEMAILER_EMAIL_PASS,
};
