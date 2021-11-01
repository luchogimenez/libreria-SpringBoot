import React, { useState, useEffect } from "react";
import { Table, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';


export default function AutorPages_v2() {
    
    const [ autores, setAutores ] = useState([]);

    useEffect(()=>{
        obtenerDatos();
        
    },[autores]);

    const obtenerDatos = async ()=>{
        const data = await fetch("http://localhost:8080/autores/lista");
        const autores = await data.json();
        setAutores(autores);
    };

    const changeAlta = (autor)=>{
        var url = "http://localhost:8080/autores/changeAlta";
        //var data = { username: "example" };
    
        fetch(url, {
          method: "PUT", // or 'PUT'
          body: JSON.stringify(autor), // data can be `string` or {object}!
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((res) => res.json())
          .catch((error) => console.error("Error:", error))
          .then((response) => console.log("Success:", response));
    };

  return (
    <div>
      <h1>Autores</h1>
      <div>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Id</th>
              <th>Nombre</th>
              <th>Habilitado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {autores.map((autor) => (
              <tr key={autor.id}>
                <td>{autor.id}</td>
                <td>{autor.nombre}</td>
                <td>{autor.alta.toString()}</td>
                <td>
                  <Link to={`/autores/editar/${JSON.stringify(autor)}`}>
                    <Button type="button" className="btn-secondary">
                      Editar
                    </Button>
                  </Link>

                  <Button
                    type="button"
                    className="btn-danger"
                    onClick={() => changeAlta(autor)}
                  >
                    Deshabilitar
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}
