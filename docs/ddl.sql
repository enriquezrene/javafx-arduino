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