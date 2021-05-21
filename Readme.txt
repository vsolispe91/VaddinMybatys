Proyecto en Spring Boot basico de CRUD para una gestion de usuarios muy basica.

-Frameworks usados:
Spring Web,Mybatys,Springsecurity

-NodeJS.
node_modules
Desde el Spring boot se descarga al momento de ejecutar la aplicacion, tambien puede desargarse directamente si se tiene instalado NodeJs 
en su sistema operativo con el comando "npm install" en el directorio del proyecto.

Para el proyecto DEMO:

CREATE TABLE persona
(
id  BIGSERIAL (1) PRIMARY key,
nombres CHAR(50),
apellidos char(50),
email char(50)
);

insert into persona (id,apellidos,nombres) values (1,'Pique','Gerard','Pique@gmail.com');
insert into persona (id,apellidos,nombres) values (2,'Iniesta','Andres','Andres@gmail.com');
insert into persona (id,apellidos,nombres) values (3,'Puyol','Carles','Puyol@gmail.com');
insert into persona (id,apellidos,nombres) values (4,'Messi','Lionel','Messi@gmail.com');


Tablas para el uso de spring security (investigar documentacion, el por que de estas tablas).

CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled INT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);