package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import org.apache.cayenne.exp.Property;
import org.haiku.haikudepotserver.dataobjects.PkgSupplement;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _PkgChangelog was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PkgChangelog extends AbstractDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> CONTENT = Property.create("content", String.class);
    public static final Property<Timestamp> CREATE_TIMESTAMP = Property.create("createTimestamp", Timestamp.class);
    public static final Property<Timestamp> MODIFY_TIMESTAMP = Property.create("modifyTimestamp", Timestamp.class);
    public static final Property<PkgSupplement> PKG_SUPPLEMENT = Property.create("pkgSupplement", PkgSupplement.class);

    protected String content;
    protected Timestamp createTimestamp;
    protected Timestamp modifyTimestamp;

    protected Object pkgSupplement;

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

    public void setPkgSupplement(PkgSupplement pkgSupplement) {
        setToOneTarget("pkgSupplement", pkgSupplement, true);
    }

    public PkgSupplement getPkgSupplement() {
        return (PkgSupplement)readProperty("pkgSupplement");
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
            case "pkgSupplement":
                return this.pkgSupplement;
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
            case "pkgSupplement":
                this.pkgSupplement = val;
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
        out.writeObject(this.pkgSupplement);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.content = (String)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.pkgSupplement = in.readObject();
    }

}
