/**
 Copyright 2014 Juan Heyns. All rights reserved.

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
package org.jdatepicker.constraints;

import org.jdatepicker.DateModel;

import java.util.Calendar;
import java.util.Date;

/**
 * This class provides a simple constraint to limit the selectable date to be inside a given range.
 *
 * @author Frankenberger Simon
 */
public class RangeConstraint implements DateSelectionConstraint {

    /**
     * The lower bound of selectable dates.
     */
    private final Calendar after;

    /**
     * The upper bound of selectable dates.
     */
    private final Calendar before;

    /**
     * Create a new constraint for values between (and excluding) the given dates.
     *
     * @param after  Lower bound for values, excluding.
     * @param before Upper bound for values, excluding.
     */
    public RangeConstraint(Calendar after, Calendar before) {
        this.after = after;
        this.before = before;

        // remove hours / minutes / seconds from dates
        cleanTime();
    }

    /**
     * Create a new constraint for values between the given dates.
     *
     * @param after  Lower bound for values, including.
     * @param before Upper bound for values, including.
     */
    public RangeConstraint(Date after, Date before) {
        Calendar _after = Calendar.getInstance();
        Calendar _before = Calendar.getInstance();

        _after.setTime(after);
        _before.setTime(before);

        this.after = _after;
        this.before = _before;

        // remove hours / minutes / seconds from dates
        cleanTime();
    }

    /**
     * Simple helper method to remove the time the date bounds.
     */
    private void cleanTime() {
        if (after != null) {
            after.set(Calendar.HOUR_OF_DAY, 0);
            after.set(Calendar.MINUTE, 0);
            after.set(Calendar.SECOND, 0);
            after.set(Calendar.MILLISECOND, 0);
        }

        if (before != null) {
            before.set(Calendar.HOUR_OF_DAY, 23);
            before.set(Calendar.MINUTE, 59);
            before.set(Calendar.SECOND, 59);
            before.set(Calendar.MILLISECOND, 999);
        }
    }

    public boolean isValidSelection(DateModel<?> model) {
        boolean result = true;

        if (model.isSelected() && after != null) {
            Calendar value = Calendar.getInstance();
            value.set(model.getYear(), model.getMonth(), model.getDay());
            value.set(Calendar.HOUR, 0);
            value.set(Calendar.MINUTE, 0);
            value.set(Calendar.SECOND, 0);
            value.set(Calendar.MILLISECOND, 0);
            result &= value.after(after);
        }
        if (model.isSelected() && before != null) {
            Calendar value = Calendar.getInstance();
            value.set(model.getYear(), model.getMonth(), model.getDay());
            value.set(Calendar.HOUR, 0);
            value.set(Calendar.MINUTE, 0);
            value.set(Calendar.SECOND, 0);
            value.set(Calendar.MILLISECOND, 0);
            result &= value.before(before);
        }

        return result;
    }

    @Override
    // Generated with eclipse depending on: after, before
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((after == null) ? 0 : after.hashCode());
        result = prime * result + ((before == null) ? 0 : before.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        RangeConstraint other = (RangeConstraint) obj;
        if (after == null) {
            if (other.after != null) {
                return false;
            }
        } else if (!after.equals(other.after)) {
            return false;
        }
        if (before == null) {
            if (other.before != null) {
                return false;
            }
        } else if (!before.equals(other.before)) {
            return false;
        }
        return true;
    }

}
