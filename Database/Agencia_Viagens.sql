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
cliente int not_null,
valor decimal(7,2) not null,
situacao enum ('PENDENTE','PAGO','ATRASADO') not null,
vencimento date not null,
primary key (id),
foreign key (cliente) references Cliente(id)
) default charset = utf8mb4;

-- 'Local' é palavra reservada do SQL. Por isso usamos 'Localizacao'.
create table Localizacao (
id int auto_increment not null,
nome varchar(100) not null,
continente enum('AMERICA_DO_SUL', 'AMERICA_DO_NORTE', 'AMERICA_CENTRAL', 
				'ASIA', 'OCEANIA', 'EUROPA', 'ORIENTE_MEDIO', 'AFRICA'),
mediaAvaliacoes float not null,
numAvaliacoes int not null,
primary key (id)
) default charset = utf8mb4;

create table Atividade (
id int auto_increment not null,
nome_atividade varchar(100) not null,
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
tipo_hospedagem enum('HOTEL', 'APARTAMENTO', 'CASA', 'ALBERGUE', 'POUSADA'),
tipo_suite enum('INDIVIDUAL', 'DUPLA', 'TRIPLA', 'QUADRUPLA', 'PREMIUM'),
tipo_cama enum('SOLTEIRO', 'BELICHE', 'CASAL', 'QUEEN', 'KING'),
descricao varchar(100),
endereco varchar(200) not null,
localizacao int not null,
checkin datetime not null,
checkout datetime not null,
diaria decimal(6,2) not null,
num_diarias int not null,
preco decimal(8, 2) not null,
mediaAvaliacoes float not null,
numAvaliacoes int not null,
disponivel bool not null,
primary key (id),
foreign key (localizacao) references Localizacao(id)
) default charset = utf8mb4;

create table ComentariosHospedagens (
id int auto_increment not null,
cliente int not null,
data_e_hora_da_postagem datetime not null,
comentario varchar(500) not null,
hospedagem int not null,
primary key(id),
foreign key (cliente) references Cliente(id),
foreign key (hospedagem) references hospedagem(id)
)default charset = uft8mb4;

create table ComentariosLocalizacoes (
id int auto_increment not null,
cliente int not null,
data_e_hora_da_postagem datetime not null,
comentario varchar(500),
localizacao int not_null,
primary key(id),
foreign key (cliente) references Cliente(id),
foreign key (localizacao) references Localizacao(id)
) default charset = utf8mb4;

create table Seguro (
id int auto_increment not null,
franquia decimal (6,2) not null,
descricao varchar(100),
primary key (id)
) default charset = utf8mb4;

create table AluguelCarro (
id int auto_increment not null,
num_diarias int not null,
modelo_carro varchar(100) not null,
locadora varchar(100) not null,
retirada datetime not null,
devolucao datetime not null,
endereco_retirada varchar(100) not null,
endereco_devolucao varchar(100) not null,
diaria decimal(6,2) not null,
preco decimal(8, 2) not null,
seguro int not null,
primary key (id),
foreign key (seguro) references Seguro(id)
) default charset = utf8mb4;

create table PassagemAerea (
id int auto_increment not null,
local_partida int not null,
local_destino int not null,
saida datetime not null,
chegada datetime not null,
duracao decimal(2, 2),
companhia varchar(100) not null,
preco decimal(6,2) not null,
aeroporto_partida varchar(100) not null,
aeroporto_chegada varchar(100) not null,
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
tipoPassagem enum ('AEREA', 'ONIBUS'),
passagem int,
aluguelCarro int,
desconto int,
preco decimal(6,2) not null,
media_avaliacoes decimal(1,2),
num_avaliacoes int,
fechado bool not null,
primary key (id),
foreign key (passagem) references PassagemAerea(id),
foreign key (passagem) references PassagemOnibus(id)
) default charset = utf8mb4;

create table ComentariosPacotes (
id int auto_increment not null,
cliente int not null,
data_e_hora_da_postagem datetime not null,
comentario varchar(500) not null,
pacote int not null,
primary key(id),
foreign key (cliente) references Cliente(id),
foreign key (pacote) references Pacote(id)
) default charset = utf8mb4;

create table ativadesPacote(
id int auto_increment not null,
pacote int not null,
atividade int not null,
primary key (id),
foreign key (pacote) references Pacote(id),
foreign key (atividade) references Atividade(id)
) default charset = utf8mb4;

create table Reserva(
id int auto_increment not null,
pacote int not null,
cliente int not null,
entrada datetime not null,
saida datetime not null,
pagamento int not null,
desconto decimal(6,2),
preco decimal(6,2) not null,
primary key (id),
foreign key (pacote) references Pacote(id),
foreign key (cliente) references Cliente(id),
foreign key (usuario) references Funcionario(id)
) default charset = utf8mb4;


