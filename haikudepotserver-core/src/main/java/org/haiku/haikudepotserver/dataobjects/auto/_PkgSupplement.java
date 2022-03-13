package org.haiku.haikudepotserver.dataobjects.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.List;

import org.apache.cayenne.exp.Property;
import org.haiku.haikudepotserver.dataobjects.Pkg;
import org.haiku.haikudepotserver.dataobjects.PkgChangelog;
import org.haiku.haikudepotserver.dataobjects.PkgIcon;
import org.haiku.haikudepotserver.dataobjects.PkgLocalization;
import org.haiku.haikudepotserver.dataobjects.PkgPkgCategory;
import org.haiku.haikudepotserver.dataobjects.PkgScreenshot;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _PkgSupplement was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PkgSupplement extends AbstractDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> BASE_PKG_NAME = Property.create("basePkgName", String.class);
    public static final Property<Timestamp> CREATE_TIMESTAMP = Property.create("createTimestamp", Timestamp.class);
    public static final Property<Timestamp> ICON_MODIFY_TIMESTAMP = Property.create("iconModifyTimestamp", Timestamp.class);
    public static final Property<Timestamp> MODIFY_TIMESTAMP = Property.create("modifyTimestamp", Timestamp.class);
    public static final Property<List<PkgChangelog>> PKG_CHANGELOGS = Property.create("pkgChangelogs", List.class);
    public static final Property<List<PkgIcon>> PKG_ICONS = Property.create("pkgIcons", List.class);
    public static final Property<List<PkgLocalization>> PKG_LOCALIZATIONS = Property.create("pkgLocalizations", List.class);
    public static final Property<List<PkgPkgCategory>> PKG_PKG_CATEGORIES = Property.create("pkgPkgCategories", List.class);
    public static final Property<List<PkgScreenshot>> PKG_SCREENSHOTS = Property.create("pkgScreenshots", List.class);
    public static final Property<List<Pkg>> PKGS = Property.create("pkgs", List.class);

    protected String basePkgName;
    protected Timestamp createTimestamp;
    protected Timestamp iconModifyTimestamp;
    protected Timestamp modifyTimestamp;

    protected Object pkgChangelogs;
    protected Object pkgIcons;
    protected Object pkgLocalizations;
    protected Object pkgPkgCategories;
    protected Object pkgScreenshots;
    protected Object pkgs;

    public void setBasePkgName(String basePkgName) {
        beforePropertyWrite("basePkgName", this.basePkgName, basePkgName);
        this.basePkgName = basePkgName;
    }

    public String getBasePkgName() {
        beforePropertyRead("basePkgName");
        return this.basePkgName;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        beforePropertyWrite("createTimestamp", this.createTimestamp, createTimestamp);
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getCreateTimestamp() {
        beforePropertyRead("createTimestamp");
        return this.createTimestamp;
    }

    public void setIconModifyTimestamp(Timestamp iconModifyTimestamp) {
        beforePropertyWrite("iconModifyTimestamp", this.iconModifyTimestamp, iconModifyTimestamp);
        this.iconModifyTimestamp = iconModifyTimestamp;
    }

    public Timestamp getIconModifyTimestamp() {
        beforePropertyRead("iconModifyTimestamp");
        return this.iconModifyTimestamp;
    }

    public void setModifyTimestamp(Timestamp modifyTimestamp) {
        beforePropertyWrite("modifyTimestamp", this.modifyTimestamp, modifyTimestamp);
        this.modifyTimestamp = modifyTimestamp;
    }

    public Timestamp getModifyTimestamp() {
        beforePropertyRead("modifyTimestamp");
        return this.modifyTimestamp;
    }

    public void addToPkgChangelogs(PkgChangelog obj) {
        addToManyTarget("pkgChangelogs", obj, true);
    }

    public void removeFromPkgChangelogs(PkgChangelog obj) {
        removeToManyTarget("pkgChangelogs", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgChangelog> getPkgChangelogs() {
        return (List<PkgChangelog>)readProperty("pkgChangelogs");
    }

    public void addToPkgIcons(PkgIcon obj) {
        addToManyTarget("pkgIcons", obj, true);
    }

    public void removeFromPkgIcons(PkgIcon obj) {
        removeToManyTarget("pkgIcons", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgIcon> getPkgIcons() {
        return (List<PkgIcon>)readProperty("pkgIcons");
    }

    public void addToPkgLocalizations(PkgLocalization obj) {
        addToManyTarget("pkgLocalizations", obj, true);
    }

    public void removeFromPkgLocalizations(PkgLocalization obj) {
        removeToManyTarget("pkgLocalizations", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgLocalization> getPkgLocalizations() {
        return (List<PkgLocalization>)readProperty("pkgLocalizations");
    }

    public void addToPkgPkgCategories(PkgPkgCategory obj) {
        addToManyTarget("pkgPkgCategories", obj, true);
    }

    public void removeFromPkgPkgCategories(PkgPkgCategory obj) {
        removeToManyTarget("pkgPkgCategories", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgPkgCategory> getPkgPkgCategories() {
        return (List<PkgPkgCategory>)readProperty("pkgPkgCategories");
    }

    public void addToPkgScreenshots(PkgScreenshot obj) {
        addToManyTarget("pkgScreenshots", obj, true);
    }

    public void removeFromPkgScreenshots(PkgScreenshot obj) {
        removeToManyTarget("pkgScreenshots", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgScreenshot> getPkgScreenshots() {
        return (List<PkgScreenshot>)readProperty("pkgScreenshots");
    }

    public void addToPkgs(Pkg obj) {
        addToManyTarget("pkgs", obj, true);
    }

    public void removeFromPkgs(Pkg obj) {
        removeToManyTarget("pkgs", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Pkg> getPkgs() {
        return (List<Pkg>)readProperty("pkgs");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "basePkgName":
                return this.basePkgName;
            case "createTimestamp":
                return this.createTimestamp;
            case "iconModifyTimestamp":
                return this.iconModifyTimestamp;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "pkgChangelogs":
                return this.pkgChangelogs;
            case "pkgIcons":
                return this.pkgIcons;
            case "pkgLocalizations":
                return this.pkgLocalizations;
            case "pkgPkgCategories":
                return this.pkgPkgCategories;
            case "pkgScreenshots":
                return this.pkgScreenshots;
            case "pkgs":
                return this.pkgs;
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
            case "basePkgName":
                this.basePkgName = (String)val;
                break;
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "iconModifyTimestamp":
                this.iconModifyTimestamp = (Timestamp)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "pkgChangelogs":
                this.pkgChangelogs = val;
                break;
            case "pkgIcons":
                this.pkgIcons = val;
                break;
            case "pkgLocalizations":
                this.pkgLocalizations = val;
                break;
            case "pkgPkgCategories":
                this.pkgPkgCategories = val;
                break;
            case "pkgScreenshots":
                this.pkgScreenshots = val;
                break;
            case "pkgs":
                this.pkgs = val;
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
        out.writeObject(this.basePkgName);
        out.writeObject(this.createTimestamp);
        out.writeObject(this.iconModifyTimestamp);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.pkgChangelogs);
        out.writeObject(this.pkgIcons);
        out.writeObject(this.pkgLocalizations);
        out.writeObject(this.pkgPkgCategories);
        out.writeObject(this.pkgScreenshots);
        out.writeObject(this.pkgs);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.basePkgName = (String)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.iconModifyTimestamp = (Timestamp)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.pkgChangelogs = in.readObject();
        this.pkgIcons = in.readObject();
        this.pkgLocalizations = in.readObject();
        this.pkgPkgCategories = in.readObject();
        this.pkgScreenshots = in.readObject();
        this.pkgs = in.readObject();
    }

}
