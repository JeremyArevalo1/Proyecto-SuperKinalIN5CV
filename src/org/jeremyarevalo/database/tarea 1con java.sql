drop database if exists superKinalIN5CV;
 
create database if not exists superKinalIN5CV;
 
use superKinalIN5CV;
 
create table Clientes(
	clienteId int not null auto_increment,
	nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(15),
    direccion varchar(150) not null,
    nit varchar(30),
    primary key PK_clienteId (clienteId)
);

create table Cargos(
	cargoId int not null auto_increment,
    nombreCargo varchar (30) not null,
    descripcionCargo varchar(100) not null,
    primary key PK_cargoId (cargoId)
);

create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal(10,2),
    primary key PK_compraId (compraId)
);

create table Distribuidores(
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar(30) not null,
    direccionDistribuidor varchar(200) not null,
    nitDistribuidor varchar(15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar(50),
    primary key PK_distribuidorId (distribuidorId)
);

create table CategoriaProductos(
	categoriaProductoId int not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategora varchar(100) not null,
    primary key PK_categoriaProductoId (categoriaProductoId)
);

create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int not null,
    precioVentaUnitario decimal(10.2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal(10,2) not null,
    imageProducto blob,
    distribuidorId int not null,
    categoriaProductoId int not null,
    primary key PK_productoId (productoId),
	constraint FK_Productos_Distribuidores foreign key Productos(distribuidorId)
		references Distribuidores(distribuidorId),
	constraint FK_Productos_CategoriaProductos foreign key Productos(categoriaProductoId)
		references CategoriaProductos(categoriaProductoId)
);

create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10,2) not null,
    descripcionPromocion varchar(100),
    fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int not null,
    primary key PK_promocionId (promocionId),
    constraint FK_Promociones_Productos foreign key Promociones(productoId)
		references Productos(productoId)
);

create table DetalleCompras(
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key detalleCompraId (detalleCompraId),
    constraint FK_DetalleCompras_Productos foreign key DetalleCompras(productoId)
		references Productos(productoId),
	constraint FK_DetalleCompras_Compras foreign key DetalleCompras(compraId)
		references Compras(compraId)
);

create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10.2) not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    encargadoId int,
    primary key PK_empleadoId(empleadoId),
    constraint FK_Empleados_Cargos foreign key Empleados(cargoId)
		references Cargos(cargoId),
	constraint FK_encargadoId foreign key Empleados(encargadoId)
		references Empleados(empleadoId)
    
);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal(10,2),
    primary key PK_facturaId(facturaId),
    constraint FK_Facturas_Clientes foreign key Facturas(clienteId)
		references Clientes(clienteId),
	constraint FK_Facturas_Empleados foreign key Facturas(empleadoId)
		references Empleados(empleadoId)
);

create table DetalleFacturas(
	detalleFacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    primary key PK_detalleFacturaId(detalleFacturaId),
    constraint FK_DetalleFacturas_Facturas foreign key DetalleFacturas(facturaId)
		references Facturas(facturaId),
	constraint FK_DetalleFacturas_Productos foreign key DetalleFacturas(productoId)
		references Productos(productoId)
);

create table TicketSoportes(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar(30) not null,
    clienteId int not null,
    facturaId int,
    primary key PK_ticketSoporteId(ticketSoporteId),
    constraint FK_TicketSoportes_Clientes foreign key TicketSoportes(clienteId)
		references Clientes(clienteId),
	constraint FK_TicketSoportes_Facturas foreign key TicketSoportes(facturaId)
		references Facturas(facturaId)
);

insert into Clientes(nombre, apellido, telefono, direccion, nit) values
	('Elkyn', 'Samayoa', '6666-6666', 'Salgoloteo el Chiquito', '5489634-0'),
	('Jorge', 'Peralta', '3333-3333', 'Chinique Quiche', '2778945-0');
    
-- select * from Clientes;
-- select * from Cargos;
-- select * from TicketSoportes;
-- select * from Distribuidores;