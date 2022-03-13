package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import org.apache.cayenne.exp.Property;
import org.haiku.haikudepotserver.dataobjects.NaturalLanguage;
import org.haiku.haikudepotserver.dataobjects.PkgSupplement;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _PkgLocalization was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PkgLocalization extends AbstractDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<Timestamp> CREATE_TIMESTAMP = Property.create("createTimestamp", Timestamp.class);
    public static final Property<String> DESCRIPTION = Property.create("description", String.class);
    public static final Property<Timestamp> MODIFY_TIMESTAMP = Property.create("modifyTimestamp", Timestamp.class);
    public static final Property<String> SUMMARY = Property.create("summary", String.class);
    public static final Property<String> TITLE = Property.create("title", String.class);
    public static final Property<NaturalLanguage> NATURAL_LANGUAGE = Property.create("naturalLanguage", NaturalLanguage.class);
    public static final Property<PkgSupplement> PKG_SUPPLEMENT = Property.create("pkgSupplement", PkgSupplement.class);

    protected Timestamp createTimestamp;
    protected String description;
    protected Timestamp modifyTimestamp;
    protected String summary;
    protected String title;

    protected Object naturalLanguage;
    protected Object pkgSupplement;

    public void setCreateTimestamp(Timestamp createTimestamp) {
        beforePropertyWrite("createTimestamp", this.createTimestamp, createTimestamp);
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getCreateTimestamp() {
        beforePropertyRead("createTimestamp");
        return this.createTimestamp;
    }

    public void setDescription(String description) {
        beforePropertyWrite("description", this.description, description);
        this.description = description;
    }

    public String getDescription() {
        beforePropertyRead("description");
        return this.description;
    }

    public void setModifyTimestamp(Timestamp modifyTimestamp) {
        beforePropertyWrite("modifyTimestamp", this.modifyTimestamp, modifyTimestamp);
        this.modifyTimestamp = modifyTimestamp;
    }

    public Timestamp getModifyTimestamp() {
        beforePropertyRead("modifyTimestamp");
        return this.modifyTimestamp;
    }

    public void setSummary(String summary) {
        beforePropertyWrite("summary", this.summary, summary);
        this.summary = summary;
    }

    public String getSummary() {
        beforePropertyRead("summary");
        return this.summary;
    }

    public void setTitle(String title) {
        beforePropertyWrite("title", this.title, title);
        this.title = title;
    }

    public String getTitle() {
        beforePropertyRead("title");
        return this.title;
    }

    public void setNaturalLanguage(NaturalLanguage naturalLanguage) {
        setToOneTarget("naturalLanguage", naturalLanguage, true);
    }

    public NaturalLanguage getNaturalLanguage() {
        return (NaturalLanguage)readProperty("naturalLanguage");
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
            case "createTimestamp":
                return this.createTimestamp;
            case "description":
                return this.description;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "summary":
                return this.summary;
            case "title":
                return this.title;
            case "naturalLanguage":
                return this.naturalLanguage;
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
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "description":
                this.description = (String)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "summary":
                this.summary = (String)val;
                break;
            case "title":
                this.title = (String)val;
                break;
            case "naturalLanguage":
                this.naturalLanguage = val;
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
        out.writeObject(this.createTimestamp);
        out.writeObject(this.description);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.summary);
        out.writeObject(this.title);
        out.writeObject(this.naturalLanguage);
        out.writeObject(this.pkgSupplement);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.createTimestamp = (Timestamp)in.readObject();
        this.description = (String)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.summary = (String)in.readObject();
        this.title = (String)in.readObject();
        this.naturalLanguage = in.readObject();
        this.pkgSupplement = in.readObject();
    }

}
