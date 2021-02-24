package app.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Discutie extends Entity<Integer>{
    private String numeExpeditor;
    private String mesaj;
    private LocalDateTime ora;

    static AtomicInteger identity = new AtomicInteger(0);

    public Discutie(String numeExpeditor, String mesaj, LocalDateTime ora){
        this.numeExpeditor = numeExpeditor;
        this.mesaj = mesaj;
        this.ora = ora;

        this.setId(identity.incrementAndGet());
    }

    public String getNumeExpeditor() {
        return numeExpeditor;
    }

    public void setNumeExpeditor(String numeExpeditor) {
        this.numeExpeditor = numeExpeditor;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public LocalDateTime getOra() {
        return ora;
    }

    public void setOra(LocalDateTime ora) {
        this.ora = ora;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Discutie))
            return false;
        Discutie discutie = (Discutie) obj;
        return Objects.equals(getNumeExpeditor(), discutie.getNumeExpeditor()) &&
                Objects.equals(getMesaj(), discutie.getMesaj()) &&
                Objects.equals(getOra(), discutie.getOra());
    }

    @Override
    public String toString() {
        return numeExpeditor + ' ' + mesaj + ' ' + ora;
    }

}
