package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import org.apache.cayenne.exp.property.DateProperty;
import org.apache.cayenne.exp.property.EntityProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;
import org.haiku.haikudepotserver.dataobjects.PkgSupplement;
import org.haiku.haikudepotserver.dataobjects.User;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _PkgSupplementModification was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PkgSupplementModification extends AbstractDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> CONTENT = PropertyFactory.createString("content", String.class);
    public static final DateProperty<Timestamp> CREATE_TIMESTAMP = PropertyFactory.createDate("createTimestamp", Timestamp.class);
    public static final DateProperty<Timestamp> MODIFY_TIMESTAMP = PropertyFactory.createDate("modifyTimestamp", Timestamp.class);
    public static final StringProperty<String> ORIGIN_SYSTEM_DESCRIPTION = PropertyFactory.createString("originSystemDescription", String.class);
    public static final StringProperty<String> USER_DESCRIPTION = PropertyFactory.createString("userDescription", String.class);
    public static final EntityProperty<PkgSupplement> PKG_SUPPLEMENT = PropertyFactory.createEntity("pkgSupplement", PkgSupplement.class);
    public static final EntityProperty<User> USER = PropertyFactory.createEntity("user", User.class);

    protected String content;
    protected Timestamp createTimestamp;
    protected Timestamp modifyTimestamp;
    protected String originSystemDescription;
    protected String userDescription;

    protected Object pkgSupplement;
    protected Object user;

    public void setContent(String content) {
        beforePropertyWrite("content", this.content, content);
        this.content = content;
    }

    public String getContent() {
        beforePropertyRead("content");
        return this.content;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        beforePropertyWrite("createTimestamp", this.createTimestamp, createTimestamp);
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getCreateTimestamp() {
        beforePropertyRead("createTimestamp");
        return this.createTimestamp;
    }

    public void setModifyTimestamp(Timestamp modifyTimestamp) {
        beforePropertyWrite("modifyTimestamp", this.modifyTimestamp, modifyTimestamp);
        this.modifyTimestamp = modifyTimestamp;
    }

    public Timestamp getModifyTimestamp() {
        beforePropertyRead("modifyTimestamp");
        return this.modifyTimestamp;
    }

    public void setOriginSystemDescription(String originSystemDescription) {
        beforePropertyWrite("originSystemDescription", this.originSystemDescription, originSystemDescription);
        this.originSystemDescription = originSystemDescription;
    }

    public String getOriginSystemDescription() {
        beforePropertyRead("originSystemDescription");
        return this.originSystemDescription;
    }

    public void setUserDescription(String userDescription) {
        beforePropertyWrite("userDescription", this.userDescription, userDescription);
        this.userDescription = userDescription;
    }

    public String getUserDescription() {
        beforePropertyRead("userDescription");
        return this.userDescription;
    }

    public void setPkgSupplement(PkgSupplement pkgSupplement) {
        setToOneTarget("pkgSupplement", pkgSupplement, true);
    }

    public PkgSupplement getPkgSupplement() {
        return (PkgSupplement)readProperty("pkgSupplement");
    }

    public void setUser(User user) {
        setToOneTarget("user", user, true);
    }

    public User getUser() {
        return (User)readProperty("user");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "content":
                return this.content;
            case "createTimestamp":
                return this.createTimestamp;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "originSystemDescription":
                return this.originSystemDescription;
            case "userDescription":
                return this.userDescription;
            case "pkgSupplement":
                return this.pkgSupplement;
            case "user":
                return this.user;
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
            case "content":
                this.content = (String)val;
                break;
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "originSystemDescription":
                this.originSystemDescription = (String)val;
                break;
            case "userDescription":
                this.userDescription = (String)val;
                break;
            case "pkgSupplement":
                this.pkgSupplement = val;
                break;
            case "user":
                this.user = val;
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
        out.writeObject(this.content);
        out.writeObject(this.createTimestamp);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.originSystemDescription);
        out.writeObject(this.userDescription);
        out.writeObject(this.pkgSupplement);
        out.writeObject(this.user);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.content = (String)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.originSystemDescription = (String)in.readObject();
        this.userDescription = (String)in.readObject();
        this.pkgSupplement = in.readObject();
        this.user = in.readObject();
    }

}
