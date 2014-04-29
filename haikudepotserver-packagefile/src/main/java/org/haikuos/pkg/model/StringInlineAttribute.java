/*
 * Copyright 2013, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.pkg.model;

import com.google.common.base.Preconditions;
import org.haikuos.pkg.AttributeContext;
import org.haikuos.pkg.HpkException;

/**
 * <p>This type of attribute is a string.  The string is supplied in the stream of attributes so this attribute will
 * carry the string.</p>
 */

public class StringInlineAttribute extends StringAttribute {

    private String stringValue;

    public StringInlineAttribute(AttributeId attributeId, String stringValue) {
        super(attributeId);
        Preconditions.checkNotNull(stringValue);
        this.stringValue = stringValue;
    }

    @Override
    public String getValue(AttributeContext context)  throws HpkException {
        return stringValue;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.STRING;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringInlineAttribute that = (StringInlineAttribute) o;

        if (!stringValue.equals(that.stringValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return stringValue.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s : %s",super.toString(),stringValue.toString());
    }

}
