package Entidad;

public class Client {


    int imagenClient;
    String cedulaCliente, nombreCliente, valorCliente, obligacionCliente, asignacion;

    public Client() {

    }

    /*public Client(int imagenClient, String cedulaCliente, String nombreCliente, String valorCliente, String sector, String asignacion) {
        this.imagenClient = imagenClient;
        this.cedulaCliente = cedulaCliente;
        this.nombreCliente = nombreCliente;
        this.valorCliente = valorCliente;
        this.sector = sector;
        this.asignacion = asignacion;
    }
*/

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

    public String getObligacionCliente() {
        return obligacionCliente;
    }

    public void setObligacionCliente(String obligacionCliente) { this.obligacionCliente = obligacionCliente;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }
}
