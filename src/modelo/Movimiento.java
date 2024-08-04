package modelo;

import java.util.Date;

public abstract class Movimiento {
    protected int id;
    protected String producto;
    protected int cantidad;
    protected Date fecha;

    public Movimiento(int id, String producto, int cantidad, Date fecha) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    
    public abstract void listarMovimientos();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    
}
