create table customers (
  id int(11) not null primary key auto_increment,
  first_name varchar(50) default null,
  last_name varchar(70) default null,
  email varchar(45) default null
)engine =InnoDB;