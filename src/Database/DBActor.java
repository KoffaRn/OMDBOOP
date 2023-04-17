package Database;

import javax.management.relation.Role;

enum Roles {
    ACTOR,
    DIRECTOR,
    WRITER
}
public class DBActor {
    private String name;
    private String imdbId;
    private Roles dbActorRole;
    public DBActor() {

    }
    public DBActor(String name, String imdbId, String dbActorRole) {
        this.name = name;
        this.imdbId = imdbId;
        this.dbActorRole = Roles.valueOf(dbActorRole);
    }
    public Roles getDbActorRole() {
        return dbActorRole;
    }

    public void setDbActorRole(Roles dbActorRole) {
        this.dbActorRole = dbActorRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String toString() {
        return "[" + dbActorRole + "] [" + name + "] ";
    }
}