/*
 * Copyright 2010 frickelbude.de
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package de.frickelbude.wicket;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Generated;

import joist.sourcegen.GClass;
import joist.sourcegen.GField;

/**
 * Generates a wicket id binding class from a class name and the field names.
 * 
 * @author Ole Langbehn (ole.langbehn@googlemail.com) (initial creation)
 */
public class BindingGenerator {

    private final String _name;
    private final Collection<String> _fields;
    private GClass _gClass;

    /**
     * Constructor.
     * 
     * @param name
     * @param fields
     */
    public BindingGenerator( final String name, final Collection<String> fields ) {
        _name = name;
        _fields = fields;
        constructClass();
    }

    private void constructClass() {
        _gClass = new GClass( _name );
        _gClass.getConstructor().setPrivate().body.line( "// creation of instances is superfluous" );
        _gClass.addImports( Generated.class );
        _gClass.addAnnotation( "@Generated(value = \"" + IdBindingProcessor.class.getName() + "\", date = \""
                + new SimpleDateFormat( "yyyy/MM/dd  HH:mm" ).format( new Date() ) + "\")" );
        for ( final String field : _fields ) {
            final GField gField = _gClass.getField( field );
            gField.initialValue( "\"{}\"", field );
            gField.setStatic().setFinal().setPublic();
            gField.type( String.class );
        }
    }

    /**
     * Returns the source code of the represented class.
     */
    public String getCode() {
        return _gClass.toCode();
    }

    /**
     * Returns the class name of the represented class.
     */
    public String getClassName() {
        return _gClass.getFullClassNameWithoutGeneric();
    }

}