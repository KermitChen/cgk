package com.dyst.utils;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ReadConfig
{
  public static final String propertiesFile = "parameter";
  private static ResourceBundle resources = null;

  public static String getPropertiesValue(String strPropertiesFile, String strItem)
  {
    String strItemValue = "";
    ResourceBundle resources1 = null;
    try {
      resources1 = ResourceBundle.getBundle("config/"+strPropertiesFile);
      strItemValue = resources1.getString(strItem);
    }
    catch (MissingResourceException localMissingResourceException)
    {
    }

    return strItemValue;
  }

  public static String getPropertiesValue(String strItem)
  {
    String msg = "";
    try {
      if (resources == null) {
        initResources();
      }
      msg = resources.getString(strItem);
    }
    catch (Exception localException) {
    }
    return msg;
  }

  private static void initResources()
  {
    try
    {
      resources = ResourceBundle.getBundle("parameter");
    } catch (MissingResourceException e) {
      throw new Error(e.getClassName());
    }
  }
}
