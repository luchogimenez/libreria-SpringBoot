import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function AutorLista() {
  const axios = require("axios");
  const [autores, setAutores] = useState([]);

  const handelDeshabilitar = (id) => {
    axios
      .post(`http://localhost:8080/autores/deshabilitar/${id}`)
      .then((res) => {});
  };
  const handelHabilitar = (id) => {
    axios
      .post(`http://localhost:8080/autores/habilitar/${id}`)
      .then((res) => {});
  };

  const obtenerListaAutores = async () => {
    await axios
      .get("http://localhost:8080/autores/lista/")
      .then(function (response) {
        setAutores(response.data);
      });
  };
  useEffect(() => {
    obtenerListaAutores();
  }, [autores]);
  return (
    <div>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Nombre</th>
            <th scope="col">Alta</th>
            <th scope="col">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {autores.map((autor) => (
            <tr key={autor.id}>
              <th scope="row">{autor.id}</th>
              <td>{autor.nombre}</td>
              <td>{autor.alta.toString()}</td>
              <td>
                <div className="">
                  <Link to={`editarAutor/${autor.id}`}>
                    <button className="btn btn-warning px-auto me-3">
                      Editar
                    </button>
                  </Link>
                  {autor.alta ? (
                    <button
                      type="submit"
                      onClick={() => handelDeshabilitar(autor.id)}
                      className="btn btn-danger px-auto"
                    >
                      Deshabilitar
                    </button>
                  ) : (
                    <button
                      type="submit"
                      onClick={() => handelHabilitar(autor.id)}
                      className="btn btn-success px-auto"
                    >
                      Habilitar
                    </button>
                  )}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
