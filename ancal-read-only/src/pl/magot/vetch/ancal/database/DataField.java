
package pl.magot.vetch.ancal.database;


import java.util.Calendar;

import android.content.ContentValues;
import android.util.Log;


public class DataField
{
	//types
	public static enum Type { INT, TEXT, BOOL };

	//fields
	private Calendar dateOut = Calendar.getInstance();
	
	//fields
	private DataRow dataRow;
	private ContentValues values = new ContentValues();
	
	//fields
	private int index = 0;
	private String sName = "";
	private Type FieldType = Type.INT;
	private boolean bCanBeNull = true;
	private boolean bPrimaryKey = false;
	
	
	//methods
	public DataField(int index, String sName, Type FieldType, boolean bCanBeNull, boolean bPrimaryKey)
	{
		this.index = index;
		this.sName = sName;
		this.FieldType = FieldType;
		this.bCanBeNull = bCanBeNull;
		this.bPrimaryKey = bPrimaryKey;
	}
	
	public String GetColumnDefinition()
	{
		String s = sName + " " + GetSqlType(FieldType);
		if (bPrimaryKey)
			s += " PRIMARY KEY";
		if (!bCanBeNull)
			s += " NOT NULL";
		return s;
	}
	
	public Type GetType()
	{
		return FieldType;
	}
	
	public int GetIndex()
	{
		return index;
	}
	
	public String GetSqlType(Type value)
	{
		switch (value)
		{
			case INT: return "INTEGER";
			case TEXT: return "TEXT";
			case BOOL: return "INTEGER";
		}
		return "TEXT";
	}
	
	public void SetParentRow(DataRow dataRow)
	{
		this.dataRow = dataRow;
		this.values = this.dataRow.GetContentValues();
	}
	
	public String GetName()
	{
		return sName;
	}
	
	//getters
	public String asString()
	{
		try
		{
			return SimpleCrypto.decrypt(values.getAsString(sName));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Decrypt Failed";
		
	}
	
	public long asLong()
	{
		Log.d("Toto","Dur : "+values+" Start : ");
		Log.d("Toto","Dur : "+sName+" Start : ");
		return values.getAsLong(sName);
	}
	
	public boolean asBoolean()
	{
		return (values.getAsLong(sName) == 1);
	}
	
	public boolean isNull()
	{
		return (values.get(sName) == null);		
	}
	
	public Calendar asCalendar()
	{
		dateOut.setTimeInMillis(values.getAsLong(sName));
		return dateOut; 
	}

	//setters
	public void set(String value)
	{	
		try
		{
			values.put(sName, SimpleCrypto.encrypt(value));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			values.put(sName, "Encryption Failed!");
			e.printStackTrace();
			
		}
		
	}
	
	public void set(long value)
	{		
		values.put(sName, value);
	}	

	public void set(boolean value)
	{
		int i = (value)?1:0;
		values.put(sName, i);		
	}	

	public void set(Calendar value)
	{
		values.put(sName, value.getTimeInMillis());
	}

	public void setNull()
	{
		values.put(sName, (String)null);
	}
	
}
