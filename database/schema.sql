DROP DATABASE IF EXISTS db_task;

CREATE DATABASE db_task CHARSET=UTF8;

USE db_task;

CREATE TABLE task (

  id                        BIGINT(20) AUTO_INCREMENT NOT NULL,
  titulo                    VARCHAR(255),
  descricao                 VARCHAR(255),
  status                    TINYINT(1) DEFAULT 0 NOT NULL,
  data_criacao	            DATE DEFAULT NULL,
  data_modificacao          DATE DEFAULT NULL,
  
  CONSTRAINT pk_task PRIMARY KEY (id)
  
) DEFAULT CHARSET=UTF8;