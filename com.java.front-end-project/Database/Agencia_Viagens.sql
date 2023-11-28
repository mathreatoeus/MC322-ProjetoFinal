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
numero_cartao int,
validade date,
cvv tinyint,
nome_cartao varchar(100),
data_registro date,
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
cliente int not null,
valor decimal(7,2) not null,
situacao enum ('PENDENTE','PAGO','ATRASADO') not null,
vencimento date not null,
primary key (id),
foreign key (cliente) references Cliente(id)
) default charset = utf8mb4;

-- 'Local' é palavra reservada do SQL. Por isso usamos 'Localizacao'.
create table Localizacao (
id int auto_increment not null,
cidade varchar(100) not null,
estado vachar (100) not null,
pais varchar(100) not null,
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
foreign key (hospedagem) references Hospedagem(id)
)default charset = utf8mb4;

create table ComentariosLocalizacoes (
id int auto_increment not null,
cliente int not null,
data_e_hora_da_postagem datetime not null,
comentario varchar(500),
localizacao int not null,
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
localPartida int not null,
localChegada int not null,
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
categoria enum ('AVENTURA', 'CULTURA', 'RELAXAMENTO', 'HISTORICO', 'VIDA_NOTURNA',
				'GASTRONOMIA', 'FAMILIA', 'CRUZEIRO', 'FESTIVAL'),
hospedagem int,
tipoPassagem enum ('AEREA', 'ONIBUS'),
passagem int,
aluguelCarro int,
desconto int,
preco decimal(6,2) not null,
media_avaliacoes decimal(2,1),
num_avaliacoes int,
fechado bool not null,
primary key (id),
foreign key (destino) references Localizacao(id),
foreign key (hospedagem) references Hospedagem(id),
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

create table AtividadesPacotes(
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
usuario int not null,
entrada datetime not null,
saida datetime not null,
pagamento int not null,
desconto decimal(6,2),
preco decimal(6,2) not null,
primary key (id),
foreign key (pacote) references Pacote(id),
foreign key (usuario) references Cliente(id),
foreign key (usuario) references Funcionario(id),
foreign key (pagamento) references Pagamento(id)
) default charset = utf8mb4;

-- -->Create first users:
-- -----------------------------------------
-- 		-->Gerente
INSERT INTO agencia_viagens.funcionario (nome, nascimento, cpf, email, senha, celular, endereco, cargo) 
VALUES ('teste', '2023-11-26', '4566454', 'algo@func.com', '123', '454654', 'rua x', 'GERENTE');

-- 		-->Cliente
INSERT INTO agencia_viagens.cliente (nome, nascimento, cpf, email, senha, celular, endereco, numero_cartao, validade, cvv, nome_cartao, data_registro) 
VALUES ('teste', '2023-11-26', '4566454', 'algo@cli.com', '123', '454654', 'rua x', '54654654', '2023-12-01', '1', 'asdas', '2023-11-26');

-- --> Create first locations:

INSERT INTO agencia_viagens.localizacao (cidade, continente, mediaAvaliacoes, numAvaliacoes, estado, pais) 
VALUES ('Santiago', 'AMERICA_DO_SUL', '0', '0', 'Santiago', 'Chile'),
 ('Buenos Aires', 'AMERICA_DO_SUL', '0', '0', 'Buenos Aires', 'Argentina'),
 ('Rio de Janeiro', 'AMERICA_DO_SUL', '0', '0', 'Rio de Janeiro', 'Brasil'),
 ('São Paulo', 'AMERICA_DO_SUL', '0', '0', 'São Paulo', 'Brasil'),
 ('Salvador', 'AMERICA_DO_SUL', '0', '0', 'Bahia', 'Brasil');
 
 -- --> Create first hospedagem
INSERT INTO agencia_viagens.hospedagem (nome, tipo_hospedagem, tipo_suite, tipo_cama, descricao, endereco, localizacao, checkin, checkout, diaria, num_diarias, preco, mediaAvaliacoes, numAvaliacoes, disponivel)
VALUES ('Recanto chile', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '1', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Recanto Chile para 1', 'POUSADA', 'INDIVIDUAL', 'SOLTEIRO', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '1', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Experiencia Argentina', 'HOTEL', 'DUPLA', 'QUEEN', 'Venha conhecer um local muito rico em experiencias gastronomicas', 'Rua xyz', '2', '2024-01-10', '2024-01-17', '2000', '7', '14000', '0', '0', '1'),
 ('Descubra o Rio', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '3', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Festas em Sampa', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '4', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Interior de São Paulo', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '4', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('História de Salvador', 'HOTEL', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '5', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Cultura Bahiana', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '5', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Praias maravilhosas', 'POUSADA', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '3', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Trilhas e cachoeiras', 'ALBERGUE', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '4', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Gastronomia Paulista', 'HOTEL', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '4', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1'),
 ('Trilha dos vinhos', 'HOTEL', 'DUPLA', 'QUEEN', 'Belo local para descansar com a família em pousada paradisiaca', 'Rua xyz', '1', '2024-01-10', '2024-01-17', '1000', '7', '7000', '0', '0', '1');

-- --> Create first passagem
INSERT INTO agencia_viagens.passagemaerea (localpartida, localchegada, saida, chegada, duracao, companhia, preco, aeroporto_partida, aeroporto_chegada, iatapartida, iatadestino)
VALUES ('4', '1', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('4', '1', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('3', '2', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('5', '3', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('3', '4', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('3', '5', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('4', '3', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('4', '3', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('2', '3', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('2', '4', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo'),
('4', '1', '2024-01-10', '2024-01-17', '0.2', 'LATAM', '500', 'GRU', 'XYZ', 'algo', 'algo');

INSERT INTO agencia_viagens.passagemonibus (localpartida, localchegada, saida, chegada, duracao, companhia, preco, enderecopartida, enderecochegada)
VALUES ('4', '1', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('4', '1', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('3', '2', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('5', '3', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('3', '4', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('3', '5', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('4', '3', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('4', '3', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('2', '3', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('2', '4', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ'),
('4', '1', '2024-01-10', '2024-01-17', '0.2', 'ONIBOUS', '500', 'GRU', 'XYZ');

-- --> Create packages
INSERT INTO agencia_viagens.pacote (destino, categoria, hospedagem, tipopassagem, passagem, aluguelcarro, desconto, preco, media_avaliacoes, num_avaliacoes, fechado)
VALUES ('1', 'FAMILIA', '1', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('1', 'RELAXAMENTO', '2', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('2', 'GASTRONOMIA', '3', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('3', 'VIDA_NOTURNA', '4', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('4', 'FAMILIA', '5', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('5', 'HISTORICO', '6', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('3', 'CULTURA', '7', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('3', 'RELAXAMENTO', '8', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('3', 'AVENTURA', '9', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('4', 'GASTRONOMIA', '10', 'AEREA', '1', '0', '0','500', '0', '0', '0'),
('1', 'GASTRONOMIA', '11', 'AEREA', '1', '0', '0','500', '0', '0', '0');