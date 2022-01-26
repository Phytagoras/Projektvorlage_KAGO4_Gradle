package my_project.model;

public class ConnectedEntity {
    int id;
    boolean init = false;
    String name;
    int helpCounter = 0;
    String ip;
    public ConnectedEntity(String pIp, int pId) {
        id = pId;
        ip = pIp;
    }

    public int getId() {
        return id;
    }

    public boolean isInit() {
        return init;
    }

    public String getName() {
        return name;
    }

    public int getHelpCounter() {
        return helpCounter;
    }

    public String getIp() {
        return ip;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setInit(boolean init){
        this.init = init;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHelpCounter(int helpCounter){
        this.helpCounter = helpCounter;
    }

    public void setIp(String ip){
        this.ip = ip;
    }
}
