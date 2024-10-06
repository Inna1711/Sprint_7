package models.CourierCreate;

public class Response {
    private boolean ok;
    private String message;


    public boolean getOk() {
        return ok;
    }

    public String getMessage(){
        return message;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
