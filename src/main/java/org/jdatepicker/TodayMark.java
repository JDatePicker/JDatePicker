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

/**
 *
 * @author Ralf Buenemann
 */
public class TodayMark {
    private static boolean showTodayBorder;

    private TodayMark() {}
    
    public static final boolean isBorder() {
        return showTodayBorder;
    }

    public static final void setBorder(boolean showTodayBorder) {
        TodayMark.showTodayBorder = showTodayBorder;
    }
    
    public static void setRedNumber(boolean isRedNumber){
        ComponentColorDefaults colors = ComponentColorDefaults.getInstance();
        if(isRedNumber){
            colors.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY, Color.RED);
            colors.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED, Color.RED);
        }else{
            Color everyDay = colors.getColor(ComponentColorDefaults.Key
                    .FG_GRID_THIS_MONTH);
            Color everyDaySelected = colors.getColor(ComponentColorDefaults.Key
                    .FG_GRID_SELECTED);
            colors.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY, everyDay);
            colors.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED, everyDaySelected);
        }
        
    }
    
}
