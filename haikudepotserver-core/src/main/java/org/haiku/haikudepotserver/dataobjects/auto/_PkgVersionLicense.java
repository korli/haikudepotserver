package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.exp.property.EntityProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;
import org.haiku.haikudepotserver.dataobjects.PkgVersion;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _PkgVersionLicense was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PkgVersionLicense extends AbstractDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> BODY = PropertyFactory.createString("body", String.class);
    public static final EntityProperty<PkgVersion> PKG_VERSION = PropertyFactory.createEntity("pkgVersion", PkgVersion.class);

    protected String body;

    protected Object pkgVersion;

    public void setBody(String body) {
        beforePropertyWrite("body", this.body, body);
        this.body = body;
    }

    public String getBody() {
        beforePropertyRead("body");
        return this.body;
    }

    public void setPkgVersion(PkgVersion pkgVersion) {
        setToOneTarget("pkgVersion", pkgVersion, true);
    }

    public PkgVersion getPkgVersion() {
        return (PkgVersion)readProperty("pkgVersion");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "body":
                return this.body;
            case "pkgVersion":
                return this.pkgVersion;
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
            case "body":
                this.body = (String)val;
                break;
            case "pkgVersion":
                this.pkgVersion = val;
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
        out.writeObject(this.body);
        out.writeObject(this.pkgVersion);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.body = (String)in.readObject();
        this.pkgVersion = in.readObject();
    }

}
