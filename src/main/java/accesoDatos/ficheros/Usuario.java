package accesoDatos.ficheros;

import java.util.List;

public class Usuario {
    private String codigo;
    private List<String> aficiones; //Uso un List para despues poder crear el ArrayList en el constructor.

    //Creo el constructor para darle los valores iniciales para una vez que se llame el constructor se pasen los argumentos.
    public Usuario(String codigo, List<String> aficiones) {
        //this. para llamar a los atributos de la misma clase
        //guarda en el atributo codigo de este usuario el valor que me acaban de introducir por parametro.
        this.codigo = codigo;
        this.aficiones = aficiones;
    }

    //Creo los getter tanto de codigoUsuario como de Aficiones para que devuelvan los valores.
    public String getCodigo() {
        return codigo;
    }

    public List<String> getAficiones() {
        return aficiones;
    }
}

