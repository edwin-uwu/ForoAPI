CREATE TABLE topicos(
    id bigint not null AUTO_INCREMENT,
    titulo varchar(255) not null unique,
    mensaje varchar(255) not null unique,
    fecha_creacion datetime not null,
    estado varchar(50) not null,
    autor varchar(50) not null,
    curso varchar(255) not null,

    primary key(id)
);