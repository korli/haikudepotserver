package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.List;

import org.apache.cayenne.exp.property.BaseProperty;
import org.apache.cayenne.exp.property.DateProperty;
import org.apache.cayenne.exp.property.EntityProperty;
import org.apache.cayenne.exp.property.ListProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;
import org.haiku.haikudepotserver.dataobjects.NaturalLanguage;
import org.haiku.haikudepotserver.dataobjects.PermissionUserPkg;
import org.haiku.haikudepotserver.dataobjects.UserPasswordResetToken;
import org.haiku.haikudepotserver.dataobjects.UserUsageConditionsAgreement;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends AbstractDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final BaseProperty<Boolean> ACTIVE = PropertyFactory.createBase("active", Boolean.class);
    public static final BaseProperty<Boolean> CAN_MANAGE_USERS = PropertyFactory.createBase("canManageUsers", Boolean.class);
    public static final DateProperty<Timestamp> CREATE_TIMESTAMP = PropertyFactory.createDate("createTimestamp", Timestamp.class);
    public static final StringProperty<String> EMAIL = PropertyFactory.createString("email", String.class);
    public static final BaseProperty<Boolean> IS_ROOT = PropertyFactory.createBase("isRoot", Boolean.class);
    public static final DateProperty<Timestamp> LAST_AUTHENTICATION_TIMESTAMP = PropertyFactory.createDate("lastAuthenticationTimestamp", Timestamp.class);
    public static final DateProperty<Timestamp> MODIFY_TIMESTAMP = PropertyFactory.createDate("modifyTimestamp", Timestamp.class);
    public static final StringProperty<String> NICKNAME = PropertyFactory.createString("nickname", String.class);
    public static final StringProperty<String> PASSWORD_HASH = PropertyFactory.createString("passwordHash", String.class);
    public static final StringProperty<String> PASSWORD_SALT = PropertyFactory.createString("passwordSalt", String.class);
    public static final EntityProperty<NaturalLanguage> NATURAL_LANGUAGE = PropertyFactory.createEntity("naturalLanguage", NaturalLanguage.class);
    public static final ListProperty<PermissionUserPkg> PERMISSION_USER_PKGS = PropertyFactory.createList("permissionUserPkgs", PermissionUserPkg.class);
    public static final ListProperty<UserPasswordResetToken> USER_PASSWORD_RESET_TOKENS = PropertyFactory.createList("userPasswordResetTokens", UserPasswordResetToken.class);
    public static final ListProperty<UserUsageConditionsAgreement> USER_USAGE_CONDITIONS_AGREEMENTS = PropertyFactory.createList("userUsageConditionsAgreements", UserUsageConditionsAgreement.class);

    protected Boolean active;
    protected Boolean canManageUsers;
    protected Timestamp createTimestamp;
    protected String email;
    protected Boolean isRoot;
    protected Timestamp lastAuthenticationTimestamp;
    protected Timestamp modifyTimestamp;
    protected String nickname;
    protected String passwordHash;
    protected String passwordSalt;

    protected Object naturalLanguage;
    protected Object permissionUserPkgs;
    protected Object userPasswordResetTokens;
    protected Object userUsageConditionsAgreements;

    public void setActive(Boolean active) {
        beforePropertyWrite("active", this.active, active);
        this.active = active;
    }

    public Boolean getActive() {
        beforePropertyRead("active");
        return this.active;
    }

    public void setCanManageUsers(Boolean canManageUsers) {
        beforePropertyWrite("canManageUsers", this.canManageUsers, canManageUsers);
        this.canManageUsers = canManageUsers;
    }

    public Boolean getCanManageUsers() {
        beforePropertyRead("canManageUsers");
        return this.canManageUsers;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        beforePropertyWrite("createTimestamp", this.createTimestamp, createTimestamp);
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getCreateTimestamp() {
        beforePropertyRead("createTimestamp");
        return this.createTimestamp;
    }

    public void setEmail(String email) {
        beforePropertyWrite("email", this.email, email);
        this.email = email;
    }

    public String getEmail() {
        beforePropertyRead("email");
        return this.email;
    }

    public void setIsRoot(Boolean isRoot) {
        beforePropertyWrite("isRoot", this.isRoot, isRoot);
        this.isRoot = isRoot;
    }

    public Boolean getIsRoot() {
        beforePropertyRead("isRoot");
        return this.isRoot;
    }

    public void setLastAuthenticationTimestamp(Timestamp lastAuthenticationTimestamp) {
        beforePropertyWrite("lastAuthenticationTimestamp", this.lastAuthenticationTimestamp, lastAuthenticationTimestamp);
        this.lastAuthenticationTimestamp = lastAuthenticationTimestamp;
    }

    public Timestamp getLastAuthenticationTimestamp() {
        beforePropertyRead("lastAuthenticationTimestamp");
        return this.lastAuthenticationTimestamp;
    }

    public void setModifyTimestamp(Timestamp modifyTimestamp) {
        beforePropertyWrite("modifyTimestamp", this.modifyTimestamp, modifyTimestamp);
        this.modifyTimestamp = modifyTimestamp;
    }

    public Timestamp getModifyTimestamp() {
        beforePropertyRead("modifyTimestamp");
        return this.modifyTimestamp;
    }

    public void setNickname(String nickname) {
        beforePropertyWrite("nickname", this.nickname, nickname);
        this.nickname = nickname;
    }

    public String getNickname() {
        beforePropertyRead("nickname");
        return this.nickname;
    }

    public void setPasswordHash(String passwordHash) {
        beforePropertyWrite("passwordHash", this.passwordHash, passwordHash);
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        beforePropertyRead("passwordHash");
        return this.passwordHash;
    }

    public void setPasswordSalt(String passwordSalt) {
        beforePropertyWrite("passwordSalt", this.passwordSalt, passwordSalt);
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt() {
        beforePropertyRead("passwordSalt");
        return this.passwordSalt;
    }

    public void setNaturalLanguage(NaturalLanguage naturalLanguage) {
        setToOneTarget("naturalLanguage", naturalLanguage, true);
    }

    public NaturalLanguage getNaturalLanguage() {
        return (NaturalLanguage)readProperty("naturalLanguage");
    }

    public void addToPermissionUserPkgs(PermissionUserPkg obj) {
        addToManyTarget("permissionUserPkgs", obj, true);
    }

    public void removeFromPermissionUserPkgs(PermissionUserPkg obj) {
        removeToManyTarget("permissionUserPkgs", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PermissionUserPkg> getPermissionUserPkgs() {
        return (List<PermissionUserPkg>)readProperty("permissionUserPkgs");
    }

    public void addToUserPasswordResetTokens(UserPasswordResetToken obj) {
        addToManyTarget("userPasswordResetTokens", obj, true);
    }

    public void removeFromUserPasswordResetTokens(UserPasswordResetToken obj) {
        removeToManyTarget("userPasswordResetTokens", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<UserPasswordResetToken> getUserPasswordResetTokens() {
        return (List<UserPasswordResetToken>)readProperty("userPasswordResetTokens");
    }

    public void addToUserUsageConditionsAgreements(UserUsageConditionsAgreement obj) {
        addToManyTarget("userUsageConditionsAgreements", obj, true);
    }

    public void removeFromUserUsageConditionsAgreements(UserUsageConditionsAgreement obj) {
        removeToManyTarget("userUsageConditionsAgreements", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<UserUsageConditionsAgreement> getUserUsageConditionsAgreements() {
        return (List<UserUsageConditionsAgreement>)readProperty("userUsageConditionsAgreements");
    }

    protected abstract void onPostAdd();

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "active":
                return this.active;
            case "canManageUsers":
                return this.canManageUsers;
            case "createTimestamp":
                return this.createTimestamp;
            case "email":
                return this.email;
            case "isRoot":
                return this.isRoot;
            case "lastAuthenticationTimestamp":
                return this.lastAuthenticationTimestamp;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "nickname":
                return this.nickname;
            case "passwordHash":
                return this.passwordHash;
            case "passwordSalt":
                return this.passwordSalt;
            case "naturalLanguage":
                return this.naturalLanguage;
            case "permissionUserPkgs":
                return this.permissionUserPkgs;
            case "userPasswordResetTokens":
                return this.userPasswordResetTokens;
            case "userUsageConditionsAgreements":
                return this.userUsageConditionsAgreements;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "active":
                this.active = (Boolean)val;
                break;
            case "canManageUsers":
                this.canManageUsers = (Boolean)val;
                break;
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "email":
                this.email = (String)val;
                break;
            case "isRoot":
                this.isRoot = (Boolean)val;
                break;
            case "lastAuthenticationTimestamp":
                this.lastAuthenticationTimestamp = (Timestamp)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "nickname":
                this.nickname = (String)val;
                break;
            case "passwordHash":
                this.passwordHash = (String)val;
                break;
            case "passwordSalt":
                this.passwordSalt = (String)val;
                break;
            case "naturalLanguage":
                this.naturalLanguage = val;
                break;
            case "permissionUserPkgs":
                this.permissionUserPkgs = val;
                break;
            case "userPasswordResetTokens":
                this.userPasswordResetTokens = val;
                break;
            case "userUsageConditionsAgreements":
                this.userUsageConditionsAgreements = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.active);
        out.writeObject(this.canManageUsers);
        out.writeObject(this.createTimestamp);
        out.writeObject(this.email);
        out.writeObject(this.isRoot);
        out.writeObject(this.lastAuthenticationTimestamp);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.nickname);
        out.writeObject(this.passwordHash);
        out.writeObject(this.passwordSalt);
        out.writeObject(this.naturalLanguage);
        out.writeObject(this.permissionUserPkgs);
        out.writeObject(this.userPasswordResetTokens);
        out.writeObject(this.userUsageConditionsAgreements);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.active = (Boolean)in.readObject();
        this.canManageUsers = (Boolean)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.email = (String)in.readObject();
        this.isRoot = (Boolean)in.readObject();
        this.lastAuthenticationTimestamp = (Timestamp)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.nickname = (String)in.readObject();
        this.passwordHash = (String)in.readObject();
        this.passwordSalt = (String)in.readObject();
        this.naturalLanguage = in.readObject();
        this.permissionUserPkgs = in.readObject();
        this.userPasswordResetTokens = in.readObject();
        this.userUsageConditionsAgreements = in.readObject();
    }

}
