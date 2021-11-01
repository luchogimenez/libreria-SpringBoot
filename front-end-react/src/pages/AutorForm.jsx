import React, { useState } from "react";
import { useHistory } from "react-router";

export default function AutorForm() {

  const axios = require("axios");
  const history = useHistory();

  const [autor, setAutor] = useState({
    id: "",
    nombre: "",
    alta: false,
  });

  const handleChange = (e) => {
    setAutor({ ...autor, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:8080/autores/guardarAutor/", autor)
      .then((res) => {
        history.push("/autores/mostrar");
        console.log(res.status);
      });
  };

  const handleClick = () => {
    history.push("/autores/mostrar");
  } 

  return (
    <div>
      <div>
        <h1>Crear Autor</h1>
        <form className="mt-3" onSubmit={handleSubmit}>
          <label>Nombre</label>
          <input
            className="form-control"
            name="nombre"
            type="text"
            defaultValue=""
            onChange={handleChange}
          ></input>

          <button type="submit" className="btn btn-primary mt-3 col-2 me-3">
            Guardar
          </button>
          <button type="button" className=" btn btn-danger mt-3 col-2" onClick={handleClick}>
            Cancelar
          </button>
        </form>
      </div>
    </div>
  );
}
