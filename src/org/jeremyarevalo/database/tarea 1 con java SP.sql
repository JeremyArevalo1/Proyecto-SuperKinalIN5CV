
use superKinalIN5CV;
-- --------------------Clientes---------------------------------------------------------
-- Agregar
DELIMITER $$
create procedure sp_agregarClientes(nom varchar(30), ape varchar(30), tel varchar(15), dir varchar(150), n varchar(30))
BEGIN	
	insert into Clientes(nombre, apellido, telefono, direccion, nit) values
		(nom, ape, tel, dir, n);
END $$
DELIMITER ;
call sp_agregarClientes('Juan','Ramires','1111-1111','Huehuetenango', '9781648-0');
 
-- Listar
DELIMITER $$
create procedure sp_listarClientes()
BEGIN
	select
		Clientes.clienteId,
		Clientes.nombre,
		Clientes.apellido,
		Clientes.telefono,
		Clientes.direccion,
        Clientes.nit
			from Clientes;
END$$
DELIMITER ;
 
call sp_listarClientes();
-- Eliminar
DELIMITER $$
create procedure sp_eliminarClientes(in cliId int)
BEGIN
	delete
		from Clientes
		where clienteId = cliId;
END $$
DELIMITER ;
call sp_eliminarClientes(6);
 
-- Buscar
DELIMITER $$
CREATE PROCEDURE sp_buscarClientes (in cliId int)
BEGIN
	select
		Clientes.clienteId,
		Clientes.nombre,
		Clientes.apellido,
		Clientes.telefono,
		Clientes.direccion,
        Clientes.nit
        from Clientes
		where clienteId = cliId;
END$$
DELIMITER ;
call sp_buscarClientes(2);
-- Editar
DELIMITER $$
create procedure sp_editarClientes (in cliId int, in nom varchar(30),in ape varchar(30),in tel varchar(15), in dir varchar(150), in n varchar(30))
BEGIN
	update Clientes
		set
			nombre = nom,
			apellido = ape,
			telefono = tel,
			direccion = dir,
            nit = n
			where clienteId = cliId;
END $$
 
DELIMITER ;
CALL sp_editarClientes (3, 'Raul', 'Marquez', '3333-3333', 'Suchitepequez', '3671898-0');

-- ---------------------- CARGOS -----------------------------
-- Agregar
delimiter $$
create procedure sp_agregarCargos(in nomCar varchar(30),in desCar varchar(100))
	begin
		insert into Cargos (nombreCargo, descripcionCargo) values
			(nomCar, desCar);
    end $$
delimiter ;
call sp_agregarCargos('supervisor', 'supervisa la actividad de los empleados');

-- listar
delimiter $$
create procedure sp_listarCargos()
	begin
		select 
			Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
			from Cargos;
    end $$
delimiter ;
-- call sp_listarCargos;

-- buscar
delimiter $$
create procedure sp_buscarCargos(in carId int)
	begin
		select 
			Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
			from Cargos
			where cargoId = carId;
    end $$
delimiter ;
-- call sp_buscarCargos;

-- eliminar
delimiter $$
create procedure sp_eliminarCargos(in carId int)
	begin
		delete 
			from Cargos
				where cargoId = carId;
    end $$
delimiter ;
--  call sp_eliminarCargos;

-- editar
delimiter $$
create procedure sp_editarCargos(in carId int, in nomCar varchar(30),in desCar varchar(100))
	begin
		update Cargos
			set 
            nombreCargo = nomCar,
            descripcionCargo = desCar
            where cargoId = carId;
    end $$
delimiter ;
call sp_editarCargos(1, 'Adminitrador', 'Administra los clientes');

-- ------------------------------------------ Compras --------------------------------------------------

-- agregar
delimiter $$
create procedure sp_agregarCompras(in fecCom date, in totCom decimal (10,2))
	begin 
		insert into Compras (fechaCompra, totalCompra) values
			(fecCom, totCom);
    end $$
delimiter ;
-- call sp_agregarCompras;

-- listar
delimiter $$
create procedure sp_listarCompras()
	begin
		select 
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
			from Compras;
    end $$
-- delimiter ;
call sp_listarCompras;

-- buscar
delimiter $$
create procedure sp_buscarCompras(in comId int)
	begin	
		select 
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
			from Compras 
			where compraId = comId;
    end $$
delimiter ;
-- call sp_buscarCompras;

-- eliminar 
delimiter $$
create procedure sp_eliminarCompras(in comId int)
	begin 
		delete from Compras
        where compraId = comId;
    end $$
delimiter ;
-- call sp_eliminarCompras;

-- editar
delimiter $$
create procedure sp_editarCompras(in comId int,in fecCom date,in totCom decimal (10,2))
	begin 
		update Compras
			set 
				fechaCompra = fecCom,
                totalCompra = totCom
                where compraId = comId;
    end $$
delimiter ;
-- call sp_editarCompras;

-- -------------------------------------- Distribuidor ----------------------------------------------------
-- Agregar
DELIMITER $$
create procedure sp_agregarDistribuidores(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),w varchar(50))
	begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis,dirDis,nitDis,telDis,w);
    end$$
DELIMITER ;
call sp_agregarDistribuidores('Intermediario comercial', 'Calle 3', '4161891-0', '8897-3364', 'http//');

-- Listar

DELIMITER $$
create procedure sp_listarDistribuidores()
	begin
		select 
			Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
			from Distribuidores;
    end$$
DELIMITER ;
-- call sp_listarDistribuidores;
 
 -- Buscar
 
DELIMITER $$
create procedure sp_buscarDistribuidores(disId int)
	begin 
		select 
			Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
			from Distribuidores
			where distribuidorId = disId;
    end$$
DELIMITER ;
-- call sp_buscarDistribuidores;
 
 -- eliminar
 
DELIMITER $$
create procedure sp_eliminarDistribuidores(dirId int)
	begin
		delete from Distribuidores 
        where distribuidorId = dirId;
    end$$
DELIMITER ;
-- call sp_eliminarDistribuidores;
 
 -- editar
 
DELIMITER $$
create procedure sp_editarDistribuidores(dirId int, nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),w varchar(50))
	begin
		update Distribuidores
			set
			nombreDistribuidor = nomDis,
			direccionDistribuidor = dirDis,
			nitDistribuidor = nitDis,
			telefonoDistribuidor = telDis,
			web = w
			where distribuidorId = dirId;
    end$$
DELIMITER ;
-- call sp_editarDistribuidores;

-- ----------------- Categoria Productos --------------------------
-- Agregar
DELIMITER $$
create procedure sp_agregarCategoriaProductos(nomCat varchar(30),desCat varchar(100))
	begin 
		insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
			(nomCat,desCat);
    end$$
DELIMITER ;
call sp_agregarCategoriaProductos('Cocina', 'Aca encontraras todo para tu cocina');

select * from CategoriaProductos;

-- listar
 
DELIMITER $$
create procedure sp_listarCategoriaProductos()
	begin
		select 
			CategoriaProductos.categoriaProductoId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
			from CategoriaProductos;
    end$$
DELIMITER ;
-- call sp_listarCategoriaProductos;

-- Buscar
 
DELIMITER $$
create procedure sp_buscarCategoriaProductos(catProId int)
	begin
		select
			CP.categoriaProductoId,
            CP.nombreCategoria,
            CP.descripcionCategoria
			from CategoriaProductos CP
			where catProId = CP.categoriaProductoId;
    end$$
DELIMITER ;
 call sp_buscarCategoriaProductos(1);


-- Eliminar
 
DELIMITER $$
create procedure sp_eliminarCategoriaProductos(catProId int)
	begin 
		delete from CategoriaProductos
        where catProId = categoriaProductoId;
    end $$
DELIMITER ;
-- call sp_eliminarCategoriaProductos;

-- Editar
 
DELIMITER $$
create procedure sp_editarCategoriaProductos(catProId int,nomCat varchar(30),desCat varchar(100) )
	begin
		update CategoriaProductos
			set
			nombreCategoria = nomCat,
			descripcionCategora = desCat
			where categoriaProductosId = catProId;
    end$$
DELIMITER ;
-- call sp_editarCategoriaProductos;

-- ------------------------------------------------Productos---------------------------------------------------------------

-- agregar
delimiter $$
create procedure sp_agregarProductos(in nomPro varchar(50),in desPro varchar(100),in canSto int, in preUni decimal(10,2),in preMay decimal(10,2),in preCom decimal(10,2), in imaPro longblob, in disId int, in catId int)
	begin
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nomPro, desPro, canSto, preUni, preMay, preCom, imaPro, disId, catId);
	end $$
delimiter ;
-- call sp_agregarProductos();

-- listar
delimiter $$
create procedure sp_listarProductos()
	begin 
		select
			P.productoId,P.nombreProducto,P.descripcionProducto,P.cantidadStock,P.precioVentaUnitario,P.precioVentaMayor,P.precioCompra,P.imagenProducto,
        concat(D.distribuidorId, ': | ' , D.nombreDistribuidor ) as 'distribuidor',
        concat(C.categoriaProductoId, ': | ', C.nombreCategoria) as 'categoriaProducto' from Productos P
        join Distribuidores D on P.distribuidorId = D.distribuidorId
        join CategoriaProductos C on P.categoriaProductoId = C.categoriaProductoId;
    end $$
delimiter ;
-- call sp_listarProductos();

-- buscar
delimiter $$
create procedure sp_buscarProductos(in proId int)
	begin 
		select 
			Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
            Productos.imagenProducto,
            Productos.distribuidorId,
            Productos.categoriaProductoId
			from Productos
        where productoId = proId;
    end $$
delimiter ;
-- call sp_buscarProductos;

-- eliminar 
delimiter $$
create procedure sp_eliminarProductos(in proId int)
	begin
		delete from Productos
			where productoId = proId;
    end $$
delimiter ;
-- call sp_eliminarProductos;

-- editar
delimiter $$
create procedure sp_editarProductos(in proId int, in nomPro varchar(50),in desPro varchar(100),in canSto int, in preUni decimal(10,2),in preMay decimal(10,2),in preCom decimal(10,2), in imaPro longblob, in disId int, in catId int )
	begin
		update Productos	
			set 
            nombreProducto = nomPro,
            descripcionProduto = desPro,
            cantidadStock = canSto,
            precioVentaUnitario = preUni,
            precioVentaMayor = preMay,
            precioCompra = preCom,
            imagenProducto = imapro,
            distribuidorId = disId,
            categoriaProductosId = catId
            where productoId = proId;
    end $$
delimiter ;
-- call sp_editarProductos;

-- ---------------------------------------- Promociones ------------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarPromociones(in prePro decimal(10, 2), in descProm varchar(100), in feIni date, in feFin date, in proId int)
	begin
		insert into Promociones (prePro, descPro, feIni, feFin, proId) values
			(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacione, productoId);
    end $$
delimiter ;
-- call sp_agregarPromociones;

-- listar
delimiter $$
create procedure sp_listarPromociones()
	begin
		select 
			Promociones.promocionId,
            Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinalizacion,
            Promociones.productoId
			from Promociones;
    end $$
delimiter ;
-- call sp_listarPromociones;

-- buscar
delimiter $$
create procedure sp_buscarPromociones(in promoId int)
	begin
		select 
			Promociones.promocionId,
            Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinalizacion,
            Promociones.productoId
			from Promociones
			where promocionId = promoId;
    end $$
delimiter ;
-- call sp_buscarPromociones;

-- eliminar
delimiter $$
create procedure sp_eliminarPromociones(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
delimiter ;
-- call sp_eliminarPromociones;

-- editar
delimiter $$
create procedure sp_editarPromociones(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFin date, in proId int)
	begin
		update Promociones
			set 
            precioPromocion = prePro,
            descripcionPromocion = descPro,
            fechaInicio = feIni,
            fechaFinalizacion = feFin,
            productoId = proId
            where promocionId = promoId;
    end $$
delimiter ;
-- call sp_editarPromociones;

-- -------------------------------------------------DetalleCompras------------------------------------------------------------------

-- agregar
delimiter $$
create procedure sp_agregarDetalleCompras(in canC int, in proId int,in comId int)
	begin 
		insert into DetalleCompra(cantidadCompra, productoId, compraId)values
			(canC, proId, comId);
    end $$
delimiter ;
-- call sp_agregarDetalleCompras;

-- listar
delimiter $$
create procedure sp_listarDetalleCompras()
	begin 
		select 
			DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
			from DetalleCompras;
    end $$
delimiter ;
-- call sp_listarDetalleCompras;

-- buscar
delimiter $$
create procedure sp_buscarDetalleCompras(in detCId int)
	begin 
		select 
			DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
			from DetalleCompras
			where detalleCompraId = detCId;
    end $$
delimiter ;
-- call sp_buscarDetalleCompras;

-- eliminar 
delimiter $$
create procedure sp_eliminarDetalleCompras(in detCId int)
	begin 
    delete from DetalleCompras
			where detalleCompraId = detCId;
    end $$
delimiter ;
-- call sp_eliminarDetalleCompras;

-- editar
delimiter $$
create procedure sp_editarDetalleCompras(in detCId int, in canC int, in proId int,in comId int)
	begin 
		update DetalleCompras
			set 
				cantidadCompra = canC,
                productoId = proId,
                compraId = comId
                where detalleCompraId = detCId;
    end $$
delimiter ;
-- call sp_editarDetalleCompras;

-- --------------------------------- Empleados --------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarEmpleados(in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in horEn time, in horSa time, in carId int, in encarId int)
	begin
		insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId) values
			(nomEmp, apeEmp, sue, horEn, horSa, carid, encarId);
    end $$
delimiter ;
call sp_agregarEmpleados('Jeremy', 'Miranda', 3525.20, '07:00:00', '05:00:00', 1, null);

-- listar
delimiter $$
create procedure sp_listarEmpleados()
	begin
		select 
			E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
			C.nombreCargo as cargo,
			E2.nombreEmpleado as encargado from Empleados E1
			join Cargos C on C.cargoId = E1.cargoId
			left join Empleados E2 on E1.encargadoId = E2.empleadoId;
    end $$
delimiter ;
call sp_listarEmpleados();

-- buscar
delimiter $$
create procedure sp_buscarEmpleados(in empId int)
	begin
		select 
            E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
			C.nombreCargo as cargo,
			E2.nombreEmpleado as 'encargado' from Empleados E1
			join Cargos C on C.cargoId = E1.cargoId
			left join Empleados E2 on E1.encargadoId = E2.empleadoId
			where E1.empleadoId = empId;
    end $$
delimiter ;
 -- call sp_buscarEmpleados(1);

-- eliminar
delimiter $$
create procedure sp_eliminarEmpleados(in empId int)
	begin
		delete 
			from Empleados
				where empleadoId = empId;
    end $$
delimiter ;
-- call sp_eliminarEmpleados;

-- editar
delimiter $$
create procedure sp_editarEmpleados(in empId int, in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in horEn time, in horSa time, in carId int, in encarId int)
	begin
		update Empleados
			set 
            nombreEmpleado = nomEmp,
            apellidoEmpleado = apeEmp,
            sueldo = sue,
            horaEntrada = horEn,
            horaSalida = horSa,
            cargoId = carId,
            encargadoId = encarId
            where empleadoId = empId;
    end $$
delimiter ;
-- call sp_editarEmpleados(1, "Carlos", "Rosas", "6563.00", "07:00:00", "17:00:00", 1, 2);

DELIMITER $$
	CREATE PROCEDURE sp_AsignarEncargadoId (IN encId INT, In empId INT)
		BEGIN
			UPDATE Empleados
				SET 
					encargadoId = encId
                    WHERE empleadoId = empId;
		END $$
DELIMITER ;

-- -------------------------------------- FACTURAS -------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarFacturas(in fec date, in hor time, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		insert into Facturas (fecha, hora, total, clienteId, empleadoId) values
			(fec, hor, tot, cliId, empId);
    end $$
delimiter ;
call sp_agregarFacturas('2021-03-24', '08:00:00', 10.02, 1, 1);

-- listar
delimiter $$
create procedure sp_listarFacturas()
	begin
		select F.facturaId, F.fecha, F.hora, F.total,
			C.nombre as cliente,
            E.nombreEmpleado as empleado from Facturas F
            join Clientes C on F.clienteId = C.clienteId
            join Empleados E on F.empleadoId = E.empleadoId;
    end $$
delimiter ;
call sp_listarFacturas();


-- buscar
delimiter $$
create procedure sp_buscarFacturas(in facId int)
	begin
		select 
			Facturas.facturaId,
            Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
            Facturas.empleadoId
			from Facturas
			where facturaId = facId;
    end $$
delimiter ;
-- call sp_buscarFacturas;

-- eliminar
delimiter $$
create procedure sp_eliminarFacturas(in facId int)
	begin
		delete 
			from Facturas
				where facturaId = facId;
    end $$
delimiter ;
-- call sp_eliminarFacturas;

-- editar
delimiter $$
create procedure sp_editarFacturas(in facId int, in fec date, in hor time, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		update Facturas
			set 
            fecha = fec,
            hora = hor,
            total = tot,
            clienteId = cliId,
            empleadoId = empId
            where facturaId = facId;
    end $$
delimiter ;
-- call sp_editarFacturas;

-- ------------------ Detalle Facturas ---------------------------
-- Agregar
DELIMITER $$
create procedure sp_agregarDetalleFacturas(in factId int, in prodId int)
begin
	insert into DetalleFacturas(facturaId, productoId) values
		(factId, prodId);
end $$
DELIMITER ;
-- call sp_agregarDetalleFacturas();

 -- listar DetalleFactura
DELIMITER $$
create procedure sp_listarDetalleFacturas()
begin
	select 
		DetalleFacturas.detalleFacturaId,
        DetalleFacturas.facturaId,
        DetalleFacturas.productoId
			from DetalleFacturas;
end $$
DELIMITER ;
-- call sp_listarDetalleFacturas

 -- Eliminar
DELIMITER $$
create procedure sp_eliminarDetalleFacturas(in detId int)
begin
	delete
		from DetalleFacturas
			where detalleFacturaId = detId;
end $$
DELIMITER ;
-- call sp_eliminarDetalleFacturas;

 -- Buscar
DELIMITER $$
create procedure sp_buscarDetalleFacturas(in detId int)
begin
	select 
		DetalleFacturas.detalleFacturaId,
        DetalleFacturas.facturaId,
        DetalleFacturas.productoId
			from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
-- call sp_buscarDetalleFacturas;

 -- Editar
DELIMITER $$
create procedure sp_editarDetalleFacturas(in detId int, in factId int, in prodId int)
begin
	update DetalleFacturas
		set 
			facturaId = factId,
            productoId = prodId
            where detalleFacturaId = detId;
end $$
DELIMITER ;
-- call sp_editarDetalleFacturas;

-- ----------------------------------------- Ticket Soporte --------------------------------------------------------
DELIMITER $$
create procedure sp_agregarTicketSoportes(in desTic varchar(250), in cliId int, in facId int)
begin
	insert into TicketSoportes(descripcionTicket,estatus,clienteId,facturaId) values
		(desTic,'Recien Creado',cliId,facId);
end $$
DELIMITER ;
call sp_agregarTicketSoportes('problema de wifi', 1, 1);
 
DELIMITER $$
create procedure sp_listarTicketSoportes()
begin
	select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
		concat('id: ', C.clienteId, '|', C.nombre, ' ', C.apellido) AS 'cliente',
        TS.facturaId from TicketSoportes TS
	join Clientes C on TS.clienteId = C.clienteId;
end $$
DELIMITER ;
call sp_listarTicketSoportes();
 
DELIMITER $$
create procedure sp_eliminarTicketSoportes(in tikSopId int)
begin
	delete
		from TicketSoportes
			where ticketSoporteId = tikSopId;
end$$
DELIMITER ;
-- call sp_eliminarTicketSoportes;
 
DELIMITER $$
create procedure sp_buscarTicketSoportes(in tikSopId int)
begin 
	select
		TicketSoportes.ticketSoporteId,
        TicketSoportes.descripcionTicket,
        TicketSoportes.estatus,
        TicketSoportes.clienteId,
        TicketSoportes.facturaId
			from TicketSoportes
			where ticketSoporteId = tikSopId;
end $$
DELIMITER ;
-- call sp_buscarTicketSoportes;
 
DELIMITER $$
create procedure sp_editarTicketSoportes(in tikSopId int,in desTic varchar(250), in est varchar(30), in cliId int, in facId int )
begin
	update TicketSoportes
		set 
			descripcionTicket = desTic,
            estatus = est,
            clienteId = cliId,
            facturaId = facId
				where ticketSoporteId = tikSopId;
end $$
DELIMITER ;
-- call sp_editarTicketSoportes;




