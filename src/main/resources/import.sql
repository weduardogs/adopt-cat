INSERT INTO tb_gatos (`id_gato`,`edad`,`estatus`,`foto`,`nombre`,`owner_name`,`peso`,`raza`,`situacion`) VALUES (1,'3',1,'3a58599b-0c35-489c-9ec2-d96a31c11992_gato1.jpg','Cascabel','admin@gmail.com',4,'Persa',0);
INSERT INTO tb_gatos (`id_gato`,`edad`,`estatus`,`foto`,`nombre`,`owner_name`,`peso`,`raza`,`situacion`) VALUES (2,'2',1,'c740c611-57cd-431c-86dc-8c9a2859e9fc_gato4.jpg','Faraon','admin@gmail.com',3,'Egipcio',0);
INSERT INTO tb_gatos (`id_gato`,`edad`,`estatus`,`foto`,`nombre`,`owner_name`,`peso`,`raza`,`situacion`) VALUES (3,'1',1,'2483e034-5886-4a05-a28f-695686ca1851_gato5.jpg','Amarillo','admin@gmail.com',3,'Europeo',0);
INSERT INTO tb_gatos (`id_gato`,`edad`,`estatus`,`foto`,`nombre`,`owner_name`,`peso`,`raza`,`situacion`) VALUES (4,'5',1,'5fd2da5a-2f8d-4c2e-a169-7e5400da7903_gato2.jpg','Pinto','admin@gmail.com',3,'Manx',0);
INSERT INTO tb_gatos (`id_gato`,`edad`,`estatus`,`foto`,`nombre`,`owner_name`,`peso`,`raza`,`situacion`) VALUES (5,'4',1,'d1a186b8-30af-437c-9147-8a254c6e8d74_gato3jpg.jpg','Pelusa','admin@gmail.com',3,'Manx',0);

INSERT INTO tb_postulacion (`postulacion`,`edad`,`estatus`,`fecha_postulacion`,`id_usuario`,`motivo_postula`,`name`,`email`,`id_gato`, `tiene_espacio`, `tuvo_gato`) VALUES (1,21,0,CURDATE(),4,'Para compañía','José Martínez','jose.matinez@gmail.com',1,'S', 'S');
INSERT INTO tb_postulacion (`postulacion`,`edad`,`estatus`,`fecha_postulacion`,`id_usuario`,`motivo_postula`,`name`,`email`,`id_gato`, `tiene_espacio`, `tuvo_gato`) VALUES (2,34,0,CURDATE(),4,'Quiero darle un hogar','Eréndira Ibarra','erendira.10@outlook.com',2,'S', 'N');
INSERT INTO tb_postulacion (`postulacion`,`edad`,`estatus`,`fecha_postulacion`,`id_usuario`,`motivo_postula`,`name`,`email`,`id_gato`, `tiene_espacio`, `tuvo_gato`) VALUES (3,45,0,CURDATE(),4,'Compañia','Esther Domínguez','flor320@gmail.com',2,'N', 'S');
INSERT INTO tb_postulacion (`postulacion`,`edad`,`estatus`,`fecha_postulacion`,`id_usuario`,`motivo_postula`,`name`,`email`,`id_gato`, `tiene_espacio`, `tuvo_gato`) VALUES (4,40,0,CURDATE(),4,'Para emparejarlo con mi actual gato','Ramón Pérez','ramon.p1@gmail.com',3,'S','S');
INSERT INTO tb_postulacion (`postulacion`,`edad`,`estatus`,`fecha_postulacion`,`id_usuario`,`motivo_postula`,`name`,`email`,`id_gato`, `tiene_espacio`, `tuvo_gato`) VALUES (5,18,0,CURDATE(),4,'Mi primer gato','Jesús González','jesus.gzl@outlook.com',4,'N','S');


