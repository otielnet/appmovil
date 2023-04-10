package Entidad;

public class detailsClient {

    int imagenClient;
    String id, cedulaCliente, nombreCliente, valorCliente, sector, asignacion, direccion1, direccion2, cartera, calificacionRiesgo, primerPedido, idasig, DomRef, mora, abonos;


    public detailsClient() {

    }

    public String getDomRef() {
        return DomRef;
    }

    public void setDomRef(String domRef) {
        DomRef = domRef;
    }

    public String getIdasig() {
        return idasig;
    }

    public void setIdasig(String idasig) {
        this.idasig = idasig;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalificacionRiesgo() {
        return calificacionRiesgo;
    }

    public void setCalificacionRiesgo(String calificacionRiesgo) {
        this.calificacionRiesgo = calificacionRiesgo;
    }

    public String getPrimerPedido() {
        return primerPedido;
    }

    public void setPrimerPedido(String primerPedido) {
        this.primerPedido = primerPedido;
    }

    public int getImagenClient() {
        return imagenClient;
    }

    public void setImagenClient(int imagenClient) {
        this.imagenClient = imagenClient;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getValorCliente() {
        return valorCliente;
    }

    public void setValorCliente(String valorCliente) {
        this.valorCliente = valorCliente;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCartera() {
        return cartera;
    }

    public void setCartera(String cartera) {
        this.cartera = cartera;
    }

    public String getMora() { return mora; }

    public void setMora(String mora) { this.mora = mora;}

    public String getAbonos() { return abonos;}

    public void setAbonos(String abonos) { this.abonos = abonos;}
}
