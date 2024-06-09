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
import org.haiku.haikudepotserver.dataobjects.PermissionUserPkg;
import org.haiku.haikudepotserver.dataobjects.PkgProminence;
import org.haiku.haikudepotserver.dataobjects.PkgSupplement;
import org.haiku.haikudepotserver.dataobjects.PkgUserRatingAggregate;
import org.haiku.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _Pkg was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Pkg extends AbstractDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final BaseProperty<Boolean> ACTIVE = PropertyFactory.createBase("active", Boolean.class);
    public static final DateProperty<Timestamp> CREATE_TIMESTAMP = PropertyFactory.createDate("createTimestamp", Timestamp.class);
    public static final DateProperty<Timestamp> MODIFY_TIMESTAMP = PropertyFactory.createDate("modifyTimestamp", Timestamp.class);
    public static final StringProperty<String> NAME = PropertyFactory.createString("name", String.class);
    public static final ListProperty<PermissionUserPkg> PERMISSION_USER_PKGS = PropertyFactory.createList("permissionUserPkgs", PermissionUserPkg.class);
    public static final ListProperty<PkgProminence> PKG_PROMINENCES = PropertyFactory.createList("pkgProminences", PkgProminence.class);
    public static final EntityProperty<PkgSupplement> PKG_SUPPLEMENT = PropertyFactory.createEntity("pkgSupplement", PkgSupplement.class);
    public static final ListProperty<PkgUserRatingAggregate> PKG_USER_RATING_AGGREGATES = PropertyFactory.createList("pkgUserRatingAggregates", PkgUserRatingAggregate.class);

    protected Boolean active;
    protected Timestamp createTimestamp;
    protected Timestamp modifyTimestamp;
    protected String name;

    protected Object permissionUserPkgs;
    protected Object pkgProminences;
    protected Object pkgSupplement;
    protected Object pkgUserRatingAggregates;

    public void setActive(Boolean active) {
        beforePropertyWrite("active", this.active, active);
        this.active = active;
    }

    public Boolean getActive() {
        beforePropertyRead("active");
        return this.active;
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

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
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

    public void addToPkgProminences(PkgProminence obj) {
        addToManyTarget("pkgProminences", obj, true);
    }

    public void removeFromPkgProminences(PkgProminence obj) {
        removeToManyTarget("pkgProminences", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgProminence> getPkgProminences() {
        return (List<PkgProminence>)readProperty("pkgProminences");
    }

    public void setPkgSupplement(PkgSupplement pkgSupplement) {
        setToOneTarget("pkgSupplement", pkgSupplement, true);
    }

    public PkgSupplement getPkgSupplement() {
        return (PkgSupplement)readProperty("pkgSupplement");
    }

    public void addToPkgUserRatingAggregates(PkgUserRatingAggregate obj) {
        addToManyTarget("pkgUserRatingAggregates", obj, true);
    }

    public void removeFromPkgUserRatingAggregates(PkgUserRatingAggregate obj) {
        removeToManyTarget("pkgUserRatingAggregates", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<PkgUserRatingAggregate> getPkgUserRatingAggregates() {
        return (List<PkgUserRatingAggregate>)readProperty("pkgUserRatingAggregates");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "active":
                return this.active;
            case "createTimestamp":
                return this.createTimestamp;
            case "modifyTimestamp":
                return this.modifyTimestamp;
            case "name":
                return this.name;
            case "permissionUserPkgs":
                return this.permissionUserPkgs;
            case "pkgProminences":
                return this.pkgProminences;
            case "pkgSupplement":
                return this.pkgSupplement;
            case "pkgUserRatingAggregates":
                return this.pkgUserRatingAggregates;
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
            case "createTimestamp":
                this.createTimestamp = (Timestamp)val;
                break;
            case "modifyTimestamp":
                this.modifyTimestamp = (Timestamp)val;
                break;
            case "name":
                this.name = (String)val;
                break;
            case "permissionUserPkgs":
                this.permissionUserPkgs = val;
                break;
            case "pkgProminences":
                this.pkgProminences = val;
                break;
            case "pkgSupplement":
                this.pkgSupplement = val;
                break;
            case "pkgUserRatingAggregates":
                this.pkgUserRatingAggregates = val;
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
        out.writeObject(this.createTimestamp);
        out.writeObject(this.modifyTimestamp);
        out.writeObject(this.name);
        out.writeObject(this.permissionUserPkgs);
        out.writeObject(this.pkgProminences);
        out.writeObject(this.pkgSupplement);
        out.writeObject(this.pkgUserRatingAggregates);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.active = (Boolean)in.readObject();
        this.createTimestamp = (Timestamp)in.readObject();
        this.modifyTimestamp = (Timestamp)in.readObject();
        this.name = (String)in.readObject();
        this.permissionUserPkgs = in.readObject();
        this.pkgProminences = in.readObject();
        this.pkgSupplement = in.readObject();
        this.pkgUserRatingAggregates = in.readObject();
    }

}
