import React, { useEffect, useState } from "react";
import { useParams } from "react-router";

export default function AutorEditar() {
  const axios = require("axios");
  const { id } = useParams();
  const [autor, setAutor] = useState({
    id:"",  
    nombre:"",
    alta:false
  });
  const obtenerAutor = async () => {
    await axios
      .get(`http://localhost:8080/autores/autorById/${JSON.parse(id)}`)
      .then(function (response) {
        setAutor(response.data);
      });
  };
  useEffect(() => {
    obtenerAutor();
  },[]);

  return (
    <div>
      <h1>Editar Autor</h1>
      <form
        className="mt-3"
        //onSubmit={handleSubmit}
      >
        <label>Nombre</label>
        <input
          className="form-control"
          name="nombre"
          type="text"
          defaultValue={autor.nombre}
          //onChange={handleChange}
        ></input>

        <button type="submit" className="btn btn-primary mt-3 col-2 me-3">
          Guardar
        </button>
        <button
          type="button"
          className=" btn btn-danger mt-3 col-2"
          //onClick={handleClick}
        >
          Cancelar
        </button>
      </form>
    </div>
  );
}
