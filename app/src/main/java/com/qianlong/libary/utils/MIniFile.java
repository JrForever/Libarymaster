package com.qianlong.libary.utils;

import android.content.Context;
import android.os.Environment;


import com.qianlong.libary.logger.QlgLog;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
/* 
	MIniFile saveIni = new MIniFile();
	saveIni.setFilePath(getApplicationContext(), "config.ini");
	saveIni.WriteString("server", "addr1", "192.168.01.1:8080");
	saveIni.WriteString("server", "addr2", "192.168.0  ");
	saveIni.WriteString("server", "date", "20111225");
	saveIni.WriteString("server", "addr3", "192.168.0.3:80");
	saveIni.Write();
	
	String addr1 = saveIni.ReadString("server", "addr1", "");
	String addr2 = saveIni.ReadString("server", "addr2", "");
*/

public final class MIniFile {
	public	static final int	MAX_LINE_NUM	= 512;
	//char	m_szFileName[FILEPATH_LEN];
	public String	m_FileData;
	StringBuffer	m_temp;
	//long	m_lFileSize;
	//long	m_lMaxSize;
	//BOOL	m_bWriteFlag;
	int[]	m_LineData;
	int		m_MaxLineNum;
	int		m_UseLineNum;
	
	Context mContext;
	String	mFilePath;
	
	public MIniFile()
	{
		m_FileData		= "";
		m_temp			= null;
		m_LineData		= null;
		m_MaxLineNum	= 0;
		m_UseLineNum	= 0;
	}
	
	public MIniFile(Context context, String file)
	{
		m_FileData		= "";
		m_temp			= null;
		m_LineData		= null;
		m_MaxLineNum	= 0;
		m_UseLineNum	= 0;

		mContext = context;
		String fileData = getFromAssets(context, file);
        setData(fileData);
	}
	
	public void setFilePath(Context context, String path) {
		
		mContext = context;
		mFilePath = path; //context.getFilesDir().getAbsolutePath() + "/" + path;
		
		final String ENCODING = "UTF-8";
        try {   
            InputStream in = mContext.openFileInput(mFilePath);   

            int lenght = in.available();   

            byte[]  buffer = new byte[lenght];   

            in.read(buffer); 
            
            String result = new String(buffer,ENCODING);
            setData(result);
            
            in.close();
        } catch (Exception e) {   
        	setData("");
			QlgLog.e(e.toString());
        }   
	}
		
	public	void	setData(String data)
	{
		m_temp			= new StringBuffer(1024);
		m_FileData		= "";

		m_LineData	= new int[MAX_LINE_NUM];	
		m_MaxLineNum	= MAX_LINE_NUM;
		m_UseLineNum	= 0;

		int ch;
		
		StringBuffer	t = new StringBuffer(1024);

		int len = data.length();
		for (int i = 0; i < len; i++)
		{
			ch	= data.charAt(i);
			if (ch == 0xfeff)
			{
				continue;
			}
			else if (ch == 0x0d)
			{
				AddLine(t);
				t.delete(0, t.length());
			}
			else if (ch != 0x0a)
			{
				t.append((char)ch);
			}
		}
		
		if (t.length() > 0)
		{
			AddLine(t);
		}
		
		m_FileData	= m_temp.toString();
		m_temp		= null;
		
	}
	
	boolean	AddLine(StringBuffer t)
	{
		String str = t.toString();
		//mobilestock.writelog("AddLine:" + str);
		int end = str.length();

		if (end == 0)
		{
			return false;
		}

		int start		= 0;
		int lineflag	= end<<1;
		if (str.charAt(0) == '[')
		{
			int index = str.indexOf(']');
			if (index != -1)
			{
				end		= index;
				if (index <= 1)
				{
					//end	= index;		
					return false;
				}
				lineflag	= ((index-1)<<1) + 1;
				start		= 1;
			}
		}

		//mobilestock.writelog("append start:" + start + " end:" + end);

		m_temp.append(str.substring(start, end));
		if (m_UseLineNum >= m_MaxLineNum)
		{
			int[] temp = m_LineData;
			m_LineData		= new int[m_MaxLineNum+128];
			m_MaxLineNum	+= 128;
			System.arraycopy(temp, 0, m_LineData, 0, m_UseLineNum);
			temp		= null;
		}
		m_LineData[m_UseLineNum]	= lineflag;
		m_UseLineNum++;
		return true;
	}
	
	private boolean	AddLine(StringBuffer data, String line)
	{
		int end = line.length();
		if (end == 0) {
			return false;
		}
		
		int start		= 0;
		int lineflag	= end<<1;
		if (line.charAt(0) == '[')
		{
			int index = line.indexOf(']');
			if (index != -1)
			{
				end		= index;
				if (index <= 1)
				{
					//end	= index;		
					return false;
				}
				lineflag	= ((index-1)<<1) + 1;
				start		= 1;
			}
		}
		
		data.append(line.substring(start, end));
		if (m_UseLineNum >= m_MaxLineNum)
		{
			int[] temp = m_LineData;
			m_LineData		= new int[m_MaxLineNum+128];
			m_MaxLineNum	+= 128;
			System.arraycopy(temp, 0, m_LineData, 0, m_UseLineNum);
			temp		= null;
		}
		m_LineData[m_UseLineNum]	= lineflag;
		m_UseLineNum++;
		return true;
	}
	
	private boolean	InsertLine(StringBuffer data, String line, int pos, int linepos)
	{
		int end = line.length();
		if (end == 0) {
			return false;
		}
		
		int start		= 0;
		int lineflag	= end<<1;
		if (line.charAt(0) == '[')
		{
			int index = line.indexOf(']');
			if (index != -1)
			{
				end		= index;
				if (index <= 1)
				{
					//end	= index;		
					return false;
				}
				lineflag	= ((index-1)<<1) + 1;
				start		= 1;
			}
		}
		data.insert(pos, line.substring(start, end));
		
		if (m_UseLineNum >= m_MaxLineNum)
		{
			int[] temp = m_LineData;
			m_LineData		= new int[m_MaxLineNum+128];
			m_MaxLineNum	+= 128;
			System.arraycopy(temp, 0, m_LineData, 0, m_UseLineNum);
			temp		= null;
		}
		
		int[] t = new int[m_MaxLineNum];
		System.arraycopy(m_LineData, 0, t, 0, linepos);
		t[linepos] = lineflag;
		System.arraycopy(m_LineData, linepos, t, linepos+1, m_UseLineNum-linepos);
		m_UseLineNum++;
		
		System.arraycopy(t, 0, m_LineData, 0, m_UseLineNum);
		t = null;
		return true;
	}
	
	private boolean	ModifyLine(StringBuffer data, String line, int pos, int linepos)
	{
		int end = line.length();
		if (end == 0) {
			return false;
		}
		
		int start		= 0;
		int lineflag	= end<<1;
		if (line.charAt(0) == '[')
		{
			int index = line.indexOf(']');
			if (index != -1)
			{
				end		= index;
				if (index <= 1)
				{
					//end	= index;		
					return false;
				}
				lineflag	= ((index-1)<<1) + 1;
				start		= 1;
			}
		}
		int linelen = m_LineData[linepos] >> 1;
		data.delete(pos, pos+linelen);
		data.insert(pos, line.substring(start, end));
		
		m_LineData[linepos]	= lineflag;
		return true;
	}
	
	public 	String	ReadString(String Section, String Name, String Default)
	{
		//mobilestock.writelog("ReadString [" + Section + "][" + Name + "]");

		if (Section==null || Name==null || Section.length() == 0 || Name.length() == 0)
		{
			return "";
		}

		int i;
		int pos = 0;
		for (i = 0; i < m_UseLineNum; i++)
		{
			int lineflag = m_LineData[i];
			int linelen = lineflag >> 1;
			
			//String	str = m_FileData.substring(pos, pos + linelen);
			//mobilestock.writelog("pos:" + pos + "i:" + i + "lineflag:" + lineflag + " " + str);
			if ((lineflag & 1) != 0)
			{
				if (STD.strcmp(Section, m_FileData, pos, linelen) == 0)
				{
					i++;
					pos		+= linelen;
					break;
				}
			}

			pos		+= linelen;
		}

		for (; i < m_UseLineNum; i++)
		{
			int lineflag = m_LineData[i];
			int linelen = lineflag >> 1;
			if ((lineflag & 1) != 0)	
			{
				break;
			}

			int index = STD.strchr(m_FileData, pos, linelen, '=');

			if (index != -1)
			{
				if (STD.strcmp(Name, m_FileData, pos, index-pos) == 0)
				{
					return m_FileData.substring(index+1, pos + linelen);
				}
			}
			pos		+= linelen;
		}

		return Default;
	}

	public int		ReadInt(String Section, String Name, int Default)
	{
		String str = ReadString(Section, Name, "");
		
		if (str.equals(""))
		{
			return Default;
		}
		else
		{
			return Integer.parseInt(str);
		}
	}
	
	public boolean WriteString(String Section, String Name, String Value)
	{
		if (Section==null || Name==null || Section.length() == 0 || Name.length() == 0)
		{
			return false;
		}

		int i;
		int pos = 0;
		for (i = 0; i < m_UseLineNum; i++)
		{
			int lineflag = m_LineData[i];
			int linelen = lineflag >> 1;
			
			if ((lineflag & 1) != 0)
			{
				if (STD.strcmp(Section, m_FileData, pos, linelen) == 0)	//�ҵ�
				{
					i++;
					pos	+= linelen;
					break;
				}
			}
			pos	+= linelen;
		}

		String line = Name + "=" + Value;
		
		m_temp = new StringBuffer(1024);
		m_temp.append(m_FileData);

		if ( i >= m_UseLineNum)
		{
			AddLine(m_temp, "["+Section+"]");
			AddLine(m_temp, line);
			
			m_FileData = m_temp.toString();
			m_temp	= null;
			return true;
		}

		for (; i < m_UseLineNum; i++)
		{
			int lineflag = m_LineData[i];
			int linelen = lineflag >> 1;
			if ((lineflag & 1) != 0)
			{
				InsertLine(m_temp, line, pos, i);
				
				m_FileData = m_temp.toString();
				m_temp	= null;
				return true;
			}

			int index = STD.strchr(m_FileData, pos, linelen, '=');
			if (index != -1)
			{
				if (STD.strcmp(Name, m_FileData, pos, index-pos) == 0)
				{
					ModifyLine(m_temp, line, pos, i);
					
					m_FileData = m_temp.toString();
					m_temp	= null;
					return true;
				}
			}
			pos	+= linelen;
		}

		AddLine(m_temp, line);
		
		m_FileData = m_temp.toString();
		m_temp	= null;
		
		return true;
	}
	
	public boolean WriteInt(String Section, String Name, int Value)
	{
		return WriteString(Section, Name, ""+Value);
	}
	
	//
	public void Write() {
		try {
			FileOutputStream fos = mContext.openFileOutput(mFilePath, Context.MODE_PRIVATE);
			DataOutputStream dos=new DataOutputStream(fos);
			
			int pos = 0;
			String data = "";
			for(int i=0; i<m_UseLineNum; i++) {
				int linelen = m_LineData[i] >> 1;
				if ((m_LineData[i] & 1) != 0) {
					data += "[" + m_FileData.substring(pos, pos+linelen) + "]";
				} else {
					data += m_FileData.substring(pos, pos+linelen);
				}
				data += "\r\n";
				pos += linelen;
			}
			
			byte[] dataBytes = data.getBytes("UTF-8");
			dos.write(dataBytes);
			dos.flush();
			dos.close();
			fos.close();
			
			boolean bTest = false;
			if (bTest)
			{
		        File file = new File(Environment.getExternalStorageDirectory() + "/" + "server.ini");
		        if (!file.exists()) {
		            try {
		                file.createNewFile();
		            } catch (IOException e) {
						QlgLog.e(e.toString());
		            }
		        }

		        OutputStreamWriter osw = null;
		        try {
		            osw = new OutputStreamWriter(new FileOutputStream(
		                    file));
					osw.write(data);
					osw.close();
		        } catch (FileNotFoundException e1) {
					QlgLog.e(e1.toString());
		        } catch (IOException e) {
					QlgLog.e(e.toString());
		        }
			}
		} catch (FileNotFoundException e) {
			//
			QlgLog.e(e.toString());
		}catch (IOException e) {
			//
			QlgLog.e(e.toString());
		}
	}
	
    public String getFromAssets(Context context, String fileName){
    	final String ENCODING = "UTF-8";
        String result = "";   
            try {   
                InputStream in = context.getResources().getAssets().open(fileName);   
                int lenght = in.available();   
                byte[]  buffer = new byte[lenght];
                in.read(buffer);   
                result = new String(buffer, ENCODING);
                in.close();
            } catch (Exception e) {
				QlgLog.e(e.toString());
            }   
            return result;
    }

	public boolean Readboolean(String Section, String Name, boolean Default) {
		String str = ReadString(Section, Name, "");
		if (str.equals("")) {
			return Default;
		} else {
			if (Integer.parseInt(str) == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

}
