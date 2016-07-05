CREATE TABLE paciente(
   id  SERIAL PRIMARY KEY,
   nombre TEXT,
   apellido TEXT,
   cedula TEXT,
   email TEXT,
   direccion TEXT,
   telefono TEXT,
   peso         REAL,
   estatura         INT
);

insert into paciente (nombre, apellido, cedula, email, direccion, telefono, peso, estatura) values
('Johana', 'Olmedo', '1718457890', 'johana.olmedo.2016@gmail.com', 'La carolina', '0987459667', 120, 165)


CREATE TABLE usuario(
   id  SERIAL PRIMARY KEY,
   nombre TEXT,
   apellido TEXT,
   email TEXT,
   nombre_de_usuario TEXT,
   contrasena TEXT
);

CREATE TABLE lectura_glucometro(
   id  SERIAL PRIMARY KEY,
   id_paciente  INTEGER,
   valor TEXT,
   fecha TIMESTAMP,
   estado TEXT
);

insert into lectura_glucometro (id_paciente, valor, fecha, estado) values
(1, '54.50', '10/05/2015', 'AYUNAS');


CREATE TABLE lectura_offline(
   id  SERIAL PRIMARY KEY,
   id_paciente  INTEGER,
   valor TEXT,
   fecha TIMESTAMP,
   estado TEXT,
   insercion TIMESTAMP,
   leido BOOLEAN
);

insert into lectura_offline (id_paciente, valor, fecha, estado, leido) values
(9, '54.50', '10/05/2015', 'AYUNAS', false);

insert into lectura_offline (id_paciente, valor, fecha, estado, leido) values
(9, '54.50', '10/05/2015', 'AYUNAS', true);