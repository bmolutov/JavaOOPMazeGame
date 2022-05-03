package playerInfo;


public class PlayerInfo<T> implements Comparable<PlayerInfo<T>> {
    private T login;
    private String name;
    private Long record;

    public PlayerInfo(T newLogin, String newName, Long newRecord) {
        this.login = newLogin;
        this.name = newName;
        this.record = newRecord;
    }

    public T getLogin() {
        return this.login;
    }
    public String getName() {
        return this.name;
    }
    public Long getRecord() {
        return this.record;
    }
    public void setRecord(Long newRecord) {
        this.record = newRecord;
    }
    @Override
    public int compareTo(PlayerInfo<T> anotherPlayerInfo) {
        return (this.record > anotherPlayerInfo.record) ? 1 : (this.record < anotherPlayerInfo.record) ? -1 : 0;
    }
}