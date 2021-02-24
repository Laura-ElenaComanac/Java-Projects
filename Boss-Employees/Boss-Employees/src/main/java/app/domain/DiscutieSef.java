package app.domain;

import java.time.LocalDateTime;

public class DiscutieSef extends Discutie{
    private String destinatar;

    public DiscutieSef(String numeExpeditor, String mesaj, LocalDateTime ora, String destinatar) {
        super(numeExpeditor, mesaj, ora);
        this.destinatar = destinatar;
    }

    public String getDestinatar() {
        return destinatar;
    }

    public void setDestinatar(String destinatar) {
        this.destinatar = destinatar;
    }
}
