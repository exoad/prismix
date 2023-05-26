// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.stl;

import java.awt.color.ColorSpace;

public final class extend_stl_Colors
{
  private extend_stl_Colors()
  {
  }

  public static String awt_colorspace_NameMatch(ColorSpace e)
  {
    switch (e.getType()) {
      case 0:
        return "XYZ";
      case 1:
        return "Lab";
      case 2:
        return "Luv";
      case 3:
        return "YCbCr";
      case 4:
        return "Yxy";
      case 5:
        return "RGB";
      case 6:
        return "GRAY";
      case 7:
        return "HSV";
      case 8:
        return "HLS";
      case 9:
        return "CMYK";
      case 11:
        return "CMY";
      case 12:
        return "2CLR";
      case 13:
        return "3CLR";
      case 14:
        return "4CLR";
      case 15:
        return "5CLR";
      case 16:
        return "6CLR";
      case 17:
        return "7CLR";
      case 18:
        return "8CLR";
      case 19:
        return "9CLR";
      case 20:
        return "ACLR";
      case 21:
        return "BCLR";
      case 22:
        return "CCLR";
      case 23:
        return "DCLR";
      default:
        return "UNKNOWN";
    }
  }
}