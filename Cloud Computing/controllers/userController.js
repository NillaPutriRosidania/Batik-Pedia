const { body, validationResult, matchedData } = require("express-validator");
const { createUser, loginUser } = require("../services/userService");
const { query, collection, where, getDocs } = require("firebase/firestore");
const { db } = require("../config/firebase");
const { validationError } = require("../helpers/validationError");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const config = require("../config/config");

const validateRegister = [
  body("nama")
    .isString()
    .withMessage("Masukkan nama yang valid")
    .notEmpty()
    .withMessage("Nama tidak boleh kosong"),

  body("email")
    .custom(async (value) => {
      const q = query(collection(db, "users"), where("email", "==", value));
      const snapshot = await getDocs(q);

      if (!snapshot.empty) {
        throw new Error("Email sudah terdaftar");
      }

      return true;
    })
    .isEmail()
    .withMessage("Harap masukkan email yang valid")
    .notEmpty()
    .withMessage("Email tidak boleh kosong"),

  body("password")
    .isLength({ min: 3 })
    .withMessage("Password harus memiliki minimal 3 karakter")
    .notEmpty()
    .withMessage("Password tidak boleh kosong"),
];

const register = async (req, res, next) => {
  const errors = validationResult(req);

  if (!errors.isEmpty()) {
    return res.status(400).json({
      status: "error",
      code: 400,
      errors: validationError(errors.array()),
    });
  }

  const { nama, email, password } = matchedData(req);

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const user = await createUser(nama, email, hashedPassword);

    const token = jwt.sign({ id: user.id }, config.jwtSecret);

    res.status(201).json({
      status: "success",
      code: 201,
      message: "Registrasi berhasil",
      token,
      user,
    });
  } catch (error) {
    next(error);
  }
};

const validateLogin = [
  body("email").notEmpty().withMessage("Email tidak boleh kosong"),

  body("password").notEmpty().withMessage("Password tidak boleh kosong"),
];

const login = async (req, res, next) => {
  const errors = validationResult(req);

  if (!errors.isEmpty()) {
    return res.status(400).json({
      status: "error",
      code: 400,
      errors: validationError(errors.array()),
    });
  }

  const { email, password } = matchedData(req);

  try {
    const user = await loginUser(email, password);

    if (!user) {
      return res
        .status(401)
        .json({ status: "error", code: 401, message: "Akun tidak ditemukan" });
    }

    const token = jwt.sign({ id: user.id }, config.jwtSecret);

    res.status(200).json({
      status: "success",
      code: 200,
      message: "Login berhasil",
      token,
      user,
    });
  } catch (error) {
    next(error);
  }
};

module.exports = {
  register: [...validateRegister, register],
  login: [...validateLogin, login],
};
