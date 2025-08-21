INSERT INTO fabricante (id, nombre) VALUES (1,'Asus');
INSERT INTO fabricante (id, nombre) VALUES (2,'Lenovo');
INSERT INTO fabricante (id, nombre) VALUES (3,'Xiaomi');
INSERT INTO fabricante (id, nombre) VALUES (4,'Samsung');


INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (1,'Discoduro SATA3',86.99,4);
INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (2,'Memoria Ram A3',82.99,3);
INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (3,'Discoduro SSD',86.99,4);
INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (4,'monitor 24 LED',186.99,2);
INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (5,'monitor 32 LED',286.99,2);
INSERT INTO producto (id, nombre,precio,id_fabricante) VALUES (6,'Celular blue',300.24,3);



INSERT INTO user_info (id,username,password,role) VALUES (1,'maxi','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','dev');