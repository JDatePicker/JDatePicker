/**
 Copyright 2004 Juan Heyns. All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are
 permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of
 conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list
 of conditions and the following disclaimer in the documentation and/or other materials
 provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the
 authors and should not be interpreted as representing official policies, either expressed
 or implied, of Juan Heyns.
 */
package org.jdatepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public final class ComponentFormatDefaults {

    private static ComponentFormatDefaults instance;

    public static ComponentFormatDefaults getInstance() {
        if (instance == null) {
            instance = new ComponentFormatDefaults();
        }
        return instance;
    }

    public enum Key {
        TODAY_SELECTOR,
        DOW_HEADER,
        MONTH_SELECTOR,
        SELECTED_DATE_FIELD
    }

    private Map<Key, DateFormat> formats;

    private ComponentFormatDefaults() {
        formats = new HashMap<Key, DateFormat>();
        formats.put(Key.TODAY_SELECTOR, SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM));
        formats.put(Key.DOW_HEADER, new SimpleDateFormat("EE"));
        formats.put(Key.MONTH_SELECTOR, new SimpleDateFormat("MMMM"));
        formats.put(Key.SELECTED_DATE_FIELD, SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM));
    }

    public DateFormat getFormat(Key key) {
        return formats.get(key);
    }

    public void setFormat(Key key, DateFormat format) {
        formats.put(key, format);
    }

}
