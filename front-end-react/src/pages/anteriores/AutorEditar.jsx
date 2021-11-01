import { useParams } from "react-router";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";

export default function AutorEditar() {
  const { id } = useParams();
  console.log(id);
  const [autor, setAutor] = useState({});
  const [nombre, setNombre] = useState({});

  let history = useHistory();

  useEffect(() => {
    obtenerDatos();
    console.log(id);
    console.log(autor);
  }, []);

  const guardar = (e) => {
    e.preventDefault();
    var url = "http://localhost:8080/autores/modificar";
    fetch(url, {
      method: "PUT", // or 'POST'
      body: JSON.stringify(nombre), // data can be `string` or {object}!
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .catch((error) => console.error("Error:", error))
      .then((response) => console.log("Success:", response));

    history.push("/autores");
  };

  const handleChange = (e) => {
    e.preventDefault();
    setNombre({ [e.target.name]: e.target.value });
  };

  const obtenerDatos = async () => {
    fetch(`http://localhost:8080/autores/autorById/${id}`)
      .then((data) => data.json())
      .then((data) => setAutor(data))
      .catch((e) => console.log(e));
  };

  return (
    <div>
      <h1>{id}</h1>

      <form onSubmit={guardar}>
        <label>Nombre</label>
        <input
          className="form-control"
          name="nombre"
          type="text"
          defaultValue={autor.nombre}
          onChange={handleChange}
        ></input>

        <button type="submit" className=" btn btn-primary">
          Guardar
        </button>
      </form>
    </div>
  );
}
