package com.f0nt.android.f0nt01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.blazeinno.android.ui.FontView;
import com.f0nt.android.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FontViewer extends Activity {
	private Context				m_Context;
	private ListView			m_FontListView;
	private Button				m_UninstallButton;
	private ArrayList<Font>		m_FontList;   
	private FontsAdapter		m_FontAdapter; 

	private class Font {
	    public String			m_Name;
	    public Typeface			m_TypeFace;
	}

	class FontComparator implements Comparator<Font> {
	    public int compare(Font font1, Font font2) {
	    	return font1.m_Name.compareToIgnoreCase(font2.m_Name);
	    }
	}

	private class FontsAdapter extends ArrayAdapter<Font> {
        public FontsAdapter(Context context, int textViewResourceId, ArrayList<Font> fontList) {
        	super(context, textViewResourceId, fontList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
            	LayoutInflater layoutInflater	= (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	convertView	= layoutInflater.inflate(R.layout.font_row_view, null);
            }

   			try {
   				Font font	= m_FontList.get(position);

   				if (font != null) {
   	   				FontView fontView	= (FontView)convertView.findViewById(R.id.font_view);
   	   				fontView.setTypeFace(font.m_TypeFace);
   	   				fontView.setName(font.m_Name);
   	   				fontView.setNameColor(Color.rgb(0, 160, 255));
                }
			} catch (Exception e) {
			}

            return convertView;
        }
	} 

    private void addFont(String name, Typeface typeFace) {
    	Font			font;

    	font			= new Font();
    	font.m_Name		= name;
    	font.m_TypeFace	= typeFace;

    	m_FontList.add(font);
    } 

    private void listFonts() {
		try {
			String[]		fonts;

	        AssetManager	assetManager	= m_Context.getAssets();
	        fonts							= assetManager.list("fonts");
			Arrays.sort(fonts);

			for (int i = 0; i < fonts.length; i++) {
				String		fileName	= fonts[i];
				Typeface	typeFace;

				try {
					typeFace	= Typeface.createFromAsset(m_Context.getAssets(), "fonts/" + fileName);
				} catch (Exception e) {
					typeFace	= Typeface.DEFAULT;
				}

				addFont(fileName, typeFace);
			}

			m_FontAdapter.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font_viewer);

		m_Context			= getBaseContext();
		m_FontList			= new ArrayList<Font>();
		m_FontListView		= (ListView)findViewById(R.id.fontListView); 
		m_UninstallButton	= (Button)findViewById(R.id.uninstallButton);

        m_FontAdapter		= new FontsAdapter(this, R.layout.font_row_view, m_FontList);  

        m_FontListView.setAdapter(m_FontAdapter);

        m_UninstallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        Uri packageURI = Uri.parse("package:" + getPackageName());
		        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		        startActivity(uninstallIntent);
			}
		});

        listFonts();
	}
}
