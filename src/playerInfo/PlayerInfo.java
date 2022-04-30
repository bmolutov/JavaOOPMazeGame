package playerInfo;

public class PlayerInfo<T> implements Comparable<PlayerInfo<T>> {
    private T login;
    private String name;
    private long record;

    public PlayerInfo(T newLogin, String newName, long newRecord) {
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
    public long getRecord() {
        return this.record;
    }
    @Override
    public int compareTo(PlayerInfo anotherPlayerInfo) {
        return (this.record > anotherPlayerInfo.record) ? 1 : (this.record < anotherPlayerInfo.record) ? -1 : 0;
    }
}
