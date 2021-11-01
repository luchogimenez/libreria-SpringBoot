import React, { useState } from "react";
import { useHistory } from "react-router";

export default function EditorialPagesForm() {
  const history = useHistory();
  const [editorial, setEditorial] = useState({
    nombre: "",
  });
  const hadleInpuntChange = (e) => {
    setEditorial({ ...editorial, [e.target.name]: e.target.value });
  };
  const enviarDatos = (e) => {
    e.preventDefault();
    fetch("http://localhost:8080/autores/insertar", {
      method: "POST",
      mode: "cors",
      cache: "no-cache",
      credentials: "same-origin",
      headers: {
        "Content-Type": "application/json",
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
      body: JSON.stringify(editorial.nombre),
    })
      .then((response) => console.log(response))
      .then(() => history.push("/editoriales/mostrar"));
  };
  return (
    <div>
      <h1>Crear Editorial</h1>
      <form className="row" onSubmit={enviarDatos}>
        <div className="col-md-3">
          <input
            type="text"
            placeholder="Ingrese el Nombre"
            className="form-control"
            name="nombre"
            onChange={hadleInpuntChange}
          />
        </div>
        <div className="col-md-3">
          <button className="btn btn-primary" type="submit">
            Guardar
          </button>
        </div>
      </form>
      <h1>{editorial.nombre}</h1>
    </div>
  );
}
