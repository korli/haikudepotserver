package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import org.apache.cayenne.exp.property.BaseProperty;
import org.apache.cayenne.exp.property.DateProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _NaturalLanguage was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _NaturalLanguage extends AbstractDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> CODE = PropertyFactory.createString("code", String.class);
    public static final DateProperty<Timestamp> CREATE_TIMESTAMP = PropertyFactory.createDate("createTimestamp", Timestamp.class);
    public static final BaseProperty<Boolean> IS_POPULAR = PropertyFactory.createBase("isPopular", Boolean.class);
    public static final DateProperty<Timestamp> MODIFY_TIMESTAMP = PropertyFactory.createDate("modifyTimestamp", Timestamp.class);
    public static final StringProperty<String> NAME = PropertyFactory.createString("name", String.class);

    protected String code;
    protected Timestamp createTimestamp;
    protected Boolean isPopular;
    protected Timestamp modifyTimestamp;
    protected String name;


    public String getCode() {
        beforePropertyRead("code");
        return this.code;
    }

    public Timestamp getCreateTimestamp() {
        beforePropertyRead("createTimestamp");
        return this.createTimestamp;
    }

    public Boolean getIsPopular() {
        beforePropertyRead("isPopular");
        return this.isPopular;
    }

    public Timestamp getModifyTimestamp() {
        beforePropertyRead("modifyTimestamp");
        return this.modifyTimestamp;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "code":
                return this.code;
            case "createTimestamp":
                return this.createTimestamp;
            case "isPopular":
                return this.isPopular;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "name":
                return this.name;
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
            case "code":
                this.code = (String)val;
                break;
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "isPopular":
                this.isPopular = (Boolean)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "name":
                this.name = (String)val;
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
        out.writeObject(this.code);
        out.writeObject(this.createTimestamp);
        out.writeObject(this.isPopular);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.name);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.code = (String)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.isPopular = (Boolean)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.name = (String)in.readObject();
    }

}
