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


INSERT INTO permissions (name) VALUES ('READ');
INSERT INTO permissions (name) VALUES ('WRITE');
INSERT INTO permissions (name) VALUES ('DELETE');

INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('USER');
INSERT INTO roles (role_name) VALUES ('DEV');

-- ADMIN: todos los permisos
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 3)


-- DEV: READ y WRITE
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 2);

--USER: READ
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1);




INSERT INTO user_info (id,username,password) VALUES (1,'maxi','$2a$10$PKjxydmUFIdVUa9FdgclO.n7SMgFb07vFKBlLLF1zhCVZ2jP3gPf2');
INSERT INTO user_info (id,username,password) VALUES (2,'safira','$2a$10$PKjxydmUFIdVUa9FdgclO.n7SMgFb07vFKBlLLF1zhCVZ2jP3gPf2');

-- maxi tiene rol ADMIN (id=1)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- safira tiene rol DEV (id=2)
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);
