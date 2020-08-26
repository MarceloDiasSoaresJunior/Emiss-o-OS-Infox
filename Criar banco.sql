-- comentarios. @Marcelo Dias.
-- a linha abaixo cria um novo banco de dados. @Marcelo Dias.
create database dbinfox;
-- A linha abaixo seleciona o banco de dados a ser utilizado. @Marcelo Dias.
use dbinfox;
-- o bloco de instruções abaixo cria uma tabela. @Marcelo Dias.
create table tbusuarios(
idusuario int primary key,
usuario varchar (50) not null,
fone varchar (15),
login varchar(15) not null unique,
senha varchar(15) not null
);
-- Abaixo, um comando para descrever a tabela.
describe tbusuarios;
-- A linha abaixo insere dados na tabaela, não esquecer do CRUD(Create, read, update e delete).
-- Create
insert into tbusuarios(idusuario,usuario,fone,login,senha)
values(1, 'Marcelo Junior',41997530702,'marcelodias','123456');
insert into tbusuarios(idusuario,usuario,fone,login,senha)
values(2,'Mônica Ribeiro Santos', '41998288432','moohrs', '1234');
insert into tbusuarios(idusuario,usuario,fone,login,senha)
values(3,'Vitória Dantas','41999999999','vitoria12','21009');
-- A linha abaixo exibirá os dados da tabela (cRude) @Marcelo Dias.
select * from tbusuarios

create table tbclientes(
idcliente int primary key auto_increment,
nomecli varchar(50) not null,
endcli varchar(100),
cepcli varchar(20),
cidadecli varchar(50),
estadocli varchar(50),
fonecli varchar (50) not null,
emailcli varchar (50)
);

describe tbclientes

insert into tbclientes(nomecli,endcli,cepcli,cidadecli,estadocli,fonecli,emailcli)
values('Osvaldo','Joaquim Francisco Bueno - 147','81630510','Curitiba','PR','41997530787','mdsj@outlook.com');
select * from tbclientes
use dbinfox
create table tbos(
os int primary key auto_increment,
data_os timestamp default current_timestamp,
equipamento varchar(150) not null,
defeito varchar(150) not null,
servico varchar(150),
responsavel varchar(30),
valor decimal(10,2),
idcliente int not null,
foreign key(idcliente) references tbclientes(idcliente)
);

insert into tbos(equipamento,defeito,servico,responsavel,valor,idcliente)
values('Notebook', 'Notebook com hd queimado','Trocar o HD','João',87.58,1);
select * from tbos;


select
O.os,equipamento,defeito,servico,valor,
C.nomecli,fonecli
from tbos as O
inner join tbclientes as C
on(O.idcliente = C.idcliente);