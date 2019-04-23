#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: stagiaire
#------------------------------------------------------------

CREATE TABLE stagiaire(
        id_stagiaire         int (11) Auto_increment  NOT NULL ,
        name_stagiaire       Varchar (50) NOT NULL ,
        firstname_stagiaire  Varchar (50) NOT NULL ,
        path_photo_stagiaire Varchar (100) ,
        id_groupe_groupe     Int NOT NULL ,
        PRIMARY KEY (id_stagiaire )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: groupe
#------------------------------------------------------------

CREATE TABLE groupe(
        id_groupe         int (11) Auto_increment  NOT NULL ,
        libelle_groupe    Varchar (50) NOT NULL ,
        path_photo_groupe Varchar (100) ,
        PRIMARY KEY (id_groupe )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: punitions
#------------------------------------------------------------

CREATE TABLE punitions(
        id_punitions                     int (11) Auto_increment  NOT NULL ,
        title_punitions                  Varchar (50) ,
        description_punitions            Varchar (500) ,
        id_type_punitions_type_punitions Int NOT NULL ,
        id_stagiaire_stagiaire           Int NOT NULL ,
        id_groupe_groupe                 Int NOT NULL ,
        PRIMARY KEY (id_punitions )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: type_punitions
#------------------------------------------------------------

CREATE TABLE type_punitions(
        id_type_punitions          int (11) Auto_increment  NOT NULL ,
        title_type_punitions       Varchar (50) NOT NULL ,
        description_type_punitions Varchar (500) NOT NULL ,
        PRIMARY KEY (id_type_punitions )
)ENGINE=InnoDB;

ALTER TABLE stagiaire ADD CONSTRAINT FK_stagiaire_id_groupe_groupe FOREIGN KEY (id_groupe_groupe) REFERENCES groupe(id_groupe);
ALTER TABLE punitions ADD CONSTRAINT FK_punitions_id_type_punitions_type_punitions FOREIGN KEY (id_type_punitions_type_punitions) REFERENCES type_punitions(id_type_punitions);
ALTER TABLE punitions ADD CONSTRAINT FK_punitions_id_stagiaire_stagiaire FOREIGN KEY (id_stagiaire_stagiaire) REFERENCES stagiaire(id_stagiaire);
ALTER TABLE punitions ADD CONSTRAINT FK_punitions_id_groupe_groupe FOREIGN KEY (id_groupe_groupe) REFERENCES groupe(id_groupe);
