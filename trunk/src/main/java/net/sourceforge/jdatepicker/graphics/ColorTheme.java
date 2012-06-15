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
package net.sourceforge.jdatepicker.graphics;

import java.awt.Color;
import java.awt.SystemColor;

/**
 * Created on 15 June 2012
 * 
 * @author Juan Heyns
 */
public abstract class ColorTheme {
	
	public Color fgMonthSelector() { return SystemColor.activeCaptionText; }
	public Color bgMonthSelector() { return SystemColor.activeCaption; }

	public Color fgGridHeader() { return new Color(10, 36, 106); }
	public Color bgGridHeader() { return Color.LIGHT_GRAY; }

	public Color fgGridThisMonth() { return Color.BLACK; }
	public Color fgGridOtherMonth() { return Color.LIGHT_GRAY; }
	public Color fgGridToday() { return Color.RED; }
	public Color bgGrid() { return Color.WHITE; }

	public Color fgGridSelected() { return Color.WHITE; }
	public Color bgGridSelected() { return new Color(10, 36, 106); }

	public Color fgGridTodaySelected() { return Color.RED; }
	public Color bgGridTodaySelected() { return new Color(10, 36, 106); }

	public Color fgTodaySelector() { return Color.BLACK; }
	public Color bgTodaySelector() { return Color.WHITE; }
	
}
