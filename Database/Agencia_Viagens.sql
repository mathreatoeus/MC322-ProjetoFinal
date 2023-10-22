drop database Agencia_Viagens;
create database Agencia_Viagens default character set utf8mb4 default collate utf8mb4_general_ci;
use Agencia_Viagens;
create table Cliente (
id int auto_increment not null,
nome varchar(100) not null,
nascimento date not null,
rg varchar(100) not null,
cpf varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
celular int not null,
telefoneResidencial int not null,
endereco varchar(100) not null,
numeroCartao int not null,
validade date not null,
cvv tinyint not null,
nomeCartao varchar(100) not null,
reservas varchar(100) not null,
dataRegistro date not null,
primary key (id)
) default charset = utf8mb4;
describe Cliente;

create table Funcionario (
id int auto_increment not null,
nome varchar(100) not null,
nascimento date not null,
rg varchar(100) not null,
cpf varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
celular int not null,
telefoneResidencial int not null,
endereco varchar(100) not null,
cargo enum('CORRETOR','GERENTE','DIRETOR'),
primary key (id)
) default charset = utf8mb4;
describe Cliente;

create table Pagamento (
id int auto_increment not null,
montante decimal(7,2) not null,
vencimento date not null,
situacao enum ('PENDENTE','PAGO','ATRAZADO') not null,
primary key (id)
) default charset = utf8mb4;

create table Atividade () default charset = utf8mb4;
create table Hospedagem () default charset = utf8mb4;
create table AluguelCarro () default charset = utf8mb4;
create table Seguro () default charset = utf8mb4;
create table PassagemAerea () default charset = utf8mb4;
create table PassagemOnibus () default charset = utf8mb4;
create table Destino () default charset = utf8mb4;
