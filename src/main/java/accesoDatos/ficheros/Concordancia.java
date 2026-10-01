package accesoDatos.ficheros;

import java.util.List;

public class Concordancia {
    private String usuario1;
    private String usuario2;
    private List<String> aficionesComunes;

    public Concordancia (String usuario1, String usuario2, List<String> aficionesComunes){
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.aficionesComunes = aficionesComunes;
    }

    public int getNumeroConcordancias() {
        return aficionesComunes.size();
    }

    public String aTextoFichero() {
        return usuario1 + " - " + usuario2 + " " + String.join(" ", aficionesComunes);
    }
}
