import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class DataUtilizador implements Serializable
{
    private int offsetDiasF;

    public DataUtilizador()
    {
        this.offsetDiasF = 0;
    }

    // adicionar dias no futuro à data atual
    public void setData(int diasNoFuturo)
    {
        this.offsetDiasF += diasNoFuturo;
    }
    // fazer reset à data (dias no futuro passa a 0, data passa à atual)
    public void resetData()
    {
        this.offsetDiasF = 0;
    }
    // data atual tendo em conta o offset
    public LocalDateTime now()
    {
        return LocalDateTime.now().plus(this.offsetDiasF, TimeUnit.DAYS.toChronoUnit());
    }
}
