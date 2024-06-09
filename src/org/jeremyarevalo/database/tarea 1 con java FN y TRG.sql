use superkinalIN5CV;


delimiter $$
create function fn_totalFactura(facId int) returns decimal(10, 2) deterministic
begin
    declare total decimal(10, 2) default 0.00;

    select sum(P.precioVentaUnitario)
    into total
    from detalleFacturas DF
    join Productos P on DF.productoId = P.productoId
    where DF.facturaId = facId;

    return total;
end$$
delimiter ;




delimiter $$
create function fn_calcularTotalUnitario(factId int) returns decimal(10, 2) deterministic
begin
    declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
 
    declare cursorDetalleFacturas cursor for 
        select DF.facturaId, DF.productoId from detalleFacturas DF;
     
    open cursorDetalleFacturas;
 
    totalLoop : loop
        fetch cursorDetalleFacturas into curFacturaId, curProductoId;
        if facId = curFacturaId then
            set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
            set total = total + precio;
        end if;
 
        if i = (select count(*) from detalleFacturas) then
            leave totalLoop;
        end if;
 
        set i = i + 1;
    end loop totalLoop;
 
    close cursorDetalleFacturas;
    call sp_asignarTotal(total, facId);
 
    return total;
end$$
delimiter ;




delimiter $$

create procedure sp_asignarTotal(in tot decimal(10,2), in facId int)
begin
    update Facturas
    set total = tot
    where facturaId = facId;
end$$

delimiter ;




delimiter $$

create trigger tg_totalFactura
after insert on detalleFacturas
for each row
begin
    declare total decimal(10,2);
    set total = fn_totalFactura(NEW.facturaId);
    call sp_asignarTotal(total, NEW.facturaId);
end$$

delimiter ;




delimiter $$
create procedure sp_manejoStock(in proId int)
begin
	update Productos
		set
			cantidadStock = cantidadStock - 1
            where productoId = proId;
end$$
delimiter ;


delimiter $$
create trigger tg_restarStock
before insert on detalleFacturas
for each row
begin
    if (select P.cantidadStock from Productos P where productoId = NEW.productoId) = 0 then
		signal sqlstate'45000'
			set message_text="No contamos con este producto en stock:c";
    else
		call sp_manejoStock(new.productoId);
	end if;
 
end $$
delimiter ;

