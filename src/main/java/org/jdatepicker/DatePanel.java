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

import java.awt.Color;
import javax.swing.JComponent;

public abstract class DatePanel extends JComponent implements DateComponent {
    private static boolean showTodayBorder;

    /**
     * Sets the visibilty of the Year navigation buttons. Defaults to false.
     *
     * @param showYearButtons show the button?
     */
    public abstract void setShowYearButtons(boolean showYearButtons);

    /**
     * Is the year navigation buttons active.
     *
     * @return visiblity of the year
     */
    public abstract boolean isShowYearButtons();

    /**
     * This changes the behaviour of the control to require a double click on
     * actionable clicks. If this is set the ActionEvent will only be fired
     * when double clicked on a date. Defaults to false.
     *
     * @param doubleClickAction use double clicks?
     */
    public abstract void setDoubleClickAction(boolean doubleClickAction);

    /**
     * @return Is a double click required to fire a ActionEvent.
     */
    public abstract boolean isDoubleClickAction();
    
    /**
     * Sets the visability of a red border that marks the number of 
     * today instead of a colored number. Defaults to false.
     * 
     * @param showTodayBorder show the border?
     */
    public static final void setStaticTodayBorder(boolean showTodayBorder){
        DatePanel.showTodayBorder = showTodayBorder;
        setTodayNumberColorSchema(showTodayBorder);
    }
    
    /**
     * @return is a border shown to mark today.
     */
    public static final boolean isShowTodayBorder(){
        return DatePanel.showTodayBorder;
    }
    
    private static void setTodayNumberColorSchema(boolean hasBorder){
            ComponentColorDefaults colorSchema = ComponentColorDefaults.getInstance();
            //set today's number color statically, depending on visibility of a surrounding border
            if (hasBorder) {
                Color everyDayColor = colorSchema
                        .getColor(ComponentColorDefaults.Key.FG_GRID_THIS_MONTH);
                Color everyDaySelectedColor = colorSchema
                        .getColor(ComponentColorDefaults.Key.FG_GRID_SELECTED);

                colorSchema.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY, everyDayColor);
                colorSchema.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED, everyDaySelectedColor);
            } else {
                colorSchema.setToDefault(ComponentColorDefaults.Key.FG_GRID_TODAY);
                colorSchema.setToDefault(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED);
            }
    }

}
