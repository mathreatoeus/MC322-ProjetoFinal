drop database if exists Agencia_Viagens;
create schema Agencia_Viagens default character set utf8mb4 default collate utf8mb4_general_ci;
use Agencia_Viagens;

create table Cliente (
id int auto_increment not null,
nome varchar(100) not null,
nascimento date not null,
cpf varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
celular int not null,
endereco varchar(100) not null,
numeroCartao int,
validade date,
cvv tinyint,
nomeCartao varchar(100),
reservas varchar(100),
dataRegistro date,
primary key (id)
) default charset = utf8mb4;
describe Cliente;

create table Funcionario (
id int auto_increment not null,
nome varchar(100) not null,
nascimento date not null,
cpf varchar(100) not null,
email varchar(100) not null,
senha varchar(100) not null,
celular int not null,
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

create table Localizacao (
id int auto_increment not null,
nome varchar(100) not null,
avaliacao tinyint not null,
primary key (id)
) default charset = utf8mb4;

create table Atividade (
id int auto_increment not null,
nome varchar(100) not null,
descricao varchar(100),
localizacao int not null,
endereco varchar(100) not null,
inicio datetime,
fim datetime,
preco decimal(6,2),
primary key (id),
foreign key (localizacao) references Localizacao(id)
) default charset = utf8mb4;

create table Hospedagem (
id int auto_increment not null,
nome varchar(100) not null,
descricao  varchar(100),
endereco varchar(100) not null,
diaria decimal(6,2) not null,
avaliacao tinyint,
primary key (id)
) default charset = utf8mb4;

create table AluguelCarro (
id int auto_increment not null,
modelo varchar(100) not null,
empresa varchar(100) not null,
retirada datetime not null,
devolucao datetime not null,
enderecoRetirada varchar(100) not null,
endereco varchar(100) not null,
diaria decimal(6,2) not null,
primary key (id)
) default charset = utf8mb4;

create table Seguro (
id int auto_increment not null,
descricao varchar(100),
franquia decimal (6,2),
primary key (id)
) default charset = utf8mb4;

create table PassagemAerea (
id int auto_increment not null,
localPartida int not null,
localChegada int not null,
saida datetime not null,
chegada datetime not null,
duracao time,
companhia varchar(100) not null,
preco decimal(6,2) not null,
aeroportoSaida varchar(100) not null,
aeroportoChegada varchar(100) not null,
iataPartida varchar(100) not null,
iataDestino varchar(100) not null,
primary key (id),
foreign key (localPartida) references Localizacao(id),
foreign key (localChegada) references Localizacao(id)
) default charset = utf8mb4;

create table PassagemOnibus (
id int auto_increment not null,
localPartida int not null,
localChegada int not null,
saida datetime not null,
chegada datetime not null,
duracao time,
companhia varchar(100) not null,
preco decimal(6,2) not null,
enderecoPartida varchar(100) not null,
enderecoChegada varchar(100) not null,
primary key (id),
foreign key (localPartida) references Localizacao(id),
foreign key (localChegada) references Localizacao(id)
) default charset = utf8mb4;

create table Pacote (
id int auto_increment not null,
destino int,
hospedagem int,
tipoPassagem enum ('PassagemAerea', 'PassagemOnibus'),
passagem int,
aluguelCarro int,
precoTotal decimal(6,2) not null,
avaliacao tinyint,
primary key (id),
foreign key (passagem) references PassagemAerea(id),
foreign key (passagem) references PassagemOnibus(id)
) default charset = utf8mb4;

create table ativadesPacote(
id int auto_increment not null,
pacote int not null,
atividade int not null,
primary key (id),
foreign key (pacote) references Pacote(id),
foreign key (atividade) references Atividade(id)
) default charset = utf8mb4;

create table reserva(
id int auto_increment not null,
pacote int not null,
tipoUsuario enum('Funcionario','Cliente') not null,
usuario int not null,
entrada datetime not null,
saida datetime not null,
pagamento int not null,
desconto decimal(6,2),
PrecoFinal decimal(6,2) not null,
primary key (id),
foreign key (pacote) references Pacote(id),
foreign key (usuario) references Cliente(id),
foreign key (usuario) references Funcionario(id)
) default charset = utf8mb4;


